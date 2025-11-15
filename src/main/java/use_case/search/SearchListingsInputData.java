package use_case.search;

public class SearchListingsInputData {
    private String keyword;
    private String categoryName;
    public SearchListingsInputData(String keyword, String categoryName) {
        this.keyword = keyword;
        this.categoryName = categoryName;

    }
    public String getKeyword() {
        return keyword;

    }
    public String getCategoryName() {
        return categoryName;
    }

}
