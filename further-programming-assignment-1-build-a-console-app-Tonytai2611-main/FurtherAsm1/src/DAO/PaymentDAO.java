/**
 * Data Access Object (DAO) for managing Payment objects.
 * Provides methods to read, write, update, and delete Payment records from a file.
 * It also uses the TenantManager to link payments to their respective tenants.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package DAO;

import Classes.Payment;
import Classes.Tenant;
import Interface.TenantManager;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class PaymentDAO {
    private static final TenantManager tenantManager = new TenantManager();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "FurtherAsm1/src/File/payments.txt"; // Link to store payment

    /**
     * Converts a Payment object into a string representation for file storage.
     *
     * @param payment The Payment object to convert.
     * @return A comma-separated string representation of the payment.
     */
    // Convert Payment object to String for file writing
    private String convertPaymentToString(Payment payment) {
        return String.join(",",
                payment.getPaymentId(),
                payment.getTenant().getId(),  // Only save Tenant ID
                String.valueOf(payment.getAmount()),
                dateFormat.format(payment.getDate()),
                payment.getPaymentMethod()
        );
    }

    /**
     * Converts a string from the file into a Payment object.
     *
     * @param line A line of text representing a Payment.
     * @return The corresponding Payment object, or null if the line is invalid.
     * @throws ParseException If the date format is incorrect.
     */
    // Convert String from file to Payment object
    private Payment convertStringToPayment(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String paymentId = parts[0];
        String tenantId = parts[1]; // Only Tenant ID
        double amount = Double.parseDouble(parts[2]);
        Date date = dateFormat.parse(parts[3]);
        String paymentMethod = parts[4];

        // Load Tenant from file using TenantManager
        tenantManager.loadFromFile("FurtherAsm1/src/File/tenants.txt");
        Tenant tenant = tenantManager.getOne(tenantId); // Get Tenant by ID

        if (tenant == null) {
            System.err.println("Tenant not found for ID: " + tenantId);
            return null;
        }

        return new Payment(paymentMethod, date, amount, tenant, paymentId);
    }

    /**
     * Writes a list of Payment objects to the file.
     *
     * @param payments  The list of payments to write.
     * @param FILE_PATH The file path where the data will be stored.
     */
    // Write a list of payments to a file (overwrite file content)
    public void writeToFile(List<Payment> payments,String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Payment payment : payments) {
                writer.write(convertPaymentToString(payment));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads Payment objects from the file and returns them as a list.
     * Creates a new file if it does not exist.
     *
     * @return A list of Payment objects.
     */
    // Read payments from file
    public List<Payment> readFromFile() {
        List<Payment> payments = new ArrayList<>();
        File file = new File(FILE_PATH);  // Check if the file exists

        if (!file.exists()) {
            System.out.println("Payment file does not exist.");
            System.out.print("Do you want to create a new file? (yes/no): ");
            String response = new Scanner(System.in).nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createNewFile();  // Create a new file
            } else {
                System.out.println("Process stopped.");
                return payments;  // Return an empty list if the user does not want to create a new file
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Payment payment = convertStringToPayment(line);  // Convert each line to a Payment object
                if (payment != null) {
                    payments.add(payment);  // Add the Payment object to the list
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return payments;
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
                System.out.println("The file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing Payment in the file.
     *
     * @param updatedPayment The updated Payment object.
     * @return True if the payment was updated, false otherwise.
     */
    // Update payment information in the list and write it back to the file
    public boolean update (Payment updatedPayment) {
        List<Payment> payments = readFromFile(); // Read the payment list from the file

        boolean paymentFound = false;
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getPaymentId().equals(updatedPayment.getPaymentId())) {
                // Update payment information
                payments.set(i, updatedPayment);
                paymentFound = true;
                break;
            }
        }
        if (paymentFound) {
            // Write the updated payment list back to the file
            writeToFile(payments, FILE_PATH);
            System.out.println("Payment updated successfully");
            return true;
        } else {
            System.out.println("Payment not found!");
            return false;
        }
    }

    /**
     * Deletes a Payment from the file by its ID.
     *
     * @param paymentId The ID of the payment to delete.
     * @return True if the payment was deleted, false otherwise.
     */
    // Delete payment by paymentId and update the file
    public boolean delete (String paymentId) {
        List<Payment> payments = readFromFile(); // Read the payment list from the file

        boolean paymentFound = false;
        Iterator<Payment> iterator = payments.iterator();
        while (iterator.hasNext()) {
            Payment payment = iterator.next();
            if (payment.getPaymentId().equals(paymentId)) {
                iterator.remove(); // Remove payment from the list
                paymentFound = true;
                break;
            }
        }
        if (paymentFound){
            // Write the payment list after deletion back to the file
            writeToFile(payments, FILE_PATH);
            System.out.println("Payment deleted successfully!");
            return true;
        } else {
            System.out.println("Payment not found!");
            return false;
        }
    }
}
