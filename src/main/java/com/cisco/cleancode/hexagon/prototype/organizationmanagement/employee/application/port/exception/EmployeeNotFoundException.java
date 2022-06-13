package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception;

import java.util.List;
import lombok.Builder;

/** The type Employee not found exception. */
public class EmployeeNotFoundException extends EmployeeException {

  /**
   * Instantiates a new Employee not found exception.
   *
   * @param errorCode the error code
   * @param args the args
   */
@Builder
  public EmployeeNotFoundException(String errorCode, List<Object> args) {
    super(errorCode, args);
  }

}
