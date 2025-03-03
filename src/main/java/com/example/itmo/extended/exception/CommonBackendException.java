package com.example.itmo.extended.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@RequiredArgsConstructor
public class CommonBackendException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

}
