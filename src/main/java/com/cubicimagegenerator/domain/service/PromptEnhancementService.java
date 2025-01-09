package com.cubicimagegenerator.domain.service;

import com.cubicimagegenerator.domain.vo.ImageDescription;
import com.cubicimagegenerator.domain.vo.Prompt;
import org.springframework.stereotype.Service;

@Service
public class PromptEnhancementService {
    private static final String CUBISM_STYLE = "2D in the style of cubism art movement";
    private static final String QUALITY_PARAMS = "high quality, detailed";

    public Prompt enhance(ImageDescription description) {
        String enhancedContent = String.format("%s with featuring geometric shapes, %s, %s",
                CUBISM_STYLE, QUALITY_PARAMS, description.getContent());
        return new Prompt(enhancedContent);
    }

    public Prompt enhance(Prompt prompt) {
        String enhancedContent = String.format("%s with featuring geometric shapes, %s, %s",
                CUBISM_STYLE, QUALITY_PARAMS, prompt.getContent());
        return new Prompt(enhancedContent);
    }
} 