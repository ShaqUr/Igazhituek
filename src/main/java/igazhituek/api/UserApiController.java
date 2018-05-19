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
    
    @PostMapping("/register")
    public ResponseEntity<User> register(String username, String birth, String email, String password,  
            String felekezet, String where, String sex, String about) {
            User user = new User();
            user.setUsername(username);
            user.setAboutMe(about);
            user.setPassword(password);
            user.setEmail(email);
            ///user.setBase64(picture);
            System.out.println("BIRTH: " + birth);
            user.setSex(sex);
            user.setDenomination(felekezet);
            user.setWhereFrom(where);
            //System.out.println("kep: " + handleFileUpload(picture));
            //user.setAge(birth);
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
   

    @Autowired
    private HttpServletRequest request;
    Boolean handleFileUpload(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadsDir = "/uploads/";
                String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
                if(! new File(realPathtoUploads).exists())
                {
                    new File(realPathtoUploads).mkdir();
                }
                String orgName = file.getOriginalFilename();
                String filePath = realPathtoUploads + orgName;
                File dest = new File(filePath);
                file.transferTo(dest);
            }catch(IOException e){
                return false;
            }
        }
        return true;
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