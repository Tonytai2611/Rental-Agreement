/**
 * A Data Access Object (DAO) class for managing `Host` objects.
 * This class provides methods to read, write, update, and delete hosts in a file-based storage system.
 * It uses a text file to persist host information and interacts with the `Host` class.
 *
 * Note: Currently, `managedProperties` and `cooperatingOwners` are not restored from the file.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */

package DAO;

import Classes.Host;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class HostDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "FurtherAsm1/src/File/hosts.txt";
    private static final Scanner scanner = new Scanner(System.in);


    /**
     * Converts a `Host` object to a string representation suitable for file storage.
     *
     * @param host The `Host` object to convert.
     * @return A comma-separated string representation of the host.
     */
    // Convert Host object to String for file writing
    private String convertHostToString(Host host) {
        return String.join(",",
                host.getId(),
                host.getFullName(),
                host.getDateOfBirth() != null ? dateFormat.format(host.getDateOfBirth())  : "",
                host.getContactInfo()
                );
    }

    /**
     * Converts a string representation of a host from the file into a `Host` object.
     *
     * @param line The string representation of a host.
     * @return A `Host` object or `null` if the input is invalid.
     * @throws ParseException If the date format in the string is invalid.
     */
    // Convert String from file to Host object
    private Host convertStringToHost(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String id = parts[0];
        String fullName = parts[1];
        Date dateOfBirth = parts[2].isEmpty() ? null : dateFormat.parse(parts[2]);
        String contactInfo = parts[3];

        // Currently not restoring managedProperties and cooperatingOwners from file
        return new Host(fullName, id, dateOfBirth, contactInfo, null, null); // Still solving
    }

    /**
     * Writes a list of `Host` objects to the file, overwriting the existing content.
     *
     * @param hosts The list of hosts to write to the file.
     * @param FILE_PATH The path of the file where data will be written.
     */
    // Write a list of hosts to a file ( overwrite file content)
    public void writeToFile(List<Host> hosts, String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Host host : hosts) {
                writer.write(convertHostToString(host));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a list of `Host` objects from the file.
     * If the file does not exist, the user is prompted to create a new file.
     *
     * @return A list of `Host` objects.
     */
    // Read hosts from file and return the list
    public List<Host> readFromFile() {
        List<Host> hosts = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("File Host does not exist");
            System.out.print("Do you want to create a new file? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();
            } else {
                System.out.println("Process terminated.");
                return hosts;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Host host = convertStringToHost(line);
                if (host != null) {
                    hosts.add(host);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return hosts;
    }

    /**
     * Creates a new file if it does not exist.
     */
    // Create a new file if it does not exist
    private void createNewFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.createNewFile()) {
                System.out.println("New file created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the information of a host in the file.
     *
     * @param updatedHost The updated host information.
     * @return True if the host was successfully updated, false otherwise.
     */
    // Update host information in the list and write it back to the file
    public boolean update(Host updatedHost) {
        List<Host> hosts = readFromFile();

        boolean hostFound = false;
        for (int i = 0; i < hosts.size(); i++){
            if (hosts.get(i).getId().equals(updatedHost.getId())){
                hosts.set(i, updatedHost);
                hostFound = true;
                break;
            }
        }
        if (hostFound){
            writeToFile(hosts,FILE_PATH);
            System.out.println("Host updated successfully!");
            return true;
        } else {
            System.out.println("Host not found");
            return false;
        }
    }

    /**
     * Deletes a host by its ID and updates the file.
     *
     * @param hostId The ID of the host to delete.
     * @return True if the host was successfully deleted, false otherwise.
     */
    // Delete host by hostId and update the file
    public  boolean delete(String hostId) {
        List<Host> hosts = readFromFile();

        boolean hostFound = false;
        Iterator<Host> iterator = hosts.iterator();
        while (iterator.hasNext()) {
            Host host = iterator.next();
            if (host.getId().equals(hostId)){
                iterator.remove();
                hostFound = true;
                break;
            }
        }

        if (hostFound) {
            writeToFile(hosts, FILE_PATH);
            System.out.println("Host deleted successfully!");
            return true;
        } else {
            System.out.println("Host not found!");
            return false;
        }
    }
}

