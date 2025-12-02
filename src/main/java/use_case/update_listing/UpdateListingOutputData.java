package use_case.update_listing;

import entity.User;

public class UpdateListingOutputData {
    private final User user;

    public UpdateListingOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
