package com.example.controller;

import com.example.entity.*;
import com.example.repository.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService){
        this.messageService = messageService;
    }

    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }



    /* 
    @Autowired
    public SocialMediaController(MessageService messageService){
        this.messageService = messageService;
    }

    */

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

    @PostMapping(value = "/messages")
    public ResponseEntity createMessageHandler(@RequestBody Message message){
        Message returnedMessage = messageService.createMessage(message);
        if(returnedMessage != null){
            return ResponseEntity.status(200).body(returnedMessage);
        }
        return ResponseEntity.status(400).body(null);
    }

    @GetMapping(value = "/messages")
    public ResponseEntity getAllMessagesHandler(){
        List<Message> returnedMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(returnedMessages);
    }

    @GetMapping(value = "/messages/{message_id}")
    public ResponseEntity getMessageByIdHandler(@PathVariable int message_id){
        Message returnedMessage = messageService.getMessageByMessageId(message_id);
        if(returnedMessage != null){
            return ResponseEntity.status(200).body(returnedMessage);
        }
        else{
            return ResponseEntity.status(200).body(null);
        }
    }

    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity deleteMessageByMessageIdHandler(@PathVariable int message_id){
        int rowsAffected = messageService.deleteMessageByMessageId(message_id);
        if(rowsAffected == 1){
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(200).body(null);
        }
    }

    
    @PatchMapping(value = "/messages/{message_id}")
    public ResponseEntity updateMessageByMessageIdHandler(@RequestBody String newMsg, @PathVariable int message_id){
        int rowsAffected = messageService.updateMessageByMessageId(newMsg, message_id);
        if(rowsAffected > 0){
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping(value = "/accounts/{account_id}/messages")
    public ResponseEntity getAllMessagesByAccountIdHandler(@PathVariable int account_id){
        List<Message> returnedMessages = messageService.getAllMessagesByAccountId(account_id);
        return ResponseEntity.status(200).body(returnedMessages);
    }
        
}
