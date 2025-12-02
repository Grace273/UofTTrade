package use_case;

import entity.Category;
import entity.Listing;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.search.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchListingsInteractorTest {

    @Test
    public void successTest() {
        // 1. Arrange: Create a mock DAO with some sample data
        SearchListingsDataAccessInterface searchDAO = new SearchListingsDataAccessInterface() {
            @Override
            public List<Listing> findByKeywordAndCategory(String keyword, String categoryName) {
                return new ArrayList<>();
            }

            @Override
            public List<Listing> findByKeyword(String keyword) {
                List<Listing> listings = new ArrayList<>();
                // Simulate finding a listing if the keyword is "textbook"
                if ("textbook".equalsIgnoreCase(keyword)) {
                    User owner = new User("Junzz", "20060627", "junhuamath@gmail.com");
                    List<Category> categories = new ArrayList<>();
                    categories.add(new Category("Textbooks"));
                    listings.add(new Listing("CSC207 Textbook", "A made up textbook as no textbook exist", categories, owner));
                }
                return listings;
            }

            @Override
            public List<Listing> findByCategory(String categoryName) {
                return new ArrayList<>();
            }

            @Override
            public List<String> getAllCategories() {
                return List.of("Textbooks", "Electronics");
            }
        };

        // Create a presenter that asserts success
        SearchListingsOutputBoundary searchPresenter = new SearchListingsOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchListingsOutputData outputData) {
                // Assert that we found results
                assertFalse(outputData.getResults().isEmpty());
                assertEquals("CSC207 Textbook", outputData.getResults().get(0).getName());
                assertEquals("textbook", outputData.getKeyword());
                assertFalse(outputData.isFallbackResults());
            }

            @Override
            public void prepareFailView(String errorMessage, String keyword, String categoryName) {
                fail("Use case fail is unexpected.");
            }
        };

        // 2. Act: Execute the interactor
        SearchListingsInteractor interactor = new SearchListingsInteractor(searchDAO, searchPresenter);
        SearchListingsInputData inputData = new SearchListingsInputData("textbook", "Select a Category");
        interactor.execute(inputData);
    }

    @Test
    public void fallbackToCategoryTest() {
        // finds nothing for keyword "missing", but finds items for category "Electronics"
        SearchListingsDataAccessInterface searchDAO = new SearchListingsDataAccessInterface() {
            @Override
            public List<Listing> findByKeywordAndCategory(String keyword, String categoryName) {
                return new ArrayList<>();
            }

            @Override
            public List<Listing> findByKeyword(String keyword) {
                // Return empty list to trigger fallback
                return new ArrayList<>();
            }

            @Override
            public List<Listing> findByCategory(String categoryName) {
                List<Listing> listings = new ArrayList<>();
                if ("Electronics".equals(categoryName)) {
                    User owner = new User("Junzz", "20060627", "junhuamath@mail.com");
                    List<Category> categories = new ArrayList<>();
                    categories.add(new Category("Electronics"));
                    listings.add(new Listing("Old iPhone", "Works fine with 82 percent battery health", categories, owner));
                }
                return listings;
            }

            @Override
            public List<String> getAllCategories() {
                return List.of("Textbooks", "Electronics");
            }
        };

        // Presenter checks for fallback flag
        SearchListingsOutputBoundary searchPresenter = new SearchListingsOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchListingsOutputData outputData) {
                assertFalse(outputData.getResults().isEmpty());
                assertEquals("Old iPhone", outputData.getResults().get(0).getName());
                //Check that fallback is true because keyword search failed
                assertTrue(outputData.isFallbackResults());
                assertEquals("Electronics", outputData.getCategoryName());
            }

            @Override
            public void prepareFailView(String errorMessage, String keyword, String categoryName) {
                fail("Use case fail is unexpected.");
            }
        };

        SearchListingsInteractor interactor = new SearchListingsInteractor(searchDAO, searchPresenter);
        // "missing" keyword should fail, forcing fallback to "Electronics"
        SearchListingsInputData inputData = new SearchListingsInputData("missing", "Electronics");
        interactor.execute(inputData);
    }

    @Test
    public void failureNoResultsTest() {
        // DAO finds nothing at all
        SearchListingsDataAccessInterface searchDAO = new SearchListingsDataAccessInterface() {
            @Override
            public List<Listing> findByKeywordAndCategory(String keyword, String categoryName) { return new ArrayList<>(); }
            @Override
            public List<Listing> findByKeyword(String keyword) { return new ArrayList<>(); }
            @Override
            public List<Listing> findByCategory(String categoryName) { return new ArrayList<>(); }
            @Override
            public List<String> getAllCategories() { return new ArrayList<>(); }
        };

        // Presenter expects failure
        SearchListingsOutputBoundary searchPresenter = new SearchListingsOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchListingsOutputData outputData) {
                fail("Use case success is unexpected. Should have failed.");
            }

            @Override
            public void prepareFailView(String errorMessage, String keyword, String categoryName) {
                assertEquals("No listings found for the current search criteria.", errorMessage);
            }
        };

        SearchListingsInteractor interactor = new SearchListingsInteractor(searchDAO, searchPresenter);
        SearchListingsInputData inputData = new SearchListingsInputData("fddSSASAAasas", "Select a Category");
        interactor.execute(inputData);
    }

    @Test
    public void searchByCategoryOnlyTest() {
        // Tests searching with no keyword, only a specific category
        SearchListingsDataAccessInterface searchDAO = new SearchListingsDataAccessInterface() {
            @Override
            public List<Listing> findByKeywordAndCategory(String keyword, String categoryName) { return new ArrayList<>(); }
            @Override
            public List<Listing> findByKeyword(String keyword) { return new ArrayList<>(); }

            @Override
            public List<Listing> findByCategory(String categoryName) {
                List<Listing> listings = new ArrayList<>();
                if ("Furniture".equals(categoryName)) {
                    User owner = new User("User1", "pw", "mail");
                    // NOTE: Passing empty list for categories to test "Uncategorized" logic in extractCategoryNames
                    listings.add(new Listing("Chair", "Nice chair", new ArrayList<>(), owner));
                }
                return listings;
            }

            @Override
            public List<String> getAllCategories() { return List.of("Furniture", "Tech"); }
        };

        SearchListingsOutputBoundary searchPresenter = new SearchListingsOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchListingsOutputData outputData) {
                assertFalse(outputData.getResults().isEmpty());
                // Verify empty keyword was handled
                assertEquals("", outputData.getKeyword());
                assertEquals("Furniture", outputData.getCategoryName());
                // Verify handling of empty category list -> "Uncategorized"
                List<String> resultCats = outputData.getResults().get(0).getCategories();
                assertEquals(1, resultCats.size());
                assertEquals("Uncategorized", resultCats.get(0));
            }

            @Override
            public void prepareFailView(String errorMessage, String keyword, String categoryName) {
                fail("Use case fail is unexpected.");
            }
        };

        SearchListingsInteractor interactor = new SearchListingsInteractor(searchDAO, searchPresenter);
        // Empty keyword, explicit category
        SearchListingsInputData inputData = new SearchListingsInputData("", "Furniture");
        interactor.execute(inputData);
    }

    @Test
    public void testNullInputDefaultsToFirstCategory() {
        // Tests null inputs for keyword and category
        SearchListingsDataAccessInterface searchDAO = new SearchListingsDataAccessInterface() {
            @Override
            public List<Listing> findByKeywordAndCategory(String keyword, String categoryName) { return new ArrayList<>(); }
            @Override
            public List<Listing> findByKeyword(String keyword) { return new ArrayList<>(); }

            @Override
            public List<Listing> findByCategory(String categoryName) {
                List<Listing> listings = new ArrayList<>();
                // Logic should default to first category "Tech"
                if ("Tech".equals(categoryName)) {
                    User owner = new User("User1", "pw", "mail");
                    // NOTE: Passing NULL for categories to test "Uncategorized" logic in extractCategoryNames
                    listings.add(new Listing("Laptop", "Fast", null, owner));
                }
                return listings;
            }

            @Override
            public List<String> getAllCategories() {
                // "Tech" is index 0
                return List.of("Tech", "Furniture");
            }
        };

        SearchListingsOutputBoundary searchPresenter = new SearchListingsOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchListingsOutputData outputData) {
                // Verify category defaulted to "Tech"
                assertEquals("Tech", outputData.getCategoryName());
                // Verify null keyword became empty string
                assertEquals("", outputData.getKeyword());
                // Verify null category list became "Uncategorized"
                assertEquals("Uncategorized", outputData.getResults().get(0).getCategories().get(0));
            }

            @Override
            public void prepareFailView(String errorMessage, String keyword, String categoryName) {
                fail("Use case fail is unexpected.");
            }
        };

        SearchListingsInteractor interactor = new SearchListingsInteractor(searchDAO, searchPresenter);
        // Pass nulls to test normalization
        SearchListingsInputData inputData = new SearchListingsInputData(null, null);
        interactor.execute(inputData);
    }

    @Test
    public void testResolveCategoryFailure() {
        // Tests when no category is provided AND DAO has no categories to default to
        SearchListingsDataAccessInterface searchDAO = new SearchListingsDataAccessInterface() {
            @Override
            public List<Listing> findByKeywordAndCategory(String keyword, String categoryName) { return new ArrayList<>(); }
            @Override
            public List<Listing> findByKeyword(String keyword) { return new ArrayList<>(); }
            @Override
            public List<Listing> findByCategory(String categoryName) { return new ArrayList<>(); }
            @Override
            public List<String> getAllCategories() { return new ArrayList<>(); } // Empty DB
        };

        SearchListingsOutputBoundary searchPresenter = new SearchListingsOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchListingsOutputData outputData) {
                fail("Should have failed as no categories exist to fall back to.");
            }

            @Override
            public void prepareFailView(String errorMessage, String keyword, String categoryName) {
                // Category resolution should result in empty string
                assertEquals("", categoryName);
                assertEquals("", keyword);
            }
        };

        SearchListingsInteractor interactor = new SearchListingsInteractor(searchDAO, searchPresenter);
        // "   " should trim to empty, triggering default logic, which fails due to empty DAO
        SearchListingsInputData inputData = new SearchListingsInputData("   ", "   ");
        interactor.execute(inputData);
    }
}