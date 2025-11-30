package interface_adapter.create_listing;
import interface_adapter.ViewManagerModel;
import interface_adapter.view_profile.ViewProfileState;
import interface_adapter.view_profile.ViewProfileViewModel;
import use_case.create_listing.CreateListingOutputBoundary;
import use_case.create_listing.CreateListingOutputData;

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
        CreateListingState createState = createListingViewModel.getState();
        createState.set_name_error(null);
        createState.set_successMessage("Listing published successfully!");

        createListingViewModel.setState(createState);
        createListingViewModel.firePropertyChanged();

        // On success, switch to the View Profile view.
        final ViewProfileState viewProfileState = viewProfileViewModel.getState();
        viewProfileState.setUsername(response.get_owner().get_username());
        viewProfileViewModel.firePropertyChanged();

        viewManagerModel.setState(viewProfileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final CreateListingState createListingState = createListingViewModel.getState();
        createListingState.set_name_error(error);
        createListingState.set_successMessage(null);
        createListingViewModel.setState(createListingState);
        createListingViewModel.firePropertyChanged();
    }

    @Override
    public void switchToProfileView() {
        viewManagerModel.setState(viewProfileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
