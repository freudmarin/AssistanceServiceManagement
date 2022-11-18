package com.marindulja.mgmt_sys_demo_2.controllers;

import com.marindulja.mgmt_sys_demo_2.dto.UserDto;
import com.marindulja.mgmt_sys_demo_2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userDetailsService;

    @PostMapping("/addUser")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return new ResponseEntity(userDetailsService.addUser(userDto), HttpStatus.OK);
    }


    @GetMapping("/")
    public List<UserDto> getAllUsers() {
        return userDetailsService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        return userDetailsService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        return userDetailsService.updateUserById(id, userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        return userDetailsService.deleteUserById(id);
    }
}
