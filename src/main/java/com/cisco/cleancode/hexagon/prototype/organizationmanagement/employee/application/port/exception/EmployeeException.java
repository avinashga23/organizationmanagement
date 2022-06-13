package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The type Employee exception. */
@RequiredArgsConstructor
@Getter
public abstract class EmployeeException extends Exception {

  /** The Error code. */
private final String errorCode;

  /** The Args. */
private final List<Object> args;

  /** The type Error code. */
public static class ErrorCode {

    /** Instantiates a new Error code. */
private ErrorCode() {}

    /** The constant EMPLOYEE_BY_ID_NOT_FOUND. */
public static final String EMPLOYEE_BY_ID_NOT_FOUND = "employee_by_id_not_found";

    /** The constant EMPLOYEE_BY_EMAIL_NOT_FOUND. */
public static final String EMPLOYEE_BY_EMAIL_NOT_FOUND = "employee_by_email_not_found";

    /** The constant EMPLOYEE_BY_EMAIL_FOUND. */
public static final String EMPLOYEE_BY_EMAIL_FOUND = "employee_by_email_found";
  }
}
