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

public class User {
    BigDecimal id;
    String username;
    String password;
    String role;
    boolean enabled;
}
