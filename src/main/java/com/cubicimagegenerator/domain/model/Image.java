package com.cubicimagegenerator.domain.model;

import lombok.Getter;
import java.util.UUID;

@Getter
public class Image {
    private final String id;
    private final byte[] content;
    private final ImageMetadata metadata;

    public Image(byte[] content, ImageMetadata metadata) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.metadata = metadata;
    }

    public boolean requiresResize() {
        return metadata.getSize() > 4 * 1024 * 1024; // 4MB
    }
} 