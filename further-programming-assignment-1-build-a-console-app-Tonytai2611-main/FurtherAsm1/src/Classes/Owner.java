/**
 * @author <Truong Phung Tan Tai - s3974929>
 */
package Classes;
import java.util.Date;
import java.util.List;


public class Owner extends Person {
    private List<Property> ownedProperties;
    private List<Host> managingHosts;

    /**
     * Constructs a new Owner object with the specified details.
     *
     * @param fullName        The full name of the owner.
     * @param id              The unique identifier for the owner.
     * @param dateOfBirth     The date of birth of the owner.
     * @param contactInfo     The contact information of the owner.
     * @param ownedProperties A list of properties owned by the owner.
     * @param managingHosts   A list of hosts managing the owner's properties.
     */
    public Owner(String fullName, String id, Date dateOfBirth, String contactInfo, List<Property> ownedProperties, List<Host> managingHosts) {
        super(fullName, id, dateOfBirth, contactInfo);
        this.ownedProperties = ownedProperties;
        this.managingHosts = managingHosts;

    }

    public Owner(String id) {super(id);}

    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(List<Property> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public List<Host> getManagingHosts() {
        return managingHosts;
    }

    public void setManagingHosts(List<Host> managingHosts) {
        this.managingHosts = managingHosts;
    }

    /**
     * Returns a string representation of the owner, including their properties and managing hosts.
     *
     * @return A string describing the owner.
     */
    @Override
    public String toString() {
        return String.format("%s|%-35s|%-35s",
                super.toString(),
                ownedProperties != null ? ownedProperties.toString() : "No owned properties",
                managingHosts != null ? managingHosts.toString() : "No managing hosts");
    }
}
