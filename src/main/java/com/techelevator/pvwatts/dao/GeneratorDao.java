package com.techelevator.pvwatts.dao;

import com.techelevator.pvwatts.model.Generator;

import java.util.List;

public interface GeneratorDao {

    /**
     * Get a generator from the datastore that has the given id.
     * If the id is not found, return null.
     *
     * @param generatorId The id of the generator.
     * @return A filled out Generator object, or null if the id is invalid.
     */
    Generator getGeneratorById(int generatorId);

    /**
     * Get all Generators from the datastore, ordered by Generator_id.
     *
     * @return All Generators as Generator objects in a List.
     */
    List<Generator> getGenerators();

    /**
     * Get all generators from the datastore that has the given utilityId.
     * If the id is not found, return an empty List.
     *
     * @param utilityId The id of the utility.
     * @return All Generators with the given utilityId as Generator objects in a List.
     */
    List<Generator> getGeneratorByUtilityId(int utilityId);


    Generator createGenerator(Generator newGenerator);

    /**
     * Update a Generator in the datastore. Only called on Generators that
     * are already in the datastore.
     *
     * @param updatedGenerator The Generator object to update.
     * @return The updated Generator object with its new id filled in.
     */
    Generator updateGenerator(Generator updatedGenerator);

    /**
     * Remove a Generator from the datastore.
     *
     * @param generatorId The id of the Generator to remove. If the id doesn't exist, no error will occur.
     * @return The number of rows deleted.
     */
    int deleteGeneratorById(int generatorId);

}
