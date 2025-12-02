package use_case;

import data_access.CreateListingDAO;
import data_access.UpdateListingDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Category;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.create_listing.*;
import use_case.login.*;
import use_case.view_profile.ViewProfileUserDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateListingInteractorTest {

//    @Test
//    public void successTest() throws CreateListingDAO.DuplicateListingException, IOException {
//        //log the user in
//        LoginUserDataAccessInterface userDataAccessObject = new UserDataAccessObject();
//        LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData loginOutputData) {}
//
//            @Override
//            public void prepareFailView(String failMessage) {}
//
//            @Override
//            public void switchToRegisterView() {}
//        };
//
//        LoginInputData loginInputData = new LoginInputData("grace123", "gracepw");
//        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
//        loginInteractor.execute(loginInputData);
//
//        //create input data
//        User user = new User("grace123", "gracepw", "grace@gmail.com");
//
//        Category category1 = new Category("Clothing");
//        Category category2 = new Category("Select a Category");
//        List<Category> categories = new ArrayList<>();
//        categories.add(category1);
//        categories.add(category2);
//
//        CreateListingInputData inputData = new CreateListingInputData(
//                "Adidas Forum high",
//                "Size 7 US women's. Never worn",
//                categories
//        );
//
//        CreateListingUserDataAccessInterface listingDAO = new CreateListingDAO();
//        CreateListingOutputBoundary successPresenter = new CreateListingOutputBoundary() {
//            @Override
//            public void prepareSuccessView(CreateListingOutputData outputData) throws IOException {
//                // 2 things to check: the output data is correct, and the listing has been created in the DAO.
//                assertEquals("UofT shirt", outputData.getName());
//                assertEquals("Size medium", outputData.getDescription());
//                assertEquals(categories, outputData.getCategories());
//                //assertEquals(user, outputData.get_owner());
//
//                assertFalse(listingDAO.existById(outputData.getListingID()+""));
//
//                //delete listing so test doesn't have unexpected fail next time
//                UpdateListingDataAccessObject updateListingDAO= new UpdateListingDataAccessObject();
//                updateListingDAO.updateListing(outputData.getListingID());
//            }
//
//            @Override
//            public void prepareFailView(String errorMessage) { fail("Use case fail is unexpected."); }
//
//            @Override
//            public void switchToProfileView() {}
//        };
//
//        //execute
//        CreateListingInputBoundary interactor = new CreateListingInteractor(
//                listingDAO,
//                successPresenter,
//                (UserDataAccessObject)userDataAccessObject
//        );
//        interactor.execute(inputData);
//
//    }

    @Test
    public void failureListingExistsTest() throws CreateListingDAO.DuplicateListingException, IOException {
        //log the user in
        LoginUserDataAccessInterface userDataAccessObject = new UserDataAccessObject();
        LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData loginOutputData) {}

            @Override
            public void prepareFailView(String failMessage) {}

            @Override
            public void switchToRegisterView() {}
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
                "UofT shirt",
                "Size medium",
                categories
        );

        CreateListingUserDataAccessInterface listingDAO = new CreateListingDAO();
        CreateListingOutputBoundary successPresenter = new CreateListingOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateListingOutputData outputData) throws IOException {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You already have a listing with this name", errorMessage);
            }

            @Override
            public void switchToProfileView() {}
        };

        //execute
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                (UserDataAccessObject)userDataAccessObject
        );
        interactor.execute(inputData);
    }

    @Test
    public void nullListingNameTest() throws CreateListingDAO.DuplicateListingException, IOException {
        //log the user in
        LoginUserDataAccessInterface userDataAccessObject = new UserDataAccessObject();
        LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData loginOutputData) {}

            @Override
            public void prepareFailView(String failMessage) {}

            @Override
            public void switchToRegisterView() {}
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
                null,
                "Size medium",
                categories
        );

        CreateListingUserDataAccessInterface listingDAO = new CreateListingDAO();
        CreateListingOutputBoundary successPresenter = new CreateListingOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateListingOutputData outputData) throws IOException {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("A listing with a null name", errorMessage);
            }

            @Override
            public void switchToProfileView() {}
        };

        //execute
        CreateListingInputBoundary interactor = new CreateListingInteractor(
                listingDAO,
                successPresenter,
                (UserDataAccessObject)userDataAccessObject
        );
        interactor.execute(inputData);
    }
}
