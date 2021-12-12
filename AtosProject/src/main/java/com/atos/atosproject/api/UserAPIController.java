package com.atos.atosproject.api;

import com.atos.atosproject.entities.UserEntity;
import com.atos.atosproject.sevices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/user")
public class UserAPIController {

@Autowired
    UserService userService;

    // URL: http://localhost:8080/api/user
    // dispaly details of  all users
    @GetMapping(value = "", produces = "application/json")
    public Iterable<UserEntity> getAll(HttpServletRequest request) {

        return userService.findAll();

    }


// URL: http://localhost:8080/api/user/?search=jad
    // dispaly details of users by  name or country
    @GetMapping(value = "/", produces = "application/json")
    public Iterable<UserEntity> getAllBykeyWord(HttpServletRequest request) {
        String search = request.getParameter("search");
        System.out.println("Recherche de user avec param = " + search);
        return userService.findAll(search);

    }


    // URL: http://localhost:8080/api/user/1
    // dispaly details of user by id
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserEntity> get(@PathVariable int id) {
        try {
            UserEntity user = userService.findUser(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    // URL: http://localhost:8080/api/user
// add user
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<UserEntity> add(@RequestBody UserEntity user) {
        try {
            userService.addUser(user);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( user.getId() ).toUri();
              return ResponseEntity.created( uri ).body(user) ;

        } catch (InvalidObjectException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    // URL: http://localhost:8080/api/user/1
    @PutMapping(value="/{id}" , consumes = "application/json")
    public void update( @PathVariable int id , @RequestBody UserEntity user ){
        try{
            userService.editUser( id , user );

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "user not found " );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }


// URL: http://localhost:8080/api/user/5
// delete user
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) throws Exception {
        try{
            userService.delete(id);
            return ResponseEntity.ok(null);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }

    }


}
