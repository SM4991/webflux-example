package com.auriga.webflux.controller;

import com.auriga.webflux.dto.EmployeeDto;
import com.auriga.webflux.entity.Employee;
import com.auriga.webflux.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /* Mono - Get API Example */
    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable Integer id) {
        return employeeRepository.findEmployeeById(id);
    }

    /* Flux - Get API Example */
    @GetMapping("/all")
    private Flux<Employee> getAll() {
        return employeeRepository.findAll();
    }

    /* Mono - Post API Example */
    @PostMapping(value = "/create")
    private Mono<Employee> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        return employeeRepository.save(employee);
    }
}