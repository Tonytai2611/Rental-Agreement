/**
 * @author <Truong Phung Tan Tai - s3974929>
 */
package DAO;

import Classes.*;
import Interface.HostManager;
import Interface.OwnerManager;
import Interface.TenantManager;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentalAgreementDAO {
    private static final OwnerManager ownerManager = new OwnerManager();
    private static final TenantManager tenantManager = new TenantManager();
    private static final HostManager hostManager = new HostManager();
    private static final String FILE_PATH = "FurtherAsm1/src/File/rental_agreements.txt";

    /**
     * Serializes a RentalAgreement object into a string format for file storage.
     *
     * @param agreement The RentalAgreement object to serialize.
     * @return A comma-separated string representation of the agreement.
     */
    // Convert RentalAgreement object to String to file writing
    private String convertRentalAgreementToString(RentalAgreement agreement) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(agreement.getContractDate()); // Format contractDate

        return String.join(",",
                agreement.getContractId(),
                formattedDate,
                agreement.getOwner().getId(),
                agreement.getMainTenant().getId(),
                convertSubTenantsToString(agreement.getSubTenants()),
                convertPropertyToString(agreement.getRentedProperty()),
                convertHostsToString(agreement.getHosts()),
                agreement.getRentalCycle().name(),
                String.valueOf(agreement.getDuration()),
                agreement.getContractTerms(),
                String.valueOf(agreement.getRentalFee()),
                agreement.getStatus().name()
        );
    }

    /**
     * Deserializes a string from the file into a RentalAgreement object.
     *
     * @param line A comma-separated string representing a RentalAgreement.
     * @return A RentalAgreement object, or null if deserialization fails.
     */
    // Convert String from file to RentalAgreement object
    private RentalAgreement convertStringToRentalAgreement(String line) {
        String[] parts = line.split(",");
        if (parts.length < 12) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        try {
            String contractId = parts[0];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date contractDate = dateFormat.parse(parts[1]);

            ownerManager.loadFromFile("FurtherAsm1/src/File/owners.txt");
            Owner owner = ownerManager.getOne(parts[2]);

            tenantManager.loadFromFile("FurtherAsm1/src/File/tenants.txt");
            Tenant mainTenant = tenantManager.getOne(parts[3]);

            List<Tenant> subTenants = convertStringToSubTenants(parts[4]);
            Property rentedProperty = convertStringToProperty(parts[5]);
            List<Host> hosts = convertStringToHosts(parts[6]);
            RentalAgreement.RentalCycleType rentalCycle = RentalAgreement.RentalCycleType.valueOf(parts[7]);
            int duration = Integer.parseInt(parts[8]);
            String contractTerms = parts[9];
            double rentalFee = Double.parseDouble(parts[10]);
            RentalAgreement.RentalAgreementStatus status = RentalAgreement.RentalAgreementStatus.valueOf(parts[11]);

            return new RentalAgreement(contractId, contractDate, owner, mainTenant, subTenants, rentedProperty, hosts,
                    rentalCycle, duration, contractTerms, rentalFee, status);
        } catch (ParseException e) {
            System.err.println("Error parsing contract date: " + parts[1]);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Error processing line: " + line);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Serializes a Property object (either CommercialProperty or ResidentialProperty) into a string format.
     *
     * @param property The Property object to serialize.
     * @return A semicolon-separated string representation of the property.
     * @throws IllegalArgumentException If the property type is unrecognized.
     */
    // Serialize Property object to String
    private String convertPropertyToString(Property property) {
        if (property instanceof CommercialProperty) {
            CommercialProperty cp = (CommercialProperty) property;
            return String.join(";",
                    "CommercialProperty",
                    cp.getPropertyId(),
                    cp.getAddress(),
                    String.valueOf(cp.getPricing()),
                    cp.getStatus().name(),
                    cp.getBusinessType(),
                    String.valueOf(cp.getParkingSpaces()),
                    String.valueOf(cp.getSquareFootage())
            );
        } else if (property instanceof ResidentialProperty) {
            ResidentialProperty rp = (ResidentialProperty) property;
            return String.join(";",
                    "ResidentialProperty",
                    rp.getPropertyId(),
                    rp.getAddress(),
                    String.valueOf(rp.getPricing()),
                    rp.getStatus().name(),
                    String.valueOf(rp.getNumBedrooms()),
                    String.valueOf(rp.isGardenAvailability()),
                    String.valueOf(rp.isPetFriendliness())
            );
        } else {
            throw new IllegalArgumentException("Unknow propterty type: " + property.getClass().getSimpleName());
        }
    }

    /**
     * Deserializes a string into a Property object.
     * Supports both CommercialProperty and ResidentialProperty.
     *
     * @param propertyString A semicolon-separated string representing a property.
     * @return A Property object (CommercialProperty or ResidentialProperty).
     * @throws IllegalArgumentException If the property type is unrecognized.
     */
    // Deserialize Property from String
    private Property convertStringToProperty(String propertyString) {
        String[] parts = propertyString.split(";");
        String propertyType = parts[0];
        switch (propertyType) {
            case "CommercialProperty":
                return new CommercialProperty(
                        parts[1], parts[2], Double.parseDouble(parts[3]), Property.PropertyStatus.valueOf(parts[4]),
                        parts[5], Integer.parseInt(parts[6]), Double.parseDouble(parts[7]));
            case "ResidentialProperty":
                return new ResidentialProperty(
                        parts[1], parts[2], Double.parseDouble(parts[3]), Property.PropertyStatus.valueOf(parts[4]),
                        Integer.parseInt(parts[5]), Boolean.parseBoolean(parts[6]), Boolean.parseBoolean(parts[7]));
            default:
                throw new IllegalArgumentException("Unknown property type: " + propertyType);
        }
    }

    /**
     * Serializes a list of Tenant objects into a semicolon-separated string of their IDs.
     *
     * @param subTenants The list of Tenant objects to serialize.
     * @return A semicolon-separated string of tenant IDs.
     */

    // Convert subTenants list to String
    private String convertSubTenantsToString(List<Tenant> subTenants) {
        StringBuilder result = new StringBuilder();
        for (Tenant tenant : subTenants) {
            result.append(tenant.getId()).append(";");
        }
        return result.toString();
    }

    /**
     * Deserializes a semicolon-separated string of tenant IDs into a list of Tenant objects.
     *
     * @param subTenantsString A semicolon-separated string of tenant IDs.
     * @return A list of Tenant objects.
     */

    // Convert String to subTenants list
    private List<Tenant> convertStringToSubTenants(String subTenantsString) {
        List<Tenant> subTenants = new ArrayList<>();
        String[] ids = subTenantsString.split(";");

        tenantManager.loadFromFile("FurtherAsm1/src/File/tenants.txt"); // Load the list of Tenants from the file

        for (String id : ids) {
            if (!id.isEmpty()) {
                Tenant tenant = tenantManager.getOne(id); // Retrieve the full Tenant object from TenantManager
                if (tenant != null) {
                    subTenants.add(tenant);
                } else {
                    System.err.println("SubTenant not found for ID: " + id);
                }
            }
        }
        return subTenants;
    }

    /**
     * Serializes a list of Host objects into a semicolon-separated string of their IDs.
     *
     * @param hosts The list of Host objects to serialize.
     * @return A semicolon-separated string of host IDs.
     */

    // Convert hosts list to String
    private String convertHostsToString(List<Host> hosts) {
        StringBuilder result = new StringBuilder();
        for (Host host : hosts) {
            result.append(host.getId()).append(";");
        }
        return result.toString();
    }

    /**
     * Deserializes a semicolon-separated string of host IDs into a list of Host objects.
     *
     * @param hostsString A semicolon-separated string of host IDs.
     * @return A list of Host objects.
     */

    // Convert String to hosts list
    private List<Host> convertStringToHosts(String hostsString) {
        List<Host> hosts = new ArrayList<>();
        String[] ids = hostsString.split(";");

        hostManager.loadFromFile("FurtherAsm1/src/File/hosts.txt"); // Load the list of Hosts from the file

        for (String id : ids) {
            if (!id.isEmpty()) {
                Host host = hostManager.getOne(id); // Retrieve the full Host object from HostManager
                if (host != null) {
                    hosts.add(host);
                } else {
                    System.err.println("Host not found for ID: " + id);
                }
            }
        }
        return hosts;
    }

    /**
     * Writes a list of RentalAgreement objects to a file, overwriting the existing content.
     *
     * @param agreements The list of RentalAgreement objects to write.
     * @param FILE_PATH The file path where the data will be stored.
     */

    // Write a list of RentalAgreements to a file
    public void writeToFile(List<RentalAgreement> agreements, String FILE_PATH) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RentalAgreement agreement : agreements) {
                writer.write(convertRentalAgreementToString(agreement));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads RentalAgreement objects from the file and returns them as a list.
     * If the file does not exist, an empty list is returned.
     *
     * @return A list of RentalAgreement objects.
     */

    // Read a list of RentalAgreements from a file
    public List<RentalAgreement> readFromFile() {
        List<RentalAgreement> agreements = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("Rental agreements file does not exist.");
            return agreements; // Return an empty list
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                RentalAgreement agreement = convertStringToRentalAgreement(line);
                if (agreement != null) {
                    agreements.add(agreement);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agreements;
    }

    /**
     * Updates an existing RentalAgreement in the file with new details.
     *
     * @param updatedAgreement The RentalAgreement object containing updated details.
     * @return True if the agreement was updated successfully, false otherwise.
     */

    // Update rental agreement information in the list and write it back to the file
    public boolean update(RentalAgreement updatedAgreement) {
        List<RentalAgreement> agreements = readFromFile();

        boolean agreementFound = false;
        for (int i = 0; i < agreements.size(); i++) {
            if (agreements.get(i).getContractId().equals(updatedAgreement.getContractId())) {
                agreements.set(i, updatedAgreement);
                agreementFound = true;
                break;
            }
        }

        if (agreementFound) {
            writeToFile(agreements, FILE_PATH);
            return true;
        } else {
            System.out.println("Rental Agreement not found!");
            return false;
        }
    }

    /**
     * Deletes a RentalAgreement from the file based on its contract ID.
     *
     * @param contractId The contract ID of the RentalAgreement to delete.
     * @return True if the agreement was deleted successfully, false otherwise.
     */

    // Delete rental agreement by contractId and update the file
    public boolean delete(String contractId) {
        List<RentalAgreement> agreements = readFromFile();

        boolean agreementFound = false;
        Iterator<RentalAgreement> iterator = agreements.iterator();
        while (iterator.hasNext()) {
            RentalAgreement agreement = iterator.next();
            if (agreement.getContractId().equals(contractId)) {
                iterator.remove();
                agreementFound = true;
                break;
            }
        }

        if (agreementFound) {
            writeToFile(agreements, FILE_PATH);
            System.out.println("Rental Agreement deleted successfully!");
            return true;
        } else {
            System.out.println("Rental Agreement not found!");
            return false;
        }
    }
}
