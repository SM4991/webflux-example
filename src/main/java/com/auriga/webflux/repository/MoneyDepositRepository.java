package com.auriga.webflux.repository;

import com.auriga.webflux.entity.MoneyDepositEvent;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyDepositRepository extends R2dbcRepository<MoneyDepositEvent, Integer> {
}
