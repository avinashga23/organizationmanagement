package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** The interface Employee jpa entity repository. */
interface EmployeeJPAEntityRepository extends JpaRepository<EmployeeJPAEntity, Long> {

  /**
   * Find by email optional.
   *
   * @param email the email
   * @return the optional
   */
  Optional<EmployeeJPAEntity> findByEmail(String email);

  /**
   * Exists by email boolean.
   *
   * @param email the email
   * @return the boolean
   */
  boolean existsByEmail(String email);
}
