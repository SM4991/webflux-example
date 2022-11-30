package com.auriga.webflux.controller;

import com.auriga.webflux.EmployeeDto;
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

    @GetMapping("/employee/{id}")
    public void getEmployee(@PathVariable Integer id) {
        Mono<Employee> employeeMono = client.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .bodyToMono(Employee.class);

        employeeMono.subscribe(System.out::println);
    }

    @GetMapping("/employee/{id}/name")
    public void getEmployeeName(@PathVariable Integer id) {
        Mono<String> employeeMono = client.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .bodyToMono(Employee.class)
                .map(new Function<Employee, String>() {
                    @Override
                    public String apply(Employee employee){
                        return employee.getName();
                    }
                });

        employeeMono.subscribe(System.out::println);
    }

    @GetMapping("/employee/all")
    public void getAllEmployee() {
        Flux<Employee> employeeMono = client.get()
                .uri("/employees/all")
                .retrieve()
                .bodyToFlux(Employee.class);

        employeeMono.subscribe(System.out::println);
    }

    @GetMapping("/employee/random/id")
    public Mono<String> getRandomEmployeeId() {
        return client.get()
                .uri("/employees/random/uuid")
                .retrieve()
                .bodyToMono(String.class);
    }

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
