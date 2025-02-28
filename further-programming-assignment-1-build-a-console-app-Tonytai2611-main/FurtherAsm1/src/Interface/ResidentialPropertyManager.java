/**
 * Manager class for handling ResidentialProperty objects.
 * Implements the RentalManager interface to provide CRUD operations,
 * sorting, and file I/O for ResidentialProperty data.
 * Author: Truong Phung Tan Tai - s3974929
 */
package Interface;

import Classes.ResidentialProperty;
import Classes.Property;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import DAO.ResidentialPropertyDAO;


public class ResidentialPropertyManager  implements RentalManager<ResidentialProperty>{
    private List<ResidentialProperty> properties = new ArrayList<>();
    private ResidentialPropertyDAO propertyDAO = new ResidentialPropertyDAO(); // Using DAO to write and read from file
    private static String FILE_PATH ="FurtherAsm1/src/File/residential_properties.txt";

    /**
     * Adds a new ResidentialProperty to the list.
     *
     * @param item The ResidentialProperty object to add.
     * @return {@code true} if the property is successfully added,
     *         {@code false} if the property ID already exists.
     */
    @Override
    public boolean add(ResidentialProperty item) {
        if (properties.stream().anyMatch(p -> p.getPropertyId().equals(item.getPropertyId()))) {
            System.out.println("Error: Property ID already exists: " + item.getPropertyId());
            return false;
        }
        properties.add(item);
        System.out.println("Residential Property successfully added:\n " + item);
        return true;
    }

    /**
     * Updates an existing ResidentialProperty in the list.
     *
     * @param item The updated ResidentialProperty object.
     */
    @Override
    public void update(ResidentialProperty item) {
        for(int i = 0; i<properties.size(); i++){
            if(properties.get(i).getPropertyId().equals(item.getPropertyId())){
                properties.set(i, item);
                System.out.println("Residential Property successfully update!");
                saveToFile("FurtherAsm1/src/File/residential_properties.txt");
                return;
            }
        }
        System.out.println("No property found with ID: " + item.getPropertyId());
    }

    /**
     * Removes a ResidentialProperty from the list by its ID.
     *
     * @param id The ID of the ResidentialProperty to remove.
     */
    @Override
    public void remove(String id) {
        boolean removed = properties.removeIf(property -> property.getPropertyId().equals(id));
        if (removed) {
            System.out.println("Residential Property with ID removed: " + id);
            saveToFile("FurtherAsm1/src/File/residential_properties.txt");
        } else {
            System.out.println("No property found with ID: " + id);
        }
    }

    /**
     * Retrieves a ResidentialProperty by its ID.
     *
     * @param id The ID of the ResidentialProperty to retrieve.
     * @return The ResidentialProperty object with the given ID, or {@code null} if not found.
     */
    @Override
    public ResidentialProperty getOne(String id) {
        return properties.stream()
                .filter(property -> property.getPropertyId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all ResidentialProperty objects.
     *
     * @return A list of all ResidentialProperty objects.
     */
    @Override
    public List<ResidentialProperty> getAll() {
        return new ArrayList<>(properties);
    }

    /**
     * Retrieves all IDs of ResidentialProperty objects.
     *
     * @return A list of IDs for all ResidentialProperty objects.
     */
    @Override
    public List<String> getAllIDs(){
        List<String> ids = new ArrayList<>();
        for(ResidentialProperty property : properties){
            ids.add(property.getPropertyId());
        }
        return ids;
    }

    /**
     * Retrieves a list of ResidentialProperty objects associated with a given customer ID.
     * This method searches through the list of ResidentialProperty objects and returns
     * properties that are linked to the specified customer ID.
     *
     * @param customerID The ID of the customer whose associated properties are to be retrieved.
     * @return A list of ResidentialProperty objects associated with the given customer ID.
     *         If no properties are found, an empty list is returned.
     */
    @Override
    public List<ResidentialProperty> getAllByCustomerID(String customerID){
        return new ArrayList<>();
    }

    /**
     * Saves the list of ResidentialProperty objects to a file.
     *
     * @param fileName The name of the file to save to.
     */
    @Override
    public void saveToFile(String fileName) {
        try {
            propertyDAO.writeToFile(properties,FILE_PATH);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Loads ResidentialProperty objects from a file.
     *
     * @param fileName The name of the file to load from.
     */
    @Override
    public void loadFromFile(String fileName) {
        try {
            properties = propertyDAO.readFromFile();
            if (properties.isEmpty()) {
                System.out.println("No data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while loading loading from file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user to input data for a new ResidentialProperty object.
     *
     * @return The created ResidentialProperty object.
     */
    // Method to input ResidentialProperty data from the user
    public ResidentialProperty inputResidentialPropertyData() {
        Scanner scanner = new Scanner(System.in);
        String propertyId;

        // Check if the ID already exists
        while (true) {
            System.out.print("Enter property ID: ");
            propertyId = scanner.nextLine();

            // Validate that the ID starts with "RP" followed by numbers
            if (!propertyId.matches("^RP\\d+$")) {
                System.out.println("Error: Property ID must start with 'RP' followed by numbers. Please re-enter.");
                continue;
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

        scanner.nextLine();  // Consume the leftover newline

        System.out.print("Enter property status (AVAILABLE, RENTED, UNDER_MAINTENANCE): ");
        String statusInput = scanner.nextLine();
        Property.PropertyStatus status = Property.PropertyStatus.valueOf(statusInput.toUpperCase());

        System.out.print("Enter number of bedrooms: ");
        int numBedrooms = scanner.nextInt();

        System.out.print("Does the property have a garden (true/false): ");
        boolean gardenAvailability = scanner.nextBoolean();

        System.out.print("Is the property pet-friendly (true/false): ");
        boolean petFriendliness = scanner.nextBoolean();

        // Create a new ResidentialProperty object
        ResidentialProperty property = new ResidentialProperty(propertyId, address, pricing, status, numBedrooms, gardenAvailability, petFriendliness);

        return property;
    }

    /**
     * Sorts the ResidentialProperty objects by their property IDs in ascending order.
     */
    // Sort Residential Properties by propertyId in ascending order
    public void sortPropertiesById() {
        properties.sort((p1, p2) -> {
            try {
                // Extract the numeric part of propertyId after "RP" and convert it to an integer
                int id1 = Integer.parseInt(p1.getPropertyId().substring(2)); // Remove "RP" and convert the remaining part to an integer
                int id2 = Integer.parseInt(p2.getPropertyId().substring(2)); // Do the same for p2

                return Integer.compare(id1, id2); // Compare the integers

            } catch (NumberFormatException e) {
                // If propertyId is not a number, use string comparison
                return p1.getPropertyId().compareTo(p2.getPropertyId());
            }
        });
        System.out.println("Residential Properties list has been sorted by propertyId (ascending).");
    }

    /**
     * Saves the list of ResidentialProperty objects to a backup file.
     *
     * @param backupFileName The name of the backup file.
     */
    // Save the list of Residential Properties to a backup file
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Invalid backup file name.");
            return;
        }

        try {
            propertyDAO.writeToFile(properties, backupFileName);  // Write the properties list to the file
            System.out.println("Residential Properties list has been saved to backup file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving to backup file: " + backupFileName);
            e.printStackTrace();
        }
    }



}
