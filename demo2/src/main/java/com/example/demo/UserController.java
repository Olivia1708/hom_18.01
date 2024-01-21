package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository UserRepository;

    public UserController(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @GetMapping("/Users")
    public List<User> getAllUsers(@RequestParam(required = false) Integer age) {
        List<User> Users = new ArrayList<User>();
        if (age == null) {
            UserRepository.findAll().forEach(Users::add);
        } else {
            for (int a = -5; a <= 5; a++) {
                UserRepository.findByAge(age + a).forEach(Users::add);
            }
        }
        Users.replaceAll(User::getCopyWithoutPassword);
        return Users;
    }
    @GetMapping("/Users/{id}")
    public User getUserById(@PathVariable("id") long id) {
        Optional<User> UserData = UserRepository.findById(id);
        if (UserData.isPresent()) {
            User User = UserData.get();
            User.setPassword("");
            return User.getCopyWithoutPassword();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/Users")
    public User createUser(@RequestBody User2 User2) {
        if (User2.isSamePassword()) {
            Optional<User> UserData = UserRepository.findById(User2.getId());
            if (UserData.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            } else {
                return UserRepository.save(User2.getUser()).getCopyWithoutPassword();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/Users/{id}")
    public User updateUserUsername(@PathVariable("id") long id, @RequestBody String username) {
        Optional<User> UserData = UserRepository.findById(id);
        if (UserData.isPresent()) {
            User _User = UserData.get();
            _User.setUsername(username);
            return UserRepository.save(_User).getCopyWithoutPassword();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/Users/{id}")
    public HttpStatus deleteUser(@PathVariable("id") long id) {
        Optional<User> UserData = UserRepository.findById(id);
        if (UserData.isPresent()) {
            UserRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
