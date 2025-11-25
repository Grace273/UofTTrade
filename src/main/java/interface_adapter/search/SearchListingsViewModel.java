package interface_adapter.search;

import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * View-model storing the available categories. The first
 * category is automatically selected to ensure that fallback searches always have a
 * destination.
 */
public class SearchListingsViewModel extends ViewModel<SearchListingsState> {

    private List<String> categories = new ArrayList<>();

    /**
     * Creates the view-model
     * @param categories category names the combo-box should expose
     */
    public SearchListingsViewModel(List<String> categories) {
        super("search listings");
        this.setState(new SearchListingsState());
        updateCategories(categories);
        if (!this.categories.isEmpty()) {
            this.getState().setCategoryName(this.categories.get(0));
        }
    }

    /**
     * @return the list of categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Replaces the known categories.
     *
     * @param categories new categories provided by the DAO/use case
     */
    public void updateCategories(List<String> categories) {
        if (categories == null) {
            this.categories = new ArrayList<>();
        } else {
            this.categories = new ArrayList<>(categories);
        }
    }
}