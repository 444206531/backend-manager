package com.example.adminsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", "")); // Basic path extraction

        // Log the exception (e.g., using SLF4J logger)
        // import org.slf4j.Logger;
        // import org.slf4j.LoggerFactory;
        // private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
        // logger.error("Error occurred: ", ex);


        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Example: Handle a specific custom exception (if you create one later)
    // @ExceptionHandler(ResourceNotFoundException.class) // Assuming you create such an exception
    // public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    //     Map<String, Object> body = new LinkedHashMap<>();
    //     body.put("timestamp", new Date());
    //     body.put("status", HttpStatus.NOT_FOUND.value());
    //     body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
    //     body.put("message", ex.getMessage());
    //     body.put("path", request.getDescription(false).replace("uri=", ""));
    //
    //     return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    // }

    // You can add more @ExceptionHandler methods for other specific exceptions
    // like MethodArgumentNotValidException for validation errors, etc.
}
