package use_case.create_listing;

import entity.Category;
import entity.Listing;
import entity.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import use_case.login.*;
import use_case.view_profile.ViewProfileUserDataAccessInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CreateListingInteractorTest {
    @Test
    void successTest() throws IOException {
        //log the user in
        LoginUserDataAccessInterface userDataAccessObject = new FakeLoginUserDataAccessObject();
        LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData loginOutputData) {
                //this is expected
            }

            @Override
            public void prepareFailView(String failMessage) {
                //this is expected
            }

            @Override
            public void switchToRegisterView() {
                //this is expected
            }
        };

        LoginInputData loginInputData = new LoginInputData("grace123", "gracepw");
        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
        loginInteractor.execute(loginInputData);

        //create input data
        User user = new User("grace123", "gracepw", "grace@gmail.com");

        Category category1 = new Category("Clothing");
        Category category2 = new Category("Select a Category");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        CreateListingInputData inputData = new CreateListingInputData(
                "item4",
                "descriptionnn",
                categories
        );

        CreateListingUserDataAccessInterface listingDAO = new FakeCreateListingUserDataAccessObject();
        var successPresenter = new CreateListingOutputBoundary() {
            int listingId;

            @Override
            public void prepareSuccessView(CreateListingOutputData outputData) throws IOException {
                // 2 things to check: the output data is correct, and the listing has been created in the DAO.
                assertEquals("item4", outputData.getName());
                assertEquals("descriptionnn", outputData.getDescription());
                assertEquals(categories, outputData.getCategories());
                assertEquals(user.get_username().trim(), outputData.getOwner().get_username().trim());

                listingId = outputData.getListingID();

                assertTrue(listingDAO.existById(listingId+""));
            }

            @Override
            public void prepareFailView(String errorMessage) { fail(errorMessage); }

            @Override
            public void switchToProfileView() {
                //this is expected
            }

        };

        //execute
        ViewProfileUserDataAccessInterface viewDAO = new FakeViewProfileUserDataAccess();
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                viewDAO
        );
        interactor.execute(inputData);
    }

    @Test
     void failureListingExistsTest() throws IOException {
        //log the user in
        LoginUserDataAccessInterface userDataAccessObject = new FakeLoginUserDataAccessObject();
        LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData loginOutputData) {
                //this is expected
            }

            @Override
            public void prepareFailView(String failMessage) {
                //this is expected
            }

            @Override
            public void switchToRegisterView() {
                //this is expected
            }
        };

        LoginInputData loginInputData = new LoginInputData("grace123", "gracepw");
        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
        loginInteractor.execute(loginInputData);

        //create input data
        Category category1 = new Category("Clothing");
        Category category2 = new Category("Select a Category");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        CreateListingInputData inputData = new CreateListingInputData(
                "UofT shirt",
                "Size medium",
                categories
        );

        CreateListingUserDataAccessInterface listingDAO = new FakeCreateListingUserDataAccessObject();
        CreateListingOutputBoundary successPresenter = new CreateListingOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateListingOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You already have a listing with this name", errorMessage);
            }

            @Override
            public void switchToProfileView() {
                //this is expected
            }
        };
        ViewProfileUserDataAccessInterface viewProfileDAO = new FakeViewProfileUserDataAccess();
        //execute
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                viewProfileDAO
        );
        interactor.execute(inputData);
    }

    @Test
     void nullListingNameTest() throws IOException {
        //log the user in
        LoginUserDataAccessInterface userDataAccessObject = new FakeLoginUserDataAccessObject();
        CreateListingInputData inputData = getCreateListingInputData(userDataAccessObject);

        CreateListingUserDataAccessInterface listingDAO = new FakeCreateListingUserDataAccessObject();
        CreateListingOutputBoundary successPresenter = new CreateListingOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateListingOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("A listing with a null name", errorMessage);
            }

            @Override
            public void switchToProfileView() {
                //this is expected
            }
        };

        ViewProfileUserDataAccessInterface viewProfileDAO = new FakeViewProfileUserDataAccess();
        //execute
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                viewProfileDAO
        );
        interactor.execute(inputData);
    }

    @Test
    void createListingWithNoCategoriesTest() throws IOException {
        // Logged in user
        ViewProfileUserDataAccessInterface viewDAO = new FakeViewProfileUserDataAccess();
        CreateListingUserDataAccessInterface listingDAO = new FakeCreateListingUserDataAccessObject();

        CreateListingInputData inputData = new CreateListingInputData(
                "NoCategoryItem",
                "desc ignored",
                new ArrayList<>()      // ← EMPTY categories
        );

        var presenter = new CreateListingOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateListingOutputData outputData) throws IOException {
                assertEquals("NoCategoryItem", outputData.getName());
                assertNull(outputData.getDescription());   // expected for this constructor
                assertTrue(outputData.getCategories().isEmpty());
                assertEquals("grace123", outputData.getOwner().get_username());

                assertTrue(listingDAO.existById(outputData.getListingID()+""));
            }

            @Override public void prepareFailView(String errorMessage) { fail(); }
            @Override public void switchToProfileView() {
                // this is expected, switchToProfileViewTest also tests this
            }
        };

        CreateListingInputBoundary interactor =
                new CreateListingInteractor(listingDAO, presenter, viewDAO);

        interactor.execute(inputData);
    }


    @Test
    void switchToProfileViewTest() {
        ViewProfileUserDataAccessInterface viewDAO = new FakeViewProfileUserDataAccess();
        CreateListingUserDataAccessInterface listingDAO = new FakeCreateListingUserDataAccessObject();

        class TestPresenter implements CreateListingOutputBoundary {
            boolean called = false;

            @Override public void prepareSuccessView(CreateListingOutputData o) {
                // this is expected
            }
            @Override public void prepareFailView(String errorMessage) {
                // this is expected
            }
            @Override public void switchToProfileView() { called = true; }
        }

        TestPresenter presenter = new TestPresenter();

        CreateListingInputBoundary interactor =
                new CreateListingInteractor(listingDAO, presenter, viewDAO);

        interactor.switchToProfileView();

        assertTrue(presenter.called);
    }

    @NotNull
    private CreateListingInputData getCreateListingInputData(
            LoginUserDataAccessInterface userDataAccessObject) throws IOException {
        LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData loginOutputData) {
                //this is expected
            }

            @Override
            public void prepareFailView(String failMessage) {
                //this is expected
            }

            @Override
            public void switchToRegisterView() {
                //this is expected
            }
        };

        LoginInputData loginInputData = new LoginInputData("grace123", "gracepw");
        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
        loginInteractor.execute(loginInputData);

        //create input data
        Category category1 = new Category("Clothing");
        Category category2 = new Category("Select a Category");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        return new CreateListingInputData(
                null,
                "Size medium",
                categories
        );
    }

    public static class FakeLoginUserDataAccessObject implements LoginUserDataAccessInterface {
        @Override
        public boolean userExists(String userIdentifier) {
            return false;
        }

        @Override
        public User getUser(String userIdentifier) {
            return null;
        }

        @Override
        public void save(User user) {
            // nothing to add since login not tested
        }

        @Override
        public void setUsername(String username) {
            // nothing to add since not tested
        }

        @Override
        public void setEmail(String email) {
            // nothing to add since login not tested
        }

        @Override
        public String getUsername() {
            return "";
        }

        @Override
        public String getEmail() {
            return "";
        }

        @Override
        public void setCurrentLoggedInUser(User user) {
            // nothing to add since login not tested
        }
    }

    public static class FakeViewProfileUserDataAccess implements ViewProfileUserDataAccessInterface {
        private final User currentUser = new User("grace123", "gracepw", "grace@gmail.com");

        @Override
        public User getCurrentLoggedInUser() {
            return currentUser;
        }

        @Override
        public List<Listing> getUserListings(String username) {
            return List.of();
        }
    }

    public static class FakeCreateListingUserDataAccessObject implements CreateListingUserDataAccessInterface {
        private final Map<String, Listing> listings = new HashMap<>();

        public FakeCreateListingUserDataAccessObject() {
            //create mock data in listings
            Category category1 = new Category("Clothing");
            Category category2 = new Category("Select a Category");
            List<Category> categories = new ArrayList<>();
            categories.add(category1);
            categories.add(category2);

            User owner = new User("grace123", "gracepw", "grace@gmail.com");

            Listing listing = new Listing("UofT shirt", "Size medium", categories, owner);

            listings.put("1740228664", listing);
        }

        @Override
        public void save(Listing listing) throws DuplicateListingException {
            if (listings.containsKey(listing.get_listingId()+"")) {
                throw new CreateListingUserDataAccessInterface.DuplicateListingException(listing.get_listingId()+"");
            }

            listings.put(generateListingId(listing.get_owner().get_username(), listing.get_name())+"", listing);
        }

        @Override
        public boolean existById(String listingID) {
            return listings.containsKey(listingID);
        }

        /**
         * Generates a unique ID for the Listing. Helper to the constructor.
         * @return the generated ID
         */

        private int generateListingId(String ownerUsername, String name) {
            return ownerUsername.hashCode() + name.hashCode();
        }
    }

}
