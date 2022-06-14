package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

/** The type Create employee request dto. */
@Data
class CreateEmployeeRequestDto {

  /** The Name. */
  private String name;

  /** The Email. */
  private String email;

  /** The Dob. */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dob;

  /** The Department id. */
  private Long departmentId;

  /** The Gender. */
  private Gender gender;
}
