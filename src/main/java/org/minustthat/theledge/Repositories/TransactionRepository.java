package org.minustthat.theledge.Repositories;

import org.minustthat.theledge.Models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface TransactionRepository extends MongoRepository<Transaction, BigInteger> {


}
