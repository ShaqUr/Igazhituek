/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.repository;

import igazhituek.model.ChatMassage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aram
 */
@Repository
public interface ChatMassageRepository extends CrudRepository<ChatMassage, String>  {
    
}
