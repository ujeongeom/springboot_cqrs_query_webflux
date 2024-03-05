package com.kt.edu.thirdproject.employee.query.repository;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeEntity, Long> {
}


