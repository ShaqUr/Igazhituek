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
    public ResponseEntity<User> login(@RequestBody User user) {
        System.out.println("login");
        try {
            return ResponseEntity.ok(userService.login(user));
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
    
    @GetMapping("/matches")
    public ResponseEntity<LinkedList<User>> mathces(Integer userID){
        User u = userService.getUserRepository().findById(userID).get();
        List<User> likes = u.getLikes();
        LinkedList<User> matches = new LinkedList<>();
        for(User usr : likes){
            if(usr.getLikes().contains(u)){
                matches.add(usr);
            }
        }
        return ResponseEntity.ok(matches);
    }
    
    @GetMapping("/logout")
    public ResponseEntity logout(int id) {
        User u = userService.getUserRepository().findById(id).get();
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
        System.out.println("kapott ID: " + id);
        //System.out.println(userService.getUserRepository().findById(id).get());
        User u = userService.getUserRepository().findById(id).get();
        //System.out.println(userService.getUsers().contains(u));
        return ResponseEntity.ok(userService.getUsers().contains(u));
    }
    
    @PostMapping("/like")
    public ResponseEntity<User> like (Integer userID, Integer likedId){
        System.out.println(userID + " like: " + likedId);
        User loggedin = userService.getUserRepository().findById(userID).get();
        User user = userService.getUserRepository().findById(likedId).get();
        //System.out.println(user);
        loggedin.getLikes().add(user);
        System.out.println(loggedin.getLikes().hashCode());
        userService.getUserRepository().save(loggedin);
        return ResponseEntity.ok(user);
    }
   

    @Autowired
    private HttpServletRequest request;


    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
        public
        @ResponseBody
        ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
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
                    
                }
            }
            return ResponseEntity.ok("");
        }
        
    @GetMapping("/notliked")
    public ResponseEntity<LinkedList<User>> getNotLiked(int userID){
        User user = userService.getUserRepository().findById(userID).get();
        LinkedList<User> users = new LinkedList<>();
        for(User u :  userService.getUserRepository().findAll()){
            users.add(u);
        }
        for(User u : user.getLikes()){
            System.out.println("getlikesban vagyok bro");
            users.remove(u);
        }
        for(User u : user.getDislikes()){
            users.remove(u);
        }
        users.remove(user);
        return ResponseEntity.ok(users);  
    } 
    
    @PostMapping("/dislike")
    public ResponseEntity<User> dislike (Integer userID, Integer likedId){
        System.out.println(userID + " dislike: " + likedId);
        User loggedin = userService.getUserRepository().findById(userID).get();
        User user = userService.getUserRepository().findById(likedId).get();
        System.out.println(user);
        loggedin.getDislikes().add(user);
        userService.getUserRepository().save(loggedin);
        return ResponseEntity.ok(user);
    }
    
    
}