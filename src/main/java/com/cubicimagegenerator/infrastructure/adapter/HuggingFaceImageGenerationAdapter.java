package com.cubicimagegenerator.infrastructure.adapter;

import com.cubicimagegenerator.domain.model.Image;
import com.cubicimagegenerator.domain.model.ImageMetadata;
import com.cubicimagegenerator.domain.port.ImageGenerationPort;
import com.cubicimagegenerator.domain.vo.Prompt;
import com.cubicimagegenerator.infrastructure.client.HuggingFaceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HuggingFaceImageGenerationAdapter implements ImageGenerationPort {
    private final HuggingFaceClient client;

    @Override
    public Image generateImage(Prompt prompt) {
        byte[] imageContent = client.generateImage(prompt.getContent());
        ImageMetadata metadata = new ImageMetadata(
                imageContent.length,
                "JPEG",
                1024,
                1024
        );
        return new Image(imageContent, metadata);
    }
} 