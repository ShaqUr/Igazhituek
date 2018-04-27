/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.api;

import igazhituek.exceptions.UserNotValidException;
import igazhituek.exceptions.UsernameOrEmailInUseException;
import igazhituek.model.User;
import static igazhituek.model.User.Role.ADMIN;
import static igazhituek.model.User.Role.USER;
import igazhituek.service.UserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aram
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;
    
    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<User> user() {
        if (userService.isLoggedIn()) {
            return ResponseEntity.ok(userService.getUser());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("search")
    public ResponseEntity<Iterable<String> > search(@RequestBody String username){
        try{
        return ResponseEntity.ok(userService.getUsername(username));
        }catch (NoSuchElementException nsee){
            return ResponseEntity.ok(new HashSet<>(Arrays.asList("Nincs ilyen felhasználó!")));
        }
    }
    @PostMapping("searchall")
    public ResponseEntity<Iterable<String> > search(){
        return ResponseEntity.ok(userService.getUsernames());
    }
    
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.login(user));
        } catch (UserNotValidException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/bann")
    public ResponseEntity<String> bann(@RequestBody String username){
        if(this.userService.getUser().getRole().equals(User.Role.ADMIN) && !username.equals(this.userService.getUser().getUsername())){
            this.userService.bann(username);
            return ResponseEntity.ok(username+" banned!");
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/changepassword")
    public ResponseEntity<User> changePassword(@RequestBody User user){
        if(this.userService.getUser().getUsername().equals(user.getUsername())){
            User u = this.userService.getUserRepository().findByUsername(user.getUsername()).get();
            u.setPassword(user.getPassword());
            this.userService.getUserRepository().save(u);
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestBody User user) {
        this.userService.setUser(null);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.register(user));
        }catch(UsernameOrEmailInUseException e){
            return ResponseEntity.badRequest().build();
        }
        
    }
    
}