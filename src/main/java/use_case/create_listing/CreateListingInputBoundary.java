package use_case.create_listing;

import java.io.IOException;

public interface CreateListingInputBoundary {
    void execute(CreateListingInputData createListingInputData) throws IOException;

    /**
     * Executes the switch to profile view use case.
     */
    void switchToProfileView();
}
