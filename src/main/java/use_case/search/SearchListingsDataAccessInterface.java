package use_case.search;

import java.util.List;

import entity.Listing;

public interface SearchListingsDataAccessInterface {
    /**
     * Returns listings whose name contains the provided keyword and that belong
     * to the provided category name.
     *
     * @param keyword      the keyword to search for in the listing name
     * @param categoryName the name of the category the listing must belong to
     * @return a list of listings matching both the keyword and category
     */
    List<Listing> findByKeywordAndCategory(String keyword, String categoryName);

    /**
     * Returns listings whose name contains the provided keyword.
     *
     * @param keyword the keyword to search for in the listing name
     * @return a list of listings whose names contain the keyword
     */
    List<Listing> findByKeyword(String keyword);

    /**
     * Returns listings that belong to the provided category name.
     *
     * @param categoryName the name of the category
     * @return a list of listings that belong to the given category
     */
    List<Listing> findByCategory(String categoryName);

    /**
     * Returns all known category names.
     *
     * @return a list of all category names
     */
    List<String> getAllCategories();
}
