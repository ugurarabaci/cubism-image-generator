package com.cubicimagegenerator.controller;

import com.cubicimagegenerator.application.ImageTransformationUseCase;
import com.cubicimagegenerator.domain.model.Image;
import com.cubicimagegenerator.domain.model.ImageMetadata;
import com.cubicimagegenerator.domain.vo.Prompt;
import com.cubicimagegenerator.exception.ImageProcessingException;
import com.cubicimagegenerator.validation.ImageValidator;
import com.cubicimagegenerator.domain.service.ImageResizingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageTransformationUseCase imageTransformationUseCase;
    private final ImageValidator imageValidator;
    private final ImageResizingService imageResizingService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateImage(@RequestBody Map<String, String> request) {
        log.info("Generating image from prompt");
        String promptText = request.get("prompt");
        if (promptText == null || promptText.trim().isEmpty()) {
            throw new ImageProcessingException("Prompt cannot be empty", null);
        }

        Image generatedImage = imageTransformationUseCase.generateImage(new Prompt(promptText));
        
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(generatedImage.getContent());
    }

    @PostMapping("/transform")
    public ResponseEntity<byte[]> transformImage(@RequestParam("image") MultipartFile file) {
        log.info("Transforming image: {}", file.getOriginalFilename());
        imageValidator.validate(file);
        
        Image sourceImage = createImageFromMultipartFile(file);
        sourceImage = imageResizingService.resize(sourceImage);
        Image transformedImage = imageTransformationUseCase.transformImage(sourceImage);
        
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(transformedImage.getContent());
    }

    private Image createImageFromMultipartFile(MultipartFile file) {
        try {
            ImageMetadata metadata = new ImageMetadata(
                    file.getSize(),
                    file.getContentType(),
                    0,
                    0
            );
            return new Image(file.getBytes(), metadata);
        } catch (Exception e) {
            throw new ImageProcessingException("Failed to process uploaded image", e);
        }
    }
}
