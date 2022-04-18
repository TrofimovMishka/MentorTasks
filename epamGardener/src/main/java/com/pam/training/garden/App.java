package com.pam.training.garden;

import com.pam.training.garden.domain.GardenProperties;
import com.pam.training.garden.domain.PlantType;
import com.pam.training.garden.service.GardenService;
import com.pam.training.garden.service.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class App {

    static {
        System.setProperty("user.language", "en");
    }

    public static void main(String[] args) {
        try {
            System.out.println("***Welcome to Garden Planner*** \n \n Please enter your garden properties.");
            System.out.print("Size (square meter): ");
            double area = requestGardenProperty();
            System.out.print("Water (in liter): ");
            double water = requestGardenProperty();

            GardenService service = new GardenService();
            service.setGardenProperties(new GardenProperties(area, water));

            printAllKnownPlants(service);

            Map<String, Integer> allPlants = requestGardenPlants();


            try {
                Result result = service.evaluatePlan(allPlants);
                System.out.println(result);
            } catch (IllegalArgumentException e) {
                System.out.println("***Result**\n");
                System.err.println(e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("\nIllegal input format");
        }
    }

    private static void printAllKnownPlants(GardenService service) {
        System.out.println("\nKnown plant types: ");
        List<PlantType> listPlantTypes = service.getPlantTypes();
        listPlantTypes.stream()
                .map(PlantType::getName)
                .forEach(plantName -> System.out.println("- " + plantName));
        System.out.println("");
    }

    private static double requestGardenProperty() {
        String propertyString = "";
        double property = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            propertyString = reader.readLine();
            property = Double.parseDouble(propertyString.trim());
        } catch (NumberFormatException | IOException e) {
            throw new IllegalArgumentException();
        }

        return property;
    }

    private static Map<String, Integer> requestGardenPlants() throws IllegalArgumentException {
        Map<String, Integer> allPlants = new HashMap<>();
        System.out.println("Please enter the plants you would like to put in your garden" +
                ". Press enter when you are done.");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String plantAndAmount = "";
            System.out.print("Enter plant (format: name, amount): ");

            while (!((plantAndAmount = reader.readLine()).equals(""))) {
                if (!plantAndAmount.isEmpty()) {

                    String plantName = getStrings(plantAndAmount)[0].trim();
                    Integer amount = extractPlantAmount(plantAndAmount);

                    allPlants.put(plantName, amount);

                    System.out.print("Enter plant (format: name,amount): ");
                }
            }
            System.out.println("");
        } catch (NumberFormatException | IOException e) {
            throw new IllegalArgumentException();
        }
        return allPlants;
    }

    private static Integer extractPlantAmount(String str) throws IllegalArgumentException {
        String[] array = getStrings(str);
        return Integer.parseInt(array[1].trim());
    }

    private static String[] getStrings(String str) throws IllegalArgumentException {
        String[] array = str.split(",");
        if (array.length < 2 || str.startsWith(",")) {
            throw new IllegalArgumentException();
        }
        return array;
    }
}
