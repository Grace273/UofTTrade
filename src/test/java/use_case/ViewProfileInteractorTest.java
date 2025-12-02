package use_case;

import use_case.view_profile.*;
import entity.Listing;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewProfileInteractorTest {

    private MockUserDataAccess mockDAO;
    private MockPresenter mockPresenter;
    private ViewProfileInteractor interactor;

    @BeforeEach
    void setup() {
        mockDAO = new MockUserDataAccess();
        mockPresenter = new MockPresenter();
        interactor = new ViewProfileInteractor(mockDAO, mockPresenter);
    }

    @Test
    void testExecute_NoUserLoggedIn() {
        mockDAO.currentUser = null;

        interactor.execute(new ViewProfileInputData("ignored"));

        assertTrue(mockPresenter.failCalled);
        assertEquals("No user is currently logged in.", mockPresenter.errorMessage);
        assertFalse(mockPresenter.successCalled);
    }

    @Test
    void testExecute_UserLoggedIn_Success() {
        User user = new User("Alice", "pw", "a@email.com");
        mockDAO.currentUser = user;

        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing("Item1", user));
        mockDAO.listingsToReturn = listings;

        interactor.execute(new ViewProfileInputData("ignored"));

        assertTrue(mockPresenter.successCalled);
        assertNotNull(mockPresenter.receivedOutput);
        assertEquals(listings, mockPresenter.receivedOutput.getListings());
        assertEquals(listings, user.get_listings());
    }

    // ------------ Mock DAO ------------
    private static class MockUserDataAccess implements ViewProfileUserDataAccessInterface {
        User currentUser;
        List<Listing> listingsToReturn;

        @Override
        public User getCurrentLoggedInUser() {
            return currentUser;
        }

        @Override
        public List<Listing> getUserListings(String username) {
            return listingsToReturn;
        }
    }

    // ------------ Mock Presenter ------------
    private static class MockPresenter implements ViewProfileOutputBoundary {

        boolean successCalled = false;
        boolean failCalled = false;

        ViewProfileOutputData receivedOutput;
        String errorMessage;

        @Override
        public void prepareSuccessView(ViewProfileOutputData data) {
            successCalled = true;
            receivedOutput = data;
        }

        @Override
        public void prepareFailView(String error) {
            failCalled = true;
            errorMessage = error;
        }
    }
}
