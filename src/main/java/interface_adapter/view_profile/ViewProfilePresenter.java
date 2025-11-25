package interface_adapter.view_profile;

import use_case.view_profile.ViewProfileOutputBoundary;
import use_case.view_profile.ViewProfileOutputData;

import java.util.List;

/**
 * Presenter for the View Profile use case.
 * Updates the ViewModel's state after success or failure and notifies the view.
 */
public class ViewProfilePresenter implements ViewProfileOutputBoundary {

    private final ViewProfileViewModel viewModel;

    public ViewProfilePresenter(ViewProfileViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewProfileOutputData outputData) {
        ViewProfileState state = viewModel.getState();

        state.setUsername(outputData.getUsername());
        state.setListingNames(outputData.getListingNames());

        // Set dynamic title text
        state.setTitleText("Profile: " + outputData.getUsername());

        // Clear previous error
        state.setErrorMessage("");

        // NEW: Show "No Listings yet..." when listingNames is empty
        if (outputData.getListingNames().isEmpty()) {
            state.setNoListingsMessage("No listings yet...");
        } else {
            state.setNoListingsMessage(""); // clear message when listings exist
        }

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ViewProfileState state = viewModel.getState();

        // Set error
        state.setErrorMessage(errorMessage);

        // Clear fields that shouldn’t show on failure
        state.setUsername("");
        state.setListingNames(List.of());
        state.setTitleText("");
        state.setNoListingsMessage("");  // ensure this stays empty on failure

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
