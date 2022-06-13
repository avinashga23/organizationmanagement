package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

/** The interface Jpa entity mapper. */
@Mapper(componentModel = ComponentModel.SPRING)
interface PersistenceMapper {

  /**
   * Entity to domain employee.
   *
   * @param employeeJPAEntity the employee jpa entity
   * @return the employee
   */
@Mapping(source = "email", target = "primaryEmail")
  @Mapping(source = "dob", target = "dateOfBirth")
  Employee entityToDomain(EmployeeJPAEntity employeeJPAEntity);

  /**
   * Domain to entity employee jpa entity.
   *
   * @param employee the employee
   * @return the employee jpa entity
   */
@Mapping(source = "primaryEmail", target = "email")
  @Mapping(source = "dateOfBirth", target = "dob")
  EmployeeJPAEntity domainToEntity(Employee employee);
}
