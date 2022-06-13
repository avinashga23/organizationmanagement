package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import java.util.Optional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/** The interface Employee query use case. */
@Validated
public interface EmployeeQueryUseCase {

  /**
   * Exists boolean.
   *
   * @param id the id
   * @return the boolean
   */
boolean existsById(@NotNull(message = "id_cannot_be_null") Long id);

  /**
   * Exists by email boolean.
   *
   * @param email the email
   * @return the boolean
   */
boolean existsByEmail(@Email(message = "invalid_email") String email);

  /**
   * Gets employee by id.
   *
   * @param id the id
   * @return the employee by id
   */
Optional<Employee> getEmployeeById(@NotNull(message = "id_cannot_be_null") Long id);

  /**
   * Gets employee by email.
   *
   * @param email the email
   * @return the employee by email
   */
Optional<Employee> getEmployeeByEmail(@Email(message = "invalid_email") String email);
}
