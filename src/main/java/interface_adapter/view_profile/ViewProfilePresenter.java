package interface_adapter.view_profile;

import use_case.view_profile.ViewProfileOutputBoundary;
import use_case.view_profile.ViewProfileOutputData;

public class ViewProfilePresenter implements ViewProfileOutputBoundary {

    private final ViewProfileViewModel viewModel;

    public ViewProfilePresenter(ViewProfileViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewProfileOutputData outputData) {
        ViewProfileState state = viewModel.getState();

        state.setUsername(outputData.getUsername());
        state.setListingNames(outputData.getListingNames());

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}