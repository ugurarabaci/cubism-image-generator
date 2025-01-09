package com.cubicimagegenerator.domain.vo;

import lombok.Getter;

@Getter
public class Prompt {
    private final String content;

    public Prompt(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Prompt cannot be empty");
        }
        this.content = content;
    }
} 