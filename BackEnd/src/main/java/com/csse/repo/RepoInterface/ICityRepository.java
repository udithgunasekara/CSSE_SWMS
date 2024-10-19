package com.csse.repo.RepoInterface;

import com.csse.DTO.City;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ICityRepository {
    public City createCity(City city) throws ExecutionException, InterruptedException;
    public Optional<City> findCityById(String id) throws ExecutionException, InterruptedException;
    public List<City> findAllCities() throws ExecutionException, InterruptedException;
    public void updateCity(City city) throws ExecutionException, InterruptedException;
    public void deleteCity(String id) throws ExecutionException, InterruptedException;
}
