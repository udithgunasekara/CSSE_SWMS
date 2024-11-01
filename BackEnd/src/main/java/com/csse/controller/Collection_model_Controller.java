package com.csse.controller;
import com.csse.DTO.Collection_model;
import com.csse.service.Imp.Collection_model_service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/model")
public class Collection_model_Controller {
    private  final Collection_model_service collection_model_service;

    public Collection_model_Controller(Collection_model_service collection_model_service) {
        this.collection_model_service = collection_model_service;
    }

    //method to get all collection models
    //url: http://localhost:8080/api/model
    @GetMapping
    public ResponseEntity<List<Collection_model>> getAllCollectionModels() throws ExecutionException, InterruptedException {
        List<Collection_model> collection_models = collection_model_service.getAllCollectionModels();
        return new ResponseEntity<>(collection_models, HttpStatus.OK);
    }


}
