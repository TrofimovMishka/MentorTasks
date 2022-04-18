package com.pam.training.garden.service;

import com.pam.training.garden.domain.GardenProperties;
import com.pam.training.garden.domain.PlantType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Map;

class GardenServiceTest {
    private static GardenService gardenService;

    @BeforeAll
    static void setUp() {
        gardenService = new GardenService();
        gardenService.getPlantTypes();
        gardenService.setGardenProperties(new GardenProperties(7, 70));
    }

    @Test
    void testEqualsEvaluatePlan() {
        Map<String, Integer> testData  = Map.of("Corn", 10, "Tomato", 10);

        Result expected = new Result(7, 70, true, true);
        Result actual = gardenService.evaluatePlan(testData);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testExceptionEvaluatePlan() {
        Map<String, Integer> testData = Map.of("Corn", 10, "Tomato", 10, "Onion", 5);

        Assertions.assertThrows(IllegalArgumentException.class,() -> gardenService.evaluatePlan(testData));
    }

}