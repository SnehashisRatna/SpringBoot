package com.example.demoApplication.service;

import com.example.demoApplication.entity.JournalEntry;
import com.example.demoApplication.entity.User;
import com.example.demoApplication.repository.JournalEntryRepository;
import com.example.demoApplication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
//    This will contain our Bussines Logic ;

    @Autowired
    private UserRepository userRepository;  //DEPENDENCY INJECTION

    private static final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder() ;


//    Saving All entity to the database ;
    public void saveEntry(User user){
        userRepository.save(user) ;
    }

    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setRoles(Array.set("USER"));
            userRepository.save(user) ;

        }catch (Exception e){
            log.error("Exception",e) ;
        }

    }

    public void saveAdmin(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setRoles(Array.set("USER","ADMIN"));
            userRepository.save(user) ;

        }catch (Exception e){
            log.error("Exception",e) ;
        }

    }

//    Getting Entity Fron DB ;
    public List<User> getAll(){

        return userRepository.findAll()  ;
    }

//    Getting Entity By Id ;
    public Optional<User> findById(ObjectId id){

        return userRepository.findById(id);
    }

//    Deleting Entity ById ;
    public void deleteById(ObjectId id){

        userRepository.deleteById(id);
    }

//    Finding User By Name  ;
    public User findByUserName(String userName){

        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String userName){

        userRepository.deleteByUserName(userName) ;
    }
}
