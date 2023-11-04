package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName : {}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path + fileNameWithExtension;
        logger.info("full image path {}", fullPathWithFileName);

        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            logger.info("File extension is {}", extension);
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Use try-with-resources to ensure the input stream is closed after copying.
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(fullPathWithFileName));
            } catch (IOException e) {

                e.printStackTrace();
                throw e;
            }

            return fileNameWithExtension;
        } else {
            throw new BadApiRequestException("File with this " + extension + " is not allowed!!");
        }
    }
@Override
public InputStream getResource(String path, String name) throws FileNotFoundException {
    String fullPath = path + File.separator + name;
    InputStream inputStream = new FileInputStream(fullPath);
    return inputStream;
}
}

