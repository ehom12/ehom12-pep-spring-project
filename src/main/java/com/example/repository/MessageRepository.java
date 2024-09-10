package com.example.repository;

import com.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


public interface MessageRepository extends JpaRepository<Message, Long>{
/* 
    @Query("FROM Message")
    List<Message> findMessages();
    */

    int deleteByMessageId(int id);

    @Modifying
    @Query("UPDATE Message set messageText =:messageTextVar WHERE messageId = :messageVar")
    int updateByMessageId(@Param("messageId") int messageVar, @Param("messageText") String messageTextVar);

    Optional<Message> findMessageByMessageId(int messageId);

    @Query("FROM Message WHERE postedBy =:accountId")
    List<Message> findMessagesByAccountId(@Param("postedBy") int accountId);
}
