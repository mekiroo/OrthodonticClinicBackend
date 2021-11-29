package com.project.orthodonticclinic.user.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUsername(String username);

    Boolean existsAccountByUsername(String username);
}
