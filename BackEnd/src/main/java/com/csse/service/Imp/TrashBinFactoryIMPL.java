package com.csse.service.Imp;

import com.csse.DTO.HazardousTrashbin;
import com.csse.DTO.OrganicTrashbin;
import com.csse.DTO.RecyclableTrashbin;
import com.csse.DTO.Trashbin;
import com.csse.service.ITrashBinFactory;
import org.springframework.stereotype.Component;

@Component
public class TrashBinFactoryIMPL implements ITrashBinFactory {
    @Override
    public Trashbin createTrashBin(Trashbin trashbin) {
        System.out.println(trashbin.getTrashbinType().toLowerCase());
        return switch (trashbin.getTrashbinType().toLowerCase()) {
            case "recyclable" -> new RecyclableTrashbin(trashbin);
            case "hazardous" -> new HazardousTrashbin(trashbin);
            case "organic" -> new OrganicTrashbin(trashbin);
            default -> throw new IllegalArgumentException("Unknown bin type: " + trashbin.getTrashbinType());
        };
    }
}
