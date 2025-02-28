/**
 * Manager class for handling RentalAgreement objects.
 * Implements the RentalManager interface to provide CRUD operations,
 * validation, sorting, filtering, and file I/O for RentalAgreement data.
 * Author: Truong Phung Tan Tai - s3974929
 */

package Interface;

import Classes.*;
import DAO.RentalAgreementDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RentalAgreementManager implements RentalManager<RentalAgreement> {
    private List<RentalAgreement> agreements = new ArrayList<>();
    private RentalAgreementDAO rentalAgreementDAO = new RentalAgreementDAO();
    private TenantManager tenantManager = new TenantManager();
    private HostManager hostManager = new HostManager();
    private static String FILE_PATH = "FurtherAsm1/src/File/rental_agreements.txt";

    /**
     * Adds a new RentalAgreement to the list.
     *
     * @param item The RentalAgreement object to add.
     * @return True if the rental agreement was successfully added, false if the contract ID already exists.
     */
    @Override
    public boolean add(RentalAgreement item) {
        if (agreements.contains(item)) {
            System.out.println("Error: contractId already exists: " + item.getContractId());
            return false;
        }
        agreements.add(item);
        System.out.println("Rental Agreement successfully added: " + item);
        return true;
    }

    /**
     * Updates an existing RentalAgreement in the list.
     *
     * @param item The updated RentalAgreement object.
     */
    @Override
    public void update(RentalAgreement item) {
        if (rentalAgreementDAO.update(item)) {
            System.out.println("Rental Agreement updated successfully!");
        } else {
            System.out.println("No rental agreement found with contractId: " + item.getContractId());
        }
    }

    /**
     * Removes a RentalAgreement from the list by its ID.
     *
     * @param id The ID of the RentalAgreement to remove.
     */
    @Override
    public void remove(String id) {
        boolean removed = agreements.removeIf(agreement -> agreement.getContractId().equals(id));
        if (removed) {
            System.out.println("Rental Agreement with contracId removed: " + id);
            saveToFile("FurtherAsm1/src/File/rental_agreements.txt");
        } else {
            System.out.println("No rental agreement found with contractId: " + id);
        }
    }

    /**
     * Retrieves a RentalAgreement by its ID.
     *
     * @param id The ID of the RentalAgreement to retrieve.
     * @return The RentalAgreement object with the given ID, or null if not found.
     */
    @Override
    public RentalAgreement getOne(String id) {
        for (RentalAgreement agreement : agreements) {
            if (agreement.getContractId().equals(id)) {
                return agreement;
            }
        }
        return null;
    }


    /**
     * Retrieves all RentalAgreement objects.
     *
     * @return A list of all RentalAgreement objects.
     */
    @Override
    public List<RentalAgreement> getAll() {
        return new ArrayList<>(agreements);
    }

    /**
     * Retrieves the IDs of all RentalAgreement objects.
     *
     * @return A list of IDs for all RentalAgreement objects.
     */
    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            ids.add(agreement.getContractId());
        }
        return ids;
    }


    /**
     * Retrieves all RentalAgreement objects associated with a specific tenant (customer) ID.
     *
     * @param customerID The ID of the tenant to search for.
     * @return A list of RentalAgreement objects associated with the given tenant ID.
     */
    @Override
    public List<RentalAgreement> getAllByCustomerID(String customerID) {
        List<RentalAgreement> customerAgreements = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getMainTenant().getId().equals(customerID) ||
                    agreement.getSubTenants().stream().anyMatch(tenant -> tenant.getId().equals(customerID))) {
                customerAgreements.add(agreement);
            }
        }
        return customerAgreements;
    }

    /**
     * Saves the list of RentalAgreement objects to a file.
     *
     * @param fileName The file name to save to.
     */
    @Override
    public void saveToFile(String fileName) {
        try {
            rentalAgreementDAO.writeToFile(agreements, FILE_PATH);
            System.out.println("Rental Agreement saved to " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving Rental Agreement to " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Loads RentalAgreement objects from a file.
     *
     * @param fileName The file name to load from.
     */
    public void loadFromFile(String fileName) {
        try {
            agreements = rentalAgreementDAO.readFromFile();
            if (agreements.isEmpty()) {
                System.out.println("No rental agreement data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }
    /**
     * Validates the format of a given contract ID.
     * The contract ID must start with "RA" followed by one or more digits.
     *
     * @param contractId The contract ID to validate.
     * @return {@code true} if the contract ID matches the required format, {@code false} otherwise.
     */

    public boolean validateContractId(String contractId) {
        // Regular expression to check if the contractId starts with "RA"
        return contractId.matches("^RA\\d+$");

    }

    /**
     * Prompts the user to input data for a new RentalAgreement object.
     *
     * @return The created RentalAgreement object.
     */
    public RentalAgreement inputRentalAgreementData() {
        tenantManager.loadFromFile("FurtherAsm1/src/File/rental_agreements.txt");
        hostManager.loadFromFile("FurtherAsm1/src/File/hosts.txt");
        RentalAgreement rentalAgreement = new RentalAgreement();
        Scanner scanner = new Scanner(System.in);
        String contractId;

        // Input contractId for the agreement
        while (true) {
            System.out.print("Enter contractId (must start with 'RA' followed by integer numbers): ");
            contractId = scanner.nextLine();

            if (!validateContractId(contractId)) {
                System.out.println("Error: contractId must start with 'RA' followed by integer numbers. Please re-enter.");
                continue;
            }
            String finalContractId = contractId;
            boolean exists = agreements.stream().anyMatch(r -> r.getContractId().equals(finalContractId));
            if (exists) {
                System.out.println("Error: contractId already exists. Please re-enter.");
            } else {
                break;
            }
        }

        String tenantId;
        Tenant tenant = null;

        do {
            // Input tenantId for the main tenant
            System.out.print("Enter main tenantId(must start with 'T' followed by integer numbers): ");
            tenantId = scanner.nextLine();

            tenant = tenantManager.getOne(tenantId);
            if (tenant != null) {
                System.out.println(tenant);
            } else {
                System.out.println("No tenant found with id: " + tenantId);
            }
        } while (tenant == null);

        List<Tenant> subTenants = new ArrayList<>();
        int subTenantCount;

        // Loop to validate the format for the number of sub-tenants
        while (true) {
            try {
                System.out.print("Enter number of sub-tenants: ");
                subTenantCount = Integer.parseInt(scanner.nextLine());
                if (subTenantCount < 0) {
                    System.out.println("Number of sub-tenants cannot be negative. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        // Input the list of sub-tenants
        for (int i = 0; i < subTenantCount; i++) {
            while (true) { // Loop if the tenant is not found
                System.out.print("Enter sub-tenantId: ");
                String subTenantId = scanner.nextLine();
                Tenant temp = tenantManager.getOne(subTenantId);
                if (temp != null) {
                    subTenants.add(temp);
                    System.out.println(temp);
                    break; // Exit the loop if the tenant is found
                } else {
                    System.out.println("Don't find sub-tenant with id: " + subTenantId + ". Please try again.");
                }
            }
        }

        // Display the entered list of sub-tenants
        System.out.println("Sub-tenants list:");
        for (Tenant t : subTenants) {
            System.out.println(t);
        }

        List<Host> listHost = new ArrayList<>();
        int hostCount;

        // Loop to validate the format for the number of hosts
        while (true) {
            try {
                System.out.print("Enter number of hosts: ");
                hostCount = Integer.parseInt(scanner.nextLine());
                if (hostCount < 0) {
                    System.out.println("Number of hosts cannot be negative. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        // Input the list of hosts
        for (int i = 0; i < hostCount; i++) {
            while (true) { // Loop if the host is not found
                System.out.print("Enter hostId (must start with 'H' followed by integer numbers): ");
                String hostId = scanner.nextLine();
                Host host = hostManager.getOne(hostId);
                if (host != null) {
                    listHost.add(host);
                    System.out.println(host);
                    break; // Exit the loop if the host is found
                } else {
                    System.out.println("Don't find host with id: " + hostId + ". Please try again.");
                }
            }
        }

        // Display the entered list of hosts
        System.out.println("Hosts list:");
        for (Host host : listHost) {
            System.out.println(host);
        }


        // Input rental cycle - Ensure rentalCycle is effectively final
        final RentalAgreement.RentalCycleType[] rentalCycle = new RentalAgreement.RentalCycleType[1];
        while (rentalCycle[0] == null) {
            System.out.print("Enter rental cycle (DAILY, WEEKLY, FORTNIGHTLY, MONTHLY, YEARLY): ");
            try {
                rentalCycle[0] = RentalAgreement.RentalCycleType.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid rental cycle. Please enter again.");
            }
        }

        // Input rental duration
        int duration;
        while (true) {
            System.out.print("Enter duration (in number of units): ");
            duration = scanner.nextInt();
            if (duration <= 0) {
                System.out.println("Duration must be a positive number. Please enter again.");
            } else {
                break;
            }
        }
        scanner.nextLine(); // Consume newline

        // Input contract terms
        System.out.print("Enter contract terms: ");
        String contractTerms = scanner.nextLine();

        // Input rental fee
        double rentalFee;
        while (true) {
            System.out.print("Enter rental fee: ");
            rentalFee = scanner.nextDouble();
            if (rentalFee <= 0) {
                System.out.println("Rental fee must be a positive value. Please enter again.");
            } else {
                break;
            }
        }
        scanner.nextLine(); // Consume newline

        // Input contract status - Ensure status is effectively final
        final RentalAgreement.RentalAgreementStatus[] status = new RentalAgreement.RentalAgreementStatus[1];
        while (status[0] == null) {
            System.out.print("Enter contract status (NEW, ACTIVE, COMPLETED): ");
            try {
                status[0] = RentalAgreement.RentalAgreementStatus.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter again.");
            }
        }

        // Create an OwnerManager object and load data
        OwnerManager ownerManager = new OwnerManager();
        ownerManager.loadFromFile("src/File/rental_agreements.txt");

        Owner owner;
        while (true) { // Loop if the owner is not found
            // Input owner information
            System.out.print("Enter ownerId: ");
            String ownerId = scanner.nextLine();

            owner = ownerManager.getOne(ownerId);
            if (owner != null) {
                System.out.println(owner);
                break; // Exit the loop if the owner is found
            } else {
                System.out.println("Owner with id: " + ownerId + " not found. Please try again.");
            }
        }


        // Input property information
        System.out.println("Enter Property\n");
        System.out.print("1. Commercial_Property\n");
        System.out.print("2. Residential_Property\n");
        System.out.print("Choose: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                CommercialPropertyManager propertyManager = new CommercialPropertyManager();
                propertyManager.loadFromFile("FurtherAsm1/src/File/rental_agreements.txt"); // Load properties
                CommercialProperty property;

                while (true) { // Loop until a valid property ID is found
                    System.out.print("Enter property id (must start with 'CP' followed by integer numbers): ");
                    String propertyId = scanner.nextLine();

                    property = propertyManager.getOne(propertyId);
                    if (property != null) {
                        System.out.println("Property found: " + property);
                        break; // Exit loop if property is found
                    } else {
                        System.out.println("Property with id: " + propertyId + " not found. Please try again.");
                    }
                }

                // Get the current date and format it to dd-MM-yyyy
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date contractDate = new Date(); // Get the current date
                String formattedDate = dateFormat.format(contractDate); // Format the date

                // Create and return the RentalAgreement
                rentalAgreement = new RentalAgreement(
                        contractId,
                        contractDate,
                        owner,
                        tenant,
                        subTenants,
                        property,
                        listHost,
                        rentalCycle[0],
                        duration,
                        contractTerms,
                        rentalFee,
                        status[0]
                );
                break;


            case 2:
                ResidentialPropertyManager residentialPropertyManager = new ResidentialPropertyManager();
                residentialPropertyManager.loadFromFile("FurtherAsm1/src/File/rental_agreements.txt");
                ResidentialProperty residentialProperty;

                while (true) { // Loop until the property is found
                    System.out.print("Enter property id (must start with 'RP' followed by integer numbers): ");
                    String propertyID = scanner.nextLine();

                    residentialProperty = residentialPropertyManager.getOne(propertyID);
                    if (residentialProperty != null) {
                        System.out.println("Property found: " + residentialProperty);
                        break; // Exit the loop if the property is found
                    } else {
                        System.out.println("Property with id: " + propertyID + " not found. Please try again.");
                    }
                }

                Date contractDate1 = new Date(); // Get the current Date
                rentalAgreement = new RentalAgreement(
                        contractId, contractDate1, owner, tenant, subTenants, residentialProperty, listHost,
                        rentalCycle[0], duration, contractTerms, rentalFee, status[0]
                );
                break;
        }
            return rentalAgreement;
}
    /**
     * Retrieves RentalAgreement objects by owner name.
     *
     * @param ownerName The full name of the owner to search for.
     * @return A list of RentalAgreement objects associated with the given owner name.
     */
    // Get a list of rental agreements by owner name
    public List<RentalAgreement> getByOwnerName(String ownerName) {
        List<RentalAgreement> result = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getOwner().getFullName().equalsIgnoreCase(ownerName)) {
                result.add(agreement);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No rental agreements found for owner name: " + ownerName);
        }
        return result;
    }


    /**
     * Retrieves RentalAgreement objects by property address.
     *
     * @param propertyAddress The address of the property to search for.
     * @return A list of RentalAgreement objects associated with the given property address.
     */
    // Get a list of rental agreements by property address
    public List<RentalAgreement> getByPropertyAddress(String propertyAddress) {
        List<RentalAgreement> result = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getRentedProperty().getAddress().equalsIgnoreCase(propertyAddress)) {
                result.add(agreement);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No rental agreements found for property address: " + propertyAddress);
        }
        return result;
    }

    /**
     * Retrieves RentalAgreement objects by their status.
     *
     * @param status The status to search for (e.g., NEW, ACTIVE, COMPLETED).
     * @return A list of RentalAgreement objects with the given status.
     */
    // Get a list of rental agreements by status
    public List<RentalAgreement> getByStatus(RentalAgreement.RentalAgreementStatus status) {
        List<RentalAgreement> result = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            if (agreement.getStatus() == status) {
                result.add(agreement);
            }
        }
        if (result.isEmpty()) {
            System.out.println("No rental agreements found with status: " + status);
        }
        return result;
    }

    /**
     * Sorts RentalAgreement objects by their contract IDs in ascending order.
     */
    // Sort rental agreements by contractId in ascending order
    public void sortRentalAgreementsById() {
        agreements.sort((a1, a2) -> {
            try {
                // Extract the numeric part after the "RA" prefix and convert it to an integer
                int id1 = Integer.parseInt(a1.getContractId().substring(2)); // Remove "RA" prefix and parse the number
                int id2 = Integer.parseInt(a2.getContractId().substring(2)); // Same for the second agreement

                return Integer.compare(id1, id2); // Compare the numeric values
            } catch (NumberFormatException e) {
                // If the ID is not a number, compare the entire contract ID as a string
                return a1.getContractId().compareTo(a2.getContractId());
            }
        });
        System.out.println("Rental Agreements list has been sorted by contractId (ascending).");
    }

    /**
     * Saves the list of RentalAgreement objects to a backup file.
     *
     * @param backupFileName The name of the backup file.
     */
    // Save the list of rental agreements to a backup file
    public void saveRentalAgreementsBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Invalid backup file name.");
            return;
        }

        try {
            rentalAgreementDAO.writeToFile(agreements, backupFileName);
            System.out.println("Rental Agreements list has been saved to backup file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving to backup file: " + backupFileName);
            e.printStackTrace();
        }
    }
}
