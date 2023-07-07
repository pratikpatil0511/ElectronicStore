package com.bikkadit.electronicstore.exception;


import com.bikkadit.electronicstore.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse
                                 .builder()
                                 .message(ex.getMessage())
                                 .success(true)
                                 .status(HttpStatus.NOT_FOUND)
                                 .build();

        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
}
