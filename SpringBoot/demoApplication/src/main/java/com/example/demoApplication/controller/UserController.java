package com.example.demoApplication.controller;

import com.example.demoApplication.entity.User;
import com.example.demoApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.* ;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
//   Here  we will do all crud operations ;
    @Autowired
    private UserService userService ;
//
////    Getting All users ---------->
//    @GetMapping
//    public List<User> getAll(){
//        return userService.getAll() ;
//    }
//
//
////    Creating User ---------->
//    @PostMapping
//    public boolean createUser(@RequestBody User user){
//        userService.saveEntry(user);
//        return true ;
//    }
////    Finding User By Name  -------->
//    @GetMapping("/userName/{myName}")
//    public ResponseEntity<?> findByUserName(@PathVariable String myName){
//        User byUserName = userService.findByUserName(myName);
//        if(byUserName != null){
//            return new ResponseEntity<>(byUserName, HttpStatus.OK) ;
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    Finding By User Id -------->
//    @GetMapping("/id/{myId}")
//    public ResponseEntity<?> findById(@PathVariable ObjectId myId){
//        Optional<User> byId = userService.findById(myId);
//        if(byId.isPresent()){
//            return new ResponseEntity<>(byId.get(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    Updating User By Name --------->
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName() ;
        User useridDB = userService.findByUserName(userName) ;
           useridDB.setUserName(user.getUserName());
           useridDB.setPassword(user.getPassword());
           userService.saveEntry(useridDB);
           return new ResponseEntity<>(useridDB , HttpStatus.OK) ;

    }

//    Deleting User By Name --------->
    @DeleteMapping
    public ResponseEntity<?> deleteBYUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }



}
