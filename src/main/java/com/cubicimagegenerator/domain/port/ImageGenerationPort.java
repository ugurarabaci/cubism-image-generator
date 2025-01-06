package com.cubicimagegenerator.domain.port;

import com.cubicimagegenerator.domain.model.Image;
import com.cubicimagegenerator.domain.vo.Prompt;

public interface ImageGenerationPort {
    Image generateImage(Prompt prompt);
} 