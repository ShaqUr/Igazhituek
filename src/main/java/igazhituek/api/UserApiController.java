/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.api;

import igazhituek.exceptions.UserNotValidException;
import igazhituek.exceptions.UsernameOrEmailInUseException;
import igazhituek.model.Dislikes;
import igazhituek.model.Likes;
import igazhituek.model.User;
import igazhituek.service.UserService;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        System.out.println("login");
        try {
            return ResponseEntity.ok(userService.login(user));
        } catch (UserNotValidException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/changepassword")
    public ResponseEntity<User> changePassword(@RequestBody User user, String newpsw){
        try{
            userService.isValid(user);
            if(this.userService.isValid(user)){
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
    
    
    @GetMapping("/matches")
    public ResponseEntity<List<User>> mathces(Integer userID){
        User u = userService.getUserRepository().findById(userID).get();
        List<User> wholikeduser = new LinkedList<>();
        List<User> userLiked = new LinkedList<>();
        for(Likes like : userService.getLikesRepository().findAll()){
            if(like.getWho()==userID){
                wholikeduser.add(userService.getUserRepository().findById(like.getLiker()).get());
            }
        }
        for(Likes like : userService.getLikesRepository().findAll()){
            if(like.getLiker()==userID){
                userLiked.add(userService.getUserRepository().findById(like.getWho()).get());
            }
        }
        for(User usr : wholikeduser){
            if(!userLiked.contains(usr)){
                wholikeduser.remove(usr);
            }
        }
        return ResponseEntity.ok(wholikeduser);
    }
    
    
    @GetMapping("/lol")
    public ResponseEntity<String> lol(){
        return ResponseEntity.ok("lefutottam");
    }
    
    static class Model{
        public String username;
        public String picture;
        public String birth;
        public String email; 
        public String password;  
        public String felekezet; 
        public String where; 
        public String sex; 
        public String about;
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody Model model) {
            User user = new User();
            user.setUsername(model.username);
            user.setAboutMe(model.about);
            user.setPassword(model.password);
            user.setEmail(model.email);
            user.setBase64(model.picture);
            System.out.println("BIRTH: " + model.birth);
            user.setSex(model.sex);
            user.setDenomination(model.felekezet);
            user.setWhereFrom(model.where);
            user.setAge(Integer.parseInt(model.birth));
        try{
            return ResponseEntity.ok(userService.register(user));
        }catch(UsernameOrEmailInUseException e){
            return ResponseEntity.badRequest().build();
        }
        
    }
    

    @PostMapping("/like")
    public ResponseEntity<String> like (Integer userID, Integer likedId){
        System.out.println(userID + " like: " + likedId);
        Likes like = new Likes();
        like.setLiker(userID);
        like.setWho(likedId);
        userService.getLikesRepository().save(like);
        return ResponseEntity.ok("liked");
    }
       
    @GetMapping("/notliked")
    public ResponseEntity<LinkedList<User>> getNotLiked(Integer userID){
        User user = userService.getUserRepository().findById(userID).get();
        LinkedList<User> users = new LinkedList<>();
        for(User u :  userService.getUserRepository().findAll()){
            users.add(u);
        }
        for(Likes like : userService.getLikesRepository().findAll()){
            if(like.getLiker() == user.getId()){
                users.remove(userService.getUserRepository().findById(like.getWho()).get());
            }
        }
        for(Dislikes dislike : userService.getDislikesRepository().findAll()){
            if(dislike.getDisliker() == user.getId()){
                users.remove(userService.getUserRepository().findById(dislike.getWho()).get());
            }
        }
        users.remove(user);
        return ResponseEntity.ok(users);  
    } 
    
    @PostMapping("/dislike")
    public ResponseEntity<String> dislike (Integer userID, Integer likedId){
        System.out.println(userID + " dislike: " + likedId);
        Dislikes dislike = new Dislikes();
        dislike.setDisliker(userID);
        dislike.setWho(likedId);
        userService.getDislikesRepository().save(dislike);
        return ResponseEntity.ok("disliked");
    }
    
}