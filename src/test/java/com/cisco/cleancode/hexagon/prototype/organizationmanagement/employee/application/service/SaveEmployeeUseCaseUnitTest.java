package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.CreateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.UpdateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeNotFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.SaveEmployeeUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.SaveEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

/** The type Save employee service unit test. */
@SpringBootTest
@ContextConfiguration(classes = {SaveEmployeeService.class, DomainMapperImpl.class})
class SaveEmployeeUseCaseUnitTest {

  /** The Query employee service. */
  @MockBean private QueryEmployeeService queryEmployeeService;

  /** The Save employee port. */
  @MockBean private SaveEmployeePort saveEmployeePort;

  /** The Save employee use case. */
  @Autowired private SaveEmployeeUseCase saveEmployeeUseCase;

  /**
   * Test create employee with non existing email.
   *
   * @throws EmployeeFoundException the employee found exception
   */
  @Test
  void testCreateEmployeeWithNonExistingEmail() throws EmployeeFoundException {
    var employee = new Employee();
    employee.setId(1L);
    employee.setDepartmentId(1L);
    employee.setPrimaryEmail("employee1@company.com");
    employee.setGender(Gender.FEMALE);
    employee.setName("employee1");
    employee.setDateOfBirth(LocalDate.of(1970, 1, 1));

    when(queryEmployeeService.existsByEmail(anyString())).thenReturn(false);
    when(saveEmployeePort.createEmployee(any(Employee.class))).thenReturn(employee);

    CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();
    createEmployeeCommand.setEmail(employee.getPrimaryEmail());
    createEmployeeCommand.setGender(employee.getGender());
    createEmployeeCommand.setName(employee.getName());
    createEmployeeCommand.setDepartmentId(employee.getDepartmentId());
    createEmployeeCommand.setDob(employee.getDateOfBirth());

    var created = saveEmployeeUseCase.createEmployee(createEmployeeCommand);
    assertNotNull(created);
    assertEquals(1L, created.getId());
    assertEquals(createEmployeeCommand.getName(), created.getName());
    assertEquals(createEmployeeCommand.getDob(), created.getDateOfBirth());
    assertEquals(createEmployeeCommand.getGender(), created.getGender());
    assertEquals(createEmployeeCommand.getEmail(), created.getPrimaryEmail());
    assertEquals(employee.getDepartmentId(), created.getDepartmentId());
  }

  /** Test create employee with existing email. */
  @Test
  void testCreateEmployeeWithExistingEmail() {
    when(queryEmployeeService.existsByEmail(anyString())).thenReturn(true);

    CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();
    createEmployeeCommand.setEmail("employee1@company.com");
    createEmployeeCommand.setGender(Gender.FEMALE);
    createEmployeeCommand.setName("employee1");
    createEmployeeCommand.setDepartmentId(10L);
    createEmployeeCommand.setDob(LocalDate.of(1990, 1, 1));

    assertThrows(EmployeeFoundException.class, () -> saveEmployeeUseCase.createEmployee(
        createEmployeeCommand));
  }

  /**
   * Test update existing employee.
   *
   * @throws EmployeeNotFoundException the employee not found exception
   */
  @Test
  void testUpdateExistingEmployee() throws EmployeeNotFoundException {
    var employee = new Employee();
    employee.setId(1L);
    employee.setDepartmentId(1L);
    employee.setPrimaryEmail("employee1@company.com");
    employee.setGender(Gender.FEMALE);
    employee.setName("employee1");
    employee.setDateOfBirth(LocalDate.of(1970, 1, 1));

    when(queryEmployeeService.getEmployeeById(anyLong())).thenReturn(Optional.of(employee));
    when(saveEmployeePort.updateEmployee(any(Employee.class))).thenReturn(employee);

    UpdateEmployeeCommand updateEmployeeRequest = new UpdateEmployeeCommand();
    updateEmployeeRequest.setId(employee.getId());
    updateEmployeeRequest.setEmail(employee.getPrimaryEmail());
    updateEmployeeRequest.setGender(employee.getGender());
    updateEmployeeRequest.setName(employee.getName());
    updateEmployeeRequest.setDob(employee.getDateOfBirth());

    var updated = saveEmployeeUseCase.updateEmployee(updateEmployeeRequest);
    assertNotNull(updated);
    assertEquals(1L, updated.getId());
    assertEquals(updateEmployeeRequest.getName(), updated.getName());
    assertEquals(updateEmployeeRequest.getDob(), updated.getDateOfBirth());
    assertEquals(updateEmployeeRequest.getGender(), updated.getGender());
    assertEquals(updateEmployeeRequest.getEmail(), updated.getPrimaryEmail());
    assertEquals(employee.getDepartmentId(), updated.getDepartmentId());
  }

  /** Test update non existing employee. */
  @Test
  void testUpdateNonExistingEmployee() {
    when(queryEmployeeService.getEmployeeById(anyLong())).thenReturn(Optional.empty());

    UpdateEmployeeCommand updateEmployeeRequest = new UpdateEmployeeCommand();
    updateEmployeeRequest.setId(10L);
    updateEmployeeRequest.setEmail("employee1@company.com");
    updateEmployeeRequest.setGender(Gender.FEMALE);
    updateEmployeeRequest.setName("employee1");
    updateEmployeeRequest.setDob(LocalDate.of(1990, 1, 1));

    assertThrows(EmployeeNotFoundException.class, () -> saveEmployeeUseCase.updateEmployee(updateEmployeeRequest));
  }

  /** Test delete existing employee. */
  @Test
  void testDeleteExistingEmployee() {
    when(queryEmployeeService.existsById(anyLong())).thenReturn(true);
    doNothing().when(saveEmployeePort).deleteEmployee(anyLong());

    assertDoesNotThrow(() -> saveEmployeeUseCase.deleteEmployee(10L));
  }

  /** Test delete non existing employee. */
  @Test
  void testDeleteNonExistingEmployee() {
    when(queryEmployeeService.existsById(anyLong())).thenReturn(false);

    assertThrows(EmployeeNotFoundException.class, () -> saveEmployeeUseCase.deleteEmployee(10L));
  }
}