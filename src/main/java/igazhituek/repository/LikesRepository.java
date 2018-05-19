/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igazhituek.repository;

import igazhituek.model.Likes;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Aram
 */
public interface LikesRepository extends CrudRepository<Likes, String>  {
     @Override
     Iterable<Likes> findAll();
}
