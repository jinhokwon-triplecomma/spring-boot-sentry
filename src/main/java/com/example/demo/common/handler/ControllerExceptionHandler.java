package com.example.demo.common.handler;

import com.example.demo.common.dto.response.BaseResponse;
import com.example.demo.common.exception.BadRequestException;
import io.sentry.Sentry;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {
            BadRequestException.class
    })
    public ResponseEntity<BaseResponse> badRequestException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(BaseResponse.badRequest(ex.getMessage()));
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            AuthenticationException.class
    })
    public ResponseEntity<BaseResponse> illegalArgumentException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(BaseResponse.badRequest());
    }

    @ExceptionHandler(value = {
            RuntimeException.class
    })
    public ResponseEntity<BaseResponse> runtimeException(Exception ex, WebRequest request) {
        Sentry.captureException(ex);
        return new ResponseEntity<>(BaseResponse.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception exception,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        Sentry.captureException(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
