package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/** The type Create employee request. */
@Data
public class CreateEmployeeCommand {

  /** The Name. */
  @NotBlank(message = "name_is_blank")
  private String name;

  /** The Email. */
  @NotNull(message = "email_is_null")
  @Email(message = "invalid_email")
  private String email;

  /** The Dob. */
  @NotNull(message = "null_dob")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dob;

  /** The Department id. */
  @NotNull(message = "null_department_id")
  private Long departmentId;

  /** The Gender. */
  @NotNull(message = "gender_is_null")
  private Gender gender;
}
