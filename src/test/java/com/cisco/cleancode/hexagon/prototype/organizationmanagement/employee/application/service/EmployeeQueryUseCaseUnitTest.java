package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.EmployeeQueryUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.QueryEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

/** The type Query employee service unit test. */
@SpringBootTest
@ContextConfiguration(classes = {QueryEmployeeService.class, DomainMapperImpl.class})
class EmployeeQueryUseCaseUnitTest {

  /** The Query employee port. */
  @MockBean private QueryEmployeePort queryEmployeePort;

  /** The Employee query use case. */
  @Autowired private EmployeeQueryUseCase employeeQueryUseCase;

  /** Test existing employee exists by id. */
  @Test
  void testExistingEmployeeExistsById() {
    when(queryEmployeePort.employeeExistsById(anyLong())).thenReturn(true);

    assertTrue(employeeQueryUseCase.existsById(10L));
  }

  /** Test non-existing employee exists by id. */
  @Test
  void testNonExistingEmployeeExistsById() {
    when(queryEmployeePort.employeeExistsById(anyLong())).thenReturn(false);

    assertFalse(employeeQueryUseCase.existsById(10L));
  }

  /** Test existing exists by email. */
  @Test
  void testExistingExistsByEmail() {
    when(queryEmployeePort.employeeExistsByEmail(anyString())).thenReturn(true);

    assertTrue(employeeQueryUseCase.existsByEmail("employee1@company.com"));
  }

  /** Gets existing employee by id. */
  @Test
  void getExistingEmployeeById() {
    Employee existing = new Employee();

    existing.setGender(Gender.MALE);
    existing.setName("employee1");
    existing.setId(10L);
    existing.setDepartmentId(10L);
    existing.setPrimaryEmail("employee1@company.com");
    existing.setDateOfBirth(LocalDate.of(1970, 1, 1));

    when(queryEmployeePort.getEmployeeById(anyLong())).thenReturn(Optional.of(existing));

    var retrieved = employeeQueryUseCase.getEmployeeById(10L);
    assertTrue(retrieved.isPresent());
    var retrievedEmployee = retrieved.get();

    assertEquals(existing.getName(), retrievedEmployee.getName());
    assertEquals(existing.getId(), retrievedEmployee.getId());
    assertEquals(existing.getDateOfBirth(), retrievedEmployee.getDateOfBirth());
    assertEquals(existing.getGender(), retrievedEmployee.getGender());
    assertEquals(existing.getPrimaryEmail(), retrievedEmployee.getPrimaryEmail());
    assertEquals(existing.getDepartmentId(), retrievedEmployee.getDepartmentId());
  }

  /** Gets non-existing employee by id. */
  @Test
  void getNonExistingEmployeeById() {
    when(queryEmployeePort.getEmployeeById(anyLong())).thenReturn(Optional.empty());

    var retrieved = employeeQueryUseCase.getEmployeeById(10L);
    assertFalse(retrieved.isPresent());
  }

  /** Gets existing employee by email. */
  @Test
  void getExistingEmployeeByEmail() {
    Employee existing = new Employee();

    existing.setGender(Gender.MALE);
    existing.setName("employee1");
    existing.setId(10L);
    existing.setDepartmentId(10L);
    existing.setPrimaryEmail("employee1@company.com");
    existing.setDateOfBirth(LocalDate.of(1970, 1, 1));

    when(queryEmployeePort.getEmployeeByEmail(anyString())).thenReturn(Optional.of(existing));

    var retrieved = employeeQueryUseCase.getEmployeeByEmail("employee1@company.com");
    assertTrue(retrieved.isPresent());
    var retrievedEmployee = retrieved.get();

    assertEquals(existing.getName(), retrievedEmployee.getName());
    assertEquals(existing.getId(), retrievedEmployee.getId());
    assertEquals(existing.getDateOfBirth(), retrievedEmployee.getDateOfBirth());
    assertEquals(existing.getGender(), retrievedEmployee.getGender());
    assertEquals(existing.getPrimaryEmail(), retrievedEmployee.getPrimaryEmail());
    assertEquals(existing.getDepartmentId(), retrievedEmployee.getDepartmentId());
  }

  /** Gets non-existing employee by email. */
  @Test
  void getNonExistingEmployeeByEmail() {
    when(queryEmployeePort.getEmployeeByEmail(anyString())).thenReturn(Optional.empty());

    var retrieved = employeeQueryUseCase.getEmployeeByEmail("employee1@company.com");
    assertFalse(retrieved.isPresent());
  }
}