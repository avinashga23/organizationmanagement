package com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.adapter.in.web;

import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.exception.EmployeeNotFoundException;
import com.cisco.cleancode.hexagon.prototype.organizationmanagement.employee.application.port.in.SaveEmployeeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/** The type Save employee rest controller. */
@RestController
@RequestMapping("/employee/")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class SaveEmployeeRestController {

  /** The Save employee use case. */
private final SaveEmployeeUseCase saveEmployeeUseCase;

  /** The Mapper. */
private final WebMapper mapper;

  /**
   * Create employee response entity.
   *
   * @param createEmployeeRequestDto the create employee request
   * @param uriComponentsBuilder the uri components builder
   * @return the response entity
   * @throws EmployeeFoundException the employee found exception
   */
@PostMapping
  ResponseEntity<EmployeeDto> createEmployee(@RequestBody CreateEmployeeRequestDto createEmployeeRequestDto,
      UriComponentsBuilder uriComponentsBuilder) throws EmployeeFoundException {
    log.info("creating employee: {}", createEmployeeRequestDto);
    var created = mapper.domainToDto(saveEmployeeUseCase.createEmployee(mapper.dtoToCommand(createEmployeeRequestDto)));
    var location = uriComponentsBuilder.path("/employee/{id}").buildAndExpand(created.getId()).toUri();

    return ResponseEntity.created(location).body(created);
  }

  /**
   * Update employee employee dto.
   *
   * @param id the id
   * @param updateEmployeeRequestDto the update employee request
   * @return the employee dto
   * @throws EmployeeNotFoundException the employee not found exception
   */
@PutMapping("{id}")
  EmployeeDto updateEmployee(@PathVariable Long id,
      @RequestBody UpdateEmployeeRequestDto updateEmployeeRequestDto) throws EmployeeNotFoundException {
    log.info("updating employee: {}", updateEmployeeRequestDto);
    updateEmployeeRequestDto.setId(id);
    return mapper.domainToDto(saveEmployeeUseCase.updateEmployee(mapper.dtoToCommand(updateEmployeeRequestDto)));
  }

  /**
   * Delete employee.
   *
   * @param id the id
   * @throws EmployeeNotFoundException the employee not found exception
   */
@DeleteMapping("{id}")
  void deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
    log.info("deleting employee by id {}", id);
    saveEmployeeUseCase.deleteEmployee(id);
  }

}
