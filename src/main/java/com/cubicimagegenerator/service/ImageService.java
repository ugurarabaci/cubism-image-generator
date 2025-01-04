package com.cubicimagegenerator.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImageService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api-inference.huggingface.co/models/CompVis/stable-diffusion-v1-4";

    private final String huggingFaceToken;

    public ImageService(@Value("${huggingface.api.token}") String huggingFaceToken) {
        this.huggingFaceToken = huggingFaceToken;
        this.restTemplate = new RestTemplate();
    }

    public byte[] generateCubicImage(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + huggingFaceToken);

        String enhancedPrompt = "2D in the style of cubism art movement with featuring geometric shapes, high quality, detailed, " + prompt;
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", enhancedPrompt);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<byte[]> response = restTemplate.postForEntity(
                API_URL, 
                request, 
                byte[].class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate image: " + e.getMessage());
        }
    }
}
