package interface_adapter.create_listing;

import interface_adapter.ViewModel;

public class CreateListingViewModel extends ViewModel<CreateListingState> {
    public static final String TITLE_LABEL = "Create Listing";
    public static final String NAME_LABEL = "Name of the item";
    public static final String IMG_LABEL = "Image of the item (.png, .jpeg or .jpg)";
    public static final String CATEGORIES_LABEL = "Categories of the item (optional)";

    public static final String PUBLISH_LISTING_BUTTON_LABEL = "Publish Listing";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public CreateListingViewModel() {
        super("create listing");
        setState(new CreateListingState());
    }
}
