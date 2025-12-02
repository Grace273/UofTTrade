package use_case.update_listing;

import entity.Listing;
import entity.User;

public class UpdateListingInteractor implements UpdateListingInputBoundary {
    private final UpdateListingUserDataAccessInterface userDataAccessObject;
    private final UpdateListingOutputBoundary updateListingPresenter;

    public UpdateListingInteractor(UpdateListingUserDataAccessInterface userDataAccessObject,
                                    UpdateListingOutputBoundary updateListingPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.updateListingPresenter = updateListingPresenter;
    }

    /**
     * Executes the update listing use case for the given input data.
     *
     * <p>
     * Currently, this implementation handles deletion of a listing. If the
     * {@code delete} flag in {@link UpdateListingInputData} is {@code true},
     * the listing is removed from the given {@link User}, the deletion is
     * persisted via the {@code userDataAccessObject}, and a success view is
     * prepared using the {@code updateListingPresenter}.
     * </p>
     *
     * @param updateListingInputData the input data containing the user, listing,
     *                               and delete flag for this use case
     */
    public void execute(UpdateListingInputData updateListingInputData) {
        final User user = updateListingInputData.getUser();
        final boolean isDelete = updateListingInputData.getDelete();
        final Listing listing = updateListingInputData.getListing();
        final int listingID = listing.get_listingId();
        if (isDelete) {
            user.delete_listing(listing);
            userDataAccessObject.updateListing(listingID);
            final UpdateListingOutputData updateListingOutputData = new UpdateListingOutputData(user);
            updateListingPresenter.prepareSuccessView(updateListingOutputData);
        }
    }
}
