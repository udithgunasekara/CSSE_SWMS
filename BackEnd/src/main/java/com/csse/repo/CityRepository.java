package com.csse.repo;

import com.csse.DTO.City;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


import static com.csse.common.CommonConstraints.CITY_COLLECTION_NAME;
@Repository
public class CityRepository {
    private final Firestore firestore;

    public CityRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String createCity(City city) throws ExecutionException, InterruptedException {
        String cityId = generateUniqueCityId();
        city.setCityid(cityId);
        DocumentReference docRef = firestore.collection(CITY_COLLECTION_NAME).document(cityId);
        ApiFuture<WriteResult> result = docRef.set(city);
        result.get();
        return cityId;
    }

    public Optional<City> findCityById(String id) throws ExecutionException, InterruptedException {
        return Optional.ofNullable(firestore.collection(CITY_COLLECTION_NAME).document(id).get().get().toObject(City.class));
    }

    public List<City> findAllCities() throws ExecutionException, InterruptedException {
        return firestore.collection(CITY_COLLECTION_NAME).get().get().toObjects(City.class);
    }

    public void updateCity(City city) throws ExecutionException, InterruptedException {
        firestore.collection(CITY_COLLECTION_NAME).document(city.getCityid()).set(city).get();
    }

    public void deleteCity(String id) throws ExecutionException, InterruptedException {
        firestore.collection(CITY_COLLECTION_NAME).document(id).delete().get();
    }

    private String generateUniqueCityId() throws ExecutionException, InterruptedException {
        String cityId;
        do {
            cityId = UUID.randomUUID().toString().substring(0,8);
        } while (findCityById(cityId).isPresent());
        return cityId;
    }

}
