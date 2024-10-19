package com.csse.repo.RepoInterface;

import com.csse.DTO.Trashbin;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ITrashBinRepository {
    public String createTrashBin(Trashbin trashBin) throws ExecutionException, InterruptedException;
    public List<Trashbin> getAllTrashbins() throws ExecutionException, InterruptedException;
    public List<Trashbin> getAllTrashbinsByFacId(String facilityId) throws ExecutionException, InterruptedException;
    public List<Trashbin> getFullTrashbins() throws ExecutionException, InterruptedException;
    public Optional<Trashbin> findTrashbinById(String id) throws ExecutionException, InterruptedException;
    public void updateTrashbin(Trashbin trashbin) throws ExecutionException, InterruptedException;
    public void deleteTrashbin(String id) throws ExecutionException, InterruptedException;
    public String generateUniqueTrashbinId() throws ExecutionException, InterruptedException;
}
