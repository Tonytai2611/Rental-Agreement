/**
 * Manager class for handling CommercialProperty objects.
 * Implements the RentalManager interface to provide CRUD operations,
 * validation, sorting, and file I/O for CommercialProperty data.
 *
 * Author: Truong Phung Tan Tai
 */
package Interface;

import Classes.CommercialProperty;
import Classes.Property;
import DAO.CommercialPropertyDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommercialPropertyManager implements  RentalManager<CommercialProperty>{
    private List<CommercialProperty> properties = new ArrayList<>();
    private CommercialPropertyDAO propertyDAO = new CommercialPropertyDAO();
    private static String FILE_NAME = "FurtherAsm1/src/File/commercial_properties.txt";

    /**
     * Adds a new CommercialProperty to the list.
     *
     * @param item The CommercialProperty object to add.
     * @return True if the property was successfully added, false if a property with the same ID already exists.
     */
    @Override
    public boolean add(CommercialProperty item) {
        if (properties.stream().anyMatch(p -> p.getPropertyId().equals(item.getPropertyId()))) {
            System.out.println("Error: Property ID already exists: " + item.getPropertyId());
            return false;
        }
        properties.add(item);
        System.out.println("Commercial Property successfully added: " + item);
        return true;
    }

    /**
     * Updates an existing CommercialProperty in the list.
     *
     * @param item The updated CommercialProperty object.
     */
    @Override
    public void update(CommercialProperty item){
        for(int i=0; i<properties.size(); i++){
            if(properties.get(i).getPropertyId().equals(item.getPropertyId())){
                properties.set(i, item);
                System.out.println("Commercial Property updated successfully!");
                saveToFile("src/FurtherAsm1/File/commercial_properties.txt");
                return;
            }
        }
        System.out.println("No property found with ID: " + item.getPropertyId());
    }

    /**
     * Removes a CommercialProperty from the list by its ID.
     *
     * @param id The ID of the CommercialProperty to remove.
     */
    @Override
    public void remove(String id) {
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(id));
        if (removed) {
            System.out.println("Commercial Property with ID removed: " + id);
            saveToFile("FurtherAsm1/src/File/commercial_properties.txt");
        } else {
            System.out.println("No property found with ID: " + id);
        }
    }

    /**
     * Retrieves a CommercialProperty by its ID.
     *
     * @param id The ID of the property to retrieve.
     * @return The CommercialProperty object with the given ID, or null if not found.
     */
    @Override
    public CommercialProperty getOne(String id){
        return properties.stream()
                .filter(property -> property.getPropertyId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all CommercialProperty objects.
     *
     * @return A list of all CommercialProperty objects.
     */
    @Override
    public List<CommercialProperty> getAll(){
        return new ArrayList<>(properties);
    }

    /**
     * Retrieves the IDs of all CommercialProperty objects.
     *
     * @return A list of IDs for all CommercialProperty objects.
     */
    @Override
    public List<String> getAllIDs(){
        List<String> ids = new ArrayList<>();
        for(CommercialProperty property : properties){
            ids.add(property.getPropertyId());
        }
        return ids;
    }

    /**
     * Retrieves CommercialProperty objects by customer ID (not implemented for this manager).
     *
     * @param customerID The customer ID to search for.
     * @return An empty list (no functionality for customer-specific properties in this manager).
     */
    @Override
    public List<CommercialProperty> getAllByCustomerID(String customerID){
        return new ArrayList<>();
    }

    /**
     * Saves the list of CommercialProperty objects to a file.
     *
     * @param fileName The file name to save to.
     */
    @Override
    public void saveToFile(String fileName){
        try {
            propertyDAO.writeToFile(properties,FILE_NAME);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }

    }

    /**
     * Loads CommercialProperty objects from a file.
     *
     * @param fileName The file name to load from.
     */
    @Override
    public void loadFromFile(String fileName) {
        try {
            properties = propertyDAO.readFromFile();
            if (properties.isEmpty()) {
                System.out.println("No data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while loading from file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Validates a CommercialProperty ID to ensure it follows the format "CP" followed by digits.
     *
     * @param propertyId The property ID to validate.
     * @return True if the ID is valid, false otherwise.
     */
    public boolean validatePropertyId(String propertyId) {
        // Regular expression to check if the propertyId starts with "CP" followed by one or more digits
        return propertyId.matches("^CP\\d+$");
    }

    /**
     * Allows the user to input data for a new CommercialProperty object.
     *
     * @return The created CommercialProperty object.
     */
    // Method to input CommercialProperty data from the user
    public CommercialProperty inputCommercialPropertyData() {
        Scanner scanner = new Scanner(System.in);
        String propertyId;

        // Check if the property ID already exists
        while (true) {
            System.out.print("Enter propertyId (must start with 'CP' followed by integer numbers): ");
            propertyId = scanner.nextLine();

            // Validate propertyId format
            if (!validatePropertyId(propertyId)) {
                System.out.println("Error: Property ID must start with 'CP' followed by natural numbers. Please re-enter.");
                continue; // Prompt to re-enter if the format is invalid
            }

            // Check if the ID already exists in the list
            String finalPropertyId = propertyId;
            boolean exists = properties.stream()
                    .anyMatch(property -> property.getPropertyId().equals(finalPropertyId));

            if (exists) {
                System.out.println("Error: Property ID already exists. Please re-enter.");
            } else {
                break;
            }
        }

        System.out.print("Enter property address: ");
        String address = scanner.nextLine();

        System.out.print("Enter property price: ");
        double pricing = scanner.nextDouble();

        scanner.nextLine(); // Read the extra leftover line

        System.out.print("Enter property status (AVAILABLE, RENTED, UNDER_MAINTENANCE): ");
        String statusInput = scanner.nextLine();
        Property.PropertyStatus status = Property.PropertyStatus.valueOf(statusInput.toUpperCase());

        System.out.print("Enter business type (e.g., Retail, Office): ");
        String businessType = scanner.nextLine();

        System.out.print("Enter the number of parking spaces: ");
        int parkingSpaces = scanner.nextInt();

        System.out.print("Enter square footage: ");
        double squareFootage = scanner.nextDouble();

        // Create a new CommercialProperty object
        CommercialProperty property = new CommercialProperty(propertyId, address, pricing, status, businessType, parkingSpaces, squareFootage);

        return property;
    }


    /**
     * Sorts CommercialProperty objects by their IDs in ascending order.
     */
    // Sort commercial properties by ID in ascending order, where IDs start with "CP"
    public void sortPropertiesById() {
        properties.sort((p1, p2) -> {
            // Extract the numeric part after "CP"
            String id1 = p1.getPropertyId().substring(2); // Extract the part after "CP"
            String id2 = p2.getPropertyId().substring(2); // Extract the part after "CP"

            // Compare as numbers
            try {
                int idNum1 = Integer.parseInt(id1); // Convert the numeric part to an int
                int idNum2 = Integer.parseInt(id2); // Convert the numeric part to an int
                return Integer.compare(idNum1, idNum2); // Compare the two numbers
            } catch (NumberFormatException e) {
                // If conversion fails (invalid ID format), compare as strings
                return id1.compareTo(id2);
            }
        });

        System.out.println("The list of Commercial Properties has been sorted by ID in ascending order.");
    }


    /**
     * Saves the list of CommercialProperty objects to a backup file.
     *
     * @param backupFileName The name of the backup file.
     */
    // Save the list of commercial properties to a backup file
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Invalid backup file name.");
            return;
        }

        try {
            propertyDAO.writeToFile(properties, backupFileName); // Write properties list to file
            System.out.println("Commercial Properties list has been saved to backup file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving to backup file: " + backupFileName);
            e.printStackTrace();
        }
    }
}
