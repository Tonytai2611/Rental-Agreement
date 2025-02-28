/**
 * Data Access Object (DAO) for managing Tenant objects.
 * This class provides methods to read, write, update, and delete Tenant records from a file.
 *
 * File Path: `FurtherAsm1/src/File/tenants.txt`
 *
 * This class ensures the persistence of Tenant data and supports serialization and deserialization.
 *
 * Author: Truong Phung Tan Tai - s3974929
 */
package DAO;

import Classes.Tenant;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TenantDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "FurtherAsm1/src/File/tenants.txt";
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Converts a Tenant object into a string representation for file storage.
     *
     * @param tenant The Tenant object to convert.
     * @return A comma-separated string representation of the tenant.
     */
    // Convert Tenant object to String for file writing
    private String convertTenantToString(Tenant tenant) {
        return String.join(",",
                tenant.getId(),
                tenant.getFullName(),
                tenant.getDateOfBirth() != null ? dateFormat.format(tenant.getDateOfBirth()) : "",
                tenant.getContactInfo()
        );
    }

    /**
     * Converts a string from the file into a Tenant object.
     *
     * @param line A line of text representing a Tenant.
     * @return A Tenant object, or null if the string is invalid.
     * @throws ParseException If the date format in the string is invalid.
     */
    // Convert String from file to Tenant object
    private Tenant convertStringToTenant(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            System.err.println("Invalid format:" + line);
            return null;
        }

        String id = parts[0];
        String fullName = parts[1];
        Date dateOfBirth = parts[2].isEmpty() ? null : dateFormat.parse(parts[2]);
        String contactInfo = parts[3];

        return new Tenant(fullName, id, dateOfBirth, contactInfo, null, null);
    }

    /**
     * Writes a list of Tenant objects to a file, overwriting the existing content.
     *
     * @param tenants  The list of Tenant objects to write.
     * @param FILE_PATH The file path where the data will be stored.
     */
    // Write a list of tenants.txt to a file (overwrite file content)
    public void writeToFile(List<Tenant> tenants, String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Tenant tenant : tenants) {
                writer.write(convertTenantToString(tenant));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads Tenant objects from the file and returns them as a list.
     * If the file does not exist, prompts the user to create a new file.
     *
     * @return A list of Tenant objects.
     */
    // Read tenants.txt from the file and return the list of tenants.txt
    public List<Tenant> readFromFile() {
        List<Tenant> tenants = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("Tenant file does not exist.");
            System.out.print("Would you like to create a new file? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();  // Create a new file if the user agrees
            } else {
                System.out.println("Process stopped.");
                return tenants;  // Return an empty list if the user does not want to create a new file
            }
        }

        // Read data from the file if it exists
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Tenant tenant = convertStringToTenant(line);  // Convert each line into a Tenant
                if (tenant != null) {
                    tenants.add(tenant);  // Add the tenant to the list
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return tenants;  // Return the list of tenants.txt read from the file
    }

    /**
     * Creates a new file if it does not exist.
     */
    // Create a new file if it does not exist
    private void createNewFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.createNewFile()) {
                System.out.println("A new file has been created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing Tenant in the file.
     *
     * @param updatedTenant The updated Tenant object.
     * @return True if the tenant was updated successfully, false otherwise.
     */
    // Update tenant information in the list and write it back to the file
    public boolean update(Tenant updatedTenant) {
        List<Tenant> tenants = readFromFile(); // Read the list of tenants.txt from the file

        boolean tenantFound = false;
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId().equals(updatedTenant.getId())) {
                // Update tenant information
                tenants.set(i, updatedTenant);
                tenantFound = true;
                break;
            }
        }

        if (tenantFound) {
            // Write the updated list of tenants.txt back to the file
            writeToFile(tenants, FILE_PATH);
            System.out.println("Tenant updated successfully!");
            return true;
        } else {
            System.out.println("Tenant not found!");
            return false;
        }
    }

    /**
     * Deletes a Tenant from the file by its ID.
     *
     * @param tenantId The ID of the Tenant to delete.
     * @return True if the tenant was deleted successfully, false otherwise.
     */
    // Delete tenant by tenantId and update the file
    public boolean delete(String tenantId) {
        List<Tenant> tenants = readFromFile(); // Read the list of tenants.txt from the file

        boolean tenantFound = false;
        Iterator<Tenant> iterator = tenants.iterator();
        while (iterator.hasNext()) {
            Tenant tenant = iterator.next();
            if (tenant.getId().equals(tenantId)) {
                iterator.remove(); // Remove the tenant from the list
                tenantFound = true;
                break;
            }
        }

        if (tenantFound) {
            // Write the list of tenants.txt after deletion back to the file
            writeToFile(tenants, FILE_PATH);
            System.out.println("Tenant deleted successfully!");
            return true;
        } else {
            System.out.println("Tenant not found!");
            return false;
        }
    }



}
