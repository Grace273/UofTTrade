package use_case.search;
import entity.Listing;

import java.util.List;
public interface SearchListingsDataAccessInterface {
    List<Listing> findByKeywordAndCategory(String keyword, String categoryName);
}
