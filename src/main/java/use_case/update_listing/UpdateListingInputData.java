package use_case.update_listing;

import entity.Listing;
import entity.User;

public class UpdateListingInputData {
    private final boolean isDelete;
    private final User user;
    private final Listing listing;

    public UpdateListingInputData(boolean isDelete, User user, Listing listing) {
        this.isDelete = isDelete;
        this.user = user;
        this.listing = listing;
    }

    Boolean getDelete() {
        return isDelete;
    }

    User getUser() {
        return user;
    }

    Listing getListing() {
        return listing;
    }
}
