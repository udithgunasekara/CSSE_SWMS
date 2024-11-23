package com.csse.Controller;

import com.csse.Entity.Users;
import com.csse.Service.Imp.UserServiceImp;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/getAllUsers")
    public List<Users> getAll(){
        return userServiceImp.getAll();
    }


    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        Users data= userServiceImp.register(user);
        return data;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user){
        System.out.println("Here the data");
        System.out.println(user);
        return ResponseEntity.ok(userServiceImp.verifyLogin(user));
    }



}
