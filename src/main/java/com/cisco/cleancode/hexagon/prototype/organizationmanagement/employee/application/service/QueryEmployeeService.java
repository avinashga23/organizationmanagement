package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.service;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.EmployeeQueryUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.QueryEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Query employee service. */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
class QueryEmployeeService implements EmployeeQueryUseCase {

  /** The Query employee port. */
  private final QueryEmployeePort queryEmployeePort;

  /**
   * Exists by id boolean.
   *
   * @param id the id
   * @return the boolean
   */
  @Override
  public boolean existsById(Long id) {
    log.info("checking employee exists by id {}", id);
    return queryEmployeePort.employeeExistsById(id);
  }

  /**
   * Exists by email boolean.
   *
   * @param email the email
   * @return the boolean
   */
  @Override
  public boolean existsByEmail(String email) {
    log.info("checking employee exists by email {}", email);
    return queryEmployeePort.employeeExistsByEmail(email);
  }

  /**
   * Gets employee by id.
   *
   * @param id the id
   * @return the employee by id
   */
  @Override
  public Optional<Employee> getEmployeeById(Long id) {
    log.info("getting employee by id {}", id);
    return queryEmployeePort.getEmployeeById(id);
  }

  /**
   * Gets employee by email.
   *
   * @param email the email
   * @return the employee by email
   */
  @Override
  public Optional<Employee> getEmployeeByEmail(String email) {
    log.info("getting employee by email {}", email);
    return queryEmployeePort.getEmployeeByEmail(email);
  }

}
