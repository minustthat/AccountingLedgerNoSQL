package org.minustthat.theledge.Controllers;

import org.minustthat.theledge.Models.AuthenticatedUser;
import org.minustthat.theledge.Models.Customer;
import org.minustthat.theledge.Models.Transaction;
import org.minustthat.theledge.Repositories.AuthenticatedUserRepository;
import org.minustthat.theledge.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionController {
    @Autowired
    AuthenticatedUserRepository authenticatedUserRepository;
TransactionRepository transactionRepository;
LocalDateTime today;
    Transaction transaction;
    Customer customer;
@Autowired
public TransactionController(TransactionRepository transactionRepository){
    this.transactionRepository = transactionRepository;
    today = LocalDateTime.now();
}
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getAllTransactions(Principal principal){
        return transactionRepository.findAll();

    }

    @GetMapping("/thisMonth")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsThisMonth(){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionDate().getMonth() == today.getMonth())
            .toList();
    }

    @GetMapping("/thisYear")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsThisYear(){
        return transactionRepository.findAll().stream()
                .filter(t-> t.getTransactionDate().getYear() == today.getYear())
                .toList();
    }

    @GetMapping("/{year:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsByYear(@PathVariable Integer year){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionDate().getYear() == year)
            .toList();
    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction addTransaction(@RequestBody Transaction transaction){
    return transactionRepository.save(transaction);
    }

    @GetMapping("/purchases")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getPurchases(){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionType().equals("purchase"))
            .toList();
    }

    @GetMapping("/deposits")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getDeposits(){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionType().equals("deposit"))
            .toList();
    }

    @GetMapping("/{vendor:.*\\D.*}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getByVendor(@PathVariable String vendor){
    return transactionRepository.findAll().stream()
            .filter(t-> t.getVendor().equalsIgnoreCase(vendor))
            .toList();
    }


    @GetMapping("/balance")
    public double getBalance(Principal principal){
    String username = principal.getName();
    Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
    AuthenticatedUser foundUser = user.get();
    List<Transaction> transactions = getAllTransactions(principal);
    foundUser.setTransactions(transactions);
    double balance = foundUser.getBalance();
    for(Transaction t : foundUser.getTransactions()){
        if(t.getTransactionType().equalsIgnoreCase("Deposit") && t.getCustomerId() == foundUser.getCustomerId()){
            foundUser.setBalance(balance += t.getAmount());
        } else if(t.getTransactionType().equalsIgnoreCase("Purchase") && t.getCustomerId() == foundUser.getCustomerId()){
            foundUser.setBalance(balance -= t.getAmount());
        }
    }
    return balance;
    }
}
