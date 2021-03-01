package com.dizquestudios.evertectest.apps.debts.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.dizquestudios.evertectest.apps.debts.api.ParameterController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Sebastian
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
            public record ResponseError(Integer status, String message, String path) {

    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseError> handleException(HttpServletRequest req, Exception ex) {
        String error = String.format("Request: '%s' | Error: '%s'", req.getRequestURL(),
                ex.getCause().getMessage());
        Logger.getLogger(ParameterController.class.getName()).log(Level.SEVERE, error, ex);

        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST.value(),
                ex.getCause().getMessage(), req.getRequestURL().toString());

        return ResponseEntity.badRequest().body(response);
    }
}
