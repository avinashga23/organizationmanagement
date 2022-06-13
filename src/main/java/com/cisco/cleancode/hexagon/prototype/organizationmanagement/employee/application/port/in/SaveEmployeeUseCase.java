package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeNotFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/** The interface Save employee use case. */
@Validated
public interface SaveEmployeeUseCase {

  /**
   * Create employee employee.
   *
   * @param createEmployeeCommand the create employee request
   * @return the employee
   * @throws EmployeeFoundException the employee found exception
   */
Employee createEmployee(@Valid CreateEmployeeCommand createEmployeeCommand)
      throws EmployeeFoundException;

  /**
   * Update employee employee.
   *
   * @param updateEmployeeCommand the update employee request
   * @return the employee
   * @throws EmployeeNotFoundException the employee not found exception
   */
Employee updateEmployee(@Valid UpdateEmployeeCommand updateEmployeeCommand)
      throws EmployeeNotFoundException;

  /**
   * Delete employee.
   *
   * @param id the id
   * @throws EmployeeNotFoundException the employee not found exception
   */
void deleteEmployee(@NotNull Long id) throws EmployeeNotFoundException;
}
