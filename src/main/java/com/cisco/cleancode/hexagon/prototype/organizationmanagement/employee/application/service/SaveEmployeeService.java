package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.service;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeException.ErrorCode;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeNotFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.CreateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.SaveEmployeeUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.UpdateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.SaveEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** The type Save employee service. */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
class SaveEmployeeService implements SaveEmployeeUseCase {

  /** The Query employee service. */
  private final QueryEmployeeService queryEmployeeService;

  /** The Save employee port. */
  private final SaveEmployeePort saveEmployeePort;

  /** The Mapper. */
  private final DomainMapper mapper;

  /**
   * Create employee employee.
   *
   * @param createEmployeeCommand the create employee request
   * @return the employee
   * @throws EmployeeFoundException the employee found exception
   */
  @Override
  public Employee createEmployee(CreateEmployeeCommand createEmployeeCommand)
      throws EmployeeFoundException {
    log.info("creating employee {}", createEmployeeCommand);
    if (queryEmployeeService.existsByEmail(createEmployeeCommand.getEmail()))
      throw EmployeeFoundException.builder()
          .errorCode(ErrorCode.EMPLOYEE_BY_EMAIL_FOUND)
          .args(Collections.singletonList(createEmployeeCommand.getEmail()))
          .build();

    return saveEmployeePort.createEmployee(mapper.commandToDomain(createEmployeeCommand));
  }

  /**
   * Update employee employee.
   *
   * @param updateEmployeeCommand the update employee request
   * @return the employee
   * @throws EmployeeNotFoundException the employee not found exception
   */
  @Override
  public Employee updateEmployee(UpdateEmployeeCommand updateEmployeeCommand)
      throws EmployeeNotFoundException {
    log.info("updating employee {}", updateEmployeeCommand);
    var persisted =
        queryEmployeeService
            .getEmployeeById(updateEmployeeCommand.getId())
            .orElseThrow(
                () ->
                    EmployeeNotFoundException.builder()
                        .errorCode(ErrorCode.EMPLOYEE_BY_ID_NOT_FOUND)
                        .args(Collections.singletonList(updateEmployeeCommand.getId()))
                        .build());

    var employee = mapper.commandToDomain(updateEmployeeCommand, persisted);
    return saveEmployeePort.updateEmployee(employee);
  }

  /**
   * Delete employee.
   *
   * @param id the id
   * @throws EmployeeNotFoundException the employee not found exception
   */
  @Override
  public void deleteEmployee(Long id) throws EmployeeNotFoundException {
    log.info("delete employee by id {}", id);
    validateEmployeeExistsByID(id);
    saveEmployeePort.deleteEmployee(id);
  }

  /**
   * Validate employee exists by id.
   *
   * @param id the id
   * @throws EmployeeNotFoundException the employee not found exception
   */
  private void validateEmployeeExistsByID(Long id) throws EmployeeNotFoundException {
    if (!queryEmployeeService.existsById(id))
      throw EmployeeNotFoundException.builder()
          .errorCode(ErrorCode.EMPLOYEE_BY_ID_NOT_FOUND)
          .args(Collections.singletonList(id))
          .build();
  }
}
