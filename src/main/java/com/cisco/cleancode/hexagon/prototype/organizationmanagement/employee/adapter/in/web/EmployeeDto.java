package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import lombok.Data;

/** The type Employee dto. */
@Data
class EmployeeDto {

  /** The Id. */
private Long id;

  /** The Name. */
private String name;

  /** The Email. */
private String email;

  /** The Dob. */
private LocalDate dob;

  /** The Department id. */
private Long departmentId;

  /** The Gender. */
private Gender gender;

}
