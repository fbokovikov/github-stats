package ru.yandex.market.github.pr.stats.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.yandex.market.github.pr.stats.dto.ErrorResponse;

/**
 * @author fbokovikov
 */
@ControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        ResponseEntity<ErrorResponse> response =
                new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }


}
