package use_case.view_profile;

import entity.Listing;
import entity.User;
import java.util.List;

/**
 * The interactor for the View Profile use case
 *
 * <p>This class coordinates data flow between the controller, the data access layer,
 * and the presenter. It retrieves the currently logged-in user's profile information
 * and prepares an appropriate response for the presenter.</p>
 */
public class ViewProfileInteractor implements ViewProfileInputBoundary {

    private final ViewProfileUserDataAccessInterface userDataAccess;
    private final ViewProfileOutputBoundary presenter;

    /**
     * Constructs a ViewProfileInteractor.
     *
     * @param userDataAccess the data access interface for retrieving user and listing data
     * @param presenter      the output boundary responsible for preparing the view model
     */
    public ViewProfileInteractor(
            ViewProfileUserDataAccessInterface userDataAccess,
            ViewProfileOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the View Profile use case.
     *
     * <p>This method performs the following steps:</p>
     * <ol>
     *     <li>Fetches the current logged-in user.</li>
     *     <li>Validates that a user is logged in.</li>
     *     <li>Retrieves the user's listings from the data access layer.</li>
     *     <li>Constructs an output data object.</li>
     *     <li>Passes the output data to the presenter.</li>
     * </ol>
     *
     * @param inputData unused input data; kept for Clean Architecture consistency
     */
    @Override
    public void execute(ViewProfileInputData inputData) {

        User user = userDataAccess.getCurrentLoggedInUser();
        if (user == null) {
            presenter.prepareFailView("No user is currently logged in.");
            return;
        }


        List<Listing> listings = userDataAccess.getUserListings(user.get_username());
        user.set_listing(listings);



        ViewProfileOutputData outputData =
                new ViewProfileOutputData(user.get_username(), listings, user);

        presenter.prepareSuccessView(outputData);
    }
}