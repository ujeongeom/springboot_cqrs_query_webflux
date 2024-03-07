package com.kt.edu.thirdproject.employee.handler;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.exception.EmployeeException;
import com.kt.edu.thirdproject.employee.query.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeHandler {

    private final EmployeeRepository employeeRepository;
    private Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();

    public Mono<ServerResponse> getEmployees(ServerRequest request) {
        Flux<EmployeeEntity> customerFlux = employeeRepository.findAll();
        return ServerResponse.ok() //ServerResponse.BodyBuilder
                .contentType(MediaType.APPLICATION_JSON) //ServerResponse.BodyBuilder / json현태로 반환
                .body(customerFlux, EmployeeEntity.class); //Mono<ServerResponse> // json으로 던저주고, 내부적으로는 잭슨(?)
    }

    public Mono<ServerResponse> getEmployee(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return employeeRepository.findById(id)
                .flatMap(employee -> ServerResponse.ok()  //ServerResponse.BodyBuilder / flatMap: 데이터를 평탄화 하는 것
                        .contentType(MediaType.APPLICATION_JSON)  //ServerResponse.BodyBuilder
                        .body(BodyInserters.fromValue(employee))
                ).switchIfEmpty(getError(id));
    }

    private Mono<ServerResponse> getError(Long id) {
        return Mono.error(new EmployeeException("Employee Not Found with id " + id, HttpStatus.NOT_FOUND));
    }

}