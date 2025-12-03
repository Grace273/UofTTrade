package use_case.create_listing;

import java.io.IOException;

public interface CreateListingInputBoundary {
    /**
     * Executes the create listing use case with the provided input data.
     *
     * <p>Implementations validate the input, attempt to create a new listing in the
     * system, and delegate presentation of the result to the appropriate output
     * boundary.</p>
     *
     * @param createListingInputData the data required to create a new listing
     * @throws IOException if an I/O error occurs while accessing persistent storage
     */
    void execute(CreateListingInputData createListingInputData) throws IOException;

    /**
     * Executes the switch to profile view use case.
     */
    void switchToProfileView();
}
