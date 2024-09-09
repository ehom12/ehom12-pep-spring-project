package com.example.controller;

import com.example.entity.*;
import com.example.repository.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity registrationHandler(@RequestBody Account account){
        Account returnedAccount = accountService.registerAccount(account);
        // conflict (duplicate username)
        if(returnedAccount != null && returnedAccount.getUsername().length() == 0){
            return ResponseEntity.status(409).body(null);
        }
        // normal path, successfull registration of user
        else if(returnedAccount != null){
            return ResponseEntity.status(200).body(null);
        }
        // client error
        return ResponseEntity.status(400).body(null);
    }

    @PostMapping(value = "/login")
    public ResponseEntity loginHandler(@RequestBody Account account){
        Account returnedAccount = accountService.loginUser(account);
        if(returnedAccount != null){
            return ResponseEntity.status(200).body(returnedAccount);
        }
        return ResponseEntity.status(401).body(null);
    }
}
