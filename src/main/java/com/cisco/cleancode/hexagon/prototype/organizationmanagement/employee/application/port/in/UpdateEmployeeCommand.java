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

/** The type Update employee request. */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeCommand {

  /** The Id. */
  @NotNull(message = "null_id")
  private Long id;

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

  /** The Gender. */
  @NotNull(message = "gender_is_null")
  private Gender gender;
}
