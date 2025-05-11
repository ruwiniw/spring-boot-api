package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ChangePasswordRequest;
import com.codewithmosh.store.dtos.RegisterUserRequestDTO;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDTO;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private UserMapper userMapper;

//    @GetMapping
//    public Iterable<User> getAllUsers(){
//        return userRepository.findAll();
//    }

    @GetMapping
    public Iterable<UserDTO> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name="sort") String sort){

        if(!Set.of("name", "email").contains(sort))
            sort = "name";

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Long id){
//        return userRepository.findById(id).orElse(null);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        var user =  userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody RegisterUserRequestDTO request){
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        return user;

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
    @RequestBody UpdateUserRequest request){
        var user =  userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);

        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        var user =  userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestBody ChangePasswordRequest request){

        var user =  userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

}
