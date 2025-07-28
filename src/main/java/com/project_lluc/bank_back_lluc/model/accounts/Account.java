package com.project_lluc.bank_back_lluc.model.accounts;

import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money balance;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee"))
    private Money penaltyFee = new Money(new BigDecimal("40"));

    @ManyToOne
    private AccountHolder primaryOwner;

    @ManyToOne
    private AccountHolder secondaryOwner;

    private LocalDate creationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
}
