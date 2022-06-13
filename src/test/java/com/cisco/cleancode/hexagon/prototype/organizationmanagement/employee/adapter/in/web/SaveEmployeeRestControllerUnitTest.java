package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeException.ErrorCode;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeNotFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.CreateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.SaveEmployeeUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.UpdateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/** The type Save employee rest controller unit test. */
@WebMvcTest(controllers = SaveEmployeeRestController.class)
@ContextConfiguration(
    classes = {
      WebMapperImpl.class,
      EmployeeControllerAdvice.class,
      SaveEmployeeRestController.class,
      ObjectMapper.class
    })
class SaveEmployeeRestControllerUnitTest {

  /** The Mock mvc. */
  @Autowired private MockMvc mockMvc;

  /** The Object mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** The Web mapper. */
  @Autowired private WebMapper webMapper;

  /** The Save employee use case. */
  @MockBean private SaveEmployeeUseCase saveEmployeeUseCase;

  /** Sets up. */
  @BeforeEach
  public void setUp() {
    objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * Test create non-existing employee.
   *
   * @throws Exception the exception
   */
  @Test
  void testCreateNonExistingEmployee() throws Exception {
    CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();

    createEmployeeCommand.setDob(LocalDate.of(1970, 1, 1));
    createEmployeeCommand.setName("employee1");
    createEmployeeCommand.setGender(Gender.FEMALE);
    createEmployeeCommand.setEmail("employee1@company.com");
    createEmployeeCommand.setDepartmentId(10L);

    Employee employee = webMapper.commandToDomain(createEmployeeCommand);
    employee.setId(1L);

    EmployeeDto employeeDto = webMapper.domainToDto(employee);

    when(saveEmployeeUseCase.createEmployee(any())).thenReturn(employee);

    mockMvc.perform(post("/employee/")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                createEmployeeCommand)))
        .andDo(print()).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(header().exists("location"))
        .andExpect(content().json(objectMapper.writeValueAsString(employeeDto)));
  }

  /**
   * Test create existing employee.
   *
   * @throws Exception the exception
   */
  @Test
  void testCreateExistingEmployee() throws Exception {
    CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();

    createEmployeeCommand.setDob(LocalDate.of(1970, 1, 1));
    createEmployeeCommand.setName("employee1");
    createEmployeeCommand.setGender(Gender.FEMALE);
    createEmployeeCommand.setEmail("employee1@company.com");
    createEmployeeCommand.setDepartmentId(10L);

    when(saveEmployeeUseCase.createEmployee(any(CreateEmployeeCommand.class))).thenThrow(EmployeeFoundException.builder()
        .errorCode(ErrorCode.EMPLOYEE_BY_EMAIL_FOUND)
        .args(Collections.singletonList(createEmployeeCommand.getEmail())).build());

    ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto("employee_by_email_found",
        "employee by email employee1@company.com exists");

    mockMvc.perform(post("/employee/")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                createEmployeeCommand)))
        .andDo(print()).andExpect(status().isPreconditionFailed()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(errorDetailsDto)));
  }

  /**
   * Test update existing employee.
   *
   * @throws Exception the exception
   */
  @Test
  void testUpdateExistingEmployee() throws Exception {
    UpdateEmployeeCommand updateEmployeeRequest = new UpdateEmployeeCommand();

    updateEmployeeRequest.setDob(LocalDate.of(1970, 1, 1));
    updateEmployeeRequest.setName("employee1");
    updateEmployeeRequest.setGender(Gender.FEMALE);
    updateEmployeeRequest.setEmail("employee1@company.com");
    updateEmployeeRequest.setId(10L);

    Employee employee = webMapper.commandToDomain(updateEmployeeRequest);

    EmployeeDto employeeDto = webMapper.domainToDto(employee);

    when(saveEmployeeUseCase.updateEmployee(any(UpdateEmployeeCommand.class))).thenReturn(employee);

    mockMvc.perform(put("/employee/10")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateEmployeeRequest)))
        .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(employeeDto)));
  }

  /**
   * Test update non existing employee.
   *
   * @throws Exception the exception
   */
  @Test
  void testUpdateNonExistingEmployee() throws Exception {
    UpdateEmployeeCommand updateEmployeeRequest = new UpdateEmployeeCommand();

    updateEmployeeRequest.setDob(LocalDate.of(1970, 1, 1));
    updateEmployeeRequest.setName("employee1");
    updateEmployeeRequest.setGender(Gender.FEMALE);
    updateEmployeeRequest.setEmail("employee1@company.com");
    updateEmployeeRequest.setId(10L);

    when(saveEmployeeUseCase.updateEmployee(any())).thenThrow(EmployeeNotFoundException.builder()
        .errorCode(ErrorCode.EMPLOYEE_BY_EMAIL_NOT_FOUND)
        .args(Collections.singletonList(updateEmployeeRequest.getEmail())).build());

    ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto("employee_by_email_not_found",
        "employee by email employee1@company.com does not found");

    mockMvc.perform(put("/employee/10")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateEmployeeRequest)))
        .andDo(print()).andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(errorDetailsDto)));
  }

  /**
   * Delete existing employee.
   *
   * @throws Exception the exception
   */
  @Test
  void deleteExistingEmployee() throws Exception {
    doNothing().when(saveEmployeeUseCase).deleteEmployee(anyLong());

    mockMvc.perform(delete("/employee/10")).andExpect(status().isOk());
  }

  /**
   * Delete non existing employee.
   *
   * @throws Exception the exception
   */
  @Test
  void deleteNonExistingEmployee() throws Exception {
    doThrow(EmployeeNotFoundException.builder()
        .errorCode(ErrorCode.EMPLOYEE_BY_ID_NOT_FOUND).args(Collections.singletonList(10)).build())
        .when(saveEmployeeUseCase).deleteEmployee(anyLong());

    ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto("employee_by_id_not_found",
        "employee by id 10 does not found");

    mockMvc.perform(delete("/employee/10")).andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(errorDetailsDto)));
  }
}