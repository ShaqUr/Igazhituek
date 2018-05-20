/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.repository;

import igazhituek.model.ChatMessage;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aram
 */
@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {

    Optional<ChatMessage> findById(int id);

    Optional<ChatMessage> findBySenderAndReceiver(int sender, int receiver);

    Iterable<ChatMessage> findAll();
}
