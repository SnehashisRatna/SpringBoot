package com.example.demoApplication.controller;


import com.example.demoApplication.entity.JournalEntry;
import com.example.demoApplication.entity.User;
import com.example.demoApplication.service.JournalEntryService;
import com.example.demoApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private JournalEntryService journalEntryService ;

    @Autowired
    private UserService userService ;

    //    Getting  Entry by userName   ;
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){

//        This SecurityContextHolder will help us to authentication our users by Auth ;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName() ;

        User user = userService.findByUserName(userName);
        List<JournalEntry> all =  user.getJournalEntries() ;
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK) ;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

//    Creating Entry of a user by userName  ;
//    So need to access the user 1st ;
//    Then we will create the journalEntry of that user ;
    @PostMapping
     public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName() ;

            journalEntryService.saveEntry(myEntry,userName);
            return  new ResponseEntity<>(myEntry , HttpStatus.CREATED) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST)  ;
        }
     }

//     #3rd End point ---> Find Entity by ID ;
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName() ;

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    Deleting Entity By Id ;
    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

//    Updating Entity by Id and UserName ;
    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId ,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName){
        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null) ;

        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals((""))? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry , HttpStatus.OK) ;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
