package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    // register users
    Optional<Account> findAccountByUsername(String username);

    Optional<Account> findAccountByAccountId(Integer accountId);

    // login users
    @Query("FROM Account WHERE username = :userVar AND password = :passwordVar")
    Optional<Account> loginUser(@Param("userVar") String username, @Param("passwordVar") String password);
}
