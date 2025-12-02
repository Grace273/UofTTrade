package use_case.update_listing;

public interface UpdateListingInputBoundary {
    /**
     * Executes the update listing use case with the provided input data.
     *
     * <p>
     * Implementations of this interface define how a listing should be updated
     * based on the information contained in the given {@link UpdateListingInputData}
     * object.
     *
     * @param updateListingInputData the data required to update an existing listing
     */
    void execute(UpdateListingInputData updateListingInputData);
}
