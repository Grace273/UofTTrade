package use_case.search;

/**
 * Raw values provided by the UI when a search request is triggered.
 */
public class SearchListingsInputData {
    private String keyword;
    private String categoryName;

    /**
     * Constructs a new {@code SearchListingsInputData} object containing the
     * search criteria for listings.
     * @param keyword      the keyword used to filter listing names or descriptions
     * @param categoryName the selected category label used to further constrain the search
     */
    public SearchListingsInputData(String keyword, String categoryName) {
        this.keyword = keyword;
        this.categoryName = categoryName;
    }

    /**
     * Returns the raw keyword string provided by the user (not normalized).
     *
     * @return the raw keyword string
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Returns the raw category name chosen by the user (not normalized).
     *
     * @return the raw category name string chosen by the user
     */
    public String getCategoryName() {
        return categoryName;
    }

}
