package com.cubicimagegenerator.validation;

import com.cubicimagegenerator.exception.ImageProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class ImageValidator {
    private static final List<String> SUPPORTED_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/jpg"
    );

    public void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageProcessingException("Image file cannot be empty", null);
        }

        if (!SUPPORTED_TYPES.contains(file.getContentType())) {
            throw new ImageProcessingException(
                    "Unsupported file type: " + file.getContentType() + 
                    ". Supported types are: " + String.join(", ", SUPPORTED_TYPES),
                    null
            );
        }
    }
} 