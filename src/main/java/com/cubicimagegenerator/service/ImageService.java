package com.cubicimagegenerator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-xl-base-1.0";
    private static final String IMAGE_CAPTION_URL = "https://api-inference.huggingface.co/models/Salesforce/blip-image-captioning-large";
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

    public byte[] transformImage(byte[] imageBytes) {
        try {
            // 1. BLIP-2 ile görseli analiz et
            String imageDescription = getImageDescription(imageBytes);
            System.out.println("BLIP-2 Description: " + imageDescription);

            // 2. Açıklamayı kübizm stili ile birleştir
            String enhancedPrompt = String.format(
                "Create a high-quality cubist painting in the style of Pablo Picasso's analytical cubism period, " +
                "showing: %s. " +
                "Break down the forms into geometric shapes, use multiple perspectives simultaneously. " +
                "Fragment the composition with angular planes, bold colors, and overlapping forms. " +
                "Maintain the essence and key elements of the original image while applying cubist interpretation. " +
                "High detail, artistic masterpiece.", 
                imageDescription
            );

            // 3. Yeni görseli üret
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + huggingFaceToken);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("inputs", enhancedPrompt);
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("guidance_scale", 8.5);
            parameters.put("num_inference_steps", 40);
            parameters.put("negative_prompt", "blurry, photorealistic, smooth surfaces, low quality");
            requestBody.put("parameters", parameters);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Debug için prompt'u yazdır
            System.out.println("Generated Prompt: " + enhancedPrompt);

            ResponseEntity<byte[]> response = restTemplate.postForEntity(
                API_URL,
                request,
                byte[].class
            );
            return response.getBody();

        } catch (Exception e) {
            System.out.println("Transform error: " + e.getMessage());
            throw new RuntimeException("Failed to transform image: " + e.getMessage());
        }
    }

    private String getImageDescription(byte[] imageBytes) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + huggingFaceToken);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("inputs", base64Image);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map[]> response = restTemplate.exchange(
                IMAGE_CAPTION_URL,
                HttpMethod.POST,
                request,
                Map[].class
            );

            Map[] results = response.getBody();
            if (results != null && results.length > 0) {
                String description = (String) results[0].get("generated_text");
                System.out.println("BLIP Description: " + description);
                return description;
            }
            
            return "an artistic image";

        } catch (Exception e) {
            System.out.println("Image analysis failed: " + e.getMessage());
            return "an artistic image";
        }
    }
}
