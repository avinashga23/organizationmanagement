package com.cisco.cleancode.hexagon.prototype.organizationmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Repository config.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.out.persistence")
@EnableJpaAuditing
class RepositoryConfig {

}
