package org.minustthat.theledge.Controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.minustthat.theledge.Models.Customer;
import org.minustthat.theledge.Models.Transaction;
import org.minustthat.theledge.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@RestController
public class BalanceController {
    Transaction transaction;
    Customer customer;
    TransactionRepository transactionRepository;
    LocalDateTime today;

    @Autowired
    public BalanceController(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
        today = LocalDateTime.now();
    }



}
