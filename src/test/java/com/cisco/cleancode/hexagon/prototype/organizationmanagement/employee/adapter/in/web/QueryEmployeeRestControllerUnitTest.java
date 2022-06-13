package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.EmployeeQueryUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/** The type Query employee rest controller unit test. */
@WebMvcTest(controllers = QueryEmployeeRestController.class)
@ContextConfiguration(
    classes = {
      WebMapperImpl.class,
      EmployeeControllerAdvice.class,
      QueryEmployeeRestController.class,
      ObjectMapper.class
    })
class QueryEmployeeRestControllerUnitTest {

  /** The Mock mvc. */
  @Autowired private MockMvc mockMvc;

  /** The Object mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** The Employee query use case. */
  @MockBean private EmployeeQueryUseCase employeeQueryUseCase;

  /** Sets up. */
  @BeforeEach
  public void setUp() {
    objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * Test get existing employee by id.
   *
   * @throws Exception the exception
   */
  @Test
  void testGetExistingEmployeeById() throws Exception {
    Employee existing = new Employee();

    existing.setGender(Gender.MALE);
    existing.setName("employee1");
    existing.setId(10L);
    existing.setDepartmentId(10L);
    existing.setPrimaryEmail("employee1@company.com");
    existing.setDateOfBirth(LocalDate.of(1970, 1, 1));

    when(employeeQueryUseCase.getEmployeeById(anyLong())).thenReturn(Optional.of(existing));
    var body = mockMvc.perform(get("/employee/1"))
        .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    var retrieved = objectMapper.readValue(body, EmployeeDto.class);
    assertNotNull(retrieved);
    assertEquals(existing.getGender(), retrieved.getGender());
    assertEquals(existing.getId(), retrieved.getId());
    assertEquals(existing.getName(), retrieved.getName());
    assertEquals(existing.getDepartmentId(), retrieved.getDepartmentId());
    assertEquals(existing.getPrimaryEmail(), retrieved.getEmail());
    assertEquals(existing.getDateOfBirth(), retrieved.getDob());
  }

  /**
   * Test get non existing employee by id.
   *
   * @throws Exception the exception
   */
  @Test
  void testGetNonExistingEmployeeById() throws Exception {
    when(employeeQueryUseCase.getEmployeeById(anyLong())).thenReturn(Optional.empty());
    var body = mockMvc.perform(get("/employee/1"))
        .andDo(print()).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
    var retrieved = objectMapper.readValue(body, ErrorDetailsDto.class);
    assertNotNull(retrieved);
    assertEquals("employee_by_id_not_found", retrieved.errorCode());
    assertEquals("employee by id 1 does not found", retrieved.message());
  }

  /**
   * Test get existing employee by email.
   *
   * @throws Exception the exception
   */
  @Test
  void testGetExistingEmployeeByEmail() throws Exception {
    Employee existing = new Employee();

    existing.setGender(Gender.MALE);
    existing.setName("employee1");
    existing.setId(10L);
    existing.setDepartmentId(10L);
    existing.setPrimaryEmail("employee1@company.com");
    existing.setDateOfBirth(LocalDate.of(1970, 1, 1));

    when(employeeQueryUseCase.getEmployeeByEmail(anyString())).thenReturn(Optional.of(existing));
    var body = mockMvc.perform(get("/employee/email/email1@company.com"))
        .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    var retrieved = objectMapper.readValue(body, EmployeeDto.class);
    assertNotNull(retrieved);
    assertEquals(existing.getGender(), retrieved.getGender());
    assertEquals(existing.getId(), retrieved.getId());
    assertEquals(existing.getName(), retrieved.getName());
    assertEquals(existing.getDepartmentId(), retrieved.getDepartmentId());
    assertEquals(existing.getPrimaryEmail(), retrieved.getEmail());
    assertEquals(existing.getDateOfBirth(), retrieved.getDob());
  }

  /**
   * Test get non existing employee by email.
   *
   * @throws Exception the exception
   */
  @Test
  void testGetNonExistingEmployeeByEmail() throws Exception {
    when(employeeQueryUseCase.getEmployeeByEmail(anyString())).thenReturn(Optional.empty());
    var body = mockMvc.perform(get("/employee/email/employee1@company.com"))
        .andDo(print()).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
    var retrieved = objectMapper.readValue(body, ErrorDetailsDto.class);
    assertNotNull(retrieved);
    assertEquals("employee_by_email_not_found", retrieved.errorCode());
    assertEquals("employee by email employee1@company.com does not found", retrieved.message());
  }
}