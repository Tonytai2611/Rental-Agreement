/**
 * A Data Access Object (DAO) for managing CommercialProperty objects.
 * This class provides methods to read, write, add, update, and delete commercial properties
 * from a persistent storage file.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package DAO;

import Classes.CommercialProperty;
import Classes.Property.PropertyStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommercialPropertyDAO {
    private static final String FILE_PATH = "FurtherAsm1/src/File/commercial_properties.txt";
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Converts a CommercialProperty object to a string representation suitable for saving to a file.
     *
     * @param property The CommercialProperty object to convert.
     * @return A comma-separated string representing the property.
     */
    // Convert a CommercialProperty object to String
    private String convertToString(CommercialProperty property) {
        return String.join(",",
                property.getPropertyId(),
                property.getAddress(),
                String.valueOf(property.getPricing()),
                property.getStatus().name(),
                property.getBusinessType(),
                String.valueOf(property.getParkingSpaces()),
                String.valueOf(property.getSquareFootage())
        );
    }

    /**
     * Converts a line of text from the file into a CommercialProperty object.
     *
     * @param line The line of text to convert.
     * @return A CommercialProperty object, or null if the line is invalid.
     */
    // Convert String to a CommercialProperty object
    private CommercialProperty convertToProperty(String line){
        String[] parts = line.split(",");
        if (parts.length < 7) return null;

        String propertyId = parts[0];
        String address = parts[1];
        double pricing = Double.parseDouble(parts[2]);
        PropertyStatus status = PropertyStatus.valueOf(parts[3]);
        String businessType = parts[4];
        int parkingSpaces = Integer.parseInt(parts[5]);
        double squareFootage = Double.parseDouble(parts[6]);
        return new CommercialProperty(propertyId, address, pricing, status, businessType, parkingSpaces, squareFootage);
    }

    /**
     * Reads a list of CommercialProperty objects from the file.
     * If the file does not exist, the user is prompted to create a new file.
     *
     * @return A list of CommercialProperty objects.
     */
    public List<CommercialProperty> readFromFile() {
        List<CommercialProperty> properties = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("Commercial_properties file does not exist.");
            System.out.print("Do you want to create a new file? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();
            } else {
                System.out.println("Process stopped.");
                return properties;  // Return an empty list if the user does not want to create a new file
            }
        }

        // Read data from the file if it exists
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CommercialProperty property = convertToProperty(line);
                if (property != null) properties.add(property);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Creates a new file if it does not exist.
     */
    // Create a new file if not found
    private void createNewFile(){
        try {
            File file = new File(FILE_PATH);
            if (file.createNewFile()){
                System.out.println("New file has been created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of CommercialProperty objects to the file.
     *
     * @param properties The list of properties to write.
     * @param FILE_PATH  The file path where the data will be saved.
     */
    public void writeToFile(List<CommercialProperty> properties, String FILE_PATH){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for (CommercialProperty property : properties){
                writer.write(convertToString(property));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new CommercialProperty to the file.
     *
     * @param property The property to add.
     */
    public void addProperty(CommercialProperty property){
        List<CommercialProperty> properties = readFromFile();
        properties.add(property);
        writeToFile(properties, FILE_PATH);
        System.out.println("Commercial property added successfully.");
    }

    /**
     * Updates an existing CommercialProperty in the file.
     *
     * @param updatedProperty The updated property details.
     * @return True if the property was updated, false otherwise.
     */
    public boolean updateProperty(CommercialProperty updatedProperty){
        List<CommercialProperty> properties = readFromFile();
        boolean updated = false;

        for (int i = 0; i < properties.size(); i++){
            if (updatedProperty.getPropertyId().equals(properties.get(i).getPropertyId())){
                properties.set(i, updatedProperty);
                updated = true;
                break;
            }
        }
        if (updated){
            writeToFile(properties,FILE_PATH);
            System.out.println("Commercial property updated successfully.");
        } else {
            System.out.println("Commercial property does not exist.");
        }
        return updated;
    }


    /**
     * Deletes a CommercialProperty from the file based on its property ID.
     *
     * @param propertyId The ID of the property to delete.
     * @return True if the property was deleted, false otherwise.
     */
    public boolean deleteProperty(String propertyId){
        List<CommercialProperty> properties = readFromFile();
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(propertyId));

        if (removed){
            writeToFile(properties,FILE_PATH);
            System.out.println("Commercial property deleted successfully.");
        } else {
            System.out.println("Commercial property cannot found.");
        }
        return removed;
    }
}


