package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.CreateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.SaveEmployeeUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.UpdateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.out.SaveEmployeePort;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import java.util.stream.Stream;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

/** The type Save employee validation unit test. */
@SpringBootTest
@ContextConfiguration(
    classes = {
      SaveEmployeeService.class,
      DomainMapperImpl.class,
      ValidationAutoConfiguration.class
    })
class SaveEmployeeValidationUnitTest {

  /** The Query employee service. */
  @MockBean private QueryEmployeeService queryEmployeeService;

  /** The Save employee port. */
  @MockBean private SaveEmployeePort saveEmployeePort;

  /** The Save employee use case. */
  @Autowired private SaveEmployeeUseCase saveEmployeeUseCase;

  /**
   * Test create employee with invalid command.
   *
   * @param command the command
   */
  @ParameterizedTest
  @MethodSource("createEmployeeCommandStream")
  void testCreateEmployeeWithInvalidCommand(CreateEmployeeCommand command) {
    assertThrows(ConstraintViolationException.class,
        () -> saveEmployeeUseCase.createEmployee(command));
  }

  /**
   * Test update employee with invalid command.
   *
   * @param command the command
   */
  @ParameterizedTest
  @MethodSource("updateEmployeeCommandStream")
  void testUpdateEmployeeWithInvalidCommand(UpdateEmployeeCommand command) {
    assertThrows(ConstraintViolationException.class,
        () -> saveEmployeeUseCase.updateEmployee(command));
  }

  /**
   * Create employee command stream.
   *
   * @return the stream
   */
  private static Stream<CreateEmployeeCommand> createEmployeeCommandStream() {
    var command = CreateEmployeeCommand.builder()
        .email("employee1@company.com")
        .gender(Gender.FEMALE)
        .name("employee1")
        .departmentId(10L)
        .dob(LocalDate.of(1990, 1, 1))
        .build();

    var nullName = command.toBuilder()
        .name(null)
        .build();
    var blankName = command.toBuilder()
        .name("")
        .build();
    var nullEmail = command.toBuilder()
        .email(null)
        .build();
    var blankEmail = command.toBuilder()
        .email("")
        .build();
    var invalidEmail = command.toBuilder()
        .email("invalid-email")
        .build();
    var nullDob = command.toBuilder()
        .dob(null)
        .build();
    var nullDepartmentId = command.toBuilder()
        .departmentId(null)
        .build();
    var nullGender = command.toBuilder()
        .gender(null)
        .build();

    return Stream.of(nullName,
        blankName,
        nullEmail,
        blankEmail,
        invalidEmail,
        nullDob,
        nullDepartmentId,
        nullGender);
  }

  /**
   * Update employee command stream.
   *
   * @return the stream
   */
  private static Stream<UpdateEmployeeCommand> updateEmployeeCommandStream() {
    var command = UpdateEmployeeCommand.builder()
        .id(10L)
        .email("employee1@company.com")
        .gender(Gender.FEMALE)
        .name("employee1")
        .dob(LocalDate.of(1990, 1, 1))
        .build();

    var nullId = command.toBuilder()
        .id(null)
        .build();

    var nullName = command.toBuilder()
        .name(null)
        .build();

    var blankName = command.toBuilder()
        .name("")
        .build();

    var nullEmail = command.toBuilder()
        .email(null)
        .build();

    var blankEmail = command.toBuilder()
        .email("")
        .build();

    var invalidEmail = command.toBuilder()
        .email("invalid-email")
        .build();

    var nullDob = command.toBuilder()
        .dob(null)
        .build();

    var nullGender = command.toBuilder()
        .gender(null)
        .build();

    return Stream.of(nullId,
        nullName,
        blankName,
        nullEmail,
        blankEmail,
        invalidEmail,
        nullDob,
        nullGender);
  }

}
