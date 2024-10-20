package com.csse.repo.RepoInterface;

import com.csse.DTO.Collection_model;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ICollectionModel {
    public List<Collection_model> getAllCollectionModels() throws ExecutionException, InterruptedException;
}
