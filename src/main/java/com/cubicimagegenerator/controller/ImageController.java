package com.cubicimagegenerator.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cubicimagegenerator.service.ImageService;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateImage(@RequestBody Map<String, String> request) {
        byte[] imageBytes = imageService.generateCubicImage(request.get("prompt"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/transform")
    public ResponseEntity<byte[]> transformImage(@RequestParam("image") MultipartFile file) {
        try {
            if (file.getSize() > 1024 * 1024 * 4) {
                throw new RuntimeException("Image size too large. Maximum size is 4MB");
            }
            
            byte[] imageBytes = file.getBytes();
            byte[] transformedImage = imageService.transformImage(imageBytes);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"transformed.jpg\"")
                    .body(transformedImage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to transform image: " + e.getMessage());
        }
    }

}
