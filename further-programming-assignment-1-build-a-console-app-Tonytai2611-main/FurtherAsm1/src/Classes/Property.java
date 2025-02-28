/**
 * Represents a base class for all types of properties in the rental system.
 * This class is abstract and provides common attributes and methods shared by
 * all property types, such as residential and commercial properties.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package Classes;
import java.util.Objects;

public abstract class Property {
    private String propertyId;
    private String address;
    private double pricing;
    private PropertyStatus status;

    /**
     * Enum to represent the status of a property.
     * The property can be AVAILABLE, RENTED, or UNDER_MAINTENANCE.
     */
    public enum PropertyStatus {
        AVAILABLE, RENTED, UNDER_MAINTENANCE;
    }

    /**
     * Constructs a Property object with only an ID.
     * Useful for scenarios where only the ID is initially known.
     *
     * @param id The unique identifier for the property.
     */
    public Property(String id) { this.propertyId = id; }

    /**
     * Constructs a Property object with all details.
     *
     * @param propertyId The unique identifier for the property.
     * @param address    The address of the property.
     * @param pricing    The rental price of the property.
     * @param status     The current status of the property (AVAILABLE, RENTED, UNDER_MAINTENANCE).
     */
    public Property(String propertyId, String address, double pricing, PropertyStatus status) {
        this.propertyId = propertyId;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
    }

    public String getPropertyId(){ return propertyId; }

    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public PropertyStatus getStatus() { return status; }

    public void setStatus(PropertyStatus status) { this.status = status; }

    public double getPricing() { return pricing; }

    public void setPricing(double pricing) { this.pricing = pricing; }

    /**
     * Compares this property with another object based on the property ID.
     *
     * @param o The object to compare with.
     * @return True if the property IDs match; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(propertyId, property.propertyId);
    }

    /**
     * Generates a hash code for the property based on its ID.
     *
     * @return The hash code for this property.
     */
    @Override
    public int hashCode() { return Objects.hashCode(propertyId); }

    /**
     * Returns a string representation of the property, including its attributes.
     *
     * @return A string describing the property.
     */
    @Override
    public String toString() {
        return "Property{" +
                "propertyId='" + propertyId + '\'' +
                ", address='" + address + '\'' +
                ", pricing=" + pricing +
                ", status='" + status + '\'' +
                '}';
    }
}
