package org.minustthat.theledge.Controllers;

import org.minustthat.theledge.Models.AuthenticatedUser;
import org.minustthat.theledge.Models.Customer;
import org.minustthat.theledge.Models.Transaction;
import org.minustthat.theledge.Repositories.AuthenticatedUserRepository;
import org.minustthat.theledge.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
@EnableMethodSecurity
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
    @PreAuthorize("isAuthenticated()")
    public List<Transaction> getAllTransactions(Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
        return transactionRepository.findAll()
                .stream()
                .filter(t-> t.getCustomerId() == foundUser.getCustomerId())
                .toList();

    }

    @GetMapping("/thisMonth")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<Transaction> getTransactionsThisMonth(Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionDate().getMonth() == today.getMonth() && t.getCustomerId() == foundUser.getCustomerId())
            .toList();
    }

    @GetMapping("/thisYear")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<Transaction> getTransactionsThisYear(Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
        return transactionRepository.findAll().stream()
                .filter(t-> t.getTransactionDate().getYear() == today.getYear() && t.getCustomerId() == foundUser.getCustomerId())
                .toList();
    }

    @GetMapping("/{year:\\d+}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactionsByYear(@PathVariable Integer year, Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionDate().getYear() == year && t.getCustomerId() == foundUser.getCustomerId())
            .toList();
    }

    @PostMapping("/transaction")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction addTransaction(@RequestBody Transaction transaction, Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
    transaction.setCustomerId(foundUser.getCustomerId());
    return transactionRepository.save(transaction);
    }

    @GetMapping("/purchases")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getPurchases(Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionType().equals("purchase") && t.getCustomerId() == foundUser.getCustomerId())
            .toList();
    }

    @GetMapping("/deposits")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getDeposits(Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
    return transactionRepository.findAll().stream()
            .filter(t-> t.getTransactionType().equals("deposit") && t.getCustomerId() == foundUser.getCustomerId())
            .toList();
    }

    @GetMapping("/{vendor:.*\\D.*}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getByVendor(@PathVariable String vendor, Principal principal){
        String username = principal.getName();
        Optional<AuthenticatedUser> user = authenticatedUserRepository.findByUsername(username);
        AuthenticatedUser foundUser = user.get();
    return transactionRepository.findAll().stream()
            .filter(t-> t.getVendor().equalsIgnoreCase(vendor) && t.getCustomerId() == foundUser.getCustomerId())
            .toList();
    }


    @GetMapping("/balance")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
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
