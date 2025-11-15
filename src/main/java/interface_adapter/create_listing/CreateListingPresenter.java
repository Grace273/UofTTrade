package interface_adapter.create_listing;
import interface_adapter.ViewManagerModel;
import interface_adapter.view_profile.ViewProfileState;
import interface_adapter.view_profile.ViewProfileViewModel;
import use_case.create_listing.CreateListingOutputBoundary;
import use_case.create_listing.CreateListingOutputData;
import view.CreateListingView;

/**
 * The Presenter for the CreateListing Use Case.
 */
public class CreateListingPresenter implements CreateListingOutputBoundary {
    private final CreateListingViewModel createListingViewModel;
    private final ViewProfileViewModel viewProfileViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateListingPresenter(CreateListingViewModel createListingViewModel, ViewProfileViewModel viewProfileViewModel, ViewManagerModel viewManagerModel) {
        this.createListingViewModel = createListingViewModel;
        this.viewProfileViewModel = viewProfileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CreateListingOutputData response) {
        // On success, switch to the View Profile view.
        final ViewProfileState viewProfileState = viewProfileViewModel.getState();
        viewProfileState.setUsername(response.get_owner().get_username());
        this.viewProfileViewModel.setState(viewProfileState);
        viewProfileViewModel.firePropertyChanged();

        viewManagerModel.setState(viewProfileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final CreateListingState createListingState = createListingViewModel.getState();

        if (error == "Owner does not exist") {
            createListingState.set_owner_error(error);
        }
        else {
            createListingState.set_listingId_error(error);
        }

        createListingViewModel.firePropertyChanged();
    }

    @Override
    public void switchToProfileView() {
        viewManagerModel.setState(viewProfileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }


}
