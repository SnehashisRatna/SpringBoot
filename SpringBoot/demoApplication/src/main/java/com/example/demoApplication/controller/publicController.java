package com.example.demoApplication.controller;

import com.example.demoApplication.entity.User;
import com.example.demoApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private UserService userService ;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK" ;
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
      userService.saveEntry(user);
    }
}
