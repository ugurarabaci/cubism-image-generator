package com.cubicimagegenerator.domain.vo;

import lombok.Getter;

@Getter
public class ImageDescription {
    private final String content;

    public ImageDescription(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.content = content;
    }
} 