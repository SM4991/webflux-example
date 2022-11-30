package com.auriga.webflux.repository;

import com.auriga.webflux.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface EmployeeRepository extends R2dbcRepository<Employee, Integer> {
    Mono<Employee> findEmployeeById(Integer id);

    Flux<Employee> findAll();

    Mono<Employee> save(Employee employee);
}
