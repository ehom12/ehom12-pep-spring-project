package com.example.service;

import com.example.entity.*;
import com.example.repository.*;

import antlr.debug.MessageAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


@Transactional
@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;


    @Autowired
    public void setAccountRepository(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }



    /* 
    @Autowired
    public MessageService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    */


    // createMessage
    public Message createMessage(Message message){
        if(message.getMessageText().length() > 0 && message.getMessageText().length() < 255){
            Optional<Account> optionalAccount = accountRepository.findAccountByAccountId((Integer) message.getPostedBy());
            if(optionalAccount.isPresent()){
                // blank message so controller knows that there is a duplicate
                return messageRepository.save(message);
                
            }
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(int messageId){
        Optional<Message> optionalMessage = messageRepository.findMessageByMessageId(messageId);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }

    public int deleteMessageByMessageId(int messageId){
        int rowsAffected = messageRepository.deleteByMessageId(messageId);
        if(rowsAffected == 1){
            return 1;
        }
        else{
            return 0;
        }
    }

    public Integer updateMessageByMessageId(Message message){
        int rowsAffected = messageRepository.updateByMessageId(message.getMessageId(), message.getMessageText());
        if(rowsAffected == 1){
            return 1;
        }
        else{
            return 0;
        }
    }

    public List<Message> getAllMessagesByAccountId(int accountId){
        return null;
    }

}
