/**
 * Represents a tenant in the rental system.
 * Inherits from the Person class and includes attributes specific to tenants,
 * such as rental agreements they are part of and their payment records.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package Classes;

import java.util.Date;
import java.util.List;

public class Tenant extends Person {

    private List<RentalAgreement> rentalAgreements;
    private List<Payment> paymentRecords;

    /**
     * Constructs a new Tenant object with the specified details.
     *
     * @param fullName          The full name of the tenant.
     * @param id                The unique identifier for the tenant.
     * @param dateOfBirth       The date of birth of the tenant.
     * @param contactInfo       The contact information of the tenant.
     * @param rentalAgreements  A list of rental agreements associated with the tenant.
     * @param paymentRecords    A list of payment records made by the tenant.
     */
    public Tenant(String fullName, String id, Date dateOfBirth, String contactInfo, List<RentalAgreement> rentalAgreements, List<Payment> paymentRecords) {
        super(fullName, id, dateOfBirth, contactInfo);
        this.rentalAgreements = rentalAgreements;
        this.paymentRecords = paymentRecords;
    }

    public Tenant(String id) {
        super(id);
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) { this.rentalAgreements = rentalAgreements; }

    public List<Payment> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(List<Payment> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    /**
     * Returns a string representation of the tenant, including their rental agreements
     * and payment records summary.
     *
     * @return A formatted string describing the tenant.
     */
    @Override
    public String toString() {
        return String.format("%s|%-35s|%-35s|",
                super.toString(),
                rentalAgreements != null ? rentalAgreements.size() + " rental agreements" : "no rental agreements",
                paymentRecords != null ? paymentRecords.size() + " payment records" : "no payment records");
    }
}
