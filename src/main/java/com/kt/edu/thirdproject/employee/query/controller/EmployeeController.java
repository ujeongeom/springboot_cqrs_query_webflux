package com.kt.edu.thirdproject.employee.query.controller;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.exception.EmployeeException;
import com.kt.edu.thirdproject.employee.query.repository.EmployeeRepository;
import com.kt.edu.thirdproject.employee.query.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Employee", description = "Employee API")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins ="*")
@RequestMapping("/api/v1/")
public class EmployeeController {

    private final EmployeeService employeeService;
    //private final EmployeeRepository employeeRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employees")
    @Operation(summary ="임직원 전체 조회",description="임직원 전체를 조회 합니다.")
    public Flux<EmployeeEntity> getEmployeeList() {
        return employeeService.getEmployeeList();
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary ="임직원 단건 조회",description="특정 임직원 단건에 대한 정보 조회 합니다.")
    @GetMapping("/employees/{id}")
    // 플러시를 생략해서 dirty checking등을 하지 않으므로 약간의 성능 향상
    public Mono<EmployeeEntity> getEmployee(@PathVariable Long id) {
            return employeeService.getEmployee(id);
    }
      /*  return employeeRepository.findById(id)
                .switchIfEmpty(Mono.error(
                                new EmployeeException("Employee Not Found with id " + id, HttpStatus.NOT_FOUND)
                        )
                );
    }*/

}
