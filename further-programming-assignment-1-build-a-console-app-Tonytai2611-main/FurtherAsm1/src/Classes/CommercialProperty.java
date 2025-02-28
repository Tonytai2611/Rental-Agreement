/**
 * Represents a commercial property with additional attributes specific to commercial use.
 * Inherits from the Property class and includes fields like business type, parking spaces, and square footage.
 * Provides getter and setter methods for these fields, along with an overridden toString() method for formatted display.
 *
 * Author: Truong Phung Tan Tai - s3974929
 */

package Classes;

public class CommercialProperty extends Property implements Comparable<CommercialProperty> {
    private String businessType;
    private int parkingSpaces;
    private double squareFootage;

    /**
     * Constructs a new CommercialProperty object with the specified details.
     *
     * @param propertyId    The unique ID of the property.
     * @param address       The address of the property.
     * @param pricing       The rental price of the property.
     * @param status        The current status of the property (Available, Rented, Under Maintenance).
     * @param businessType  The type of business suitable for the property.
     * @param parkingSpaces The number of parking spaces available.
     * @param squareFootage The total square footage of the property.
     */

    public CommercialProperty(String propertyId, String address, double pricing, PropertyStatus status, String businessType, int parkingSpaces, double squareFootage) {
        super(propertyId, address, pricing, status);
        this.businessType = businessType;
        this.parkingSpaces = parkingSpaces;
        this.squareFootage = squareFootage;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public double getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }

    /**
     * Returns a string representation of the commercial property, including all its details.
     *
     * @return A string describing the commercial property.
     */
    @Override
    public String toString() {
        return  "|       CommercialProperty" + "\n" +
                "|       PropertyId: " + getPropertyId() + "\n" +
                "|       Address: " + getAddress() + "\n" +
                "|       Pricing: " + getPricing() + "\n" +
                "|       Status: " + getStatus() + "\n" +
                "|       BusinessType: " + businessType + "\n" +
                "|       ParkingSpaces: " + parkingSpaces + "\n" +
                "|       SquareFootage: " + squareFootage;
    }

    /**
     * Compares this property with another commercial property based on their square footage.
     *
     * @param other The other commercial property to compare with.
     * @return A negative integer, zero, or a positive integer as this property
     *         has less than, equal to, or greater square footage than the other property.
     */
    @Override
    public int compareTo(CommercialProperty other) {
        return Double.compare(this.squareFootage, other.squareFootage);
    }
}

