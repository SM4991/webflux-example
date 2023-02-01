package com.auriga.webflux.controller;

import com.auriga.webflux.dto.DepositRequestDto;
import com.auriga.webflux.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bank-service")
public class BankServiceController {
    @Autowired
    private BankService bankService;

    /* Deposit money to account - without transactions */
    @PostMapping("/deposit")
    public Mono<Void> deposit(@RequestBody DepositRequestDto request) {
        return bankService.deposit(request);
    }

    /* Deposit money to account - with transactions */
    @PostMapping("/transactional-deposit")
    public Mono<Void> depositTransactional(@RequestBody DepositRequestDto request) {
        return bankService.depositTransactional(request);
    }
}
