package com.kt.edu.thirdproject.employee.query.service;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.exception.EmployeeException;
import com.kt.edu.thirdproject.employee.query.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    //@Cacheable("employees")
    public Flux<EmployeeEntity> getEmployeeList() {
        return employeeRepository.findAll();
    }

    //@Cacheable("employee")
    @Transactional(readOnly = true)
    public Mono<EmployeeEntity> getEmployee(Long id) {
        return employeeRepository.findById(id)
                .switchIfEmpty(Mono.error(
                                new EmployeeException("Employee Not Found with id " + id, HttpStatus.NOT_FOUND)
                        )
                );
    }

}
