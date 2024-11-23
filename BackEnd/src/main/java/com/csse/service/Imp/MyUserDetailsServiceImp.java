package com.csse.Service.Imp;

import com.csse.Entity.UserPrinciple;
import com.csse.Entity.Users;
import com.csse.Repository.UserRepo;
import com.csse.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServiceImp implements MyUserDetailsService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         Users user = userRepo.findByUsername(username);

         if(user == null){
             System.out.println("User does not exists ");
             throw new UsernameNotFoundException("User not found");
         }


        return new UserPrinciple(user);
    }
}
