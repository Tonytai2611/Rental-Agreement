/**
 * @author Truong Phung Tan Tai - s3974929
 * The Main class serves as the entry point for managing rental properties, tenants, owners,
 * hosts, payments, and rental agreements. It includes a user-friendly menu system for navigating
 * between various management functionalities. Data is loaded and saved to files for persistence.
 */

package Main;


import Classes.*;
import Interface.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static final RentalAgreementManager rentalAgreementManager = new RentalAgreementManager();
    private static final OwnerManager ownerManager = new OwnerManager();
    private static final HostManager hostManager = new HostManager();
    private static final TenantManager tenantManager = new TenantManager();
    private static final CommercialPropertyManager commercialPropertyManager = new CommercialPropertyManager();
    private static final ResidentialPropertyManager residentialPropertyManager = new ResidentialPropertyManager();
    private static final PaymentManager paymentManager = new PaymentManager();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load data from file
        paymentManager.loadFromFile("FurtherAsm1/src/File/payments.txt");
        commercialPropertyManager.loadFromFile("FurtherAsm1/src/File/commercial_properties.txt");
        hostManager.loadFromFile("FurtherAsm1/src/File/hosts.txt");
        residentialPropertyManager.loadFromFile("FurtherAsm1/src/File/residential_properties.txt");
        ownerManager.loadFromFile("FurtherAsm1/src/File/owners.txt");
        tenantManager.loadFromFile("FurtherAsm1/src/File/tenants.txt");
        rentalAgreementManager.loadFromFile("FurtherAsm1/src/File/rental_agreements.txt");

        int choice = 0;
        do {
            try {
                // Display menu
                displayMainMenu();
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> rentalAgreementMenu();
                    case 2 -> tenantMenu();
                    case 3 -> ownerMenu();
                    case 4 -> hostMenu();
                    case 5 -> paymentMenu();
                    case 6 -> commercialPropertyMenu();
                    case 7 -> residentialPropertyMenu();
                    case 8 -> System.out.println("Thank you for using the program!");
                    default -> System.out.println("Invalid choice. Please choose again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please choose again.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 8);

        scanner.close();
    }

    /**
     * Provides a submenu for managing commercial properties, including options
     * to add, remove, update, display, and export property details.
     */
    private static void commercialPropertyMenu() {
        int choice;
        do {
            displaySubMenu("Commercial Property");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewCommercialProperty();
                case 2 -> removeCommercialProperty();
                case 3 -> updateCommercialProperty();
                case 4 -> displayCommercialProperties();
                case 5 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid choice. Please choose again.");
            }
        } while (choice != 5);
    }

    /**
     * Provides a submenu for managing residential properties, including options
     * to add, remove, update, display, and export property details.
     */
    private static void residentialPropertyMenu() {
        int choice;
        do {
            displaySubMenu("Residential Property");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewResidentialProperty();
                case 2 -> removeResidentialProperty();
                case 3 -> updateResidentialProperty();
                case 4 -> displayResidentialProperties();
                case 5 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void paymentMenu() {
        int choice;
        do {
            displaySubMenu("Payment");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewPayment();
                case 2 -> removePayment();
                case 3 -> updatePayment();
                case 4 -> displayPayments();
                case 5 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void hostMenu() {
        int choice;
        do {
            displaySubMenu("Host");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewHost();
                case 2 -> removeHost();
                case 3 -> updateHost();
                case 4 -> displayHosts();
                case 5 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void ownerMenu() {
        int choice;
        do {
            displaySubMenu("Owner");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewOwner();
                case 2 -> removeOwner();
                case 3 -> updateOwner();
                case 4 -> displayOwners();
                case 5 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void tenantMenu() {
        int choice;
        do {
            displaySubMenu("Tenant");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addNewTenant();
                case 2 -> removeTenant();
                case 3 -> updateTenant();
                case 4 -> displayTenants();
                case 5 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void rentalAgreementMenu() {
        int choice;
        do {
            displaySubMenuRA("Rental Agreement");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addRentalAgreement();
                case 2 -> removeRentalAgreement();
                case 3 -> updateRentalAgreement();
                case 4 -> displayRentalAgreements();
                case 5 -> getByOwnerName();
                case 6 -> getByPropertyAddress();
                case 7 -> getByStatus();
                case 8 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    /**
     * Displays a submenu for managing rental agreements.
     *
     * @param entity The name of the entity (e.g., "Rental Agreement").
     */
    private static void displaySubMenuRA(String entity) {
        System.out.printf("\n-------- MANAGEMENT MENU %s --------\n", entity.toUpperCase());
        System.out.println("    1. Add " + entity);
        System.out.println("    2. Remove " + entity);
        System.out.println("    3. Update " + entity);
        System.out.println("    4. Display list of " + entity);
        System.out.println("    5. Get by Owner " + entity);
        System.out.println("    6. Get by Property Address " + entity);
        System.out.println("    7. Get by Status " + entity);
        System.out.println("    8. Return to the main menu");
    }

    /**
     * Displays a submenu for managing entities like tenants, owners, hosts, and properties.
     *
     * @param entity The name of the entity to manage (e.g., "Tenant", "Owner").
     */
    private static void displaySubMenu(String entity) {
        System.out.printf("\n-------- MANAGEMENT MENU %s --------\n", entity.toUpperCase());
        System.out.println("    1. Add " + entity);
        System.out.println("    2. Remove " + entity);
        System.out.println("    3. Update " + entity);
        System.out.println("    4. Display list of " + entity);
        System.out.println("    5. Return to the main menu");
    }

    /**
     * Displays the main menu and handles navigation to various management submenus.
     */
    private static void displayMainMenu() {
        System.out.println("\n-------- MAIN MENU --------");
        System.out.println("1. Manage Rental Agreement");
        System.out.println("2. Manage Tenant");
        System.out.println("3. Manage Owner");
        System.out.println("4. Manage Host");
        System.out.println("5. Manage Payment");
        System.out.println("6. Manage Commercial Property");
        System.out.println("7. Manage Residential Property");
        System.out.println("8. Exit");
    }

    /**
     * Adds a new rental agreement by collecting input from the user.
     */
    public static void addRentalAgreement() {
        RentalAgreement newAgreement = rentalAgreementManager.inputRentalAgreementData(); // Input new rental agreement information
        if (newAgreement != null) {
            // Display the new rental agreement information
            System.out.println("New rental agreement information:");
            System.out.println(newAgreement);

            // Ask user if they want to save
            System.out.println("Do you want to save this rental agreement? (y/n)");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                rentalAgreementManager.add(newAgreement); // Add the rental agreement to the list
                rentalAgreementManager.saveToFile("FurtherAsm1/src/File/rental_agreements.txt"); // Save the list to file
                System.out.println("Rental agreement added successfully!");

                // Update the rental agreements list for the tenant
                Tenant tenantUpdate = tenantManager.getOne(newAgreement.getMainTenant().getId());
                if (tenantUpdate != null) {
                    // Retrieve the current list of rental agreements
                    List<RentalAgreement> listRentalAgreements = tenantUpdate.getRentalAgreements();
                    if (listRentalAgreements == null) {
                        listRentalAgreements = new ArrayList<>(); // Initialize if the list is null
                    }
                    // Add the new agreement to the end of the list
                    listRentalAgreements.add(newAgreement);
                    // Update the list in the tenant object
                    tenantUpdate.setRentalAgreements(listRentalAgreements);
                    // Save the updated tenant
                    tenantManager.update(tenantUpdate);
                    tenantManager.saveToFile("src/File/rental_agreements.txt"); // Save to file
                } else {
                    // If the tenant is not found, display an error message
                    System.out.println("Tenant not found for update.");
                }
                displayRentalAgreements();
            } else if ("n".equals(confirmation)) {
                System.out.println("Adding rental agreement action was canceled.");
            } else {
                System.out.println("Invalid choice. Please enter 'y' to confirm or 'n' to cancel.");
            }
        } else {
            System.out.println("Cannot add rental agreement.");
        }
    }

    /**
     * Removes a rental agreement based on its contract ID.
     */
    private static void removeRentalAgreement() {
        System.out.print("\nEnter the contractId to delete: ");
        String contractId = scanner.nextLine();

        // Confirm if the user wants to delete this rental agreement
        System.out.print("Are you sure you want to delete this rental agreement? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            rentalAgreementManager.remove(contractId); // Remove the rental agreement
            System.out.println("Rental agreement with contractId " + contractId + " has been successfully deleted.");
        } else if ("n".equals(confirmation)) {
            System.out.println("Deleting rental agreement action was canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to confirm deletion or 'n' to cancel.");
        }
    }

    /**
     * Updates an existing rental agreement by modifying its attributes.
     */
    private static void updateRentalAgreement() {
        System.out.print("\nEnter the contractId to update: ");
        String contractId = scanner.nextLine();

        // Retrieve the rental agreement object to update
        RentalAgreement existingAgreement = rentalAgreementManager.getOne(contractId);
        if (existingAgreement == null) {
            System.out.println("No rental agreement found with contractId: " + contractId);
            return;
        }
        System.out.println("Updating rental agreement with contractId: " + contractId);
        System.out.println("Press Enter to keep the current value.");

        System.out.println("Current Owner: " + existingAgreement.getOwner());
        Owner owner = null;
        String newOwner;

        do {
            System.out.print("Enter new Owner ID (or press Enter to keep current): ");
            newOwner = scanner.nextLine();
            if (!newOwner.isEmpty()) {
                // Check for Owner by ID
                owner = ownerManager.getOne(newOwner);
                if (owner == null) {
                    System.out.println("No Owner found with ID: " + newOwner + ". Please try again.");
                } else {
                    System.out.println("Found Owner information: " + owner);
                }
            } else {
                owner = existingAgreement.getOwner(); // Keep the current Owner
            }
        } while (owner == null);

        existingAgreement.setOwner(owner);
        System.out.println("Owner updated successfully!");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        System.out.println("Current Tenant: " + existingAgreement.getMainTenant());
        Tenant tenant = null;
        String newTenant;
        do {
            System.out.print("Enter new Tenant ID (or press Enter to keep current): ");
            newTenant = scanner.nextLine();
            if (!newTenant.isEmpty()) {
                // Check for Tenant by ID
                tenant = tenantManager.getOne(newTenant);
                if (tenant == null) {
                    System.out.println("No Tenant found with ID: " + newTenant + ". Please try again.");
                } else {
                    System.out.println("Found Tenant information: " + tenant);
                }
            } else {
                tenant = existingAgreement.getMainTenant(); // Keep the current Tenant
            }
        } while (tenant == null);

        existingAgreement.setMainTenant(tenant);
        System.out.println("Tenant updated successfully!");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Update SubTenant
        System.out.println("Current Sub Tenants: " + existingAgreement.getSubTenants());
        List<Tenant> subTenants = existingAgreement.getSubTenants();

        System.out.print("Enter the number of new SubTenants to update (0 to keep current): ");
        int newSubTenantCount = 0;
        try {
            newSubTenantCount = Integer.parseInt(scanner.nextLine());
            if (newSubTenantCount < 0) {
                System.out.println("Count cannot be less than 0. Please try again.");
                newSubTenantCount = 0; // If negative, set to 0 to not update
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid count. Please enter again.");
        }

        if (newSubTenantCount == 0) {
            System.out.println("Keeping the existing SubTenants.");
        } else {
            subTenants.clear(); // Clear existing SubTenants

            List<Tenant> updatedSubTenants = new ArrayList<>();
            String newSubTenantId;
            for (int i = 1; i <= newSubTenantCount; i++) {
                Tenant subTenant = null;
                do {
                    System.out.print("Enter ID of SubTenant #" + i + ": ");
                    newSubTenantId = scanner.nextLine();
                    subTenant = tenantManager.getOne(newSubTenantId); // Check SubTenant by ID
                    if (subTenant == null) {
                        System.out.println("No SubTenant found with ID: " + newSubTenantId + ". Please try again.");
                    }
                } while (subTenant == null);

                updatedSubTenants.add(subTenant); // Add valid SubTenant to the list
                System.out.println("Found SubTenant #" + i + " information: " + subTenant);
            }

            existingAgreement.setSubTenants(updatedSubTenants); // Update new SubTenant list
            System.out.println("SubTenants updated successfully!");
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Check the number of new Hosts
        System.out.print("Enter the number of new Hosts to update (0 to keep current): ");
        int newHostCount = Integer.parseInt(scanner.nextLine());

        if (newHostCount == 0) {
            System.out.println("Keeping the existing list of Hosts.");
        } else {
            List<Host> updatedHosts = new ArrayList<>();
            String newHostId;

            for (int i = 1; i <= newHostCount; i++) {
                Host host = null;
                do {
                    System.out.print("Enter the ID of Host #" + i + ": ");
                    newHostId = scanner.nextLine();
                    host = hostManager.getOne(newHostId); // Check Host by ID

                    if (host == null) {
                        System.out.println("No Host found with ID: " + newHostId + ". Please try again.");
                    }
                } while (host == null);

                updatedHosts.add(host); // Add valid Host to the list
                System.out.println("Host #" + i + " information found: " + host);
            }

            existingAgreement.setHosts(updatedHosts); // Update the list of Hosts
            System.out.println("Hosts updated successfully!");
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Update Rental Cycle
        System.out.println("Current Rental Cycle: " + existingAgreement.getRentalCycle() + " -> ");
        String newRentalCycle = scanner.nextLine();
        if (!newRentalCycle.isEmpty()) {
            try {
                existingAgreement.setRentalCycle(RentalAgreement.RentalCycleType.valueOf(newRentalCycle.toUpperCase()));
                System.out.println("Rental Cycle updated successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid rental cycle. Update failed.");
                return;
            }
        } else {
            System.out.println("Keeping the existing Rental Cycle.");
        }
        // Update Rental Agreement Duration
        System.out.print("Current contract duration: " + existingAgreement.getDuration() + " months -> ");
        String newDuration = scanner.nextLine();

        if (!newDuration.isEmpty()) {
            try {
                int duration = Integer.parseInt(newDuration);
                if (duration <= 0) {
                    System.out.println("Invalid duration. Must be a positive integer.");
                    return;
                }
                existingAgreement.setDuration(duration); // Update duration
                System.out.println("Contract duration updated successfully: " + duration + " months.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid duration. Update failed. Please enter a positive integer.");
            }
        } else {
            System.out.println("No changes to the contract duration.");
        }

        // Update Contract Terms
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.print("Current contract terms: " + existingAgreement.getContractTerms() + " -> ");
        String newContractTerms = scanner.nextLine();
        if (!newContractTerms.isEmpty()) {
            existingAgreement.setContractTerms(newContractTerms); // Update Contract Terms
        }

        // Update Rental Agreement Status
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.print("Current status: " + existingAgreement.getStatus() + " -> ");
        String newStatus = scanner.nextLine();
        if (!newStatus.isEmpty()) {
            try {
                existingAgreement.setStatus(RentalAgreement.RentalAgreementStatus.valueOf(newStatus.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Update failed.");
                return;
            }
        }

        // Update Rental Fee
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.print("Current rental fee: " + existingAgreement.getRentalFee() + " -> ");
        String newRentalFee = scanner.nextLine();
        if (!newRentalFee.isEmpty()) {
            try {
                existingAgreement.setRentalFee(Double.parseDouble(newRentalFee));
            } catch (NumberFormatException e) {
                System.out.println("Invalid rental fee. Update failed.");
                return;
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
        // Ask user whether to save the updated information
        String saveChoice;
        do {
            System.out.print("Do you want to save the updated information? (y/n): ");
            saveChoice = scanner.nextLine().trim().toLowerCase();
            if (!saveChoice.equals("y") && !saveChoice.equals("n")) {
                System.out.println("Invalid choice. Please enter 'y' or 'n'.");
            }
        } while (!saveChoice.equals("y") && !saveChoice.equals("n"));

        if (saveChoice.equals("y")) {
            rentalAgreementManager.update(existingAgreement); // Update in the list
            rentalAgreementManager.saveToFile("FurtherAsm1/src/File/rental_agreements.txt"); // Save to file
            rentalAgreementManager.loadFromFile("FurtherAsm1/src/File/rental_agreements.txt");
            System.out.println("Rental agreement updated and saved successfully!");
        } else {
            System.out.println("Update canceled. No changes were saved.");
        }
    }

    /**
     * Displays the list of tenants and provides actions like sorting or exporting.
     */
    // Manage Tenants
    private static void displayTenants() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n ================================================================ Current List of Tenants ==============================================================");
            System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            System.out.println("|      Id       |      Full Name     |     Date      |      Contract Infor     |         RentalAgreements          |           PaymentRecords          | ");
            System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            for (Tenant tenant : tenantManager.getAll()) {
                System.out.println(tenant.toString());
                System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            }

            System.out.println("\nChoose an action:");
            System.out.println("1. Sort tenants by ID and display again");
            System.out.println("2. Export tenant list to report");
            System.out.println("3. Return to main menu");


            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Read leftover newline character

            switch (choice) {
                case 1:
                    tenantManager.sortTenantsById();
                    System.out.println("\nTenants sort by ID: ");
                    break;
                case 2:
                    System.out.print("Enter the report name (include path (ex: FurtherAsm1/src/File/) if necessary): ");
                    String backupFileName = scanner.nextLine();
                    tenantManager.saveBackupToFile(backupFileName);
                    break;
                case 3:
                    System.out.println("Returning to main menu.");
                    return; // Exit method
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Adds a new tenant by collecting input from the user.
     */
    private static void addNewTenant() {
        Tenant newTenant = tenantManager.inputTenantData(); // Input new tenant information
        if (newTenant != null) {
            // Display new tenant information
            System.out.println("New tenant information:");
            System.out.println(newTenant);

            // Ask the user if they want to save
            System.out.print("Do you want to save this tenant? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                if (tenantManager.add(newTenant)) {
                    tenantManager.saveToFile("FurtherAsm1/src/File/tenants.txt"); // Save tenant to file
                    System.out.println("New tenant added successfully!");
                } else {
                    System.out.println("Unable to add tenant.");
                }
            } else if ("n".equals(confirmation)) {
                System.out.println("Adding tenant action was canceled.");
            } else {
                System.out.println("Invalid choice. Please enter 'y' to confirm save or 'n' to cancel.");
            }
        } else {
            System.out.println("Unable to add tenant.");
        }
    }

    /**
     * Removes a tenant based on their ID.
     */
    private static void removeTenant() {
        System.out.print("\nEnter the tenantId to delete: ");
        String tenantId = scanner.nextLine();

        // Confirm deletion of tenant
        System.out.print("Are you sure you want to delete the tenant with ID: " + tenantId + "? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            tenantManager.remove(tenantId); // Delete tenant
            System.out.println("Tenant deleted successfully!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Delete action was canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to confirm deletion or 'n' to cancel.");
        }
    }


    /**
     * Updates an existing tenant by modifying their attributes.
     */
    private static void updateTenant() {
        System.out.print("\nEnter the tenantId to update: ");
        String tenantId = scanner.nextLine();

        // Retrieve the Tenant object to update
        Tenant existingTenant = tenantManager.getOne(tenantId);
        if (existingTenant == null) {
            System.out.println("No tenant found with tenantId: " + tenantId);
            return;
        }

        System.out.println("Updating tenant with ID: " + tenantId);
        System.out.println("Press Enter to keep the current value.");

        // Input new values, leave empty to retain current values
        System.out.print("Current full name: " + existingTenant.getFullName() + " -> ");
        String newFullName = scanner.nextLine();
        if (!newFullName.isEmpty()) {
            existingTenant.setFullName(newFullName);
        }

        System.out.print("Current date of birth (dd-MM-yyyy): " +
                new SimpleDateFormat("dd-MM-yyyy").format(existingTenant.getDateOfBirth()) + " -> ");
        String newDateOfBirthStr;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // Enable strict date format validation
        java.util.Date newDateOfBirth = null;

        do {
            newDateOfBirthStr = scanner.nextLine();
            if (newDateOfBirthStr.isEmpty()) {
                break; // User enters nothing, retain the old value
            }
            try {
                newDateOfBirth = dateFormat.parse(newDateOfBirthStr);
            } catch (ParseException e) {
                System.out.print("Invalid date of birth. Please re-enter (dd-MM-yyyy): ");
            }
        } while (newDateOfBirth == null);

        if (newDateOfBirth != null) {
            existingTenant.setDateOfBirth(new java.sql.Date(newDateOfBirth.getTime()));
        }


        System.out.print("Current contact info: " + existingTenant.getContactInfo() + " -> ");
        String newContactInfo = scanner.nextLine();
        if (!newContactInfo.isEmpty()) {
            existingTenant.setContactInfo(newContactInfo);
        }

        // Ask the user whether to save the updated information
        System.out.print("Do you want to save the updates? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            tenantManager.update(existingTenant);
            tenantManager.saveToFile("FurtherAsm1/src/File/tenants.txt");
            System.out.println("Tenant updated successfully!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Update action was canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to save changes or 'n' to cancel.");
        }
    }

    /**
     * Removes a payment based on its ID.
     */
    private static void removePayment() {
        System.out.print("\nEnter the paymentId to delete: ");
        String paymentId = scanner.nextLine();

        // Confirm deletion of payment
        System.out.print("Are you sure you want to delete the payment with ID: " + paymentId + "? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            paymentManager.remove(paymentId); // Delete payment
            System.out.println("Payment deleted successfully!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Delete action was canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to confirm deletion or 'n' to cancel.");
        }
    }

    /**
     * Updates an existing payment by modifying its attributes.
     */
    private static void updatePayment() {
        System.out.print("\nEnter the paymentId to update: ");
        String paymentId = scanner.nextLine();

        // Retrieve the Payment object to update
        Payment existingPayment = paymentManager.getOne(paymentId);
        if (existingPayment == null) {
            System.out.println("No payment found with paymentId: " + paymentId);
            return;
        }
        System.out.println("Updating payment with ID: " + paymentId);
        System.out.println("Press Enter to keep the current value.");

        // Input new values, leave empty to retain current values
        System.out.print("Current payment method: " + existingPayment.getPaymentMethod() + " -> ");
        String newPaymentMethod = scanner.nextLine();
        if (newPaymentMethod.isEmpty()) {
            newPaymentMethod = existingPayment.getPaymentMethod();
        }

        System.out.print("Current amount: " + existingPayment.getAmount() + " -> ");
        String newAmountStr = scanner.nextLine();
        double newAmount = newAmountStr.isEmpty() ? existingPayment.getAmount() : Double.parseDouble(newAmountStr);

        System.out.print("Current tenant ID: " + existingPayment.getTenant().getId() + " -> ");
        String newTenantId = scanner.nextLine();
        if (newTenantId.isEmpty()) {
            newTenantId = existingPayment.getTenant().getId();
        }

        // Confirm save changes
        System.out.print("Do you want to save the changes? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            // Update the Payment object
            Payment updatedPayment = new Payment(
                    newPaymentMethod,
                    existingPayment.getDate(),
                    newAmount,
                    new Tenant(newTenantId),
                    existingPayment.getPaymentId() // Keep the original paymentId
            );

            // Perform the update
            paymentManager.update(updatedPayment);
            System.out.println("Payment updated successfully!");
            paymentManager.loadFromFile("FurtherAsm1/src/File/payments.txt");
        } else if ("n".equals(confirmation)) {
            System.out.println("Update action was canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to save changes or 'n' to cancel.");
        }
    }

    /**
     * Adds a new payment by collecting input from the user.
     */
    private static void addNewPayment() {
        Payment newPayment = paymentManager.inputPaymentData();
        if (newPayment != null) {
            // Confirm addition of the new payment
            System.out.print("Are you sure you want to add this new payment? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                paymentManager.add(newPayment);
                paymentManager.saveToFile("FurtherAsm1/src/File/payments.txt");
                System.out.println("New payment added successfully!");
            } else if ("n".equals(confirmation)) {
                System.out.println("Adding payment action was canceled.");
            } else {
                System.out.println("Invalid choice. Please enter 'y' to add payment or 'n' to cancel.");
            }
        } else {
            System.out.println("Unable to add payment due to duplicate ID.");
        }
    }

    /**
     * Displays the list of payments and provides actions like sorting or exporting.
     */
    private static void displayPayments() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n================================   Current Payment List     ===================================");
            System.out.println("|______________________________________________________________________________________________|");
            System.out.println("|  Id           |  Full name    |   Amount      |     Date      |     Payment Method           |");
            System.out.println("|______________________________________________________________________________________________|");
            for (Payment payment : paymentManager.getAll()) {
                System.out.println(payment.toString());
                System.out.println("|______________________________________________________________________________________________|");
            }

            // Action menu
            System.out.println("\nChoose an action:");
            System.out.println("1. Sort Payments by ID and display again");
            System.out.println("2. Export the list of Payments to report");
            System.out.println("3. Return to main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Read leftover newline character

            switch (choice) {
                case 1:
                    // Call method to sort Payments by ID
                    paymentManager.sortPaymentsById();
                    System.out.println("\nPayments sorted by ID.");
                    break;
                case 2:
                    // Request backup file name and save Payments list to the file
                    System.out.print("Enter the report name (include path (ex: FurtherAsm1/src/File/) if necessary): ");
                    String backupFileName = scanner.nextLine();
                    paymentManager.saveBackupToFile(backupFileName);
                    System.out.println("Payments list saved to file: " + backupFileName);
                    break;
                case 3:
                    System.out.println("Returning to main menu.");
                    return; // Exit method
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Displays the list of hosts and provides actions like sorting or exporting.
     */
    private static void displayHosts() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n ================================================================ Current List of Hosts ================================================================");
            System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            System.out.println("|      Id       |      Full Name     |     Date      |      Contract Infor     |        ManagedProperties          |         CooperatingOwners         | ");
            System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            for (Host host : hostManager.getAll()) {
                System.out.println(host.toString());
                System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            }


            // Action menu
            System.out.println("\nChoose an action:");
            System.out.println("1. Sort Hosts by ID and display again");
            System.out.println("2. Export the list of Hosts to report");
            System.out.println("3. Return to main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Read leftover newline character

            switch (choice) {
                case 1:
                    // Sort the list of Hosts by ID
                    hostManager.sortHostsById();
                    System.out.println("\nHosts sorted by ID:");
                    break;
                case 2:
                    // Request backup file name and save the Hosts list
                    System.out.print("Enter the report name (include path (ex: FurtherAsm1/src/File/) if necessary): ");
                    String backupFileName = scanner.nextLine();
                    hostManager.saveBackupToFile(backupFileName);
                    System.out.println("Hosts list saved to file: " + backupFileName);
                    break;
                case 3:
                    System.out.println("Returning to main menu.");
                    return; // Exit the method
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Adds a new host by collecting input from the user.
     */
    private static void addNewHost() {
        Host newHost = hostManager.inputHostData(); // This method needs to be implemented in the HostManager
        if (newHost != null) {
            hostManager.add(newHost);
            hostManager.saveToFile("FurtherAsm1/src/File/hosts.txt"); // Path to the Host file
            System.out.println("New Host added successfully!");
        } else {
            System.out.println("Cannot add Host due to duplicate ID.");
        }
    }

    /**
     * Removes a host based on their ID.
     */
    private static void removeHost() {
        System.out.print("\nEnter the hostId to delete: ");
        String hostId = scanner.nextLine();

        // Confirm deletion
        System.out.print("Are you sure you want to delete the Host with ID: " + hostId + "? (y/n): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            hostManager.remove(hostId);
            System.out.println("Host deleted successfully!");
        } else {
            System.out.println("Host deletion canceled.");
        }
    }

    /**
     * Updates an existing host by modifying their attributes.
     */
    private static void updateHost() {
        System.out.print("\nEnter the hostId to update: ");
        String hostId = scanner.nextLine();

        // Retrieve the Host object to update
        Host existingHost = hostManager.getOne(hostId);
        if (existingHost == null) {
            System.out.println("No Host found with ID: " + hostId);
            return;
        }

        System.out.println("Updating Host with ID: " + hostId);
        System.out.println("Press Enter to keep the current value.");

        // Input new values for Host
        System.out.print("Current name: " + existingHost.getFullName() + " -> ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            existingHost.setFullName(newName);
        }

        // Enter new date of birth
        System.out.print("Current date of birth (dd-MM-yyyy): " +
                new SimpleDateFormat("dd-MM-yyyy").format(existingHost.getDateOfBirth()) + " -> ");
        String newDateOfBirthStr;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // Enable strict date format validation
        java.util.Date newDateOfBirth = null;

        do {
            newDateOfBirthStr = scanner.nextLine();
            if (newDateOfBirthStr.isEmpty()) {
                break; // User enters nothing, retain the old value
            }
            try {
                newDateOfBirth = dateFormat.parse(newDateOfBirthStr); // Parse the date of birth
            } catch (ParseException e) {
                System.out.print("Invalid date of birth. Please re-enter (dd-MM-yyyy): ");
            }
        } while (newDateOfBirth == null);

        if (newDateOfBirth != null) {
            existingHost.setDateOfBirth(newDateOfBirth);
        }

        System.out.print("Current contact info: " + existingHost.getContactInfo() + " -> ");
        String newContactInfo = scanner.nextLine();
        if (!newContactInfo.isEmpty()) {
            existingHost.setContactInfo(newContactInfo);
        }

        // Confirm update
        System.out.print("Are you sure you want to save the updates for this Host? (y/n): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            // Perform update
            hostManager.update(existingHost);
            hostManager.saveToFile("FurtherAsm1/src/File/hosts.txt");
            System.out.println("Host updated successfully!");
        } else {
            System.out.println("Host update canceled.");
        }
    }

    /**
     * Displays the list of owners and provides actions like sorting or exporting.
     */
    private static void displayOwners() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n ================================================================ Current List of Owners ==============================================================");
            System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            System.out.println("|      Id       |      Full Name     |     Date      |      Contract Infor     |         RentalAgreements          |           PaymentRecords          | ");
            System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            for (Owner owner : ownerManager.getAll()) {
                System.out.println(owner.toString());
                System.out.println("|______________________________________________________________________________________________________________________________________________________|");
            }

            // Action menu
            System.out.println("\nChoose an action:");
            System.out.println("1. Sort Owners by ID and display again");
            System.out.println("2. Export the list of Owners to report");
            System.out.println("3. Return to main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Read leftover newline character

            switch (choice) {
                case 1:
                    // Sort Owners by ID
                    ownerManager.sortOwnersById();
                    System.out.println("\nOwners sorted by ID:");
                    break;
                case 2:
                    // Request backup file name and save the Owners list
                    System.out.print("Enter the report name (include path (ex: FurtherAsm1/src/File/) if necessary): ");
                    String backupFileName = scanner.nextLine();
                    ownerManager.saveBackupToFile(backupFileName);
                    System.out.println("Owners list saved to file: " + backupFileName);
                    break;
                case 3:
                    System.out.println("Returning to main menu.");
                    return; // Exit the method
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Adds a new owner by collecting input from the user.
     */
    private static void addNewOwner() {
        Owner newOwner = ownerManager.inputOwnerData(); // This method needs to be implemented in OwnerManager
        if (newOwner != null) {
            ownerManager.add(newOwner);
            ownerManager.saveToFile("FurtherAsm1/src/File/owners.txt");
            System.out.println("Owner added successfully!");
        } else {
            System.out.println("Cannot add Owner due to duplicate ID.");
        }
    }

    /**
     * Removes an owner based on their ID.
     */
    private static void removeOwner() {
        System.out.print("\nEnter the ownerId to delete: ");
        String ownerId = scanner.nextLine();

        // Confirm deletion
        System.out.print("Are you sure you want to delete the owner with ID: " + ownerId + "? (y/n): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            ownerManager.remove(ownerId);
            ownerManager.saveToFile("FurtherAsm1/src/File/owners.txt");
            System.out.println("Owner removed successfully!");
        } else {
            System.out.println("Owner delete canceled.");
        }
    }

    /**
     * Updates an existing owner by modifying their attributes.
     */
    private static void updateOwner() {
        System.out.print("\nEnter the ownerId to update: ");
        String ownerId = scanner.nextLine();

        // Retrieve the Owner object to date
        Owner existingOwner = ownerManager.getOne(ownerId);
        if (existingOwner == null) {
            System.out.println("No owner found with ID: " + ownerId);
            return;
        }
        System.out.println("Updating Owner with ID: " + ownerId);
        System.out.println("Press Enter to keep the current value.");

        // Input new values for Owner
        System.out.print("Current name: " + existingOwner.getFullName() + " -> ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            existingOwner.setFullName(newName);
        }
        // Enter new date of birth
        System.out.print("Current date of birth (dd-MM-yyyy): " +
                new SimpleDateFormat("dd-MM-yyyy").format(existingOwner.getDateOfBirth()) + " -> ");
        String newDateOfBirthStr;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // Enable strict date format validation
        java.util.Date newDateOfBirth = null;

        do {
            newDateOfBirthStr = scanner.nextLine();
            if (newDateOfBirthStr.isEmpty()) {
                break; // User enters nothing, retain the old value
            }
            try {
                newDateOfBirth = dateFormat.parse(newDateOfBirthStr);
            } catch (ParseException e) {
                System.out.print("Invalid date of birth. Please re-enter (dd-MM-yyyy): ");
            }
        } while (newDateOfBirth == null);

        if (newDateOfBirth != null) {
            existingOwner.setDateOfBirth(newDateOfBirth);
        }


        System.out.print("Current contact info: " + existingOwner.getContactInfo() + " -> ");
        String newContactInfo = scanner.nextLine();
        if (!newContactInfo.isEmpty()) {
            existingOwner.setContactInfo(newContactInfo);
        }

        // Confirm update
        System.out.print("Are you sure you want to save the updates for this owner? (y/n): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("y")) {
            // Perform the update
            ownerManager.update(existingOwner);
            ownerManager.saveToFile("FurtherAsm1/src/File/owners.txt");
            System.out.println("Owner updated successfully!");
        } else {
            System.out.println("Owner update canceled.");
        }
    }

    /**
     * Adds a new commercial property by collecting input from the user.
     */
    private static void removeCommercialProperty() {
        System.out.print("\nEnter the propertyId to delete: ");
        String propertyId = scanner.nextLine();

        // Confirm deletion
        System.out.print("Are you sure you want to delete this commercial property? (y/n): ");
        String confirmation = scanner.nextLine();

        if ("y".equals(confirmation)) {
            commercialPropertyManager.remove(propertyId);
            System.out.println("Commercial property with propertyId " + propertyId + " deleted successfully.");
        } else if ("n".equals(confirmation)) {
            System.out.println("Commercial property deletion canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to confirm or 'n' to cancel.");
        }
    }

    /**
     * Updates an existing commercial property by modifying its attributes.
     */
    private static void updateCommercialProperty() {
        System.out.print("\nEnter the propertyId to update: ");
        String propertyId = scanner.nextLine();
        CommercialProperty existingProperty = commercialPropertyManager.getOne(propertyId);

        if (existingProperty == null) {
            System.out.println("No commercial property found with ID: " + propertyId);
            return;
        }
        System.out.println("Updating Commercial property with ID: " + propertyId);
        System.out.println("Press Enter to keep the current value.");

        // Input new values, leave empty to retain current values
        System.out.print("Current address: " + existingProperty.getAddress() + " -> ");
        String newAddress = scanner.nextLine();
        if (newAddress.isEmpty()) {
            newAddress = existingProperty.getAddress();
        }

        System.out.print("Current rental price: " + existingProperty.getPricing() + " -> ");
        String newPricingStr = scanner.nextLine();
        double newPricing = newPricingStr.isEmpty() ? existingProperty.getPricing() : Double.parseDouble(newPricingStr);

        System.out.print("Current status: " + existingProperty.getStatus() + " -> ");
        String newStatusStr = scanner.nextLine();
        Property.PropertyStatus newStatus = newStatusStr.isEmpty() ? existingProperty.getStatus() : Property.PropertyStatus.valueOf(newStatusStr.toUpperCase());

        System.out.print("Current business type: " + existingProperty.getBusinessType() + " -> ");
        String newBusinessType = scanner.nextLine();
        if (newBusinessType.isEmpty()) {
            newBusinessType = existingProperty.getBusinessType();
        }

        System.out.print("Current parking spaces: " + existingProperty.getParkingSpaces() + " -> ");
        String newParkingSpacesStr = scanner.nextLine();
        int newParkingSpaces = newParkingSpacesStr.isEmpty() ? existingProperty.getParkingSpaces() : Integer.parseInt(newParkingSpacesStr);

        System.out.print("Current square footage: " + existingProperty.getSquareFootage() + " -> ");
        String newSquareFootageStr = scanner.nextLine();
        double newSquareFootage = newSquareFootageStr.isEmpty() ? existingProperty.getSquareFootage() : Double.parseDouble(newSquareFootageStr);

        // Create updated property object
        CommercialProperty updatedProperty = new CommercialProperty(
                existingProperty.getPropertyId(),
                newAddress,
                newPricing,
                newStatus,
                newBusinessType,
                newParkingSpaces,
                newSquareFootage
        );

        // Confirm update
        System.out.print("Do you want to save these changes? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            commercialPropertyManager.update(updatedProperty);
            System.out.println("Commercial property updated successfully!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Commercial property deletion canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to confirm or 'n' to cancel.");
        }
    }

    private static void addNewCommercialProperty() {
        CommercialProperty newProperty = commercialPropertyManager.inputCommercialPropertyData();
        if (newProperty != null) {
            // Confirm if the user wants to add the property
            System.out.print("Do you want to add this commercial property? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                commercialPropertyManager.add(newProperty);
                commercialPropertyManager.saveToFile("FurtherAsm1/src/File/commercial_properties.txt");
                System.out.println("New commercial property added successfully!");
            } else if ("n".equals(confirmation)) {
                System.out.println("Adding commercial property action was canceled.");
            } else {
                System.out.println("Invalid choice. Please enter 'y' to confirm or 'n' to cancel.");
            }
        } else {
            System.out.println("Cannot add commercial property due to duplicate ID.");
        }
    }

    /**
     * Removes a residential property based on its ID.
     */
    private static void removeResidentialProperty() {
        System.out.print("\nEnter the propertyId to delete: ");
        String propertyId = scanner.nextLine();

        // Confirm if the user wants to delete this property
        System.out.print("Are you sure you want to delete this property? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            residentialPropertyManager.remove(propertyId);
            System.out.println("Residential property with propertyId " + propertyId + " was successfully deleted.");
        } else if ("n".equals(confirmation)) {
            System.out.println("Deletion action was canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to confirm or 'n' to cancel.");
        }
    }

    /**
     * Updates an existing residential property by modifying its attributes.
     */
    private static void updateResidentialProperty() {
        System.out.print("\nEnter the propertyId to update: ");
        String propertyId = scanner.nextLine();
        ResidentialProperty existingProperty = residentialPropertyManager.getOne(propertyId);

        if (existingProperty == null) {
            System.out.println("No residential property found with ID: " + propertyId);
            return;
        }
        System.out.println("Updating residential property with ID: " + propertyId);
        System.out.println("Press Enter to keep the current value.");

        // Input new values, leave empty to retain current values
        System.out.print("Current address: " + existingProperty.getAddress() + " -> ");
        String newAddress = scanner.nextLine();
        if (newAddress.isEmpty()) {
            newAddress = existingProperty.getAddress();
        }

        System.out.print("Current rental price: " + existingProperty.getPricing() + " -> ");
        String newPricingStr = scanner.nextLine();
        double newPricing = newPricingStr.isEmpty() ? existingProperty.getPricing() : Double.parseDouble(newPricingStr);

        System.out.print("Current status: " + existingProperty.getStatus() + " -> ");
        String newStatusStr = scanner.nextLine();
        Property.PropertyStatus newStatus = newStatusStr.isEmpty() ? existingProperty.getStatus() : Property.PropertyStatus.valueOf(newStatusStr.toUpperCase());

        System.out.print("Current number of bedrooms: " + existingProperty.getNumBedrooms() + " -> ");
        String newNumBedroomsStr = scanner.nextLine();
        int newNumBedrooms = newNumBedroomsStr.isEmpty() ? existingProperty.getNumBedrooms() : Integer.parseInt(newNumBedroomsStr);

        System.out.print("Garden availability (true/false): " + existingProperty.isGardenAvailability() + " -> ");
        String newGardenAvailabilityStr = scanner.nextLine();
        boolean newGardenAvailability = newGardenAvailabilityStr.isEmpty() ? existingProperty.isGardenAvailability() : Boolean.parseBoolean(newGardenAvailabilityStr);

        System.out.print("Pet-friendliness (true/false): " + existingProperty.isPetFriendliness() + " -> ");
        String newPetFriendlinessStr = scanner.nextLine();
        boolean newPetFriendliness = newPetFriendlinessStr.isEmpty() ? existingProperty.isPetFriendliness() : Boolean.parseBoolean(newPetFriendlinessStr);

        // Create updated property object
        ResidentialProperty updatedProperty = new ResidentialProperty(
                existingProperty.getPropertyId(),
                newAddress,
                newPricing,
                newStatus,
                newNumBedrooms,
                newGardenAvailability,
                newPetFriendliness
        );

        // Confirm update
        System.out.print("Do you want to save these changes? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if ("y".equals(confirmation)) {
            residentialPropertyManager.update(updatedProperty);
            System.out.println("Residential property updated successfully!");
        } else if ("n".equals(confirmation)) {
            System.out.println("Update action was canceled.");
        } else {
            System.out.println("Invalid choice. Please enter 'y' to confirm or 'n' to cancel.");
        }
    }

    /**
     * Displays the list of residential properties and provides actions like sorting or exporting.
     */
    private static void addNewResidentialProperty() {
        ResidentialProperty newProperty = residentialPropertyManager.inputResidentialPropertyData();
        if (newProperty != null) {
            // Confirm if the user wants to add the property
            System.out.print("Do you want to add this residential property? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirmation)) {
                residentialPropertyManager.add(newProperty);
                residentialPropertyManager.saveToFile("FurtherAsm1/src/File/residential_properties.txt");
                System.out.println("A new Residential property added successfully!");
            } else if ("n".equals(confirmation)) {
                System.out.println("Adding the residential property was canceled.");
            } else {
                System.out.println("Invalid choice. Please enter 'y' to confirm or 'n' to cancel.");
            }
        } else {
            System.out.println("Unable to add residential property due to duplicate ID.");
        }
    }

    private static void displayResidentialProperties() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display current list of residential properties
            System.out.println("\nCurrent list of residential properties:");
            for (ResidentialProperty property : residentialPropertyManager.getAll()) {
                System.out.println(property.toString());
                System.out.println("---------------------------------------------------------");
            }

            // Menu options
            System.out.println("\nChoose an action:");
            System.out.println("1. Sort residential properties by ID and redisplay");
            System.out.println("2. Export the list of residential properties to report");
            System.out.println("3. Return to the main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    residentialPropertyManager.sortPropertiesById();  // Sort by ID
                    System.out.println("\nThe residential properties have been sorted by ID.");
                    break;
                case 2:
                    System.out.print("Enter the report name (include path (ex: FurtherAsm1/src/File/) if needed): ");
                    String backupFileName = scanner.nextLine();
                    residentialPropertyManager.saveBackupToFile(backupFileName);  // Save backup
                    break;
                case 3:
                    System.out.println("Returning to the main menu.");
                    return;  // Exit this method
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Displays the list of commercial properties and provides actions like sorting or exporting.
     */
    private static void displayCommercialProperties() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display current list of commercial properties
            System.out.println("\nCurrent list of commercial properties:");
            for (CommercialProperty property : commercialPropertyManager.getAll()) {
                System.out.println(property.toString());
                System.out.println("---------------------------------------------------------");
            }

            // Menu options
            System.out.println("\nChoose an action:");
            System.out.println("1. Sort commercial properties by ID and redisplay");
            System.out.println("2. Export the list of commercial properties to report");
            System.out.println("3. Return to the main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    commercialPropertyManager.sortPropertiesById();  // Sort by ID
                    System.out.println("\nThe commercial properties have been sorted by ID.");
                    break;
                case 2:
                    System.out.print("Enter the report name (include path (ex: FurtherAsm1/src/File/) if needed): ");
                    String backupFileName = scanner.nextLine();
                    commercialPropertyManager.saveBackupToFile(backupFileName);  // Save backup
                    break;
                case 3:
                    System.out.println("Returning to the main menu.");
                    return;  // Exit this method
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Retrieves and displays rental agreements based on the owner's name.
     */
    private static void getByOwnerName() {
        System.out.println("\nEnter the owner's name: ");
        String ownerNameStr = scanner.nextLine();
        List<RentalAgreement> listR = rentalAgreementManager.getByOwnerName(ownerNameStr);
        for (RentalAgreement rentalAgreement : listR) {
            System.out.println(rentalAgreement.toString());
            System.out.println("---------------------------------------------------------");
        }
    }

    /**
     * Retrieves and displays rental agreements based on property address.
     */
    private static void getByPropertyAddress() {
        System.out.println("\nEnter the property address: ");
        String propertyAddressStr = scanner.nextLine();
        List<RentalAgreement> listR = rentalAgreementManager.getByPropertyAddress(propertyAddressStr);
        if (listR.isEmpty()) {
            System.out.println("No rental agreements found for the property address: " + propertyAddressStr);
        } else {
            for (RentalAgreement rentalAgreement : listR) {
                System.out.println(rentalAgreement.toString());
                System.out.println("---------------------------------------------------------");
            }
        }
    }

    /**
     * Retrieves and displays rental agreements based on their status.
     */
    private static void getByStatus() {
        System.out.println("\nEnter the rental agreement status (NEW, ACTIVE, COMPLETED): ");
        String statusStr = scanner.nextLine().toUpperCase();

        try {
            RentalAgreement.RentalAgreementStatus status = RentalAgreement.RentalAgreementStatus.valueOf(statusStr);
            List<RentalAgreement> listR = rentalAgreementManager.getByStatus(status);

            if (listR.isEmpty()) {
                System.out.println("No rental agreements found with status: " + status);
            } else {
                for (RentalAgreement rentalAgreement : listR) {
                    System.out.println(rentalAgreement.toString());
                    System.out.println("---------------------------------------------------------");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Please enter one of the following: NEW, ACTIVE, COMPLETED.");
        }
    }

    /**
     * Displays the list of rental agreements and provides actions like sorting or exporting.
     */
    private static void displayRentalAgreements() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display current list of rental agreements
            System.out.println("\nCurrent list of rental agreements:");
            for (RentalAgreement agreement : rentalAgreementManager.getAll()) {
                System.out.println(agreement.toString());
                System.out.println("---------------------------------------------------------");
            }

            // Menu options
            System.out.println("\nChoose an action:");
            System.out.println("1. Sort rental agreements by ID and redisplay");
            System.out.println("2. Export the list of rental agreements to report");
            System.out.println("3. Return to the main menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    rentalAgreementManager.sortRentalAgreementsById();  // Sort by ID
                    System.out.println("\nThe rental agreements have been sorted by contractId.");
                    break;
                case 2:
                    System.out.print("Enter the report name (include path (ex: FurtherAsm1/src/File/) if needed): ");
                    String backupFileName = scanner.nextLine();
                    rentalAgreementManager.saveRentalAgreementsBackupToFile(backupFileName);
                    break;
                case 3:
                    System.out.println("Returning to the main menu.");
                    return;  // Exit this method
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}













