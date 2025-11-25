package interface_adapter.search;

import use_case.search.SearchListingsInputBoundary;
import use_case.search.SearchListingsInputData;

/**
 * Controller that accepts user-entered search information and forwards it to the
 * {@link SearchListingsInputBoundary}. No validation occurs here – the interactor handles
 * keyword/category normalization as well as fallback behaviour.
 */
public class SearchListingsController {

    private final SearchListingsInputBoundary interactor;

    /**
     * Creates a controller that forwards UI events to the provided interactor.
     *
     * @param interactor the use-case boundary coordinating keyword/category searches
     */
    public SearchListingsController(SearchListingsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Dispatches a search request that may include a keyword, category, or both. When the
     * interactor cannot find any keyword matches it automatically falls back to the provided
     * category, which is reported to the UI via the fallbackResults.
     *
     * @param keyword      free-form text entered by the user
     * @param categoryName selected category that should be used for fallback (and standalone) searches
     */
    public void execute(String keyword, String categoryName) {
        SearchListingsInputData inputData = new SearchListingsInputData(keyword, categoryName);
        interactor.execute(inputData);
    }
}
