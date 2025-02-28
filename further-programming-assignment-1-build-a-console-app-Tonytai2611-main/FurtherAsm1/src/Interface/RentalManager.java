/**
 * @author <Truong Phung Tan Tai - s3974929>
 */

package Interface;

import java.util.List;

public interface RentalManager<T> {
    /**
     * Adds a new item to the management system.
     * @param item the item to be added.
     */
    boolean add(T item);

    /**
     * Updates the information of an item in the management system.
     * @param item the item to be updated.
     */
    void update(T item);

    /**
     * Removes an item from the management system based on the item's ID.
     * @param id the ID of the item to be removed.
     */
    void remove(String id);

    /**
     * Retrieves the information of a specific item based on its ID.
     * @param id the ID of the item to retrieve.
     * @return the item with the corresponding ID, or null if not found.
     */
     T getOne(String id);

    /**
     * Retrieves all items in the management system.
     * @return a list of all items.
     */
     List<T> getAll();

    /**
     * Retrieves all IDs of the items in the management system.
     * @return a list of IDs for all items.
     */
     List<String> getAllIDs();

    /**
     * Retrieves all items associated with a specific customer ID.
     * @param customerID the ID of the customer.
     * @return a list of items belonging to the customer with the corresponding ID.
     */
     List<T> getAllByCustomerID(String customerID);

    /**
     * Saves the management system's data to a file.
     * @param fileName the name of the file to save to.
     */
     void saveToFile(String fileName);

    /**
     * Loads data into the management system from a file.
     * @param fileName the name of the file to load from.
     */
     void loadFromFile(String fileName);

}
