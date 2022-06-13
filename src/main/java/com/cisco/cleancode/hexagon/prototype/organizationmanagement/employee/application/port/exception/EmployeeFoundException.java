package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception;

import java.util.List;
import lombok.Builder;

/**
 * The type Employee found exception.
 */
public class EmployeeFoundException extends EmployeeException {

  /**
   * Instantiates a new Employee found exception.
   *
   * @param errorCode the error code
   * @param args the args
   */
@Builder
  public EmployeeFoundException(String errorCode, List<Object> args) {
    super(errorCode, args);
  }
}
