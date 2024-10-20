package com.csse.controller;

import com.csse.DTO.City;
import com.csse.service.ICityService;
import com.csse.service.Imp.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    //url: http://localhost:8080/api/city
    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) throws ExecutionException, InterruptedException {
        logger.info("creating a new city with name : {}", city.getCityname());
        City cityId = cityService.createCity(city);
        logger.info("city created with id : {}", cityId);
        return new ResponseEntity<>(cityId, HttpStatus.CREATED);
    }

    //url: http://localhost:8080/api/city/1
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable String id) throws ExecutionException, InterruptedException {
        logger.info("getting city with id : {}", id);
        return cityService.getCityById(id)
                .map(city -> {
                    logger.info("city found with id : {}", id);
                    return new ResponseEntity<>(city, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    logger.info("city not found with id : {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });

    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() throws ExecutionException, InterruptedException {
        logger.info("getting all cities");
        List<City> cities = cityService.getAllCities();
        logger.info("cities found : {}", cities.size());
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    //url: http://localhost:8080/api/city/1?activeModel=1
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCityActiveModel(@PathVariable String id, @RequestParam String activeModel) throws ExecutionException, InterruptedException {
        logger.info("Updating active model of city with ID: {} to {}", id, activeModel);
        cityService.updateCityActiveModel(id, activeModel);
        logger.info("active model updated successfully");
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) throws ExecutionException, InterruptedException {
        logger.info("deleting city with id : {}", id);
        cityService.deleteCity(id);
        logger.info("city deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
