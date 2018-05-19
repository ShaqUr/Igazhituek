/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.api;

import igazhituek.model.ChatMessage;
import igazhituek.service.ChatService;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aram
 */

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {
   @Autowired
   ChatService chatService;
   
   public ChatApiController(ChatService chatService){
       this.chatService = chatService;
   }
   
   @GetMapping("/messages")
   public ResponseEntity<List<ChatMessage>> messages(Integer sender, Integer receiver){
       System.out.println(sender + " és " + receiver + " uzenetei");
       Iterable<ChatMessage> chatmesss = chatService.getChatRepository().findAll();
       List<ChatMessage> ret = new LinkedList<>();
       System.out.println("haliho");
       for(ChatMessage ch : chatmesss){
           if((ch.getSender()==sender && ch.getReceiver()==receiver) || ch.getSender()==receiver && ch.getReceiver()==sender){
               ret.add(ch);
           }
       }
      return ResponseEntity.ok(ret);
   }
   
   @PostMapping("/savemessage")
   public ResponseEntity<List<ChatMessage>> messages(Integer sender, Integer receiver, String message){
       ChatMessage msg = new ChatMessage();
       msg.setReceiver(receiver);
       msg.setSender(sender);
       msg.setMessage(message);
       Timestamp timestamp = new Timestamp(System.currentTimeMillis());
       msg.setTimestamp(timestamp.getTime());
       chatService.getChatRepository().save(msg);
       return messages(sender, receiver);
   }
}
