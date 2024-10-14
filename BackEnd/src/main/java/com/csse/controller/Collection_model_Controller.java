package com.csse.controller;
import com.csse.DTO.Collection_model;
import com.csse.service.Collection_model_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/model")
public class Collection_model_Controller {
    private final Collection_model_service collectionmodelservice;

    @Autowired
    public Collection_model_Controller(Collection_model_service collectionmodelservice) {
        this.collectionmodelservice = collectionmodelservice;
    }

    //url: http://localhost:8080/api/model/add
    @PostMapping("/add")
    public ResponseEntity<String> addModel(@RequestBody Collection_model model)  {
        try{
            String result = collectionmodelservice.saveWasteModel(model);
            return ResponseEntity.ok("product added successfully. Updated time: " + result);
        }catch (ExecutionException| InterruptedException e){
            return ResponseEntity.status(500).body("Error occurred in model adding : " + e.getMessage());
        }
    }
}
