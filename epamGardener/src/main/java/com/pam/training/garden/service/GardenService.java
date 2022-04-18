package com.pam.training.garden.service;

import com.pam.training.garden.domain.GardenProperties;
import com.pam.training.garden.domain.PlantType;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GardenService {
    private List<PlantType> allPlants;
    private GardenProperties gardenProperties;
    private Map<String, PlantType> plantsWithNames;

    public GardenService() {
    }

    public List<PlantType> getPlantTypes() {
        if (allPlants == null) {
            allPlants = List.of(new PlantType("Corn", 0.4, 10)
                    , new PlantType("Pumpkin", 2, 5)
                    , new PlantType("Grape", 3, 5)
                    , new PlantType("Tomato", 0.3, 10)
                    , new PlantType("Cucumber", 0.4, 10));
        }
        return allPlants;
    }

    public void setGardenProperties(GardenProperties gardenProperties) {
        this.gardenProperties = gardenProperties;
    }

    public Result evaluatePlan(Map<String, Integer> items) throws IllegalArgumentException {
        String unknownPlant = findUnknownPlant(items);
        if (!unknownPlant.isEmpty()) {
            throw new IllegalArgumentException("Unknown plant: " + unknownPlant);
        }
        double[] requiredResource = findRequiredResource(items);

        double requiredArea = requiredResource[0];
        double requiredWater = requiredResource[1];
        boolean areaOk = gardenProperties.getArea() >= requiredArea;
        boolean waterOk = gardenProperties.getWaterSupply() >= requiredWater;
        return new Result(requiredArea, requiredWater, areaOk, waterOk);
    }

    private String findUnknownPlant(Map<String, Integer> items) {
        String unknownPlant = "";

        Set<String> unknownPlants = new HashSet<>(items.keySet());
        unknownPlants.removeAll(getMapWithNames().keySet());

        Iterator<String> iterator = unknownPlants.iterator();
        if (iterator.hasNext()) {
            unknownPlant = iterator.next();
        }
        return unknownPlant;
    }

    private double[] findRequiredResource(Map<String, Integer> items) {
        double requiredArea = 0;
        double requiredWater = 0;
        Set<String> plants = items.keySet();

        for (String plant : plants) {
            PlantType type = getMapWithNames().get(plant);
            double area = type.getArea() * items.get(plant);
            requiredArea += area;
            requiredWater += area * type.getWaterAmount();
        }
        return new double[]{requiredArea, requiredWater};
    }

    private Map<String, PlantType> getMapWithNames() {
        if (plantsWithNames == null) {
            plantsWithNames = allPlants.stream()
                    .collect(Collectors.toMap(PlantType::getName, Function.identity()));
        }
        return plantsWithNames;
    }
}
