/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.repository;

/**
 *
 * @author Aram
 */
import igazhituek.model.User;
import igazhituek.model.User.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
   
    
    Optional<User> findByRole(Role role);
    
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
    
    @Override
    Iterable<User> findAll();
    
    Optional<User> findByUsernameAndPassword(String username, String password);
}