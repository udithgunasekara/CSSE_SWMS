package com.csse.Service.Imp;

import com.csse.Entity.Users;
import com.csse.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.coyote.http11.Constants.a; // remove i use to version not strength

@Service
public class UserServiceImp {

    @Autowired // reason is "i don't want to have object creation"
    private UserRepo userRepo;


    @Autowired
    private JWTService jwtService;

    //we can get the obj here from authentication manager
    @Autowired
    private AuthenticationManager authenticationManager;



    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);

    public Users register (Users user){
        //we use bcrypt library: give us from spring security
        user.setPassword(encoder.encode(user.getPassword()));



        return userRepo.save(user);

    }

    public List<Users> getAll(){
        return userRepo.findAll();
    }

    public String verifyLogin(Users user) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));


        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        return "Fail";

    }
}
