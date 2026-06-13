package com.zenflow.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String code, String message, String requestId) {}

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex,
                                        HttpServletRequest req) {
        return new ErrorResponse("NOT_FOUND", ex.getMessage(), getRequestId(req));
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleExpiredToken(TokenExpiredException ex,
                                            HttpServletRequest req) {
        return new ErrorResponse("TOKEN_EXPIRED",
            "Session expired. Re-authenticate.", getRequestId(req));
    }

    @ExceptionHandler(DriveUploadException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse handleDriveError(DriveUploadException ex,
                                          HttpServletRequest req) {
        return new ErrorResponse("DRIVE_ERROR",
            "Cloud sync unavailable. Data is safe locally.", getRequestId(req));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception on request {}", getRequestId(req), ex);
        return new ErrorResponse("INTERNAL_ERROR", "Something went wrong.", getRequestId(req));
    }

    private String getRequestId(HttpServletRequest req) {
        return req.getHeader("X-Request-ID") != null
            ? req.getHeader("X-Request-ID")
            : UUID.randomUUID().toString();
    }
}
