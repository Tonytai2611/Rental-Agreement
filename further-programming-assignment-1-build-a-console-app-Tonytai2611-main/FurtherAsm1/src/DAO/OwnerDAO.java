/**
 * Data Access Object (DAO) for managing Owner objects.
 * Provides methods to read, write, update, and delete Owner records from a file.
 *
 * This class currently does not handle serialization of `ownedProperties` and `managingHosts`.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package DAO;

import Classes.Owner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class OwnerDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "FurtherAsm1/src/File/owners.txt";
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Converts an Owner object into a string representation for file storage.
     *
     * @param owner The Owner object to convert.
     * @return A comma-separated string representation of the owner.
     */
    // Convert Owner object to String for file writing
    private String convertOwnerToString(Owner owner) {
        return String.join(",",
                owner.getId(),
                owner.getFullName(),
                owner.getDateOfBirth() != null ? dateFormat.format(owner.getDateOfBirth()) : "",
                owner.getContactInfo()
        );
    }

    /**
     * Converts a string from the file into an Owner object.
     *
     * @param line A line of text representing an Owner.
     * @return The corresponding Owner object, or null if the line is invalid.
     * @throws ParseException If the date format is incorrect.
     */
    // Convert String from file to Owner object
    private Owner convertStringToOwner(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String id = parts[0];
        String fullName = parts[1];
        Date dateOfBirth = parts[2].isEmpty() ? null : dateFormat.parse(parts[2]);
        String contactInfo = parts[3];

        return new Owner(fullName, id, dateOfBirth, contactInfo, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Writes a list of Owner objects to the file.
     *
     * @param owners   The list of owners to write.
     * @param FILE_PATH The file path where the data will be stored.
     */
    // Write a list of owners to a file (overwrite file content)
    public void writeToFile(List<Owner> owners, String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Owner owner : owners) {
                writer.write(convertOwnerToString(owner));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads Owner objects from the file and returns them as a list.
     * Creates a new file if it does not exist.
     *
     * @return A list of Owner objects.
     */
    // Read owners from file and return the list
    public List<Owner> readFromFile() {
        List<Owner> owners = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("File Owner does not exist");
            System.out.println("Do you want to create a new file? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();
            } else {
                System.out.println("Processed terminated.");
                return owners;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Owner owner = convertStringToOwner(line);
                if (owner != null) {
                    owners.add(owner);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return owners;
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
     * Updates an existing Owner in the file.
     *
     * @param updatedOwner The updated Owner object.
     * @return True if the owner was updated, false otherwise.
     */
    // Update owner information in the list and write it back to the file
    public boolean update(Owner updatedOwner) {
        List<Owner> owners = readFromFile();

        boolean ownerFound = false;
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(updatedOwner.getId())) {
                owners.set(i, updatedOwner);
                ownerFound = true;
                break;
            }
        }

        if (ownerFound) {
            writeToFile(owners, FILE_PATH);
            System.out.println("Owner updated successfully!");
            return true;
        } else {
            System.out.println("Owner not found!");
            return false;
        }
    }

    /**
     * Deletes an Owner from the file by its ID.
     *
     * @param ownerId The ID of the owner to delete.
     * @return True if the owner was deleted, false otherwise.
     */
    // Delete owner by ownerId and update the file
    public boolean delete(String ownerId) {
        List<Owner> owners = readFromFile();

        boolean ownerFound = false;
        Iterator<Owner> iterator = owners.iterator();
        while (iterator.hasNext()) {
            Owner owner = iterator.next();
            if (owner.getId().equals(ownerId)) {
                iterator.remove();
                ownerFound = true;
                break;
            }
        }

        if (ownerFound) {
            writeToFile(owners, FILE_PATH);
            System.out.println("Owner deleted successfully!");
            return true;
        } else {
            System.out.println("Owner not found!");
            return false;
        }
    }
}

