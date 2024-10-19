package com.csse.service;

import com.csse.DTO.City;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ICityService {
    public City createCity(City city) throws ExecutionException, InterruptedException;
    public Optional<City> getCityById(String id) throws ExecutionException, InterruptedException;
    public void updateCityActiveModel(String id, String activeModel) throws ExecutionException, InterruptedException;
    public void deleteCity(String id) throws ExecutionException, InterruptedException;
    public List<City> getAllCities() throws ExecutionException, InterruptedException;
}
