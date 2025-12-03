package use_case.view_listing;

import data_access.UserDataAccessObject;
import entity.User;

import data_access.CreateListingDataAccessObject;
import interface_adapter.view_listing.ViewListingController;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import use_case.login.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewListingInteractorTest {


    @Test
    public void viewListingSuccessTest() throws IOException {
        ViewListingInputData inputData = new ViewListingInputData("Garbage Can", "TutorialBot77");
        UserDataAccessObject userRepository = new UserDataAccessObject() {
            @Override
            public User getUser(String userIdentifier) {
                return new User(userIdentifier, userIdentifier + "45", userIdentifier.toLowerCase()
                        + "@gmail.com");
            }
        };
        CreateListingDataAccessObject CreateListingDataAccessObject = new CreateListingDataAccessObject() {
            @Override
            public JSONObject getSpecificListingInfo(String listingName, String listingOwner) {
                final JSONObject listingInfo = new JSONObject();
                final List<String> categoryList = new ArrayList<String>();
                categoryList.add("Category1");
                categoryList.add("Category2");
                listingInfo.put("Name", listingName);
                listingInfo.put("Owner", listingOwner);
                listingInfo.put("Description", "The Garbage Can");
                listingInfo.put("Categories", categoryList);
                listingInfo.put("ListingID", 1);
                return listingInfo;
            }
        };
        User user = userRepository.getUser("TutorialBot77");
        final List<String> categoryList = new ArrayList<String>();
        categoryList.add("Category1");
        categoryList.add("Category2");

        ViewListingOutputBoundary successPresenter = new ViewListingOutputBoundary() {

            @Override
            public void switchToListingView(ViewListingOutputData data) {
                assertEquals("Garbage Can", data.getListingName());
                assertEquals("TutorialBot77", data.getListingOwner());
                assertEquals(user.get_email(), data.getListingOwnerEamil());
                assertEquals("The Garbage Can", data.getListingDescription());
                assertEquals(categoryList, data.getListingCategories());
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

        ViewListingInputBoundary interactor = new ViewListingInteractor(CreateListingDataAccessObject, userRepository,
                successPresenter);
        interactor.execute(inputData);


    }

    @Test
    public void viewListingFailListingDoesntExist() throws IOException {
        UserDataAccessObject userRepository = new UserDataAccessObject();
        CreateListingDataAccessObject CreateListingDataAccessObject = new CreateListingDataAccessObject() {
            @Override
            public JSONObject getSpecificListingInfo(String listingName, String listingOwner) {
                return null;
            }
        };
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

        ViewListingInputBoundary interactor = new ViewListingInteractor(CreateListingDataAccessObject, userRepository,
                successPresenter);
        interactor.execute(inputData);

    }

    @Test
    public void testSwitchToPreviousView() throws IOException {
        ViewListingInputData inputData = new ViewListingInputData("Garbage Can", "TutorialBot77");
        UserDataAccessObject userRepository = new UserDataAccessObject() {
            @Override
            public User getUser(String userIdentifier) {
                return new User(userIdentifier, userIdentifier + "45", userIdentifier.toLowerCase()
                        + "@gmail.com");
            }
        };
        CreateListingDataAccessObject CreateListingDataAccessObject = new CreateListingDataAccessObject() {
            @Override
            public JSONObject getSpecificListingInfo(String listingName, String listingOwner) {
                final JSONObject listingInfo = new JSONObject();
                final List<String> categoryList = new ArrayList<String>();
                categoryList.add("Category1");
                categoryList.add("Category2");
                listingInfo.put("Name", listingName);
                listingInfo.put("Owner", listingOwner);
                listingInfo.put("Description", "The Garbage Can");
                listingInfo.put("Categories", categoryList);
                listingInfo.put("ListingID", 1);
                return listingInfo;
            }
        };
        User user = userRepository.getUser("TutorialBot77");

        ViewListingOutputBoundary successPresenter = new ViewListingOutputBoundary() {

            @Override
            public void switchToListingView(ViewListingOutputData data) {
                switchToPreviousView();
            }

            @Override
            public void switchToPreviousView() {

                assertTrue(true, "Previous view is reached from the listing view.");
            }

            @Override
            public void prepareFailView() {
                fail("Use case failure is unexpected.");
            }
        };

        ViewListingInputBoundary interactor = new ViewListingInteractor(CreateListingDataAccessObject, userRepository,
                successPresenter);
        interactor.execute(inputData);
        ViewListingController viewListingController = new ViewListingController(interactor);
        // Simulates back button press
        viewListingController.switchToPreviousView();
    }

}
