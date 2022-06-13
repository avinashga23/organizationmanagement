package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.QueryEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/** The type Query employee persistence adapter. */
@Repository
@RequiredArgsConstructor
@Slf4j
class QueryEmployeePersistenceAdapter implements QueryEmployeePort {

  /** The Employee jpa entity repository. */
  private final EmployeeJPAEntityRepository employeeJPAEntityRepository;

  /** The Mapper. */
  private final PersistenceMapper mapper;

  /**
   * Gets employee by id.
   *
   * @param id the id
   * @return the employee by id
   */
  public Optional<Employee> getEmployeeById(long id) {
    log.info("getting employee by id {}", id);
    return employeeJPAEntityRepository.findById(id).map(mapper::entityToDomain);
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
    return employeeJPAEntityRepository.findByEmail(email).map(mapper::entityToDomain);
  }

  /**
   * Employee exists by id boolean.
   *
   * @param id the id
   * @return the boolean
   */
  @Override
  public boolean employeeExistsById(long id) {
    log.info("checking employee exists by id {}", id);
    return employeeJPAEntityRepository.existsById(id);
  }

  /**
   * Employee exists by email boolean.
   *
   * @param email the email
   * @return the boolean
   */
  @Override
  public boolean employeeExistsByEmail(String email) {
    log.info("checking employee exists by email {}", email);
    return employeeJPAEntityRepository.existsByEmail(email);
  }
}
