package com.csse.service.Imp;

import com.csse.DTO.City;
import com.csse.repo.CityRepository;
import com.csse.repo.RepoInterface.ICityRepository;
import com.csse.service.ICityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class CityService implements ICityService {

    private final ICityRepository cityRepository;

    public CityService(ICityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City createCity(City city) throws ExecutionException, InterruptedException {
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
