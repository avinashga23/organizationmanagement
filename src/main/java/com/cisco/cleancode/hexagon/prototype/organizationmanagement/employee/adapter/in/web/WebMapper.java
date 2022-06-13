package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.CreateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.UpdateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

/** The interface Mapper. */
@Mapper(componentModel = ComponentModel.SPRING)
interface WebMapper {

  /**
   * Dto to domain employee.
   *
   * @param employeeDto the employee dto
   * @return the employee
   */
  @Mapping(source = "email", target = "primaryEmail")
  @Mapping(source = "dob", target = "dateOfBirth")
  Employee commandToDomain(EmployeeDto employeeDto);

  /**
   * Dto to domain employee.
   *
   * @param createEmployeeCommand the create employee request
   * @return the employee
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "email", target = "primaryEmail")
  @Mapping(source = "dob", target = "dateOfBirth")
  Employee commandToDomain(CreateEmployeeCommand createEmployeeCommand);

  /**
   * Dto to command create employee request.
   *
   * @param createEmployeeRequestDto the create employee request dto
   * @return the create employee request
   */
  CreateEmployeeCommand dtoToCommand(CreateEmployeeRequestDto createEmployeeRequestDto);

  /**
   * Dto to command update employee request.
   *
   * @param updateEmployeeRequestDto the update employee request dto
   * @return the update employee request
   */
  UpdateEmployeeCommand dtoToCommand(UpdateEmployeeRequestDto updateEmployeeRequestDto);

  /**
   * Dto to domain employee.
   *
   * @param updateEmployeeRequest the update employee request
   * @return the employee
   */
  @Mapping(target = "departmentId", ignore = true)
  @Mapping(source = "email", target = "primaryEmail")
  @Mapping(source = "dob", target = "dateOfBirth")
  Employee commandToDomain(UpdateEmployeeCommand updateEmployeeRequest);

  /**
   * Domain to dto employee dto.
   *
   * @param employee the employee
   * @return the employee dto
   */
  @Mapping(source = "primaryEmail", target = "email")
  @Mapping(source = "dateOfBirth", target = "dob")
  EmployeeDto domainToDto(Employee employee);
}
