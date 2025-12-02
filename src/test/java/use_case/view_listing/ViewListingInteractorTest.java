package use_case.view_listing;

import data_access.CreateListingDataAccessObject;
import data_access.UserDataAccessObject;
import entity.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ViewListingInteractorTest {

    // for multiple tests you will have to change the name of the item in the listing constructor
    @Test
    public void viewListingSuccessTest() throws IOException {
        ViewListingInputData inputData = new ViewListingInputData("Garbage Can", "TutorialBot77");
        UserDataAccessObject userRepository = new UserDataAccessObject();
        CreateListingDataAccessObject createListingDAO = new CreateListingDataAccessObject();
        User user = userRepository.getUser("TutorialBot77");

        ViewListingOutputBoundary successPresenter = new ViewListingOutputBoundary() {

            @Override
            public void switchToListingView(ViewListingOutputData data) {
                assertEquals("Garbage Can", data.getListingName());
                assertEquals("TutorialBot77", data.getListingOwner());
                assertEquals(user.get_email(), data.getListingOwnerEamil());
                assertEquals("The Garbage Can", data.getListingDescription());
            }

            @Override
            public void switchToPreviousView() {
                fail("Switching to another view is unexpected.");
            }

            @Override
            public void prepareFailView() {
                fail("Use case failure is unexpected.");
            }
        };

        ViewListingInputBoundary interactor = new ViewListingInteractor(createListingDAO, userRepository,
                successPresenter);
        interactor.execute(inputData);


    }

    @Test
    public void viewListingFailListingDoesntExist() throws IOException {
        UserDataAccessObject userRepository = new UserDataAccessObject();
        CreateListingDataAccessObject createListingDAO = new CreateListingDataAccessObject();
        ViewListingInputData inputData = new ViewListingInputData("Mouse and Keyboard", "TutorialBot77");

        ViewListingOutputBoundary successPresenter = new ViewListingOutputBoundary() {

            @Override
            public void switchToListingView(ViewListingOutputData data) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView() {
                assertTrue(true, "Listing was not found leading to message dialog in view.");
            }

            @Override
            public void switchToPreviousView() {
                fail("Switching views is unexpected.");
            }
        };

        ViewListingInputBoundary interactor = new ViewListingInteractor(createListingDAO, userRepository,
                successPresenter);
        interactor.execute(inputData);

    }



}
