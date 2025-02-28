/**
 * Represents a residential property in the rental system.
 * Inherits from the Property class and includes attributes specific to residential properties,
 * such as the number of bedrooms, garden availability, and pet-friendliness.
 *
 * @author <Truong Phung Tan Tai - s3974929>
 */
package Classes;

public class ResidentialProperty extends Property {
    private int numBedrooms;
    private boolean gardenAvailability;
    private boolean petFriendliness;

    /**
     * Constructs a new ResidentialProperty object with the specified details.
     *
     * @param propertyId         The unique ID of the property.
     * @param address            The address of the property.
     * @param pricing            The rental price of the property.
     * @param status             The current status of the property (AVAILABLE, RENTED, UNDER_MAINTENANCE).
     * @param numBedrooms        The number of bedrooms in the property.
     * @param gardenAvailability Whether the property has a garden.
     * @param petFriendliness    Whether the property is pet-friendly.
     */
    public ResidentialProperty(String propertyId, String address, double pricing, PropertyStatus status, int numBedrooms, boolean gardenAvailability, boolean petFriendliness) {
        super(propertyId, address, pricing, status);
        this.numBedrooms = numBedrooms;
        this.gardenAvailability = gardenAvailability;
        this.petFriendliness = petFriendliness;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public boolean isGardenAvailability() {
        return gardenAvailability;
    }

    public void setGardenAvailability(boolean gardenAvailability) {
        this.gardenAvailability = gardenAvailability;
    }

    public boolean isPetFriendliness() {
        return petFriendliness;
    }

    public void setPetFriendliness(boolean petFriendliness) {
        this.petFriendliness = petFriendliness;
    }

    /**
     * Returns a string representation of the residential property, including its specific attributes.
     *
     * @return A formatted string describing the residential property.
     */
    @Override
    public String toString() {
        return  "|      ResidentialProperty" + "\n" +
                "|      PropertyId: " + getPropertyId() + "\n" +
                "|      Address: " + getAddress() + "\n" +
                "|      Pricing: " + getPricing() + "\n" +
                "|      Status=" + getStatus() + "\n" +
                "|      NumBedrooms: " + numBedrooms + "\n" +
                "|      GardenAvailability: " + gardenAvailability + "\n" +
                "|      PetFriendliness: " + petFriendliness;
    }

}
