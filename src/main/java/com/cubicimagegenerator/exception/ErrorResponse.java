package com.cubicimagegenerator.exception;

import lombok.Value;

@Value
public class ErrorResponse {
    String error;
    String message;
} 