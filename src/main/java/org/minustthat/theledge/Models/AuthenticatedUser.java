package org.minustthat.theledge.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection="user")
public class AuthenticatedUser {
    @Indexed
    private String username;
    private String password;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private String balance;
}
