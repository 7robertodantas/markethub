package br.ufrn.imd.markethub.service.checkout.advice;

import br.ufrn.imd.markethub.service.checkout.dto.ErrorDto;
import br.ufrn.imd.markethub.service.checkout.exception.ServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorDto> handleException(ServerException e) {
        return ResponseEntity
                .status(e.getError().getCode())
                .body(e.getError());
    }

}
