package com.bikkadit.electronicstore.exception;


import com.bikkadit.electronicstore.helper.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
