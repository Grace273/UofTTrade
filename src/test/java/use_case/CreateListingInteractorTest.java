package use_case;

import data_access.CreateListingDataAccessObject;
import data_access.UpdateListingDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Category;
import entity.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import use_case.create_listing.*;
import use_case.login.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 3 tests for the CreateListing usecase.
 * WARNING: Because of the API rate limit, you need to only run one test at a time (comment the other ones out)
 * or else only the first test will succeed and the rest will fail with
 * "A JSONObject text must begin with '{' at 1 [character 2 line 1]"
 */
class CreateListingInteractorTest {

    @Test
    void successTest() throws CreateListingDataAccessObject.DuplicateListingException, IOException {
        //log the user in
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
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

        CreateListingUserDataAccessInterface listingDAO = new CreateListingDataAccessObject();
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
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                userDataAccessObject
        );
        interactor.execute(inputData);

        //delete listing so test doesn't have unexpected fail next time
        UpdateListingDataAccessObject updateDAO = new UpdateListingDataAccessObject();
        updateDAO.updateListing(successPresenter.listingId);
    }

    @Test
     void failureListingExistsTest() throws CreateListingDataAccessObject.DuplicateListingException, IOException {
        //log the user in
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
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

        CreateListingUserDataAccessInterface listingDAO = new CreateListingDataAccessObject();
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

        //execute
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                userDataAccessObject
        );
        interactor.execute(inputData);
    }

    @Test
     void nullListingNameTest() throws CreateListingDataAccessObject.DuplicateListingException, IOException {
        //log the user in
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
        CreateListingInputData inputData = getCreateListingInputData(userDataAccessObject);

        CreateListingUserDataAccessInterface listingDAO = new CreateListingDataAccessObject();
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

        //execute
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                userDataAccessObject
        );
        interactor.execute(inputData);
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
}
