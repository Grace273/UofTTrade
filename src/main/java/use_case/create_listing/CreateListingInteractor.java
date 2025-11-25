package use_case.create_listing;

import entity.Category;
import entity.Listing;
import entity.User;
import use_case.view_profile.ViewProfileUserDataAccessInterface;

import java.io.IOException;
import java.util.List;

public class CreateListingInteractor implements CreateListingInputBoundary{
    private final CreateListingUserDataAccessInterface createListingDataAccessObject;
    private final CreateListingOutputBoundary createListingPresenter;
    private final ViewProfileUserDataAccessInterface userDataAccess;

    public CreateListingInteractor(CreateListingUserDataAccessInterface createListingDataAccess, CreateListingOutputBoundary createListingOutputBoundary, ViewProfileUserDataAccessInterface userDataAccess) {
        this.createListingDataAccessObject = createListingDataAccess;
        this.createListingPresenter = createListingOutputBoundary;
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void execute(CreateListingInputData createListingInputData) throws IOException {
        final String name = createListingInputData.get_name();
        final String photo = createListingInputData.get_img_in_Base64();
        final List<Category> categories = createListingInputData.get_categories();

        User user = userDataAccess.getCurrentLoggedInUser();
//        createListingInputData.set_owner_and_listingID(user);

//        if(createListingDataAccessObject.existsByListingID(createListingInputData.get_listing_id())){
//            createListingPresenter.prepareFailView("You already have a listing with this name");
//        }
        if(createListingInputData.get_name() == ""){
            createListingPresenter.prepareFailView("A listing with a null name");
        }
        else if(createListingInputData.get_img_in_Base64() == ""){
            createListingPresenter.prepareFailView("The listing image is empty");
        }
        else {
            final CreateListingOutputData createListingOutputData;
            final Listing listing;

            if (createListingInputData.get_categories().isEmpty()) {
                listing = new Listing(createListingInputData.get_name(), createListingInputData.get_img_in_Base64(), user);
                createListingOutputData = new CreateListingOutputData(
                        createListingInputData.get_name(),
                        createListingInputData.get_img_in_Base64(),
                        user
                );
            }
            else {
                listing = new Listing(
                        createListingInputData.get_name(),
                        createListingInputData.get_img_in_Base64(),
                        createListingInputData.get_categories(),
                        user
                );
                createListingOutputData = new CreateListingOutputData(
                        createListingInputData.get_name(),
                        createListingInputData.get_img_in_Base64(),
                        createListingInputData.get_categories(),
                        user
                );
            }

            createListingDataAccessObject.save(listing);
            createListingPresenter.prepareSuccessView(createListingOutputData);
        }
    }

    @Override
    public void switchToProfileView() {
        createListingPresenter.switchToProfileView();
    }
}
