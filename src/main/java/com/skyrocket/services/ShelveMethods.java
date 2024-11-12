package com.skyrocket.services;

import com.skyrocket.model.Shelve;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShelveMethods {
    Shelve shelve;

    public Shelve createShelve(UUID id,
                               String name,
                               String category,
                               boolean isForServices){
        return shelve.setId(id)
                .setName(name)
                .setCategory(category)
                .setForServices(isForServices)
                .build();
    }
}
