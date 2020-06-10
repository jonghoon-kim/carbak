package com.chabak.controllers;


import com.chabak.repositories.FileUploadDao;
import com.chabak.services.FileUploadService;
import com.chabak.vo.TestFileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    FileUploadDao fileUploadDao;

//    @RequestMapping(value= {"","/","form"},method=RequestMethod.GET)
//    public String form() {
//        return "fileupload/form";
//    }
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public String upload(@RequestParam(required=false) List<MultipartFile> files, Model model) {
        String saveFileName = "";
        for (MultipartFile file : files) {

            saveFileName = fileUploadService.store(file);
            TestFileVo testFileVo = new TestFileVo();
            testFileVo.setSaveName(saveFileName);

            fileUploadDao.insertFile(testFileVo);
        }
            List<TestFileVo> fileList = fileUploadDao.selectFileList();
            model.addAttribute("fileList", fileList);
            return "result";
        }
    }

