package com.example.service;

import com.example.entity.*;
import com.example.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // register
    public Account registerAccount(Account account){
        if(account.getUsername().length() > 0 && account.getPassword().length() >= 4){
            Optional<Account> optionalAccount = accountRepository.findAccountByUsername(account.getUsername());
            if(optionalAccount.isPresent()){
                // blank account so controller knows that there is a duplicate
                return new Account("","");
            }
            else{
                return accountRepository.save(account);
            }
        }
        return null;
    }

    public Account loginUser(Account account){

        Optional<Account> optionalAccount = accountRepository.loginUser(account.getUsername(), account.getPassword());
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }

        return null;
    }

    public Account findUserById(Integer accountId){
        Optional<Account> optionalAccount = accountRepository.findAccountByAccountId(accountId);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }

        return null;
    }
}
