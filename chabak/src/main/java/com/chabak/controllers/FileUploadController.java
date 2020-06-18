package com.chabak.controllers;


import com.chabak.repositories.FileUploadDao;
import com.chabak.services.FileUploadService;

import com.chabak.vo.FileUpload;
import com.chabak.vo.SmartEditorVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    FileUploadDao fileUploadDao;

//    @SneakyThrows
//    @RequestMapping(value="/single",method=RequestMethod.POST)
//    public String singleUpload(HttpServletRequest req, SmartEditorVo smarteditorVO){
//        String callback = smarteditorVO.getCallback();
//        String callback_func = smarteditorVO.getCallback_func();
//        String file_result = "";
//        String result = "";
//        FileUpload fileUpload = new FileUpload();
//        System.out.println("fileName:"+req.getHeader("file-name"));////////////////////
//
//        MultipartFile multiFile = smarteditorVO.getFiledata();
//        try{
//            if(multiFile != null && multiFile.getSize() > 0 &&
//                    StringUtils.isNotBlank(multiFile.getName())){
//                if(multiFile.getContentType().toLowerCase().startsWith("image/")){
//                    String oriName = multiFile.getName();
//                    String uploadPath = req.getServletContext().getRealPath("/img");
//                    String path = uploadPath + "/smarteditor/";
//                    System.out.println("path:"+path);//must delete
//                    fileUpload.setFilePath(path); ////////////////////////////
//                    File file = new File(path);
//                    if(!file.exists()){
//                        file.mkdirs();
//                    }
//                    String fileName = UUID.randomUUID().toString();
//                    fileUpload.setSaveName(fileName);
//                    smarteditorVO.getFiledata().transferTo(new File(path + fileName));
//                    file_result += "&bNewLine=true&sFileName=" + oriName +
//                            "&sFileURL=/img/smarteditor/" + fileName;
//                }else{
//                    file_result += "&errstr=error";
//                }
//            }else{
//                file_result += "&errstr=error";
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        fileUpload.setUploadKey(21);
//        //fileUploadDao.insertFile(fileUpload);
//        result = "redirect:" + callback +
//                "?callback_func=" + URLEncoder.encode(callback_func,"UTF-8") + file_result;
//        return result;
//
//    }

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
    }

//    @SneakyThrows
//    @RequestMapping(value="/single",method=RequestMethod.POST)
//    public void singleUpload(MultipartHttpServletRequest multiRequest, HttpServletResponse response){
//        try {
//            String callback = multiRequest.getParameter("callback");
//            String callback_func = "?callback_func="+multiRequest.getParameter("callback_func");
//            String return_url = "";
//
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html;charset=UTF-8");
//
//            //fileUpload
//
//            return_url += "&bNewLine=true";
//            return_url += "&sFileName="+multiRequest.getFileNames().next();
//            return_url += "&sFileURL=/upload/"+"date"+"/"+"newName"+ "."+"fileExt";
//            System.out.println("return_url:"+return_url);//////must delete
//            response.sendRedirect(callback+callback_func+return_url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return;
//    }
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public String upload(@RequestParam(required=false) List<MultipartFile> files, Model model) {
        String saveFileName = "";
        for (MultipartFile file : files) {

            saveFileName = fileUploadService.store(file);
            FileUpload fileUpload = new FileUpload();
            fileUpload.setSaveName(saveFileName);

            fileUploadDao.insertFile(fileUpload);
        }
            List<FileUpload> fileList = fileUploadDao.selectFileList();
            model.addAttribute("fileList", fileList);
            return "주소 설정 필요";
        }
    }

