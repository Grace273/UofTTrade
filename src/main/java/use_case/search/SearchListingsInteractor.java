package use_case.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Category;
import entity.Listing;

public class SearchListingsInteractor implements SearchListingsInputBoundary {
    private final SearchListingsDataAccessInterface searchListingsDataAccess;
    private final SearchListingsOutputBoundary presenter;

    /**
     * Constructs a new {@code SearchListingsInteractor} with the given
     * data access object and presenter.
     *
     * @param searchListingsDataAccess the data access object responsible for
     *                                 performing keyword and category lookups
     * @param presenter                the output boundary that prepares the
     *                                 search results for the UI
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
    @Override
    public void execute(SearchListingsInputData inputData) {
        final String keyword = normalize(inputData.getKeyword());
        final String requestedCategory = inputData.getCategoryName();

        final boolean hasKeyword = !keyword.isBlank();
        final List<Listing> keywordMatches;
        if (hasKeyword) {
            keywordMatches = searchListingsDataAccess.findByKeyword(keyword);
        }
        else {
            keywordMatches = Collections.emptyList();
        }

        final boolean fallbackToCategory = hasKeyword && keywordMatches.isEmpty();
        final String resolvedCategory = resolveCategory(requestedCategory);

        final List<Listing> listings;
        if (fallbackToCategory || !hasKeyword) {
            listings = searchListingsDataAccess.findByCategory(resolvedCategory);
        }
        else {
            listings = keywordMatches;
        }

        if (listings.isEmpty()) {
            presenter.prepareFailView(
                    "No listings found for the current search criteria.",
                    keyword,
                    resolvedCategory
            );
        }
        else {
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
    }

    /**
     * Normalizes the keyword by trimming whitespace and replacing {@code null}
     * with an empty string.
     *
     * @param keyword the raw keyword, possibly {@code null}
     * @return the trimmed keyword, or an empty string if {@code keyword} is {@code null}
     */
    private String normalize(String keyword) {
        final String result;
        if (keyword == null) {
            result = "";
        }
        else {
            result = keyword.trim();
        }
        return result;
    }

    /**
     * Ensures a category name is available. If the UI supplies an empty value,
     * the first known category is used so fallback searches work.
     *
     * @param requestedCategory the category name provided by the UI, possibly {@code null}
     * @return a non-empty category name, or an empty string if none are available
     */
    private String resolveCategory(String requestedCategory) {
        final String resolved;
        if (requestedCategory == null) {
            resolved = "";
        }
        else {
            resolved = requestedCategory.trim();
        }

        final String result;
        if (!resolved.isEmpty()) {
            result = resolved;
        }
        else {
            final List<String> categories = searchListingsDataAccess.getAllCategories();
            if (!categories.isEmpty()) {
                result = categories.get(0);
            }
            else {
                result = "";
            }
        }

        return result;
    }

    /**
     * Produces the category names attached to a listing, or {@code "Uncategorized"}
     * when none exist, so the presenter can include them in the UI.
     *
     * @param listing the listing whose categories are to be extracted
     * @return a list of category names, or a single-element list containing
     *         {@code "Uncategorized"} if the listing has no categories
     */
    private List<String> extractCategoryNames(Listing listing) {
        final List<Category> categories = listing.get_categories();
        final List<String> names = new ArrayList<>();

        if (categories == null || categories.isEmpty()) {
            names.add("Uncategorized");
        }
        else {
            for (Category category : categories) {
                names.add(category.getName());
            }
        }
        return names;
    }
}
