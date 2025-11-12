package use_case.view_profile;

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

        String username = userDataAccess.getCurrentLoggedInUsername();


        List<String> listings = userDataAccess.getUserListings(username);


        ViewProfileOutputData outputData = new ViewProfileOutputData(username, listings);


        presenter.present(outputData);
    }
}