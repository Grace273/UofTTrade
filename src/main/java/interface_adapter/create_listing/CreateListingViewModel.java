package interface_adapter.create_listing;

import interface_adapter.ViewModel;

public class CreateListingViewModel extends ViewModel<CreateListingState> {
    public static final String TITLE_LABEL = "Create Listing View";
    public static final String NAME_LABEL = "Enter name of the item";
    public static final String IMG_LABEL = "Add an image of the item";
    public static final String CATEGORIES_LABEL = "Choose categories of the item (optional)";

    public static final String CREATE_LISTING_BUTTON_LABEL = "Create Listing";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public CreateListingViewModel() {
        super("create listing");
        setState(new CreateListingState());
    }
}
