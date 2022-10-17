package net.cloudcentrik.autolink.tokenserver.controller;

import net.cloudcentrik.autolink.tokenserver.exception.UserCollectionException;
import net.cloudcentrik.autolink.tokenserver.model.User;
import net.cloudcentrik.autolink.tokenserver.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO activate when ui is ready
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody User userData){
        try {
            User newUser = userService.createUser(userData);
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        } catch (UserCollectionException.UserAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<List<User>>(users, users.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);

        } catch (Exception e){
            LOG.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/paged")
    public ResponseEntity<Map<String, Object>> getAllUsersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            List<User> users = new ArrayList<User>();
            Page<User> pageUsers = userService.getAllUsersPaged(page,size);
            users = pageUsers.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("data", users);
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());

            return new ResponseEntity<>(response, users.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);

        } catch (Exception e){
            LOG.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {

        try {
            User user = userService.findUserById(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);

        } catch (UserCollectionException.UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") String id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User userData) {

        try {
            User updatedUser = userService.updateUser(id,userData);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } catch (UserCollectionException.UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserCollectionException.InvalidData e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
