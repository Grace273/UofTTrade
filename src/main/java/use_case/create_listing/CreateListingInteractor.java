package use_case.create_listing;

import java.io.IOException;

import data_access.CreateListingDataAccessObject;
import entity.Listing;
import entity.User;
import use_case.view_profile.ViewProfileUserDataAccessInterface;

public class CreateListingInteractor implements CreateListingInputBoundary {
    private final CreateListingUserDataAccessInterface createListingDataAccessObject;
    private final CreateListingOutputBoundary createListingPresenter;
    private final ViewProfileUserDataAccessInterface userDataAccess;

    public CreateListingInteractor(CreateListingUserDataAccessInterface createListingDataAccess,
                                   CreateListingOutputBoundary createListingOutputBoundary,
                                   ViewProfileUserDataAccessInterface userDataAccess) {
        this.createListingDataAccessObject = createListingDataAccess;
        this.createListingPresenter = createListingOutputBoundary;
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void execute(CreateListingInputData createListingInputData) throws IOException {
        final String name = createListingInputData.get_name();
        final String description = createListingInputData.get_description();
        final User user = userDataAccess.getCurrentLoggedInUser();

        if (name == null || name.isEmpty()) {
            createListingPresenter.prepareFailView("A listing with a null name");
        }
        else {
            final CreateListingOutputData createListingOutputData;
            final Listing listing;

            if (createListingInputData.get_categories().isEmpty()) {
                listing = new Listing(createListingInputData.get_name(), user);
                createListingOutputData = new CreateListingOutputData(
                        createListingInputData.get_name(),
                        user
                );
            }
            else {
                listing = new Listing(
                        createListingInputData.get_name(),
                        description,
                        createListingInputData.get_categories(),
                        user
                );
                createListingOutputData = new CreateListingOutputData(
                        createListingInputData.get_name(),
                        description,
                        createListingInputData.get_categories(),
                        user
                );
            }

            try {
                createListingDataAccessObject.save(listing);
                createListingPresenter.prepareSuccessView(createListingOutputData);
            }
            catch (CreateListingDataAccessObject.DuplicateListingException exception) {
                createListingPresenter.prepareFailView("You already have a listing with this name");
            }
        }
    }

    @Override
    public void switchToProfileView() {
        createListingPresenter.switchToProfileView();
    }
}
