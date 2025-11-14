package use_case.create_listing;

import entity.Listing;
import entity.User;

public class CreateListingInteractor implements CreateListingInputBoundary{
    private final CreateListingUserDataAccessInterface createListingDataAccess;
    private final CreateListingOutputBoundary createListingPresenter;

    public CreateListingInteractor(CreateListingUserDataAccessInterface createListingDataAccess, CreateListingOutputBoundary createListingPresenter) {
        this.createListingDataAccess = createListingDataAccess;
        this.createListingPresenter = createListingPresenter;
    }

    /**
     * Executes the create listing use case
     * @param createListingInputData the InputData for creating a listing
     */
    @Override
    public void execute(CreateListingInputData createListingInputData) {

        final String id = createListingInputData.get_listingId();
        final User owner = createListingInputData.get_owner();

        // check if a listing of the same ID already exists
        if(createListingDataAccess.existsById(id)){
            createListingPresenter.prepareFailView("Listing with id " + id + " already exists");
            return;
        }

        // check if a user with the owner's username does not exist
        if(!createListingDataAccess.existsByUsername(owner.get_username())) {
            createListingPresenter.prepareFailView("Owner does not exist");
            return;
        }

        // create instance of Listing entity save to the database
        final Listing listing = getListing(createListingInputData);
        createListingDataAccess.save(listing);

        // create the output data object and send to the presenter
        final CreateListingOutputData createListingOutputData = getCreateListingOutputData(createListingInputData);
        createListingPresenter.prepareSuccessView(createListingOutputData);
    }

    /**
     * Switches to ViewProfile use case
     */
    @Override
    public void switchToProfileView() { userPresenter.switchToProfileView();}

    /**
     * Returns a Listing object. Helper method for execute.
     * @param createListingInputData the InputData for creating a listing
     * @return a Listing object
     */
    private Listing getListing(CreateListingInputData createListingInputData) {
        final Listing listing;

        if (createListingInputData.get_categories().isEmpty()) {
            listing = new Listing(
                    createListingInputData.get_name(),
                    createListingInputData.get_img(),
                    createListingInputData.get_owner(),
                    createListingInputData.get_listing_id()
            );
        } else {
            listing = new Listing(
                    createListingInputData.get_name(),
                    createListingInputData.get_img(),
                    createListingInputData.get_categories(),
                    createListingInputData.get_owner(),
                    createListingInputData.get_listing_id()
            );
        }
        return listing;
    }

    /**
     * Returns a CreateListingOutputData object. Helper method for execute.
     * @param createListingInputData the InputData for creating a listing
     * @return a CreateListingOutputData object
     */
    private CreateListingOutputData getCreateListingOutputData(CreateListingInputData createListingInputData) {
        final CreateListingOutputData createListingOutputData;

        if (createListingInputData.get_categories().isEmpty()) {
            createListingOutputData = new CreateListingOutputData(
                    createListingInputData.get_name(),
                    createListingInputData.get_img(),
                    createListingInputData.get_owner(),
                    createListingInputData.get_listing_id()
            );
        } else {
            createListingOutputData = new CreateListingOutputData(
                    createListingInputData.get_name(),
                    createListingInputData.get_img(),
                    createListingInputData.get_categories(),
                    createListingInputData.get_owner(),
                    createListingInputData.get_listing_id()
            );
        }

        return createListingOutputData;
    }

}
