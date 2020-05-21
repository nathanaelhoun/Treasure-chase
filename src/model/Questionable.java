package model;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public interface Questionable {
    /**
     * Update the Model.Hunter h
     * Show to the user the modifications
     *
     * @param h the Model.Hunter to process
     */
    void process(Hunter h);
}
