package com.csse.Repository;

import com.csse.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository <Users, Integer>{

    Users findByUsername(String username);

    //creating user account
   // Users registerUser(Users user);

}
