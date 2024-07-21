package org.minustthat.theledge.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.math.BigInteger;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@Document(collection="Transactions")
public class Transaction {
@Id
@JsonIgnore
    private BigInteger id;
    private int customerId;
    private int amount;
    private LocalDateTime transactionDate;
    @Indexed
    private String vendor;
    private String transactionType;
}
