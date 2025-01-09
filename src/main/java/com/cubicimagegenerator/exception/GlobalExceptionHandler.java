package com.cubicimagegenerator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ImageProcessingException.class)
    public ResponseEntity<ErrorResponse> handleImageProcessingException(ImageProcessingException e) {
        log.error("Image processing error: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Image processing failed", e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("File size exceeded: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("File too large", "Image size should not exceed 10MB"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Internal server error", "An unexpected error occurred"));
    }
} 