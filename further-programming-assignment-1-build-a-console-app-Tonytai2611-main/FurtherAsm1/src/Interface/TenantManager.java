/**
 * @author <Truong Phung Tan Tai - s3974929
 *
 * This class manages Tenant objects, including adding, updating, removing, and retrieving tenants.
 * It also supports saving and loading tenant data from files, validating tenant IDs,
 * sorting tenants by ID, and managing backups.
 */


package Interface;

import Classes.Tenant;
import DAO.TenantDAO;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TenantManager implements RentalManager<Tenant> {
    private List<Tenant> tenants = new ArrayList<>();
    private TenantDAO tenantDAO = new TenantDAO();
    private static String FilePath = "FurtherAsm1/src/File/tenants.txt";

    /**
     * Adds a new Tenant to the list if it doesn't already exist.
     *
     * @param item The Tenant object to be added.
     * @return true if the tenant was added successfully, false if the tenant already exists.
     */
    @Override
    public boolean add(Tenant item) {
        // Check if tenantId already exists
        Tenant tempTenant = new Tenant(item.getFullName(),item.getId(),item.getDateOfBirth(), item.getContactInfo(), item.getRentalAgreements(), item.getPaymentRecords());
        if (tenants.contains(tempTenant)) {
            System.out.println("Tenant with tenantId: " + item.getId());
            return false;
        }
        tenants.add(item);
        System.out.println("Tenant successfully added: " + item);
        return true;
    }

    /**
     * Updates the information of an existing Tenant.
     *
     * @param item The Tenant object containing updated information.
     */
    @Override
    public void update(Tenant item) {
        boolean updated = false;
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId().equals(item.getId())) {
                tenants.set(i, item);
                updated = true;
                break;
            }
        }

        if (updated) {
            System.out.println("Tenant updated successfully!");
        } else {
            System.out.println("No tenant found with tenantId: " + item.getId());
        }
    }

    /**
     * Removes a Tenant from the list based on their ID.
     *
     * @param id The ID of the Tenant to be removed.
     */
    @Override
    public void remove(String id) {
        boolean removed = tenants.removeIf(tenant -> tenant.getId().equals(id));
        if (removed) {
            System.out.println("Tenant with tenantId removed: " + id);
            saveToFile("FurtherAsm1/src/File/tenants.txt"); // Update data into file after remove
        } else {
            System.out.println("No tenant found with tenantId: " + id);
        }
    }

    /**
     * Retrieves a Tenant object based on their ID.
     *
     * @param id The ID of the Tenant to retrieve.
     * @return The Tenant object with the specified ID, or null if not found.
     */
    @Override
    public Tenant getOne(String id) {
        for (Tenant tenant : tenants) {
            if (tenant.getId().equals(id)) {
                return tenant;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of all Tenant objects.
     *
     * @return A list containing all tenants.
     */
    @Override
    public List<Tenant> getAll() { return new ArrayList<>(tenants); }

    /**
     * Retrieves a list of all tenant IDs.
     *
     * @return A list of strings representing tenant IDs.
     */
    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Tenant tenant : tenants) {
            ids.add(tenant.getId());
        }
        return ids;
    }

    /**
     * This method is not implemented. Returns an empty list.
     *
     * @param customerID The customer ID (not used in this implementation).
     * @return An empty list.
     */
    @Override
    public List<Tenant> getAllByCustomerID(String customerID) {
        return new ArrayList<>();
    }

    /**
     * Saves the current list of tenants to a specified file.
     *
     * @param fileName The name of the file to save the data to.
     */
    @Override
    public void saveToFile(String fileName) {
        try {
            tenantDAO.writeToFile(tenants,FilePath);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }

    }

    /**
     * Loads tenant data from a specified file into the list.
     *
     * @param fileName The name of the file to load data from.
     */
    @Override
    public void loadFromFile(String fileName) {
        try {
            tenants = tenantDAO.readFromFile();
            if (tenants.isEmpty()) {
                System.out.println("No tenant data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Validates a tenant ID to ensure it follows the required format.
     * The ID must start with "T" followed by natural numbers.
     *
     * @param contractId The tenant ID to validate.
     * @return true if the ID is valid, false otherwise.
     */
    public boolean validateContractId(String contractId) {
        // Regular expression to check if the contractId starts with "RA"
        return contractId.matches("^T\\d+$");

    }

    /**
     * Prompts the user to input data for a new Tenant and creates a Tenant object.
     * The tenant ID is validated to ensure it follows the correct format and is unique.
     *
     * @return A Tenant object containing the entered data.
     */
    public Tenant inputTenantData() {
        Scanner scanner = new Scanner(System.in);
        String tenantId;

        // Prompt for tenantId input and check for duplicates
        while (true) {
            System.out.print("Enter tenantId (must start with 'T' followed by natural numbers): ");
            tenantId = scanner.nextLine();

            // Validate format using a regular expression
            if (!validateContractId(tenantId)) {
                System.out.println("Error: tenantId must start with 'T' followed by natural numbers. Please re-enter.");
                continue; // Prompt to re-enter if invalid
            }

            if (getOne(tenantId) != null) {
                System.out.println("This ID already exists, please enter a different ID.");
            } else {
                break; // Exit the loop if the ID is valid and unique
            }
        }

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        Date dateOfBirth = null;

        while (dateOfBirth == null) { // Loop until entering correctly
            System.out.print("Enter date of birth (dd-MM-yyyy): ");
            String dateOfBirthStr = scanner.nextLine();

            try {
                // Ép kiểu String sang Date
                dateOfBirth = dateFormat.parse(dateOfBirthStr);
                System.out.println("Date of Birth: " + dateOfBirth);
            } catch (ParseException e) {
                // Thông báo lỗi nếu định dạng không đúng
                System.out.println("Invalid date format. Please enter the date in dd-MM-yyyy format.");
            }
        }

        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        Tenant tenant = new Tenant(fullName, tenantId, null, contactInfo, null, null);

        return tenant;
    }

    /**
     * Sorts the list of tenants by their IDs in ascending order.
     */
    public void sortTenantsById() {
        tenants.sort((t1, t2) -> {
            try {
                // Extract the numeric part of tenant IDs after the "T" prefix
                int id1 = Integer.parseInt(t1.getId().substring(1)); // Remove "T" and convert the rest to an integer
                int id2 = Integer.parseInt(t2.getId().substring(1)); // Same for the second tenant

                return Integer.compare(id1, id2); // Compare the numeric values
            } catch (NumberFormatException e) {
                // If there's a problem with parsing the ID, fallback to string comparison
                return t1.getId().compareTo(t2.getId());
            }
        });
        System.out.println("Tenants sorted by ID in ascending order.");
    }


    /**
     * Saves a backup of the tenant list to a specified file.
     *
     * @param backupFileName The name of the backup file to save the data to.
     */
    public void saveBackupToFile(String backupFileName) {
        try {
            tenantDAO.writeToFile(tenants, backupFileName);
            System.out.println("Backup saved successfully to file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving backup to file: " + backupFileName);
            e.printStackTrace();
        }
    }
}


