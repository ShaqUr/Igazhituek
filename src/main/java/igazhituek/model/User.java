/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.model;

/**
 *
 * @author Aram
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
     
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    
    private String email;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN, BANNED, GUEST
    }
    private String denomination;
    
    private int age;
    
    private String aboutMe;
    
    private String whereFrom;
    
    private String sex;
    
    @OneToMany
    private List<User> likes = new ArrayList();
   
    @OneToMany
    private List<User> dislikes = new ArrayList();
}