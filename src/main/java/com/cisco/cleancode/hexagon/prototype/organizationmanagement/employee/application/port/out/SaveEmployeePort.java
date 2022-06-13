package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;

/** The interface Save employee port. */
public interface SaveEmployeePort {

  /**
   * Create employee employee.
   *
   * @param employee the employee
   * @return the employee
   */
Employee createEmployee(Employee employee);

  /**
   * Update employee employee.
   *
   * @param employee the employee
   * @return the employee
   */
Employee updateEmployee(Employee employee);

  /**
   * Delete employee employee.
   *
   * @param id the id
   */
void deleteEmployee(long id);
}
