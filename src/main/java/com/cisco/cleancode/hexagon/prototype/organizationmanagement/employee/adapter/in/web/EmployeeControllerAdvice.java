package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeNotFoundException;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/** The type Advice. */
@ControllerAdvice
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j()
class EmployeeControllerAdvice {

  /** The Message source. */
  private final MessageSource messageSource;

  /**
   * Handle employee not found exception error details dto.
   *
   * @param exception the exception
   * @return the error details dto
   */
  @ExceptionHandler(EmployeeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  ErrorDetailsDto handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
    log.info("handling {}", exception.getClass().getName());
    return mapExceptionToErrorDetailsDto(exception);
  }

  /**
   * Handle employee found exception error details dto.
   *
   * @param exception the exception
   * @return the error details dto
   */
  @ExceptionHandler(EmployeeFoundException.class)
  @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
  @ResponseBody
  ErrorDetailsDto handleEmployeeFoundException(EmployeeFoundException exception) {
    log.info("handling {}", exception.getClass().getName());
    return mapExceptionToErrorDetailsDto(exception);
  }

  /**
   * Handle constraint violation exception error details dto.
   *
   * @param ex the ex
   * @return the error details dto
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorDetailsDto handleConstraintViolationException(ConstraintViolationException ex) {
    log.info("handling constraint violation");
    String errorMessage = ex.getConstraintViolations().stream().map(violation -> messageSource.getMessage(violation.getMessage(),
        Collections.emptyList().toArray(), Locale.getDefault())).collect(Collectors.joining(", "));

    return new ErrorDetailsDto("validation_error", errorMessage);
  }

  /**
   * Map exception to error details dto error details dto.
   *
   * @param exception the exception
   * @return the error details dto
   */
  private ErrorDetailsDto mapExceptionToErrorDetailsDto(EmployeeException exception) {
    return new ErrorDetailsDto(
        exception.getErrorCode(),
        messageSource.getMessage(
            exception.getErrorCode(), exception.getArgs().toArray(), Locale.getDefault()));
  }
}
