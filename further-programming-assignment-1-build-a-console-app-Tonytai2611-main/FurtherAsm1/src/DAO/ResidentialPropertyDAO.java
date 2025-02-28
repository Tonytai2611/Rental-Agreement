/**
 * Data Access Object (DAO) for managing ResidentialProperty objects.
 * Provides methods to read, write, add, update, and delete residential property records from a file.
 *
 * File Path: `FurtherAsm1/src/File/residential_properties.txt`
 *
 * This class handles the persistence of ResidentialProperty objects and manages serialization and deserialization.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package DAO;

import Classes.ResidentialProperty;
import Classes.Property.PropertyStatus;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResidentialPropertyDAO {

    private static final String FILE_PATH = "FurtherAsm1/src/File/residential_properties.txt";
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Converts a ResidentialProperty object into a string representation for file storage.
     *
     * @param property The ResidentialProperty object to convert.
     * @return A comma-separated string representation of the property.
     */
    // Convert a ResidentialProperty object to String
    private String convertToString(ResidentialProperty property) {
        return String.join(",",
                property.getPropertyId(),
                property.getAddress(),
                String.valueOf(property.getPricing()),
                property.getStatus().name(),
                String.valueOf(property.getNumBedrooms()),
                String.valueOf(property.isGardenAvailability()),
                String.valueOf(property.isPetFriendliness())
        );
    }

    /**
     * Converts a string from the file into a ResidentialProperty object.
     *
     * @param line A line of text representing a ResidentialProperty.
     * @return A ResidentialProperty object, or null if the string is invalid.
     */
    // Convert String to a ResidentialProperty object
    private ResidentialProperty convertToProperty(String line) {
        String[] parts = line.split(",");
        if (parts.length < 7) return null;

        String propertyId = parts[0];
        String address = parts[1];
        double pricing = Double.parseDouble(parts[2]);
        PropertyStatus status = PropertyStatus.valueOf(parts[3]);
        int numBedrooms = Integer.parseInt(parts[4]);
        boolean gardenAvailability = Boolean.parseBoolean(parts[5]);
        boolean petFriendliness = Boolean.parseBoolean(parts[6]);

        return new ResidentialProperty(propertyId, address, pricing, status, numBedrooms, gardenAvailability, petFriendliness);
    }

    /**
     * Reads ResidentialProperty objects from the file and returns them as a list.
     * If the file does not exist, prompts the user to create a new file.
     *
     * @return A list of ResidentialProperty objects.
     */
    public List<ResidentialProperty> readFromFile() {
        List<ResidentialProperty> properties = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("The Residential_properties file does not exist.");
            System.out.print("Do you want to create a new file? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();
            } else {
                System.out.println("Process terminated.");
                return properties; // Return an empty list if the user does not want to create a new file
            }
        }

        // Read data from the file if it exists
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while ((line =reader.readLine()) != null) {
                ResidentialProperty property = convertToProperty(line);
                if ( property != null) properties.add(property);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Creates a new file if it does not exist.
     */
    // Create a new file if not found
    private void createNewFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.createNewFile()) {
                System.out.println("A new file has been created: " + file.getName());
            } else {
                System.out.println("The file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of ResidentialProperty objects to the file.
     *
     * @param properties The list of ResidentialProperty objects to write.
     * @param FILE_PATH  The file path where the data will be stored.
     */
    public void writeToFile(List<ResidentialProperty> properties, String FILE_PATH){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ResidentialProperty property : properties) {
                writer.write(convertToString(property));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new ResidentialProperty to the file.
     *
     * @param property The ResidentialProperty object to add.
     */
    public void addProperty(ResidentialProperty property) {
        List<ResidentialProperty> properties = readFromFile();
        properties.add(property);
        writeToFile(properties, FILE_PATH);
        System.out.println("Residential property added successfully.");
    }

    /**
     * Updates an existing ResidentialProperty in the file.
     *
     * @param updatedProperty The updated ResidentialProperty object.
     * @return True if the property was updated successfully, false otherwise.
     */
    public boolean updateProperty(ResidentialProperty updatedProperty) {
        List<ResidentialProperty> properties = readFromFile();
        boolean updated = false;

        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).getPropertyId().equals(updatedProperty.getPropertyId())) {
                properties.set(i, updatedProperty);
                updated = true;
                break;
            }
        }

        if (updated) {
            writeToFile(properties, FILE_PATH);
            System.out.println("Residential property updated successfully. ");
        } else {
            System.out.print("Property not found");
        }
        return updated;
    }

    /**
     * Deletes a ResidentialProperty from the file based on its property ID.
     *
     * @param propertyId The ID of the property to delete.
     * @return True if the property was deleted successfully, false otherwise.
     */
    public boolean deleteProperty(String propertyId) {
        List<ResidentialProperty> properties = readFromFile();
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(propertyId));

        if (removed) {
            writeToFile(properties, FILE_PATH);
            System.out.println("Residential property deleted successfully.");
        } else {
            System.out.println("Property not found. ");
        }
        return removed;
    }
}
