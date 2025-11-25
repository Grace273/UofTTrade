package interface_adapter.search;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mutable state backing the search listings UI. It stores the keyword, selected category,
 * transformed results, and whether the most recent response represents category fallback
 * results (i.e., no keyword matches were found).
 */
public class SearchListingsState {
    private String keyword = "";
    private String categoryName = "";
    private List<ListingViewModel> results = new ArrayList<>();
    private String errorMessage;
    private boolean showingFallbackResults;

    /**
     * @return the trimmed keyword currently displayed in the search field
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Records the keyword that the presenter/view supplied. Null values are coerced into an empty
     * string so Swing components never receive {@code null}.
     */
    public void setKeyword(String keyword) {
        if (keyword == null) {
            this.keyword = "";
        } else {
            this.keyword = keyword.trim();
        }
    }

    /**
     * @return the currently selected category label
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Updates the selected category while trimming whitespace for consistent combo-box matching.
     */
    public void setCategoryName(String categoryName) {
        if (categoryName == null) {
            this.categoryName = "";
        } else {
            this.categoryName = categoryName.trim();
        }
    }

    /**
     * @return snapshot of the listing rows that should be rendered
     */
    public List<ListingViewModel> getResults() {
        return results;
    }

    /**
     * Replaces the visible results. Defensive copies are taken so the UI cannot mutate presenter
     * data after publication.
     */
    public void setResults(List<ListingViewModel> results) {
        if (results == null) {
            this.results = new ArrayList<>();
        } else {
            this.results = new ArrayList<>(results);
        }
    }

    /**
     * @return the error message that should be displayed above the results (if any)
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Records an easy-to-read error message. Passing null clears any existing error state.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return {@code true} when the interactor could not find keyword matches and had to display
     * category fallback results instead
     */
    public boolean isShowingFallbackResults() {
        return showingFallbackResults;
    }

    /**
     * Marks whether the current results originated from a fallback category search.
     */
    public void setShowingFallbackResults(boolean showingFallbackResults) {
        this.showingFallbackResults = showingFallbackResults;
    }

    /**
     * Immutable representation of a single listing row in the Swing list widget.
     */
    public static class ListingViewModel {
        private final String name;
        private final String categorySummary;

        /**
         * @param name             listing title shown to the user
         * @param categorySummary  comma-separated category labels
         */
        public ListingViewModel(String name, String categorySummary) {
            this.name = name;
            this.categorySummary = categorySummary;
        }

        /**
         * @return listing name
         */
        public String getName() {
            return name;
        }

        /**
         * @return comma-separated category summary suitable for display next to the name
         */
        public String getCategorySummary() {
            return categorySummary;
        }
    }
}