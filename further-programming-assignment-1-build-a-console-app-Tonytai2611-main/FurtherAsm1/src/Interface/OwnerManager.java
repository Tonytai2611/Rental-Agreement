/**
 * Manager class for handling Owner objects.
 * Implements the RentalManager interface to provide CRUD operations,
 * validation, sorting, and file I/O for Owner data.
 *
 * Author: Truong Phung Tan Tai - s3974929
 */

package Interface;

import Classes.Owner;
import DAO.OwnerDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OwnerManager implements RentalManager<Owner> {
    private List<Owner> owners = new ArrayList<>();
    private OwnerDAO ownerDAO = new OwnerDAO();
    private static String FILE_PATH = "FurtherAsm1/src/File/owners.txt";

    /**
     * Adds a new Owner to the list.
     *
     * @param item The Owner object to add.
     * @return True if the owner was successfully added, false if the owner ID already exists.
     */
    @Override
    public boolean add(Owner item){
        if (owners.contains(item)) {
            System.out.println("Owner with ownerId: " + item.getId() + " already exists.");
            return false;
        }
        owners.add(item);
        System.out.println("Owner successfully added: " + item);
        return true;
    }

    /**
     * Updates an existing Owner in the list.
     *
     * @param item The updated Owner object.
     */
    @Override
    public void update(Owner item){
        boolean updated = false;
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(item.getId())) {
                owners.set(i, item);
                updated = true;
                break;
            }
        }

        if (updated) {
            System.out.println("Owner updated successfully!");
        } else {
            System.out.println("No owner found with ownerId: " + item.getId());
        }
    }

    /**
     * Removes an Owner from the list by its ID.
     *
     * @param id The ID of the Owner to remove.
     */
    @Override
    public void remove(String id ){
        boolean removed = owners.removeIf(owner -> owner.getId().equals(id));
        if (removed) {
            System.out.println("Owner with ownerId removed: " + id);
            saveToFile("FurtherAsm1/src/File/owners.txt");
        } else {
            System.out.println("No owner found with ownerId: " + id);
        }
    }

    /**
     * Retrieves an Owner by its ID.
     *
     * @param id The ID of the Owner to retrieve.
     * @return The Owner object with the given ID, or null if not found.
     */
    @Override
    public Owner getOne(String id){
        for(Owner owner : owners){
            if(owner.getId().equals(id)){
                return owner;
            }
        }
        return null;
    }

    /**
     * Retrieves all Owner objects.
     *
     * @return A list of all Owner objects.
     */
    @Override
    public List<Owner> getAll(){
        return new ArrayList<>(owners);
    }

    /**
     * Retrieves the IDs of all Owner objects.
     *
     * @return A list of IDs for all Owner objects.
     */
    @Override
    public List<String> getAllIDs(){
        List<String> ids = new ArrayList<>();
        for(Owner owner : owners){
            ids.add(owner.getId());
        }
        return ids;
    }

    /**
     * Retrieves Owner objects by customer ID (not implemented for this manager).
     *
     * @param customerID The customer ID to search for.
     * @return An empty list (no functionality for customer-specific owners in this manager).
     */
    @Override
    public List<Owner> getAllByCustomerID(String customerID){
        return new ArrayList<>();
    }

    /**
     * Saves the list of Owner objects to a file.
     *
     * @param fileName The file name to save to.
     */
    @Override
    public void saveToFile(String fileName){
        try {
            // Call OwnerDAO to save List owner into file
            ownerDAO.writeToFile(owners,FILE_PATH);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Loads Owner objects from a file.
     *
     * @param fileName The file name to load from.
     */
    @Override
    public void loadFromFile(String fileName){
        try {
            // Call OwnerDAO to load List Owner from file
            owners = ownerDAO.readFromFile();
            if (owners.isEmpty()) {
                System.out.println("No owner data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Allows the user to input data for a new Owner object.
     *
     * @return The created Owner object.
     */
    public Owner inputOwnerData() {
        Scanner scanner = new Scanner(System.in);
        String ownerId;

        // Prompt for ID input and check for duplicates
        while (true) {
            System.out.print("Enter ownerId: ");
            ownerId = scanner.nextLine();

            // Validate ownerId format using the regular expression "O" + digits
            if (!ownerId.matches("^O\\d+$")) {
                System.out.println("Invalid ownerId format. It must start with 'O' followed by digits.");
                continue;
            }

            if (getOne(ownerId) != null) {
                System.out.println("This ID already exists, please enter a different ID.");
            } else {
                break; // Exit the loop if the ID is valid and not a duplicate
            }
        }


        // Input other information
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter date of birth (dd-MM-yyyy): ");
        String dateOfBirthStr = scanner.nextLine();

        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        Owner owner = new Owner(fullName, ownerId, null, contactInfo, null, null);

        // Convert dateOfBirth from String to Date if necessary
        try {
            owner.setDateOfBirth(new java.text.SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirthStr));
        } catch (Exception e) {
            System.out.println("Invalid date format! Setting dateOfBirth to null.");
        }

        return owner;
    }

    /**
     * Sorts Owner objects by their IDs in ascending order.
     */
    // Sort owners by ID in ascending order
    public void sortOwnersById() {
        owners.sort((o1, o2) -> {
            try {
                // Extract the numeric part after the "O" prefix and convert it to an integer
                int id1 = Integer.parseInt(o1.getId().substring(1)); // Remove "O" prefix and parse the number
                int id2 = Integer.parseInt(o2.getId().substring(1)); // Same for the second owner

                return Integer.compare(id1, id2); // Compare the numeric values
            } catch (NumberFormatException e) {
                // If the ID can't be parsed as a number, fall back to comparing them as strings
                return o1.getId().compareTo(o2.getId());
            }
        });
        System.out.println("Owners list has been sorted by ID (ascending).");
    }

    /**
     * Saves the list of Owner objects to a backup file.
     *
     * @param backupFileName The name of the backup file.
     */
    // Save owners to a backup file
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Invalid backup file name.");
            return;
        }

        try {
            ownerDAO.writeToFile(owners, backupFileName);
            System.out.println("Owners list has been saved to backup file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving to backup file: " + backupFileName);
            e.printStackTrace();
        }
    }
}
