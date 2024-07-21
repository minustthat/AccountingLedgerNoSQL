package org.minustthat.theledge.Controllers;

import org.minustthat.theledge.Models.Transaction;
import org.minustthat.theledge.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

TransactionRepository transactionRepository;
LocalDateTime today;
@Autowired
public TransactionController(TransactionRepository transactionRepository){
    this.transactionRepository = transactionRepository;
    today = LocalDateTime.now();
}
    @GetMapping()
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    @GetMapping("/thisMonth")
    public List<Transaction> getTransactionsThisMonth(){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionDate().getMonth() == today.getMonth())
            .toList();
    }

    @GetMapping("/thisYear")
    public List<Transaction> getTransactionsThisYear(){
        return transactionRepository.findAll().stream()
                .filter(t-> t.getTransactionDate().getYear() == today.getYear())
                .toList();
    }

    @GetMapping("{year}")
    public List<Transaction> getTransactionsByYear(@PathVariable int year){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionDate().getYear() == year)
            .toList();
    }

    @PostMapping("/transaction")
    public Transaction addTransaction(@RequestBody Transaction transaction){
    return transactionRepository.save(transaction);
    }

    @GetMapping("/purchases")
    public List<Transaction> getPurchases(){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionType().equals("purchase"))
            .toList();
    }

    @GetMapping("/deposits")
    public List<Transaction> getDeposits(){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionType().equals("deposit"))
            .toList();
    }

}
