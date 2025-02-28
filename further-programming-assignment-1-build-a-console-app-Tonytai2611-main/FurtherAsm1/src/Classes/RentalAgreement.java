/**
 * @author <Truong Phung Tan Tai - s3974929>
 */
package Classes;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RentalAgreement implements Comparable<RentalAgreement> {
    private String contractId;
    private Date ContractDate;
    private Owner owner;
    private Tenant mainTenant;
    private List<Tenant> subTenants;
    private Property rentedProperty;
    private List<Host> hosts;
    private RentalCycleType rentalCycle; // Added rentalCycle attribute for rental cycle
    private int duration; // Duration in units like days, weeks, months, etc.
    private String contractTerms;
    private double rentalFee;
    private RentalAgreementStatus status;

    // Enum defining the status of the agreement
    public enum RentalAgreementStatus {
        NEW, ACTIVE, COMPLETED;
    }

    // Enum defining the rental cycle
    public enum RentalCycleType {
        DAILY, WEEKLY, FORTNIGHTLY, MONTHLY, YEARLY;
    }

    // Constructor for RentalAgreement
    public RentalAgreement(String contractId, Date ConDate, Owner owner, Tenant mainTenant, List<Tenant> subTenants,
                           Property rentedProperty, List<Host> hosts, RentalCycleType rentalCycle,
                           int duration, String contractTerms, double rentalFee, RentalAgreementStatus status) {
        this.contractId = contractId;
        this.ContractDate = ConDate;
        this.owner = owner;
        this.mainTenant = mainTenant;
        this.subTenants = subTenants;
        this.rentedProperty = rentedProperty;
        this.hosts = hosts;
        this.rentalCycle = rentalCycle;
        this.duration = duration;  // Duration in units (days, weeks, months, etc.)
        this.contractTerms = contractTerms;
        this.rentalFee = rentalFee;
        this.status = status;
    }

    public RentalAgreement() {
    }

    // Getters and Setters
    public RentalCycleType getRentalCycle() { return rentalCycle; }

    public void setRentalCycle(RentalCycleType rentalCycle) { this.rentalCycle = rentalCycle; }

    public int getDuration() { return duration; }

    public void setDuration(int duration) { this.duration = duration; }

    public String getContractId() { return contractId; }

    public void setContractId(String contractId) { this.contractId = contractId; }

    public Date getContractDate() { return ContractDate; }

    public void setContractDate(Date contractDate) { this.ContractDate = contractDate; }

    public Tenant getMainTenant() { return mainTenant; }

    public void setMainTenant(Tenant mainTenant) { this.mainTenant = mainTenant; }

    public List<Tenant> getSubTenants() { return subTenants; }

    public void setSubTenants(List<Tenant> subTenants) { this.subTenants = subTenants; }

    public Property getRentedProperty() { return rentedProperty; }

    public void setRentedProperty(Property rentedProperty) { this.rentedProperty = rentedProperty; }

    public List<Host> getHosts() { return hosts; }

    public void setHosts(List<Host> hosts) { this.hosts = hosts; }

    public String getContractTerms() { return contractTerms; }

    public void setContractTerms(String contractTerms) { this.contractTerms = contractTerms; }

    public double getRentalFee() { return rentalFee; }

    public void setRentalFee(double rentalFee) { this.rentalFee = rentalFee; }

    public RentalAgreementStatus getStatus() { return status; }

    public void setStatus(RentalAgreementStatus status) { this.status = status; }

    public Owner getOwner() { return owner; }

    public void setOwner(Owner owner) { this.owner = owner; }

    /**
     * Compares this rental agreement with another based on the rental fee.
     *
     * @param other The other rental agreement to compare with.
     * @return A negative integer, zero, or a positive integer as this rental fee
     *         is less than, equal to, or greater than the specified rental fee.
     */
    // Implement compareTo method to sort by rentalFee
    @Override
    public int compareTo(RentalAgreement other) {
        return Double.compare(this.rentalFee, other.rentalFee);
    }

    /**
     * Checks if this rental agreement is equal to another object based on the contract ID.
     *
     * @param o The object to compare with.
     * @return True if the contract IDs match; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RentalAgreement that = (RentalAgreement) o;
        return Objects.equals(contractId, that.contractId);
    }

    /**
     * Generates a hash code for the rental agreement based on the contract ID.
     *
     * @return The hash code for this agreement.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(contractId);
    }

    /**
     * Returns a formatted string representation of the rental agreement.
     *
     * @return A string describing the rental agreement.
     */
    @Override
    public String toString() {
        // Format the contractDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = (ContractDate != null) ? dateFormat.format(ContractDate) : "N/A";

        // String of full names for each subTenant
        StringBuilder subTenantsNames = new StringBuilder();
        if (subTenants != null && !subTenants.isEmpty()) {
            for (Tenant tenant : subTenants) {
                subTenantsNames.append(tenant.getFullName()).append(", ");
            }
            // Remove the last comma
            if (subTenantsNames.length() > 0) {
                subTenantsNames.setLength(subTenantsNames.length() - 2);
            }
        } else {
            subTenantsNames.append("None");
        }

        // String of full names for each host
        StringBuilder hostsNames = new StringBuilder();
        if (hosts != null && !hosts.isEmpty()) {
            for (Host host : hosts) {
                hostsNames.append(host.getFullName()).append(", ");
            }
            // Remove the last comma
            if (hostsNames.length() > 0) {
                hostsNames.setLength(hostsNames.length() - 2);
            }
        } else {
            hostsNames.append("None");
        }

        return "| ContractId: " + contractId + "\n" +
                "| ContractDate: " + formattedDate + "\n" +
                "| FullName_Owner: " + owner.getFullName() + "\n" +
                "| MainTenant: " + mainTenant.getFullName() + " | SubTenants: " + subTenantsNames + "\n" +
                "| RentedProperty: " + "\n" + rentedProperty + "\n" +
                "| Hosts: " + hostsNames + "\n" +
                "| rentalCycle: " + rentalCycle + "\n" +
                "| duration: " + duration + "\n" +
                "| contractTerms: " + contractTerms + "\n" +
                "| rentalFee: " + rentalFee + "\n" +
                "| status: " + status;
    }
}