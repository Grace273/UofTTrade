package use_case.search;
import java.util.List;

public class SearchListingsOutputData {
    private List<ListingResult> results;
    private String categoryName;
    private String keyword;

    public SearchListingsOutputData(List<ListingResult> results, String keyword, String categoryName) {
        this.results = results;
        this.keyword = keyword;
        this.categoryName = categoryName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public String getKeyword() {
        return keyword;
    }
    public List<ListingResult> getResults() {
        return results;
    }
    public static class ListingResult {
        private final String name;
        private final List<String> categories;
        public ListingResult(String name, List<String> categories) {
            this.name = name;
            this.categories = categories;
        }
        public String getName() {
            return name;
        }
        public List<String> getCategories() {
            return categories;
        }
    }


}
