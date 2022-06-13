package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.EmployeeQueryUseCase;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/** The type Query employee service integration test. */
@Testcontainers
@SpringBootTest
@Transactional
@ContextConfiguration(
    initializers = EmployeeQueryUseCaseIntegrationTest.DockerPostgreDataSourceInitializer.class)
@Sql({"/scripts/query-employee-service-integration.sql"})
class EmployeeQueryUseCaseIntegrationTest {

  /** The Query employee service. */
  @Autowired private EmployeeQueryUseCase employeeQueryUseCase;

  /** The constant postgreSQLContainer. */
  @Container
  private static final PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:latest");

  /** The type Docker postgre data source initializer. */
  static class DockerPostgreDataSourceInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * Initialize.
     *
     * @param applicationContext the application context
     */
    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {

      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
          "spring.datasource.username=" + postgreSQLContainer.getUsername(),
          "spring.datasource.password=" + postgreSQLContainer.getPassword());
    }
  }

  /** Test existing employee by id. */
  @Test
  void testExistingEmployeeById() {
    var employeeContainer = employeeQueryUseCase.getEmployeeById(1L);
    assertTrue(employeeContainer.isPresent());

    var employee = employeeContainer.get();

    assertEquals(1L, employee.getId());
    assertEquals("employee1@company.com", employee.getPrimaryEmail());
    assertEquals(LocalDate.of(1990, 10, 10), employee.getDateOfBirth());
    assertEquals(Gender.FEMALE, employee.getGender());
    assertEquals("employee1", employee.getName());
    assertEquals(1L, employee.getDepartmentId());
  }

  /** Test non-existing employee by id. */
  @Test
  void testNonExistingEmployeeById() {
    assertFalse(employeeQueryUseCase.getEmployeeById(10L).isPresent());
  }

  /** Test existing employee by email. */
  @Test
  void testExistingEmployeeByEmail() {
    var employeeContainer = employeeQueryUseCase.getEmployeeByEmail("employee1@company.com");
    assertTrue(employeeContainer.isPresent());

    var employee = employeeContainer.get();

    assertEquals(1L, employee.getId());
    assertEquals("employee1@company.com", employee.getPrimaryEmail());
    assertEquals(LocalDate.of(1990, 10, 10), employee.getDateOfBirth());
    assertEquals(Gender.FEMALE, employee.getGender());
    assertEquals("employee1", employee.getName());
    assertEquals(1L, employee.getDepartmentId());
  }

  /** Test non-existing employee by email. */
  @Test
  void testNonExistingEmployeeByEmail() {
    assertFalse(employeeQueryUseCase.getEmployeeByEmail("employee100@company.com").isPresent());
  }

}