package com.auriga.webflux.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "money_deposit_event")
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Setter
@Getter
@Data
@ToString
public class MoneyDepositEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer accountNumber;
    private Integer amount;

}
