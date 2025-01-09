package com.cubicimagegenerator.domain.port;

import com.cubicimagegenerator.domain.model.Image;
import com.cubicimagegenerator.domain.vo.ImageDescription;

public interface ImageAnalysisPort {
    ImageDescription analyzeImage(Image image);
} 