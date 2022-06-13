package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.SaveEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

/** The type Save employee persistence adapter unit test. */
@SpringBootTest
@ContextConfiguration(classes = {SaveEmployeePersistenceAdapter.class, PersistenceMapperImpl.class})
class SaveEmployeePersistenceAdapterUnitTest {

  /** The Employee jpa entity repository. */
  @MockBean private EmployeeJPAEntityRepository employeeJPAEntityRepository;

  /** The Employee persistence adapter. */
  @Autowired private SaveEmployeePort employeePersistenceAdapter;

  /** The Mapper. */
  @Autowired private PersistenceMapper mapper;

  /** Test create employee with non-existing email. */
  @Test
  void testCreateEmployeeWithNonExistingEmail() {
    var employeeJPAEntity = new EmployeeJPAEntity();
    employeeJPAEntity.setId(1L);
    employeeJPAEntity.setDepartmentId(1L);
    employeeJPAEntity.setEmail("employee1@company.com");
    employeeJPAEntity.setGender(Gender.FEMALE);
    employeeJPAEntity.setName("employee1");
    employeeJPAEntity.setDob(LocalDate.of(1970, 1, 1));

    when(employeeJPAEntityRepository.save(any(EmployeeJPAEntity.class))).thenReturn(employeeJPAEntity);

    var created = employeePersistenceAdapter.createEmployee(mapper.entityToDomain(employeeJPAEntity));
    assertNotNull(created);
    assertEquals(employeeJPAEntity.getName(), created.getName());
    assertEquals(employeeJPAEntity.getId(), created.getId());
    assertEquals(employeeJPAEntity.getDob(), created.getDateOfBirth());
    assertEquals(employeeJPAEntity.getGender(), created.getGender());
    assertEquals(employeeJPAEntity.getEmail(), created.getPrimaryEmail());
    assertEquals(employeeJPAEntity.getDepartmentId(), created.getDepartmentId());
  }

  /** Test create employee with existing email. */
  @Test
  void testCreateEmployeeWithExistingEmail() {
    var employeeJPAEntity = new EmployeeJPAEntity();
    employeeJPAEntity.setId(1L);
    employeeJPAEntity.setDepartmentId(1L);
    employeeJPAEntity.setEmail("employee1@company.com");
    employeeJPAEntity.setGender(Gender.FEMALE);
    employeeJPAEntity.setName("employee1");
    employeeJPAEntity.setDob(LocalDate.of(1970, 1, 1));

    when(employeeJPAEntityRepository.save(any(EmployeeJPAEntity.class))).thenThrow(new DataIntegrityViolationException(""));
    var employee = mapper.entityToDomain(employeeJPAEntity);

    assertThrows(DataIntegrityViolationException.class, () ->
        employeePersistenceAdapter.createEmployee(employee));
  }

  /** Update existing employee. */
  @Test
  void updateExistingEmployee() {
    var employeeJPAEntity = new EmployeeJPAEntity();
    employeeJPAEntity.setId(1L);
    employeeJPAEntity.setDepartmentId(1L);
    employeeJPAEntity.setEmail("employee1@company.com");
    employeeJPAEntity.setGender(Gender.FEMALE);
    employeeJPAEntity.setName("employee1");
    employeeJPAEntity.setDob(LocalDate.of(1970, 1, 1));

    when(employeeJPAEntityRepository.save(any(EmployeeJPAEntity.class))).thenReturn(employeeJPAEntity);

    var updated = employeePersistenceAdapter.updateEmployee(mapper.entityToDomain(employeeJPAEntity));
    assertNotNull(updated);
    assertEquals(employeeJPAEntity.getName(), updated.getName());
    assertEquals(employeeJPAEntity.getId(), updated.getId());
    assertEquals(employeeJPAEntity.getDob(), updated.getDateOfBirth());
    assertEquals(employeeJPAEntity.getGender(), updated.getGender());
    assertEquals(employeeJPAEntity.getEmail(), updated.getPrimaryEmail());
    assertEquals(employeeJPAEntity.getDepartmentId(), updated.getDepartmentId());
  }

  /** Delete employee. */
  @Test
  void deleteEmployee() {
    doNothing().when(employeeJPAEntityRepository).deleteById(anyLong());

    assertDoesNotThrow(() -> employeePersistenceAdapter.deleteEmployee(10L));
  }

}