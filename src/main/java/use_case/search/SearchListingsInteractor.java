package use_case.search;
import entity.Category;
import entity.Listing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class SearchListingsInteractor implements SearchListingsInputBoundary{
    private final SearchListingsDataAccessInterface searchListingsDataAccess;
    private final SearchListingsOutputBoundary presenter;

    /**
     * @param searchListingsDataAccess DAO responsible for keyword/category lookups
     * @param presenter                 presenter that transforms output data for the UI
     */
    public SearchListingsInteractor(SearchListingsDataAccessInterface searchListingsDataAccess,
                                    SearchListingsOutputBoundary presenter) {
        this.searchListingsDataAccess = searchListingsDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the search request and instructs the presenter to show either results or an
     * appropriate error.
     *
     * @param inputData raw keyword/category values coming from the controller
     */
    public void execute(SearchListingsInputData inputData) {
        final String keyword = normalize(inputData.getKeyword());
        final String requestedCategory = inputData.getCategoryName();

        final boolean hasKeyword = !keyword.isBlank();
        final List<Listing> keywordMatches;
        if (hasKeyword) {
            keywordMatches = searchListingsDataAccess.findByKeyword(keyword);
        } else {
            keywordMatches = Collections.emptyList();
        }

        final boolean fallbackToCategory = hasKeyword && keywordMatches.isEmpty();
        final String resolvedCategory = resolveCategory(requestedCategory);

        final List<Listing> listings;
        if (fallbackToCategory || !hasKeyword) {
            listings = searchListingsDataAccess.findByCategory(resolvedCategory);
        } else {
            listings = keywordMatches;
        }

        if (listings.isEmpty()) {
            presenter.prepareFailView("No listings found for the current search criteria.", keyword, resolvedCategory);
            return;
        }

        final List<SearchListingsOutputData.ListingResult> outputResults = new ArrayList<>();
        for (Listing listing : listings) {
            outputResults.add(new SearchListingsOutputData.ListingResult(
                    listing.get_name(),
                    listing.get_description(),
                    listing.get_owner().get_username(),
                    extractCategoryNames(listing)
            ));
        }

        final SearchListingsOutputData outputData = new SearchListingsOutputData(
                outputResults,
                keyword,
                resolvedCategory,
                fallbackToCategory
        );
        presenter.prepareSuccessView(outputData);
    }

    /**
     * Trims the keyword and converts into an empty string
     */
    private String normalize(String keyword) {
        if (keyword == null) {
            return "";
        }
        return keyword.trim();
    }

    /**
     * Ensures a category name is available. If the UI supplies an empty value the first
     * known category is used so fallback searches works.
     */
    private String resolveCategory(String requestedCategory) {
        String resolved;
        if (requestedCategory == null) {
            resolved = "";
        } else {
            resolved = requestedCategory.trim();
        }
        if (!resolved.isEmpty()) {
            return resolved;
        }

        final List<String> categories = searchListingsDataAccess.getAllCategories();
        if (!categories.isEmpty()) {
            return categories.get(0);
        }
        return "";
    }

    /**
     * Produces the category names attached to a listing or "Uncategorized" when none exist so
     * the presenter can include them in the UI.
     */
    private List<String> extractCategoryNames(Listing listing) {
        List<Category> categories = listing.get_categories();
        if (categories == null || categories.isEmpty()) {
            return List.of("Uncategorized");
        }
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.getName());
        }
        return names;
    }
}
