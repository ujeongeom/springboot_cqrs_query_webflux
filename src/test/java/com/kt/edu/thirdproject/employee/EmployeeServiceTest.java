package com.kt.edu.thirdproject.employee;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;

@DataR2dbcTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void getEmployeeListTest() {
        Flux<EmployeeEntity>  employee =  employeeRepository.findAll();

        Flux<String> mapStream = employee.map(emp -> emp.getEmpName());
        mapStream.subscribe(System.out::println);

    }
}
