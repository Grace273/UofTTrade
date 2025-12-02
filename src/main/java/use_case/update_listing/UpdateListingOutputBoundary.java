package use_case.update_listing;

public interface UpdateListingOutputBoundary {
    /**
     * Prepares and presents the success view for the update listing use case.
     *
     * <p>
     * Implementations of this method use the provided {@link UpdateListingOutputData}
     * to format and display the result of a successful listing update operation
     * to the user.
     * </p>
     *
     * @param outputData the output data containing information about the updated user
     *                   and/or listing after a successful update operation
     */
    void prepareSuccessView(UpdateListingOutputData outputData);
}
