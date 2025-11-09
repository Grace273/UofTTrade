package use_case.create_listing;

public interface CreateListingOutputBoundary {
    /**
     * Prepares the success view for the Create Listing Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(CreateListingOutputData outputData);

    /**
     * Prepares the failure view for the Create Listing Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
