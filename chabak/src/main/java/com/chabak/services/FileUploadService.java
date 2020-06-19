package com.chabak.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

@Service
public class FileUploadService {

    public String store(MultipartFile file){
        String saveFileName="";
        String originalFileName = file.getOriginalFilename();
        Long size = file.getSize();

        saveFileName = getSaveFileName(originalFileName);

        try {
            byte[] data = file.getBytes();
            FileOutputStream fos = new FileOutputStream("/upload/"+saveFileName);
            fos.write(data);

           fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFileName;

    }

    private String getSaveFileName(String fileName) {
        String extName=fileName.substring(fileName.lastIndexOf("."));
        Calendar cal=Calendar.getInstance();
        String fName=String.valueOf(cal.getTimeInMillis());
        String fullName=fName+extName.toLowerCase();
        return fullName;
    }

}
