package com.auriga.webflux.controller;

import com.auriga.webflux.dto.EmployeeDto;
import com.auriga.webflux.entity.Employee;
import jdk.jfr.ContentType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/webClient")
public class WebClientController {
    WebClient client = WebClient.create("http://localhost:8083");

    /* Webclient - Get API Example */
    @GetMapping("/employee/{id}/name")
    public Mono<String> getEmployeeName(@PathVariable Integer id) {
        return client.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .bodyToMono(Employee.class)
                .map(new Function<Employee, String>() {
                    @Override
                    public String apply(Employee employee){
                        return employee.getName();
                    }
                });
    }

    /* Webclient - Post API Example */
    @PostMapping("/employee/create")
    public Mono<String> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return client.post()
                .uri("/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .retrieve()
                .bodyToMono(Employee.class)
                .map(new Function<Employee, String>() {
                    @Override
                    public String apply(Employee employee) {
                        return employee.getName();
                    }
                });
    }
}
