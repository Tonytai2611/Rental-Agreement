/**
 * Represents a base class for all persons in the rental system.
 * This class is abstract and provides common attributes and methods
 * that are shared among all person types, such as `Tenant`, `Host`, and `Owner`.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package Classes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public abstract class Person {
    private String id;
    private String fullName;
    private Date dateOfBirth;
    private String contactInfo;


    /**
     * Constructs a new Person object with the specified details.
     *
     * @param fullName    The full name of the person.
     * @param id          The unique identifier for the person.
     * @param dateOfBirth The date of birth of the person.
     * @param contactInfo The contact information of the person.
     */
    public Person(String fullName, String id, Date dateOfBirth, String contactInfo) {
        this.fullName = fullName;
        this.id= id;
        this.dateOfBirth = dateOfBirth;
        this.contactInfo = contactInfo;
    }

    public Person(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {return fullName;}

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactInfo() { return contactInfo; }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Compares this person with another object based on the ID.
     *
     * @param o The object to compare with.
     * @return True if the IDs match; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }


    /**
     * Generates a hash code for the person based on their ID.
     *
     * @return The hash code for this person.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Returns a string representation of the person's details.
     *
     * @return A formatted string with the person's details.
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format("|%-15s|%-20s|%-15s|%-25s",
                id,
                fullName,
                dateFormat.format(dateOfBirth),
                contactInfo);
    }
}
