package com.csse.service.Imp;

import com.csse.DTO.City;
import com.csse.repo.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public String createCity(City city) throws ExecutionException, InterruptedException {
        return cityRepository.createCity(city);
    }

    public Optional<City> getCityById(String id) throws ExecutionException, InterruptedException {
        return cityRepository.findCityById(id);
    }

    public List<City> getAllCities() throws ExecutionException, InterruptedException {
        return cityRepository.findAllCities();
    }

    public void updateCityActiveModel(String id, String activeModel) throws ExecutionException, InterruptedException {
        Optional<City> cityOptional = cityRepository.findCityById(id);
        if (cityOptional.isPresent()) {
            City city = cityOptional.get();
            city.setActivemodel(activeModel);
            cityRepository.updateCity(city);
        } else {
            throw new IllegalArgumentException("City not found with id: " + id);
        }
    }

    public void deleteCity(String id) throws ExecutionException, InterruptedException {
        cityRepository.deleteCity(id);
    }

}
