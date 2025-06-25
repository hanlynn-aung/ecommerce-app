package com.app.ecom.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Map;

public class HttpResponse {

    public static ResponseEntity<?> build(ApiResponse apiResponse, HttpStatus status) {
        return new ResponseEntity<>(apiResponse, status);
    }

    public static ResponseEntity<?> created(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                    Instant.now(),
                    HttpStatus.CREATED.value(),
                    message,
                    data
                ),
                HttpStatus.CREATED
        );
    }

    public static ResponseEntity<?> success(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                        Instant.now(),
                        HttpStatus.OK.value(),
                        message,
                        data
                ),
                HttpStatus.OK
        );
    }

    public static ResponseEntity<?> badRequest(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        data
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public static ResponseEntity<?> notFound(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        message,
                        data
                ),
                HttpStatus.NOT_FOUND
        );
    }

    public ResponseEntity<?> unauthorized(String message, Map<String, ?> data) {
        return build(
                new ApiResponse(
                        Instant.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        message,
                        data
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

}
