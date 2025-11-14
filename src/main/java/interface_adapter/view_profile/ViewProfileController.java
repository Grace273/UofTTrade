package interface_adapter.view_profile;

import use_case.view_profile.ViewProfileInputBoundary;
import use_case.view_profile.ViewProfileInputData;

public class ViewProfileController {

    private final ViewProfileInputBoundary interactor;

    public ViewProfileController(ViewProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onProfileButtonClicked() {
        ViewProfileInputData inputData = new ViewProfileInputData();
        interactor.execute(inputData);
    }
}