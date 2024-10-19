package com.csse.repo;

import com.csse.DTO.City;
import com.csse.firebase.FirestoreOperations;
import com.csse.repo.RepoInterface.ICityRepository;
import com.csse.util.Iutil.IdManagementStrategy;
import com.csse.util.UniqueIdGenerator;
import com.csse.util.UniqueIdValidator;
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
public class CityRepository implements ICityRepository {
    private final FirestoreOperations dbOperations;
    private final IdManagementStrategy idManager;

    public CityRepository(FirestoreOperations dbOperations, IdManagementStrategy idManager) {
        this.dbOperations = dbOperations;
        this.idManager = idManager;
    }

    @Override
    public City createCity(City city) throws ExecutionException, InterruptedException {
        String cityId;
        do{
            cityId = idManager.generateId();
        }while(!idManager.validateId(CITY_COLLECTION_NAME, cityId));

        city.setCityid(cityId);
        dbOperations.saveDocument(CITY_COLLECTION_NAME, cityId, city);
        return city;
    }

    @Override
    public Optional<City> findCityById(String id) throws ExecutionException, InterruptedException {
        return Optional.ofNullable(dbOperations.getDocument(CITY_COLLECTION_NAME, id, City.class));
    }

    @Override
    public List<City> findAllCities() throws ExecutionException, InterruptedException {
        return dbOperations.getAllDocuments(CITY_COLLECTION_NAME, City.class);
    }

    @Override
    public void updateCity(City city) throws ExecutionException, InterruptedException {
        dbOperations.saveDocument(CITY_COLLECTION_NAME, city.getCityid(), city);

    }

    @Override
    public void deleteCity(String id) throws ExecutionException, InterruptedException {
        dbOperations.deleteDocument(CITY_COLLECTION_NAME, id);
    }
}


/*private final Firestore firestore;
    private final UniqueIdValidator uniqueIdValidator;
    private final UniqueIdGenerator uniqueIdGenerator;

    //constructor
    public CityRepository(Firestore firestore, UniqueIdValidator uniqueIdValidator, UniqueIdGenerator uniqueIdGenerator) {
        this.firestore = firestore;
        this.uniqueIdValidator = uniqueIdValidator;
        this.uniqueIdGenerator = uniqueIdGenerator;
    }

    public City createCity(City city) throws ExecutionException, InterruptedException {
        String cityId ;
        do {
            cityId = uniqueIdGenerator.generateUniqueId();
        } while (!uniqueIdValidator.isUniqueIdValid(CITY_COLLECTION_NAME, cityId));
        city.setCityid(cityId);
        DocumentReference docRef = firestore.collection(CITY_COLLECTION_NAME).document(cityId);
        ApiFuture<WriteResult> result = docRef.set(city);
        result.get();
        return city;
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
    }*/