package com.cubicimagegenerator.infrastructure.client;

import com.cubicimagegenerator.config.HuggingFaceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class HuggingFaceClient {
    private static final String STABLE_DIFFUSION_URL = "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-xl-base-1.0";
    private static final String BLIP_URL = "https://api-inference.huggingface.co/models/Salesforce/blip-image-captioning-large";
    
    private final RestTemplate restTemplate;
    private final HuggingFaceProperties properties;

    public HuggingFaceClient(HuggingFaceProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    public byte[] generateImage(String prompt) {
        try {
            HttpEntity<Map<String, Object>> request = buildRequest(prompt);
            ResponseEntity<byte[]> response = restTemplate.postForEntity(STABLE_DIFFUSION_URL, request, byte[].class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Failed to generate image: {}", e.getMessage());
            throw new RuntimeException("Failed to generate image from Hugging Face API", e);
        }
    }

    public String analyzeImage(String base64Image) {
        try {
            HttpEntity<Map<String, Object>> request = buildAnalysisRequest(base64Image);
            ResponseEntity<Map[]> response = restTemplate.exchange(BLIP_URL, HttpMethod.POST, request, Map[].class);
            return extractDescription(response.getBody());
        } catch (Exception e) {
            log.error("Failed to analyze image: {}", e.getMessage());
            throw new RuntimeException("Failed to analyze image from Hugging Face API", e);
        }
    }

    private HttpEntity<Map<String, Object>> buildRequest(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + properties.getApiToken());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", prompt);
        requestBody.put("parameters", getParameters());

        return new HttpEntity<>(requestBody, headers);
    }

    private HttpEntity<Map<String, Object>> buildAnalysisRequest(String base64Image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + properties.getApiToken());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", base64Image);

        return new HttpEntity<>(requestBody, headers);
    }

    private Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("guidance_scale", 8.5);
        parameters.put("num_inference_steps", 40);
        parameters.put("negative_prompt", "blurry, photorealistic, smooth surfaces, low quality");
        return parameters;
    }

    private String extractDescription(Map[] results) {
        if (results != null && results.length > 0) {
            return (String) results[0].get("generated_text");
        }
        return "an artistic image";
    }
} 