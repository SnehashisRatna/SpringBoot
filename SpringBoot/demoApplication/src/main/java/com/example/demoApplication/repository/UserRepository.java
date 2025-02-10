package com.example.demoApplication.repository;

import com.example.demoApplication.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username) ;
    User deleteByUserName(String userName)  ;

}
