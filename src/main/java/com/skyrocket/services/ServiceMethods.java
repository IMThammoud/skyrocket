package com.skyrocket.services;

import com.skyrocket.model.OfferedService;

import java.util.UUID;

@org.springframework.stereotype.Service
 public class ServiceMethods {
    private OfferedService offeredService;

    public OfferedService createOfferedService(UUID id,
                                               String name,
                                               int hoursNeeded,
                                               String description,
                                               double materialCost,
                                               double sellingPrice){
        return offeredService.setId(id)
                .setName(name)
                .setHoursNeeded(hoursNeeded)
                .setDescription(description)
                .setMaterialCost(materialCost)
                .setSellingPrice(sellingPrice)
                .build();

    }
}
