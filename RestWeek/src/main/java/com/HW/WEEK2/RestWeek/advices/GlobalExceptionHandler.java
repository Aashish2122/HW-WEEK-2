package com.HW.WEEK2.RestWeek.advices;

import com.HW.WEEK2.RestWeek.exception.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFoundNotFound(ResourceNotFound exception) {
        APIError apiError = APIError.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();

        return buildErrorResponseEntity(apiError);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleInternalServerError(Exception exception) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<APIResponse<?>> buildErrorResponseEntity(APIError apiError) {
        return new ResponseEntity<>(new APIResponse<>(apiError),apiError.getStatus());
    }
}
