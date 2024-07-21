package org.minustthat.theledge.Repositories;

import org.minustthat.theledge.Models.AuthenticatedUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticatedUserRepository extends MongoRepository<AuthenticatedUser,String> {
  Optional<AuthenticatedUser> findByUsername(String username);
}
