package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.exception.BadApiException;
import com.bikkadit.electronicstore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName: {}",originalFilename);
        String filename= UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filenameWithExtension=filename+extension;
        String fullPathWithFileName=path+filenameWithExtension;
                                      //don't need to use File.separator between bcz we have used in properties file
        if(extension.equalsIgnoreCase(".png")||
                extension.equalsIgnoreCase(".jpg")||
                extension.equalsIgnoreCase("jpeg"))
        {
            //file save
            File folder=new File(path);
            if(!folder.exists())
            {
                //create folder
                folder.mkdirs();
            }
            //upload file
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return filenameWithExtension;
        }
        else
        {
            throw new BadApiException("File with this "+extension+" not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path + File.separator + name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
