package interface_adapter.update_listing;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_profile.ViewProfileState;
import interface_adapter.view_profile.ViewProfileViewModel;
import use_case.update_listing.UpdateListingOutputBoundary;
import use_case.update_listing.UpdateListingOutputData;

public class UpdateListingPresenter implements UpdateListingOutputBoundary {

    private final ViewProfileViewModel viewProfileViewModel;
    private final ViewManagerModel viewManagerModel;

    public UpdateListingPresenter(ViewProfileViewModel viewProfileViewModel,
                                  ViewManagerModel viewManagerModel) {
        this.viewProfileViewModel = viewProfileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(UpdateListingOutputData outputData) {
        // Update the profile state with the current user
        final ViewProfileState state = viewProfileViewModel.getState();
        state.setUsername(outputData.getUser().get_username());

        // Notify the profile view that its state changed
        viewProfileViewModel.firePropertyChanged();

        // Stay on profile view
        viewManagerModel.setState(viewProfileViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
