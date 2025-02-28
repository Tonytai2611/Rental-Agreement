/**
 * Manager class for handling Host objects.
 * Implements the RentalManager interface to provide CRUD operations,
 * validation, sorting, and file I/O for Host data.
 *
 * Author: Truong Phung Tan Tai
 */
package Interface;

import Classes.Host;
import DAO.HostDAO;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostManager implements RentalManager<Host> {
    private List<Host> hosts = new ArrayList<>();
    private HostDAO hostDAO = new HostDAO(); // DAO for saving and loading data from the file
    private static String FilePath = "FurtherAsm1/src/File/hosts.txt";


    /**
     * Adds a new Host to the list.
     *
     * @param item The Host object to add.
     * @return True if the host was successfully added, false if the host ID already exists.
     */

    @Override
    public boolean add(Host item){
        // Check if the hostId already exists
        Host tempHost = new Host(item.getFullName(), item.getId(), item.getDateOfBirth(), item.getContactInfo(), item.getManagedProperties(), item.getCooperatingOwners());
        if (hosts.contains(tempHost)) {
            System.out.println("Host with hostId: " + item.getId() + " already exists.");
            return false;
        }
        hosts.add(item);
        System.out.println("Host successfully added: " + item);
        return true;
    }

    /**
     * Updates an existing Host in the list.
     *
     * @param item The updated Host object.
     */
    @Override
    public void update(Host item){
        boolean updated = false;
        for(int i=0; i<hosts.size(); i++){
            if(hosts.get(i).getId().equals(item.getId())){
                hosts.set(i,item);
                updated = true;
                break;
            }
        }

        if (updated) {
            System.out.println("Host updated successfully!");
        } else {
            System.out.println("No host found with hostId:" + item.getId());
        }
    }

    /**
     * Removes a Host from the list by its ID.
     *
     * @param id The ID of the Host to remove.
     */
    @Override
    public void remove(String id){
        boolean removed = hosts.removeIf(host -> host.getId().equals(id));
        if (removed) {
            System.out.println("Host with hostId removed: " + id);
            saveToFile("FurtherAsm1/src/File/hosts.txt"); // Update the file after deletion
        } else {
            System.out.println("No host found with hostId:" + id);
        }
    }

    /**
     * Retrieves a Host by its ID.
     *
     * @param id The ID of the Host to retrieve.
     * @return The Host object with the given ID, or null if not found.
     */
    @Override
    public Host getOne(String id){
        for(Host host : hosts){
            if(host.getId().equals(id)){
                return host;
            }
        }
        return null;
    }

    /**
     * Retrieves all Host objects.
     *
     * @return A list of all Host objects.
     */
    @Override
    public List<Host> getAll(){
        return new ArrayList<>(hosts);
    }

    /**
     * Retrieves the IDs of all Host objects.
     *
     * @return A list of IDs for all Host objects.
     */
    @Override
    public List<String> getAllIDs(){
        List<String> ids = new ArrayList<>();
        for(Host host : hosts){
            ids.add(host.getId());
        }
        return ids;
    }

    /**
     * Retrieves Host objects by customer ID (not implemented for this manager).
     *
     * @param customerID The customer ID to search for.
     * @return An empty list (no functionality for customer-specific hosts in this manager).
     */
    @Override
    public List<Host> getAllByCustomerID(String customerID){
        return new ArrayList<>();
    }

    /**
     * Saves the list of Host objects to a file.
     *
     * @param fileName The file name to save to.
     */
    @Override
    public void saveToFile(String fileName){
        try {
            // Call HostDAO to save the Host list to the file
            hostDAO.writeToFile(hosts,FilePath);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Loads Host objects from a file.
     *
     * @param fileName The file name to load from.
     */
    @Override
    public void loadFromFile(String fileName){
        try {
            // Call HostDAO to load the Host list from the file
            hosts = hostDAO.readFromFile();
            if (hosts.isEmpty()) {
                System.out.println("No host data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Validates a Host ID to ensure it follows the format "H" followed by digits.
     *
     * @param hostId The host ID to validate.
     * @return True if the ID is valid, false otherwise.
     */
    public boolean validateHostId(String hostId) {
        // Regular expression to check if the hostId starts with "H" followed by one or more digits
        return hostId.matches("^H\\d+$");
    }

    /**
     * Allows the user to input data for a new Host object.
     *
     * @return The created Host object.
     */
    public Host inputHostData() {
        Scanner scanner = new Scanner(System.in);
        String hostId;

        // Prompt for ID input and check for duplicates
        while (true) {
            System.out.print("Enter hostId (must start with 'H' followed by integer numbers): ");
            hostId = scanner.nextLine();

            // Validate hostId format using a regular expression
            if (!validateHostId(hostId)) {
                System.out.println("Error: hostId must start with 'H' followed by integer numbers. Please re-enter.");
                continue; // Prompt to re-enter if the format is invalid
            }

            if (getOne(hostId) != null) {
                System.out.println("This ID already exists, please enter a different ID.");
            } else {
                break; // Exit the loop if the ID is valid and not a duplicate
            }
        }

        // Continue to input other information
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter date of birth (dd-MM-yyyy): ");
        String dateOfBirthStr = scanner.nextLine();

        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        Host host = new Host(fullName, hostId, null, contactInfo, null, null);

        // Convert dateOfBirth from String to Date if necessary
        try {
            host.setDateOfBirth(new java.text.SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirthStr));
        } catch (Exception e) {
            System.out.println("Invalid date format! Setting dateOfBirth to null.");
        }
        return host;
    }

    /**
     * Sorts Host objects by their IDs in ascending order.
     */
    // Sort hosts by ID in ascending order
    public void sortHostsById() {
        hosts.sort((h1, h2) -> {
            try {
                // Extract the numeric part after the "H" prefix and convert it to an integer
                int id1 = Integer.parseInt(h1.getId().substring(1)); // Remove the "H" prefix and parse the number
                int id2 = Integer.parseInt(h2.getId().substring(1)); // Do the same for the second host

                return Integer.compare(id1, id2); // Compare the numeric values
            } catch (NumberFormatException e) {
                // If the ID is not numeric, compare the entire host ID as a string
                return h1.getId().compareTo(h2.getId());
            }
        });
        System.out.println("The list of Hosts has been sorted by ID in ascending order.");
    }

    /**
     * Saves the list of Host objects to a backup file.
     *
     * @param backupFileName The name of the backup file.
     */
    // Save hosts to a backup file
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Invalid backup file name.");
            return;
        }

        try {
            hostDAO.writeToFile(hosts, backupFileName);
            System.out.println("The list of Hosts has been saved to the backup file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving the backup file: " + backupFileName);
            e.printStackTrace();
        }
    }
}
