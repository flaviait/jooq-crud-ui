package de.flaviait.crud.backoffice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackageClasses = {AbstractCRUDBackofficeController.class})
public class BackofficeControllerAdvice extends ResponseEntityExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(BackofficeControllerAdvice.class);

  @ExceptionHandler(AbstractBackofficeController.ResourceNotFoundException.class)
  @ResponseBody
  ResponseEntity<?> handleResourceNotFoundException(Throwable ex) {
    return createErrorResponse(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  ResponseEntity<?> handleException(Throwable ex) {
    log.error("An unexpected error occurred", ex);
    return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /* Helpers */
  private ResponseEntity<Object> createErrorResponse(Throwable ex, HttpStatus status) {
    Map<String, String> map = new HashMap<>();
    map.put("message", ex.getMessage());
    map.put("exception", ex.getClass().getSimpleName());
    return new ResponseEntity<>(map, status);
  }

  class ErrorResponseBody {

    private final String message;
    private final String exception;

    private ErrorResponseBody(String message, String exception) {
      this.message = message;
      this.exception = exception;
    }

  }

}
