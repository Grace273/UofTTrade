package interface_adapter.search;
import use_case.search.SearchListingsOutputBoundary;
import use_case.search.SearchListingsOutputData;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Presenter that converts raw SearchListingsOutputData coming from the interactor
 * into SearchListingsState data used by views.
 */
public class SearchListingsPresenter implements SearchListingsOutputBoundary {

    private final SearchListingsViewModel viewModel;

    /**
     * Creates a presenter that mutates the provided view-model whenever the interactor responds.
     *
     * @param viewModel backing model consumed by the Swing view
     */
    public SearchListingsPresenter(SearchListingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Updates the view state with the results
     *
     * @param outputData payload produced by the interactor
     */
    @Override
    public void prepareSuccessView(SearchListingsOutputData outputData) {
        SearchListingsState state = viewModel.getState();
        if (state == null) {
            state = new SearchListingsState();
            viewModel.setState(state);
        }

        state.setKeyword(outputData.getKeyword());
        state.setCategoryName(outputData.getCategoryName());
        state.setShowingFallbackResults(outputData.isFallbackResults());
        state.setErrorMessage(null);
        state.setResults(toListingViewModels(outputData.getResults()));

        viewModel.firePropertyChanged();
    }

    /**
     * Displays an error but preserves the most recent keyword/category so the user can
     * adjust their search without retyping.
     *
     * @param errorMessage human-friendly explanation of what went wrong
     * @param keyword      keyword that was attempted (may be blank)
     * @param categoryName fallback/selected category that should stay highlighted
     */
    @Override
    public void prepareFailView(String errorMessage, String keyword, String categoryName) {
        SearchListingsState state = viewModel.getState();
        if (state == null) {
            state = new SearchListingsState();
            viewModel.setState(state);
        }
        state.setKeyword(keyword);
        state.setCategoryName(categoryName);
        state.setResults(Collections.emptyList());
        state.setErrorMessage(errorMessage);
        state.setShowingFallbackResults(false);
        viewModel.firePropertyChanged();
    }

    /**
     * Converts raw use-case listings into lightweight rows that the Swing list component can render.
     *
     * @param results listing results coming from the interactor
     * @return immutable view-model rows for the UI
     */
    private List<SearchListingsState.ListingViewModel> toListingViewModels(
            List<SearchListingsOutputData.ListingResult> results) {
        return results.stream()
                .map(result -> new SearchListingsState.ListingViewModel(
                        result.getName(),
                        formatCategories(result.getCategories())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Concatenates category names or marks the listing as uncategorized when none exist.
     *
     * @param categories names assigned to a listing
     * @return comma-separated summary suitable for the list row
     */
    private String formatCategories(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return "Uncategorized";
        }
        return String.join(", ", categories);
    }
}
