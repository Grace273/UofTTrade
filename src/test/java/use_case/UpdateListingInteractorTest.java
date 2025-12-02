package use_case;

import entity.Category;
import entity.Listing;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.update_listing.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UpdateListingInteractor}.
 */
class UpdateListingInteractorTest {

    /**
     * Simple in-memory implementation of the data access interface
     * so we can see which listing ID was updated.
     */
    private static class InMemoryUpdateListingUserDAO
            implements UpdateListingUserDataAccessInterface {

        int lastUpdatedListingId = -1;
        int updateCallCount = 0;

        @Override
        public void updateListing(int listingId) {
            lastUpdatedListingId = listingId;
            updateCallCount += 1;
        }
    }

    @Test
    void deleteListingSuccessTest() {
        // Arrange: create user, listing and input data.
        User user = new User("tina", "123","tinagao426@gmail.com");
        List<Category> categories = new ArrayList<>();
        Category furniture = new Category("furniture");
        categories.add(furniture);
        Listing listing = new Listing("table", "brand new table", categories , user);
        List<Listing> listings = new ArrayList<>();
        listings.add(listing);
        user.set_listing(listings);

        UpdateListingInputData inputData =
                new UpdateListingInputData(true, user, listing);

        InMemoryUpdateListingUserDAO userRepository =
                new InMemoryUpdateListingUserDAO();

        // Presenter that checks the success case.
        UpdateListingOutputBoundary successPresenter = new UpdateListingOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateListingOutputData outputData) {
                // Check that the user in the output is the same user.
                assertSame(user, outputData.getUser());
                // Check that the DAO was called with the correct ID.
                assertEquals(listing.get_listingId(), userRepository.lastUpdatedListingId);
                assertEquals(1, userRepository.updateCallCount);
                // verify that the listing is no longer with the user:
                assertFalse(user.get_listings().contains(listing));
            }
        };

        UpdateListingInputBoundary interactor =
                new UpdateListingInteractor(userRepository, successPresenter);

        // Act
        interactor.execute(inputData);
    }
}
