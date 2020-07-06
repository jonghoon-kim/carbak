package com.chabak.controllers;


import com.chabak.vo.SmartEditorVo;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {



    @SneakyThrows
    @RequestMapping(value="/single",method=RequestMethod.POST)
    public String singleUpload(HttpServletRequest request, SmartEditorVo smarteditorVO){
        String return1=request.getParameter("callback");
        String return2="?callback_func=" + request.getParameter("callback_func");
        String return3="";
        String name = "";
        try {
            if(smarteditorVO.getFiledata() != null && smarteditorVO.getFiledata().getOriginalFilename() != null && !smarteditorVO.getFiledata().getOriginalFilename().equals("")) {
                // 기존 상단 코드를 막고 하단코드를 이용
                name = smarteditorVO.getFiledata().getOriginalFilename().substring(smarteditorVO.getFiledata().getOriginalFilename().lastIndexOf(File.separator)+1);
                String filename_ext = name.substring(name.lastIndexOf(".")+1);
                filename_ext = filename_ext.toLowerCase();
                String[] allow_file = {"jpg","png","bmp","gif"};
                int cnt = 0;
                for(int i=0; i<allow_file.length; i++) {
                    if(filename_ext.equals(allow_file[i])){
                        cnt++;
                    }
                }
                if(cnt == 0) {
                    return3 = "&errstr="+name;
                } else {
                    //파일 기본경로
                    String dftFilePath = request.getSession().getServletContext().getRealPath("/");
                    //파일 기본경로 _ 상세경로
                    String filePath = dftFilePath + "resources"+ File.separator + "editor" + File.separator +"upload" + File.separator;
                    File file = new File(filePath);
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                    String realFileNm = "";
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    String today= formatter.format(new java.util.Date());
                    realFileNm = today+UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));

                    String rlFileNm = filePath + realFileNm;
                    ///////////////// 서버에 파일쓰기 /////////////////
                    smarteditorVO.getFiledata().transferTo(new File(rlFileNm));
                    ///////////////// 서버에 파일쓰기 /////////////////
                    return3 += "&bNewLine=true";
                    return3 += "&sFileName="+ name;
                    return3 += "&sFileURL=/resources/editor/upload/"+realFileNm;


                }
            }else {
                return3 += "&errstr=error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+return1+return2+return3;
    } //스마트 에디터 단일 파일 업로드 기능



    }

