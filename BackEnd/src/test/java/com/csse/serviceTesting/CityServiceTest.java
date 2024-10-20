package com.csse.serviceTesting;

import com.csse.DTO.City;
import com.csse.repo.RepoInterface.ICityRepository;
import com.csse.service.ICityService;
import com.csse.service.Imp.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {
    //mocking the repository
    @Mock
    private ICityRepository cityRepository;

    //injecting the mocked service
    @InjectMocks
    private CityService cityService;
    private City city;

    //initializing the mock
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        city = new City();
        city.setCityid("1");
        city.setCityname("TestCity");
        city.setActivemodel("active");
    }

    @Test
    void createCity_Success() throws ExecutionException, InterruptedException {
        when(cityRepository.createCity(city)).thenReturn(city);

        City createdCity = cityService.createCity(city);

        assertNotNull(createdCity);
        assertEquals("TestCity", createdCity.getCityname());
        verify(cityRepository, times(1)).createCity(city);
    }
    @Test
    void getCityById_CityFound() throws ExecutionException, InterruptedException {
        when(cityRepository.findCityById("1")).thenReturn(Optional.of(city));

        Optional<City> foundCity = cityService.getCityById("1");

        assertTrue(foundCity.isPresent());
        assertEquals("TestCity", foundCity.get().getCityname());
        verify(cityRepository, times(1)).findCityById("1");
    }

    @Test
    void getCityById_CityNotFound() throws ExecutionException, InterruptedException {
        when(cityRepository.findCityById("1")).thenReturn(Optional.empty());

        Optional<City> foundCity = cityService.getCityById("1");

        assertFalse(foundCity.isPresent());
        verify(cityRepository, times(1)).findCityById("1");
    }

    @Test
    void getAllCities_Success() throws ExecutionException, InterruptedException {
        when(cityRepository.findAllCities()).thenReturn(List.of(city));

        List<City> cities = cityService.getAllCities();

        assertNotNull(cities);
        assertEquals(1, cities.size());
        assertEquals("TestCity", cities.get(0).getCityname());
        verify(cityRepository, times(1)).findAllCities();
    }

    @Test
    void updateCityActiveModel_Success() throws ExecutionException, InterruptedException {
        when(cityRepository.findCityById("1")).thenReturn(Optional.of(city));

        cityService.updateCityActiveModel("1", "inactive");

        assertEquals("inactive", city.getActivemodel());
        verify(cityRepository, times(1)).updateCity(city);
    }

    @Test
    void updateCityActiveModel_CityNotFound() throws ExecutionException, InterruptedException {
        when(cityRepository.findCityById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cityService.updateCityActiveModel("1", "inactive");
        });

        String expectedMessage = "City not found with id: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(cityRepository, never()).updateCity(any(City.class));
    }

    @Test
    void deleteCity_Success() throws ExecutionException, InterruptedException {
        doNothing().when(cityRepository).deleteCity("1");

        cityService.deleteCity("1");

        verify(cityRepository, times(1)).deleteCity("1");
    }
}
