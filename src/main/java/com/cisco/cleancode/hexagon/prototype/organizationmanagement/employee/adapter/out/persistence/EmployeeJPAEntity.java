package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.domain.Gender;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type Employee jpa entity. */
@Entity(name = "employee")
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
class EmployeeJPAEntity {

  /** The Id. */
@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  /** The Name. */
private String name;

  /** The Email. */
@Column(unique = true)
  private String email;

  /** The Dob. */
private LocalDate dob;

  /** The Gender. */
private Gender gender;

  /** The Department id. */
private Long departmentId;

}
