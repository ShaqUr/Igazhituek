/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.api;

import igazhituek.model.ChatMessage;
import igazhituek.service.ChatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
   public ResponseEntity<List<ChatMessage>> messages(Integer senderID, Integer receiverId){
       Iterable senderMessages = chatService.getChatRepository().findBySenderAndReceiver(senderID, receiverId);
       Iterable receiverMessages = chatService.getChatRepository().findBySenderAndReceiver(receiverId, senderID);
       
       //for(ChatMessage msg : senderMessages){
           
      // }
      return ResponseEntity.ok(null);
   }
}
