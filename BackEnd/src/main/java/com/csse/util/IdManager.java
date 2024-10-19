package com.csse.util;

import com.csse.util.Iutil.IdManagementStrategy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class IdManager implements IdManagementStrategy {
    private final UniqueIdGenerator uniqueIdGenerator;
    private final UniqueIdValidator uniqueIdValidator;

    public IdManager(UniqueIdGenerator uniqueIdGenerator, UniqueIdValidator uniqueIdValidator) {
        this.uniqueIdGenerator = uniqueIdGenerator;
        this.uniqueIdValidator = uniqueIdValidator;
    }

    @Override
    public String generateId() {
        return uniqueIdGenerator.generateUniqueId();
    }

    @Override
    public boolean validateId(String collectionName, String id) throws ExecutionException, InterruptedException {
        return uniqueIdValidator.isUniqueIdValid(collectionName, id);
    }

}
