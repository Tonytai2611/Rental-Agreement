/**
 * Manager class for handling Payment objects.
 * Implements the RentalManager interface to provide CRUD operations,
 * validation, sorting, and file I/O for Payment data.
 * Author: Truong Phung Tan Tai - s3974929
 */

package Interface;

import Classes.Payment;
import Classes.Tenant;
import DAO.PaymentDAO;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PaymentManager implements  RentalManager<Payment>{
    private List<Payment> payments = new ArrayList<>();
    private PaymentDAO paymentDAO = new PaymentDAO(); // Create PaymentDAO object to save and load from file
    private TenantManager tenantManager = new TenantManager();
    private static String FILE_PATH = "FurtherAsm1/src/File/payments.txt";

    /**
     * Adds a new Payment to the list.
     *
     * @param item The Payment object to add.
     * @return True if the payment was successfully added, false if the payment ID already exists.
     */
    @Override
    public boolean add(Payment item){
        Payment temp = new Payment(item.getPaymentMethod(), item.getDate(), item.getAmount(), item.getTenant(), item.getPaymentId());

        if(payments.contains(temp)){
            System.out.println("Error: paymentId already exists"+ item.getPaymentId());
            return false;
        }

        // If paymentId does not exist, add to the list
        payments.add(temp);
        System.out.println("Payment successfully added: " + item);
        return true;
    }

    /**
     * Updates an existing Payment in the list.
     *
     * @param item The updated Payment object.
     */
    @Override
    public void update(Payment item){
        // Call update method from DAO to update payment and write back to file
        if (paymentDAO.update(item)){
            System.out.println("Payment updated successfully!");
        } else {
            System.out.println("No payment found with paymentId: " + item.getPaymentId());
        }
    }

    /**
     * Removes a Payment from the list by its ID.
     *
     * @param id The ID of the Payment to remove.
     */
    @Override
    public void remove(String id) {
        boolean removed = payments.removeIf(payment -> payment.getPaymentId().equals(id));
        if (removed) {
            System.out.println("Payment with paymentId removed: " + id);
            saveToFile("FurtherAsm1/src/File/payments.txt"); // Update data to file after removal
        } else {
            System.out.println("No payment found with paymentId: " + id);
        }
    }

    /**
     * Retrieves a Payment by its ID.
     *
     * @param id The ID of the Payment to retrieve.
     * @return The Payment object with the given ID, or null if not found.
     */
    @Override
    public Payment getOne(String id) {
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(id)) {
                return payment;
            }
        }
        return null;
    }

    /**
     * Retrieves all Payment objects.
     *
     * @return A list of all Payment objects.
     */
    @Override
    public List<Payment> getAll() {
        return new ArrayList<>(payments);
    }

    /**
     * Retrieves the IDs of all Payment objects.
     *
     * @return A list of IDs for all Payment objects.
     */
    @Override
    public List<String> getAllIDs(){
        List<String> ids = new ArrayList<>();
        for (Payment payment : payments) {
            ids.add(payment.getPaymentId());
        }
        return ids;
    }

    /**
     * Retrieves all Payment objects made by a specific tenant (customer) ID.
     *
     * @param customerID The ID of the tenant to search for.
     * @return A list of Payment objects associated with the given tenant ID.
     */
    @Override
    public List<Payment> getAllByCustomerID(String customerID) {
        List<Payment> customerPayments = new ArrayList<>();
        for (Payment payment : payments) {
            if (payment.getTenant().getId().equals(customerID)) {
                customerPayments.add(payment);
            }
        }
        return customerPayments;
    }

    /**
     * Saves the list of Payment objects to a file.
     *
     * @param fileName The file name to save to.
     */
    @Override
    public void saveToFile(String fileName) {
        try {
            // Call PaymentDAO to save the payment list to a file
            paymentDAO.writeToFile(payments,FILE_PATH);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Loads Payment objects from a file.
     *
     * @param fileName The file name to load from.
     */
    @Override
    public void loadFromFile(String fileName) {
        try {
            // Call PaymentDAO to load the payment list from a file
            payments = paymentDAO.readFromFile();
            if (payments.isEmpty()) {
                System.out.println("No payment data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Prompts user input to create a new Payment object.
     *
     * @return The created Payment object.
     */
    // Method to prompt user input and return a Payment object
    public Payment inputPaymentData() {
        Scanner scanner = new Scanner(System.in);

        // Enter paymentId
        String paymentId;
        while (true) {
            System.out.print("Enter paymentId (must start with 'P' followed by integer numbers): ");
            paymentId = scanner.nextLine();

            // Validate paymentId format using the regular expression "P" + digits
            if (!paymentId.matches("^P\\d+$")) {
                System.out.println("Invalid paymentId format. It must start with 'P' followed by digits.");
                continue;
            }

            // Check if paymentId already exists
            boolean exists = false;
            for (Payment p : payments) {
                if (p.getPaymentId().equals(paymentId)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Error: paymentId already exists. Please re-enter.");
            } else {
                break; // Exit the loop if paymentId is valid and unique
            }
        }


        // Input tenantId and automatically assign Tenant
        System.out.print("Enter tenantId: ");
        String tenantId = scanner.nextLine();
        tenantManager.loadFromFile("FurtherAsm1/src/File/tenants.txt");
        Tenant tenant = tenantManager.getOne(tenantId); // Find tenant by tenantId
        if (tenant != null) {
            System.out.println("Found tenant: " + tenant); // Display information of the found tenant
        } else {
            System.out.println("No tenant found with id: " + tenantId); // If no tenant is found
        }

        // Input payment amount
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine(); // Consume the remaining newline

        // Input payment method
        System.out.print("Enter payment method (e.g., Credit Card, Bank Transfer): ");
        String paymentMethod = scanner.nextLine();

        // Create a Payment object with the entered information
        Payment payment = new Payment(paymentMethod, new Date(), amount, tenant, paymentId);

        return payment; // Return the created Payment object
    }

    /**
     * Sorts Payment objects by their IDs in ascending order.
     */
    // Sort payments by ID in ascending order
    public void sortPaymentsById() {
        payments.sort((p1, p2) -> {
            try {
                // Extract the numeric part after the "P" prefix and convert it to an integer
                int id1 = Integer.parseInt(p1.getPaymentId().substring(1)); // Remove "P" prefix and parse the number
                int id2 = Integer.parseInt(p2.getPaymentId().substring(1)); // Same for the second payment

                return Integer.compare(id1, id2); // Compare the numeric values
            } catch (NumberFormatException e) {
                // If the ID can't be parsed as a number, fall back to comparing them as strings
                return p1.getPaymentId().compareTo(p2.getPaymentId());
            }
        });
        System.out.println("Payments list has been sorted by ID (ascending).");
    }

    /**
     * Saves the list of Payment objects to a backup file.
     *
     * @param backupFileName The name of the backup file.
     */
    // Save the Payments list to a backup file
    public void saveBackupToFile(String backupFileName) {
        if (backupFileName == null || backupFileName.isEmpty()) {
            System.out.println("Invalid backup file name.");
            return;
        }

        try {
            paymentDAO.writeToFile(payments, backupFileName);
            System.out.println("Payments list has been saved to backup file: " + backupFileName);
        } catch (Exception e) {
            System.out.println("Error while saving to backup file: " + backupFileName);
            e.printStackTrace();
        }
    }
}
