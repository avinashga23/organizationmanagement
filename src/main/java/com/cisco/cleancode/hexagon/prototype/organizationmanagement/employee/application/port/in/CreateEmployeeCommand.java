package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Create employee request. */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeCommand {

  /** The Name. */
  @NotBlank(message = "name_is_blank")
  private String name;

  /** The Email. */
  @NotBlank(message = "email_is_null")
  @Email(message = "invalid_email")
  private String email;

  /** The Dob. */
  @NotNull(message = "null_dob")
  private LocalDate dob;

  /** The Department id. */
  @NotNull(message = "null_department_id")
  private Long departmentId;

  /** The Gender. */
  @NotNull(message = "gender_is_null")
  private Gender gender;
}
