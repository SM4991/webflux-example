package com.auriga.webflux.service;


import com.auriga.webflux.dto.DepositRequestDto;
import com.auriga.webflux.entity.MoneyDepositEvent;
import com.auriga.webflux.repository.AccountRepository;
import com.auriga.webflux.repository.MoneyDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
public class BankService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MoneyDepositRepository eventRepository;


    /* Deposit amount in account and add deposit event entry */
    public Mono<Void> deposit(DepositRequestDto request){
        return this.accountRepository.findById(request.getAccount())
                .doOnNext(ac -> ac.setBalance(ac.getBalance() + request.getAmount()))
                .flatMap(this.accountRepository::save)
                .thenReturn(toEvent(request))
                .flatMap(this.eventRepository::save)
                .doOnError(System.out::println)
                .then();
    }

    /* Deposit amount in account and add deposit event entry with transactions */
    @Transactional
    public Mono<Void> depositTransactional(DepositRequestDto request){
        return this.accountRepository.findById(request.getAccount())
                .doOnNext(ac -> ac.setBalance(ac.getBalance() + request.getAmount()))
                .flatMap(this.accountRepository::save)
                .thenReturn(toEvent(request))
                .flatMap(this.eventRepository::save)
                .doOnError(err -> {
                    System.out.println("Exception: "+err.getMessage());
                })
                .then();
    }

    // create money deposit event from request
    private MoneyDepositEvent toEvent(DepositRequestDto request){
        return MoneyDepositEvent.create(
                null,
                request.getAccount(),
                request.getAmount()
        );
    }

}
