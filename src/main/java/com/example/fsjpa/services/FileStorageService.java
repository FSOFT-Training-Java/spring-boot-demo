package com.example.fsjpa.services;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.fsjpa.component.FileStorageProperties;
import com.example.fsjpa.exception.FileStorageException;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).
        toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileExtension = fileName.substring(dotIndex);
            }

            String randomFileName = UUID.randomUUID().toString() + fileExtension;

            Path targetLocation = this.fileStorageLocation.resolve(randomFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return randomFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Could not read file: " + fileName);
            }
        } catch (IOException ex) {
            throw new FileStorageException("Could not read file: " + fileName, ex);
        }
    }
    
}
