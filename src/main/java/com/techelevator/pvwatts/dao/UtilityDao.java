package com.techelevator.pvwatts.dao;

import com.techelevator.pvwatts.model.Utility;

import java.util.List;

public interface UtilityDao {
    /**
     * Get a utility from the datastore that has the given id.
     * If the id is not found, return null.
     *
     * @param utilityId The id of the utility.
     * @return A filled out Utility object, null if the utilityId isn't in the database.
     */
    Utility getUtilityById(int utilityId);

    /**
     * Get all utilities from the datastore, ordered by utility_id.
     *
     * @return All utilities as Utility objects in a List.
     */
    List<Utility> getUtilities();

    /**
     * Add a new utility into the datastore.
     *
     * @param newUtility the Utility object to add.
     * @return The added Utility object with its new id filled in.
     */
    Utility createUtility(Utility newUtility);

    /**
     * Update a utility to the datastore. Only called on utilities that
     * are already in the datastore.
     *
     * @param updatedUtility The Utility object to update.
     * @return The updated Utility object.
     */
    Utility updateUtility(Utility updatedUtility);

}
