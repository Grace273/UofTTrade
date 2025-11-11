package use_case.view_profile;
import entity.Listing;
import entity.User;

import java.util.List;

public class ViewProfileInteractor implements ViewProfileInputBoundary {
    private final ViewProfileUserDataAccessInterface userDataAccess;
    private final ViewProfileOutputBoundary presenter;

    public ViewProfileInteractor(ViewProfileUserDataAccessInterface userDataAccess,
                                 ViewProfileOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewProfileInputData inputData) {

        // 1. Get the user who is viewing the profile
        User user = userDataAccess.getUserByUsername(inputData.getUsername());

        // 2. Convert domain Listing objects into simple displayable strings
        List<String> listingNames = user.get_listings().stream()
                .map(Listing::get_name)
                .toList();

        // 3. Prepare the output data to send to the UI layer
        ViewProfileOutputData outputData =
                new ViewProfileOutputData(user.get_username(), listingNames);

        // 4. Pass that result to the presenter to display the profile screen
        presenter.present(outputData);
    }
}
