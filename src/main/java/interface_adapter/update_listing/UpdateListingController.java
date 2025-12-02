package interface_adapter.update_listing;

import entity.Listing;
import entity.User;
import use_case.update_listing.UpdateListingInputBoundary;
import use_case.update_listing.UpdateListingInputData;

public class UpdateListingController {
    private final UpdateListingInputBoundary updateListingUseCaseInteractor;

    public UpdateListingController(UpdateListingInputBoundary updateListingUseCaseInteractor) {
        this.updateListingUseCaseInteractor = updateListingUseCaseInteractor;
    }

    /**
     * Executes the update listing use case with the given parameters.
     *
     * <p>
     * This method wraps the provided deletion flag, user, and listing into an
     * {@link UpdateListingInputData} object and delegates execution to the
     * {@code updateListingUseCaseInteractor}.
     * </p>
     *
     * @param isDelete {@code true} if the listing should be deleted; {@code false} otherwise
     * @param user     the user who owns or is associated with the listing
     * @param listing  the listing to be updated or deleted
     */
    public void execute(boolean isDelete, User user, Listing listing) {
        final UpdateListingInputData updateListingInputData = new UpdateListingInputData(
                isDelete, user, listing);
        updateListingUseCaseInteractor.execute(updateListingInputData);
    }
}
