package com.example.service;

import com.example.model.GalleryImage;
import com.example.repository.GalleryImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class GalleryService {

    @Autowired
    private GalleryImageRepository galleryImageRepository;

    private final String uploadDir = "uploads/gallery/";

    public List<GalleryImage> getAllImages() {
        return galleryImageRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<GalleryImage> getImagesByCategory(String category) {
        return galleryImageRepository.findByCategory(category);
    }

    public GalleryImage saveImage(GalleryImage galleryImage, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            galleryImage.setImageUrl("/uploads/gallery/" + fileName);
        }
        return galleryImageRepository.save(galleryImage);
    }

    public void deleteImage(Long id) throws IOException {
        GalleryImage image = galleryImageRepository.findById(id).orElse(null);
        if (image != null && image.getImageUrl() != null) {
            Path filePath = Paths.get(image.getImageUrl().substring(1)); // Remove leading slash
            Files.deleteIfExists(filePath);
        }
        galleryImageRepository.deleteById(id);
    }
}
