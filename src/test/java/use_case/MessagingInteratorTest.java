package use_case;

import data_access.UserDataAccessObject;
import entity.Messaging;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.messaging.*;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class MessagingInteratorTest {
    // get the email from the database and verify that Presenter get the email successfully.
    @Test

    public void successUseEmailFromDatabase() throws IOException {
        // A successful test when the seller implements a correct email address.
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
        // Get an existed username from the database
        User user = userDataAccessObject.getUser("Kael");
        assertNotNull(user, "There is no user called Kael");
        // Check if the user has a correct email
        String username = user.get_username();
        String email = user.get_email();
        assertNotNull(email, "This guy should have an email");

        MessagingInputData inputData = new MessagingInputData(username, email);
        MessagingOutputBoundary presenter = new MessagingOutputBoundary() {
            @Override
            public void presentSuccessView(MessagingOutputData data) {
                // It supposed to be True
                assertEquals(username, data.getName());
                String url = data.getNormalizedurl();
                assertNotNull(url);
                assertTrue(url.startsWith("https://mail.google.com/"), "url should be Gmail compose address");
                String encodeEmail = email.replace("@", "%40");
                assertTrue(url.contains(encodeEmail), "url should be Gmail compose address");
            }

            @Override
            public void presentFailureView(String errorMessage) {
                // Invalid path as the username and email both occurred correctly, so a successful view should show up.
                fail("it is unexpectedly present as the email is correct.");

            }

        };
        MessagingInputBoundary interactor = new MessagingInteractor(userDataAccessObject, presenter);
        interactor.execute(inputData);
    }
    public  void invalidEmailFromDatabase() throws IOException {
        // When the user types an incorrect email
        UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
        MessagingInputData inputData = new MessagingInputData("Kael", "123@!");
        MessagingOutputBoundary presenter = new MessagingOutputBoundary() {
            @Override
            public void presentSuccessView(MessagingOutputData data) {
                // Unlikely to happen as the email is invalid
                fail("It is unexpectedly present as the email address is invalid");
            }
            @Override
            public void presentFailureView(String errorMessage) {
                // The isPlasuibleEmail method should return False, so return early.
                assertNotNull(errorMessage);
                assertFalse(errorMessage.isEmpty());
            }
        };
        MessagingInputBoundary interactor = new MessagingInteractor(userDataAccessObject, presenter);
        interactor.execute(inputData);
    }

    class StubMessagingDao implements  MessagingUserDataAccessInterface{
        // A Stub DAO class for a null or empty email
        boolean getCalled = false;
        @Override
        public String getValidEmailForUser(String username) {
            getCalled = true;
            return "stub@mail.com";
        }
        @Override
        public boolean isPlasuibleEmail(String email) {
            return true;

        }
    }

    @Test
    public void nullEmailFromDAO_success() {
        // When the inputdata.getEmail() is null or empty, trying to get the email through DAO.
        StubMessagingDao stubMessagingDao = new StubMessagingDao();
        MessagingInputData inputData = new MessagingInputData("Kael", null);
        MessagingOutputBoundary presenter = new MessagingOutputBoundary() {
            @Override
            public void presentSuccessView(MessagingOutputData data) {
                // the Input email DNE, but through the DAO, we get the email directly, so it will call DAO and success.
                assertEquals("Kael", data.getName());
                assertTrue(stubMessagingDao.getCalled,"must implement getValidEmailForUser");
                assertTrue(data.getNormalizedurl().contains("stub%40mail.com"));
            }
            @Override
            public void presentFailureView(String errorMessage) {
                // Unable to happen
                fail("No email return from DAO");
            }
        };
        MessagingInputBoundary interactor = new MessagingInteractor(stubMessagingDao, presenter);
        interactor.execute(inputData);
    }
    class StubMessagingDAO_NullEmail implements  MessagingUserDataAccessInterface{
        //No email found in both input and DAO
        @Override
        public String getValidEmailForUser(String username) {
            return null;
        }
        @Override
        public boolean isPlasuibleEmail(String email) {
            return false;
        }
    }
    @Test
    public void nullEmailFromDAO_fail() {
        StubMessagingDAO_NullEmail DAO = new StubMessagingDAO_NullEmail();
        MessagingInputData inputData = new MessagingInputData("Kael", null);
        MessagingOutputBoundary presenter = new MessagingOutputBoundary() {
            @Override
            public void presentSuccessView(MessagingOutputData data) {
                // No email from both path
                fail("it is unexpectedly present as there is no email");
            }
            @Override
            public void presentFailureView(String errorMessage) {
                // Check that it pass the input data and DAO doesn't have either.
                assertNotNull(errorMessage);
                assertTrue(errorMessage.contains("Invalid email address for user: Kael"));
            }
        };
        MessagingInputBoundary interactor = new MessagingInteractor(DAO, presenter);
        interactor.execute(inputData);
    }

    class StubMessagingDAO_ExceptionEmail implements  MessagingUserDataAccessInterface{
        // If there is an exception occurred
        @Override
        public String getValidEmailForUser(String username) {
            throw new RuntimeException("RuntimeException");
        }
        @Override
        public boolean isPlasuibleEmail(String email) {
            return true;
        }
    }
    @Test
    public void DAOThrowsException(){
        StubMessagingDAO_ExceptionEmail DAO = new StubMessagingDAO_ExceptionEmail();
        MessagingInputData inputData = new MessagingInputData("Kael", null);
        MessagingOutputBoundary presenter = new MessagingOutputBoundary() {
            @Override
            public void presentSuccessView(MessagingOutputData data) {
                // Exception, how could it be to success?
                fail("it is unexpectedly present as it is exception");
            }
            @Override
            public void presentFailureView(String errorMessage) {
                // Check Exception occurs
                assertNotNull(errorMessage);
                assertTrue(errorMessage.contains("Failed to create Gmail link"));
            }
        };
        MessagingInputBoundary interactor = new MessagingInteractor(DAO, presenter);
        interactor.execute(inputData);
    }
}