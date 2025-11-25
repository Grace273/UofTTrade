package use_case.view_profile;

import entity.User;
import java.util.List;

public class ViewProfileInteractor implements ViewProfileInputBoundary {

    private final ViewProfileUserDataAccessInterface userDataAccess;
    private final ViewProfileOutputBoundary presenter;

    public ViewProfileInteractor(
            ViewProfileUserDataAccessInterface userDataAccess,
            ViewProfileOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewProfileInputData inputData) {

        // 1. Fetch the logged-in user
        User user = userDataAccess.getCurrentLoggedInUser();

        if (user == null) {
            presenter.prepareFailView("No user is currently logged in.");
            return;
        }

        // 2. The username comes from the User entity
        String username = user.get_username();

        // 3. Fetch the user's listings using their username
        List<String> listings = userDataAccess.getUserListings(username);

        // 4. Prepare output data to the presenter
        ViewProfileOutputData outputData =
                new ViewProfileOutputData(username, listings);

        presenter.prepareSuccessView(outputData);
    }
}

