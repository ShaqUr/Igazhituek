/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.api;

import igazhituek.exceptions.UserNotValidException;
import igazhituek.exceptions.UsernameOrEmailInUseException;
import igazhituek.model.User;
import igazhituek.service.UserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    
    @Autowired
    private final UserService userService;
    
    public UserApiController(UserService userService) {
        this.userService = userService;
    }
    /*
    @GetMapping
    public ResponseEntity<User> user() {
        if (userService.isLoggedIn()) {
            return ResponseEntity.ok(userService.getUser());
        }
        return ResponseEntity.badRequest().build();
    }
*/
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
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println("login");
        try {
            return ResponseEntity.ok(String.valueOf(userService.login(user)));
        } catch (UserNotValidException e) {
            return ResponseEntity.badRequest().build();
        }
        finally{
            System.out.println("felhasznalo: " + userService.getUsers());
        }
    }
    
    /*
    @PostMapping("/bann")
    public ResponseEntity<String> bann(@RequestBody String username){
        if(this.userService.getUser().getRole().equals(User.Role.ADMIN) && !username.equals(this.userService.getUser().getUsername())){
            this.userService.bann(username);
            return ResponseEntity.ok(username+" banned!");
        }else{
            return ResponseEntity.badRequest().build();
        }
    }*/
    
    @PostMapping("/changepassword")
    public ResponseEntity<User> changePassword(@RequestBody User user, String newpsw){
        try{
            userService.isValid(user);
            if(this.userService.getUsers().contains(user)){
                User u = this.userService.getUserRepository().findByUsername(user.getUsername()).get();
                u.setPassword(user.getPassword());
                this.userService.getUserRepository().save(u);
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.badRequest().build();
            }
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
        
    @GetMapping("/logout")
    public ResponseEntity logout(User user) {
        User u = userService.getUserRepository().findByUsername(user.getUsername()).get();
        System.out.println("kilep: " + u);
        this.userService.getUsers().remove(u);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/lol")
    public ResponseEntity<String> lol(){
        return ResponseEntity.ok("lefutottam");
    }
    
    public ResponseEntity<User> register(@RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.register(user));
        }catch(UsernameOrEmailInUseException e){
            return ResponseEntity.badRequest().build();
        }
        
    }
    
    @GetMapping("/isloggedin")
    public ResponseEntity<Boolean> isloggedin(Integer id){
        User u = userService.getUserRepository().findById(id).get();
        return ResponseEntity.ok(userService.getUsers().contains(u));
    }
    
    @PostMapping("/like")
    public ResponseEntity<User> like (User loggedinUser, int id){
        System.out.println(loggedinUser.getUsername() + "like: " + id);
        User loggedin = userService.getUserRepository().findByUsername(loggedinUser.getUsername()).get();
        User user = userService.getUserRepository().findById(id).get();
        System.out.println(user);
        loggedin.getLikes().add(user);
        return ResponseEntity.ok(user);
    }
    
    
    @PostMapping("/dislike")
    public ResponseEntity<User> dislike (User loggedinUser, int id){
        System.out.println(loggedinUser.getUsername() + " dislike: " + id);
        User loggedin = userService.getUserRepository().findByUsername(loggedinUser.getUsername()).get();
        User user = userService.getUserRepository().findById(id).get();
        System.out.println(user);
        loggedin.getDislikes().add(user);
        return ResponseEntity.ok(user);
    }
    
    
}