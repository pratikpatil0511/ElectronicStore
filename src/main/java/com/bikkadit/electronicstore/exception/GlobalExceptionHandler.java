package com.bikkadit.electronicstore.exception;


import com.bikkadit.electronicstore.helper.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        logger.info("ResourceNotFoundExceptionHandler invoked");
        ApiResponse apiResponse = ApiResponse
                                 .builder()
                                 .message(ex.getMessage())
                                 .success(true)
                                 .status(HttpStatus.NOT_FOUND)
                                 .build();

        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex)
    {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> map=new HashMap<>();
        allErrors.stream().forEach(error->{
            String message = error.getDefaultMessage();
            String field = ((FieldError) error).getField();
            map.put(field,message);
        });

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiException.class)
    public ResponseEntity<ApiResponse> BadApiExceptionHandler(BadApiException ex)
    {
        logger.info("BadApiExceptionHandler invoked");
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
