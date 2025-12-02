package use_case.search;

import java.util.List;

public class SearchListingsOutputData {
    private List<ListingResult> results;
    private String categoryName;
    private String keyword;
    private boolean fallbackResults;

    public SearchListingsOutputData(List<ListingResult> results, String keyword, String categoryName, boolean fallbackResults) {
        this.results = results;
        this.keyword = keyword;
        this.categoryName = categoryName;
        this.fallbackResults = fallbackResults;
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

    public boolean isFallbackResults() {
        return fallbackResults;
    }

    public static class ListingResult {
        private final String name;
        private final String description;
        private final String owner;
        private final List<String> categories;

        public ListingResult(String name, String description, String owner, List<String> categories) {
            this.name = name;
            this.description = description;
            this.owner = owner;
            this.categories = categories;
        }

        public String getName() {
            return name;
        }

        public List<String> getCategories() {
            return categories;
        }

        public String getOwner() {
            return owner;
        }

        public String getDescription() {
            return description;
        }

    }

}
