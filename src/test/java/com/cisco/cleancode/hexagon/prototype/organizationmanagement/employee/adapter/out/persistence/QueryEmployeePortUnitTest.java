package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.QueryEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

/** The type Query employee persistence adapter unit test. */
@SpringBootTest
@ContextConfiguration(
    classes = {QueryEmployeePersistenceAdapter.class, PersistenceMapperImpl.class})
class QueryEmployeePortUnitTest {

  /** The Employee jpa entity repository. */
  @MockBean private EmployeeJPAEntityRepository employeeJPAEntityRepository;

  /** The Employee persistence adapter. */
  @Autowired private QueryEmployeePort queryEmployeePort;

  /** Test get existing employee by id. */
  @Test
  void testGetExistingEmployeeById() {
    var employeeJPAEntity = new EmployeeJPAEntity();
    employeeJPAEntity.setId(1L);
    employeeJPAEntity.setDepartmentId(1L);
    employeeJPAEntity.setEmail("employee1@company.com");
    employeeJPAEntity.setGender(Gender.FEMALE);
    employeeJPAEntity.setName("employee1");
    employeeJPAEntity.setDob(LocalDate.of(1970, 1, 1));

    when(employeeJPAEntityRepository.findById(anyLong())).thenReturn(Optional.of(employeeJPAEntity));

    var employeeOptional = queryEmployeePort.getEmployeeById(1L);

    assertTrue(employeeOptional.isPresent());
    var employee = employeeOptional.get();
    assertNotNull(employee);
    assertEquals(employeeJPAEntity.getId(), employee.getId());
    assertEquals(employeeJPAEntity.getDob(), employee.getDateOfBirth());
    assertEquals(employeeJPAEntity.getDepartmentId(), employee.getDepartmentId());
    assertEquals(employeeJPAEntity.getEmail(), employee.getPrimaryEmail());
    assertEquals(employeeJPAEntity.getGender(), employee.getGender());
    assertEquals(employeeJPAEntity.getName(), employee.getName());
    assertEquals(employeeJPAEntity.getDob(), employee.getDateOfBirth());
  }

  /** Test get non existing employee by id. */
  @Test
  void testGetNonExistingEmployeeById() {
    when(employeeJPAEntityRepository.findById(anyLong())).thenReturn(Optional.empty());

    var employeeOptional = queryEmployeePort.getEmployeeById(1L);
    assertFalse(employeeOptional.isPresent());
  }

  /** Test get existing employee by email. */
  @Test
  void testGetExistingEmployeeByEmail() {
    var employeeJPAEntity = new EmployeeJPAEntity();
    employeeJPAEntity.setId(1L);
    employeeJPAEntity.setDepartmentId(1L);
    employeeJPAEntity.setEmail("employee1@company.com");
    employeeJPAEntity.setGender(Gender.FEMALE);
    employeeJPAEntity.setName("employee1");
    employeeJPAEntity.setDob(LocalDate.of(1970, 1, 1));

    when(employeeJPAEntityRepository.findByEmail(anyString())).thenReturn(Optional.of(employeeJPAEntity));

    var employeeOptional = queryEmployeePort.getEmployeeByEmail("employee1@company.com");

    assertTrue(employeeOptional.isPresent());
    var employee = employeeOptional.get();
    assertNotNull(employee);
    assertEquals(employeeJPAEntity.getId(), employee.getId());
    assertEquals(employeeJPAEntity.getDob(), employee.getDateOfBirth());
    assertEquals(employeeJPAEntity.getDepartmentId(), employee.getDepartmentId());
    assertEquals(employeeJPAEntity.getEmail(), employee.getPrimaryEmail());
    assertEquals(employeeJPAEntity.getGender(), employee.getGender());
    assertEquals(employeeJPAEntity.getName(), employee.getName());
    assertEquals(employeeJPAEntity.getDob(), employee.getDateOfBirth());
  }

  /** Test get non existing employee by email. */
  @Test
  void testGetNonExistingEmployeeByEmail() {
    when(employeeJPAEntityRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    var employeeOptional = queryEmployeePort.getEmployeeByEmail("employee1@company.com");
    assertFalse(employeeOptional.isPresent());
  }

  /** Test existing employee exists by id. */
  @Test
  void testExistingEmployeeExistsById() {
    when(employeeJPAEntityRepository.existsById(anyLong())).thenReturn(true);

    assertTrue(queryEmployeePort.employeeExistsById(1L));
  }

  /** Test non-existing employee exists by id. */
  @Test
  void testNonExistingEmployeeExistsById() {
    when(employeeJPAEntityRepository.existsById(anyLong())).thenReturn(false);

    assertFalse(queryEmployeePort.employeeExistsById(1L));
  }

  /** Test existing employee exists by email. */
  @Test
  void testExistingEmployeeExistsByEmail() {
    when(employeeJPAEntityRepository.existsByEmail(anyString())).thenReturn(true);

    assertTrue(queryEmployeePort.employeeExistsByEmail("employee1@company.com"));
  }

  /** Test non-existing employee exists by email. */
  @Test
  void testNonExistingEmployeeExistsByEmail() {
    when(employeeJPAEntityRepository.existsByEmail(anyString())).thenReturn(false);

    assertFalse(queryEmployeePort.employeeExistsByEmail("employee1@company.com"));
  }

}