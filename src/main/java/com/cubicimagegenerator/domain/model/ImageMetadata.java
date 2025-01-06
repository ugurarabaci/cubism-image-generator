package com.cubicimagegenerator.domain.model;

import lombok.Getter;

@Getter
public class ImageMetadata {
    private final long size;
    private final String format;
    private final int width;
    private final int height;

    public ImageMetadata(long size, String format, int width, int height) {
        this.size = size;
        this.format = format;
        this.width = width;
        this.height = height;
    }
} 