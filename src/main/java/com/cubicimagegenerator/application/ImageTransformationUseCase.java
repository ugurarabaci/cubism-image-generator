package com.cubicimagegenerator.application;

import com.cubicimagegenerator.domain.model.Image;
import com.cubicimagegenerator.domain.port.ImageAnalysisPort;
import com.cubicimagegenerator.domain.port.ImageGenerationPort;
import com.cubicimagegenerator.domain.service.PromptEnhancementService;
import com.cubicimagegenerator.domain.vo.ImageDescription;
import com.cubicimagegenerator.domain.vo.Prompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageTransformationUseCase {
    private final ImageGenerationPort imageGenerator;
    private final ImageAnalysisPort imageAnalyzer;
    private final PromptEnhancementService promptEnhancer;

    public Image generateImage(Prompt prompt) {
        Prompt enhancedPrompt = promptEnhancer.enhance(prompt);
        log.info("Generating image with prompt: {}", enhancedPrompt.getContent());
        return imageGenerator.generateImage(enhancedPrompt);
    }

    public Image transformImage(Image sourceImage) {
        ImageDescription description = imageAnalyzer.analyzeImage(sourceImage);
        log.info("Image analysis result: {}", description.getContent());
        
        Prompt enhancedPrompt = promptEnhancer.enhance(description);
        log.info("Transforming image with prompt: {}", enhancedPrompt.getContent());
        
        return imageGenerator.generateImage(enhancedPrompt);
    }
} 