package com.cubicimagegenerator.infrastructure.adapter;

import com.cubicimagegenerator.domain.model.Image;
import com.cubicimagegenerator.domain.port.ImageAnalysisPort;
import com.cubicimagegenerator.domain.vo.ImageDescription;
import com.cubicimagegenerator.infrastructure.client.HuggingFaceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
@RequiredArgsConstructor
public class BlipImageAnalysisAdapter implements ImageAnalysisPort {
    private static final String BLIP_URL = "https://api-inference.huggingface.co/models/Salesforce/blip-image-captioning-large";
    private final HuggingFaceClient client;

    @Override
    public ImageDescription analyzeImage(Image image) {
        String base64Image = Base64.getEncoder().encodeToString(image.getContent());
        String description = client.analyzeImage(base64Image);
        return new ImageDescription(description);
    }
} 