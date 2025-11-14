package interface_adapter.create_listing;
import interface_adapter.ViewManagerModel;
import use_case.create_listing.CreateListingOutputBoundary;
import use_case.create_listing.CreateListingOutputData;
import view.CreateListingView;

public class CreateListingPresenter implements CreateListingOutputBoundary {

    private final CreateListingViewModel createListingViewModel;
    private final ViewManagerModel viewManagerModel;
//  private final ViewProfileViewModel viewProfileViewModel

    public CreateListingPresenter(CreateListingViewModel createListingViewModel, ViewManagerModel viewManagerModel) {
        this.createListingViewModel = createListingViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CreateListingOutputData outputData) {
        // on success, switch to the profile view

//        final ViewProfileState viewProfileState = viewProfileViewModel.getState();
//        loggedInState.setUsername(response.getUsername());
//        this.loggedInViewModel.setState(loggedInState);
//        this.loggedInViewModel.firePropertyChanged();
//
//        this.viewManagerModel.setState(loggedInViewModel.getViewName());
//        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        final CreateListingState createListingState = createListingViewModel.getState();
        createListingState.set_createListingError(errorMessage);
        createListingViewModel.firePropertyChanged();
    }
}
