package com.portfolio.files.businessLayer.service;

import com.portfolio.files.dataAccessLayer.entity.Resume;
import com.portfolio.files.dataAccessLayer.repository.ResumeRepository;
import com.portfolio.files.utils.exceptions.FileStorageException;
import com.portfolio.files.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final ResumeRepository resumeRepository;

    public FileStorageService(@Value("${file.upload.dir}") String uploadDir,
                             ResumeRepository resumeRepository) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.resumeRepository = resumeRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Resume storeFile(MultipartFile file) {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new FileStorageException("Invalid path sequence in file name: " + originalFileName);
            }

            // Generate unique file name
            String fileExtension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = originalFileName.substring(dotIndex);
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Copy file to the target location
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Save file metadata to database
            Resume resume = new Resume();
            resume.setFileName(originalFileName);
            resume.setFilePath(uniqueFileName);
            resume.setFileSize(file.getSize());
            resume.setContentType(file.getContentType());
            resume.setUploadedAt(LocalDateTime.now());

            return resumeRepository.save(resume);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found: " + fileName);
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException("File not found: " + fileName);
        }
    }

    public Resume getCurrentResume() {
        return resumeRepository.findTopByOrderByUploadedAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No resume found"));
    }
}
