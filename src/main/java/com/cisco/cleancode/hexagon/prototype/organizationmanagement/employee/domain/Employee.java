package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Employee. */
@Data
@NoArgsConstructor
public class Employee {

  /** The Id. */
  private Long id;

  /** The Name. */
  @NotBlank(message = "name_is_blank")
  private String name;

  /** The Primary email. */
  @NotNull(message = "email_is_null")
  @Email(message = "invalid_email")
  private String primaryEmail;

  /** The Date of birth. */
  private LocalDate dateOfBirth;

  /** The Gender. */
  @NotNull(message = "gender_is_null")
  private Gender gender;

  /** The Department id. */
  @NotNull private Long departmentId;
}
