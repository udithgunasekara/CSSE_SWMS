package com.csse.util.Iutil;

import java.util.concurrent.ExecutionException;

public interface IdManagementStrategy {
    String generateId();
    boolean validateId(String collectionName, String id) throws ExecutionException, InterruptedException;
}
