package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.EmployeeQueryUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeException.ErrorCode;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeNotFoundException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Query employee rest controller. */
@RestController
@RequestMapping("/employee/")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class QueryEmployeeRestController {

  /** The Employee query use case. */
  private final EmployeeQueryUseCase employeeQueryUseCase;

  /** The Mapper. */
  private final WebMapper mapper;

  /**
   * Gets employee by id.
   *
   * @param id the id
   * @return the employee by id
   * @throws EmployeeNotFoundException the employee not found exception
   */
  @GetMapping("{id}")
  EmployeeDto getEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
    log.info("getting employee by id: {}", id);
    return employeeQueryUseCase.getEmployeeById(id).map(mapper::domainToDto).orElseThrow(() ->
        EmployeeNotFoundException.builder().errorCode(ErrorCode.EMPLOYEE_BY_ID_NOT_FOUND).args(Collections.singletonList(id)).build());
  }

  /**
   * Gets employee by email.
   *
   * @param email the email
   * @return the employee by email
   * @throws EmployeeNotFoundException the employee not found exception
   */
  @GetMapping("email/{email}")
  EmployeeDto getEmployeeByEmail(@PathVariable String email) throws EmployeeNotFoundException {
    log.info("getting employee by email: {}", email);
    return employeeQueryUseCase.getEmployeeByEmail(email).map(mapper::domainToDto).orElseThrow(() ->
        EmployeeNotFoundException.builder().errorCode(ErrorCode.EMPLOYEE_BY_EMAIL_NOT_FOUND).args(Collections.singletonList(email)).build());
  }

}
