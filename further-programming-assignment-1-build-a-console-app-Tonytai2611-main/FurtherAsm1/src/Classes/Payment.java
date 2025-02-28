/**
 * Represents a payment transaction in the rental system.
 * Each payment includes details such as the tenant who made the payment,
 * the amount, the payment method, and the date of the transaction.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package Classes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Payment {
    private String paymentId;
    private Tenant tenant;
    private double amount;
    private Date date;
    private String paymentMethod;

    /**
     * Constructs a new Payment object with the specified details.
     *
     * @param paymentMethod The method used for the payment (e.g., Credit Card, Cash).
     * @param date          The date the payment was made.
     * @param amount        The amount paid.
     * @param tenant        The tenant who made the payment.
     * @param paymentId     The unique identifier for the payment.
     */
    public Payment(String paymentMethod, Date date, double amount, Tenant tenant, String paymentId) {
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.amount = amount;
        this.tenant = tenant;
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Checks if this payment is equal to another payment based on the payment ID.
     *
     * @param o The object to compare with.
     * @return True if the payment IDs are the same; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId);
    }

    /**
     * Generates a hash code for the payment based on its ID.
     *
     * @return The hash code for this payment.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(paymentId);
    }

    /**
     * Returns a string representation of the payment, including its details.
     *
     * @return A formatted string describing the payment.
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format("|%-15s|%-15s|%-15s|%-15s|%-30s|",
                paymentId,
                tenant.getFullName(),
                amount,
                dateFormat.format(date),
                paymentMethod);
    }
}
