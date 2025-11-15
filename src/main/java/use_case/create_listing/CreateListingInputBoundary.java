package use_case.create_listing;

public interface CreateListingInputBoundary {

    /**
     * Executes the signup use case.
     * @param createListingInputData the input data
     */
    void execute(CreateListingInputData createListingInputData);

    /**
     * Executes the switch to the view profile use case
     */
    void switchToProfileView();
}