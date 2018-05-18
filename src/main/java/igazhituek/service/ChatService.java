/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.service;

import igazhituek.repository.ChatMessageRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Aram
 */
@Service
@SessionScope
@Data
public class ChatService {
    @Autowired
    private ChatMessageRepository chatRepository;
    
}
