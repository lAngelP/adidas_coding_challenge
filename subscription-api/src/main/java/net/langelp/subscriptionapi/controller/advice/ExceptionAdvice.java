package net.langelp.subscriptionapi.controller.advice;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.langelp.subscriptionapi.exception.EntityAlreadyFoundException;
import net.langelp.subscriptionapi.exception.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ApiResponse(description = "Entity could not be found", responseCode = "404")
    public ResponseEntity<Void> handleUserNotFound(EntityNotFoundException ignoredException){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(value = {EntityAlreadyFoundException.class})
    @ApiResponse(description = "Entity could not be created as it already exists and it is not compatible", responseCode = "400")
    public ResponseEntity<Void> handle(EntityAlreadyFoundException ignoredException){
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ApiResponse(description = "Invalid arguments obtained", responseCode = "400")
    public ResponseEntity<Void> handle(IllegalArgumentException ignoredException){
        return ResponseEntity.badRequest().build();
    }
}
