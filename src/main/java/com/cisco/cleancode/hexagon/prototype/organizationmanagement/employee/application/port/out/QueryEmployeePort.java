package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import java.util.Optional;

/** The interface Query employee port. */
public interface QueryEmployeePort {

  /**
   * Gets employee by id.
   *
   * @param id the id
   * @return the employee by id
   */
Optional<Employee> getEmployeeById(long id);

  /**
   * Gets employee by email.
   *
   * @param email the email
   * @return the employee by email
   */
Optional<Employee> getEmployeeByEmail(String email);

  /**
   * Employee exists by id boolean.
   *
   * @param id the id
   * @return the boolean
   */
boolean employeeExistsById(long id);

  /**
   * Employee exists by email boolean.
   *
   * @param email the email
   * @return the boolean
   */
boolean employeeExistsByEmail(String email);
}
