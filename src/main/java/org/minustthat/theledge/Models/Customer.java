package org.minustthat.theledge.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer {
    BigDecimal id;
    String userName;
    String password;
    String email;
    String address;
    String zipCode;
    String phoneNumber;
    String balance;
}
