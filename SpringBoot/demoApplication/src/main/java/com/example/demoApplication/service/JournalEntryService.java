package com.example.demoApplication.service;

import com.example.demoApplication.entity.JournalEntry;
import com.example.demoApplication.entity.User;
import com.example.demoApplication.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService  {
//    This will contain our Bussines Logic ;

    @Autowired
    private JournalEntryRepository journalEntryRepository ;  //DEPENDENCY INJECTION

    @Autowired
    private UserService userService ;


//    Saving All entity to the database ;
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName) ;
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            journalEntry.setDate(LocalDateTime.now());
            user.getJournalEntries().add(saved)  ;
            userService.saveEntry(user);
        }catch (Exception e){
            log.error("Exception",e) ;
            throw new RuntimeException("An error occurred while saving a entry.",e);
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        try {
            journalEntryRepository.save(journalEntry);
            journalEntry.setDate(LocalDateTime.now());
        }catch (Exception e){
            log.error("Exception",e) ;
        }
    }

//    Getting Entity Fron DB ;
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll()  ;
    }

//    Getting Entity By Id ;
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

//    Deleting Entity ById ;
    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName)  ;
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }

//    public void saveEntry(JournalEntry myEntry, User user) {
//    }
}
