package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.service;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.CreateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.UpdateEmployeeCommand;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/** The interface Domain mapper. */
@Mapper(componentModel = ComponentModel.SPRING)
interface DomainMapper {

  /**
   * Command to domain employee.
   *
   * @param createEmployeeCommand the create employee request
   * @return the employee
   */
@Mapping(target = "id", ignore = true)
  @Mapping(source = "email", target = "primaryEmail")
  @Mapping(source = "dob", target = "dateOfBirth")
  Employee commandToDomain(CreateEmployeeCommand createEmployeeCommand);

  /**
   * Command to domain employee.
   *
   * @param src the src
   * @param dest the dest
   * @return the employee
   */
@Mapping(target = "departmentId", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "dob", target = "dateOfBirth")
  @Mapping(source = "email", target = "primaryEmail")
  Employee commandToDomain(UpdateEmployeeCommand src, @MappingTarget Employee dest);
}
