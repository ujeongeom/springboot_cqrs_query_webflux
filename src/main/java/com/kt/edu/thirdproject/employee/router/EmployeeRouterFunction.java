package com.kt.edu.thirdproject.employee.router;

import com.kt.edu.thirdproject.employee.handler.EmployeeHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class EmployeeRouterFunction {
//
//    @RouterOperations({
//            @RouterOperation(path = "/api/v1/employees", method = RequestMethod.GET,
//                    beanClass = EmployeeHandler.class, beanMethod = "getEmployees"),
//            @RouterOperation(path = "/api/v1/employee/{id}", method = RequestMethod.GET,
//                    beanClass = EmployeeHandler.class, beanMethod = "getEmployee",
//                    operation = @Operation(operationId = "getEmployee",
//                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id")})
//            )
//    })
//
//    @Bean
//    public RouterFunction<ServerResponse> routerFunction(EmployeeHandler employeeHandler) {
//        //route() 메서드이 2번째 아규먼트 HandlerFunction
//        // HandlerFunction의 추상메서드 reactor.core.publisher.Mono<T> handle(ServerRequest request)
//        return RouterFunctions
//                .route(GET("/api/v1/employees"), employeeHandler::getEmployees)
//                .andRoute(GET("/api/v1/employee/{id}"), employeeHandler::getEmployee);
//                //.andRoute(POST("/api/v1/employee"), employeeHandler::saveEmployee)
//                //.andRoute(PUT("/api/v1/employe/{id}"), employeeHandler::updateEmployee)
//                //.andRoute(DELETE("/api/v1/employee/{id}"), employeeHandler::deleteEmployee);
//
//    }
}