package com.csse.controller;

import com.csse.DTO.City;
import com.csse.service.ICityService;
import com.csse.service.Imp.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/city")
public class CityController {

    private final ICityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    //url: http://localhost:8080/api/city
    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) throws ExecutionException, InterruptedException {
        City cityId = cityService.createCity(city);
        return new ResponseEntity<>(cityId, HttpStatus.CREATED);
    }

    //url: http://localhost:8080/api/city/1
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return cityService.getCityById(id)
                .map(city -> new ResponseEntity<>(city, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() throws ExecutionException, InterruptedException {
        List<City> cities = cityService.getAllCities();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    //url: http://localhost:8080/api/city/1?activeModel=1
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCityActiveModel(@PathVariable String id, @RequestParam String activeModel) throws ExecutionException, InterruptedException {
        cityService.updateCityActiveModel(id, activeModel);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) throws ExecutionException, InterruptedException {
        cityService.deleteCity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
