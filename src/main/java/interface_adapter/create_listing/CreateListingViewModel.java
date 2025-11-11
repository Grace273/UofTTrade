package interface_adapter.create_listing;

import interface_adapter.ViewModel;

public class CreateListingViewModel extends ViewModel<CreateListingState> {

    public CreateListingViewModel() {
        super("create listing");
        setState(new CreateListingState());
    }
}
