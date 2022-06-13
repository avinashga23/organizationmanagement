package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.SaveEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/** The type Save employee persistence adapter. */
@Repository
@RequiredArgsConstructor
@Slf4j
class SaveEmployeePersistenceAdapter implements SaveEmployeePort {

  /** The Employee jpa entity repository. */
  private final EmployeeJPAEntityRepository employeeJPAEntityRepository;

  /** The Mapper. */
  private final PersistenceMapper mapper;

  /**
   * Create employee employee.
   *
   * @param employee the employee
   * @return the employee
   */
  @Override
  public Employee createEmployee(Employee employee) {
    log.info("creating employee {}", employee);
    return save(employee);
  }

  /**
   * Update employee employee.
   *
   * @param employee the employee
   * @return the employee
   */
  @Override
  public Employee updateEmployee(Employee employee) {
    log.info("updating employee {}", employee);
    return save(employee);
  }

  /**
   * Save employee.
   *
   * @param employee the employee
   * @return the employee
   */
  private Employee save(Employee employee) {
    return mapper.entityToDomain(
        employeeJPAEntityRepository.save(mapper.domainToEntity(employee)));
  }

  /**
   * Delete employee.
   *
   * @param id the id
   */
  @Override
  public void deleteEmployee(long id) {
    log.info("deleting employee {}", id);
    employeeJPAEntityRepository.deleteById(id);
  }

}
