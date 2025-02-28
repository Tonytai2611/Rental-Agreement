/**
 * @author <Truong Phung Tan Tai - s3974929>
 */



package Classes;
import java.util.Date;
import java.util.List;

/**
 * Represents a host who manages properties.
 * Inherits from the Person class and includes attributes for managing properties and cooperating owners.
 * Provides getter and setter methods for associated lists and overrides the toString() method for detailed representation.
 */

public class Host extends Person implements Comparable<Host> {
    private List<Property> managedProperties;
    private List<Owner> cooperatingOwners;

    /**
     * Constructs a new Host object with the specified details.
     *
     * @param fullName           The full name of the host.
     * @param id                 The unique identifier for the host.
     * @param dateOfBirth        The date of birth of the host.
     * @param contactInfo        The contact information of the host.
     * @param managedProperties  A list of properties managed by the host.
     * @param cooperatingOwners  A list of owners cooperating with the host.
     */
    public Host(String fullName, String id, Date dateOfBirth, String contactInfo, List<Property> managedProperties, List<Owner> cooperatingOwners) {
        super(fullName, id, dateOfBirth, contactInfo);
        this.managedProperties = managedProperties;
        this.cooperatingOwners = cooperatingOwners;

    }

    public Host(String id) {super(id);}

    public List<Property> getManagedProperties() {
        return managedProperties;
    }

    public void setManagedProperties(List<Property> managedProperties) {
        this.managedProperties = managedProperties;
    }

    public List<Owner> getCooperatingOwners() {
        return cooperatingOwners;
    }

    public void setCooperatingOwners(List<Owner> cooperatingOwners) {
        this.cooperatingOwners = cooperatingOwners;
    }


    /**
     * Returns a string representation of the host, including managed properties and cooperating owners.
     *
     * @return A string describing the host.
     */
    @Override
    public String toString() {
        return String.format("%s|%-35s|%-35s|",
                super.toString(),
                managedProperties != null ? managedProperties.toString() : "No managed properties",
                cooperatingOwners != null ? cooperatingOwners.toString() : "No cooperating owners");
    }

    /**
     * Compares this host with another host based on the number of managed properties.
     *
     * @param other The other host to compare with.
     * @return A negative integer, zero, or a positive integer as this host manages fewer,
     *         the same, or more properties than the other host.
     */
    @Override
    public int compareTo(Host other){
        return Integer.compare(this.managedProperties.size(), other.managedProperties.size());
    }
}
