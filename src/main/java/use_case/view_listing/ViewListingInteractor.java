package use_case.view_listing;

import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewListingInteractor implements ViewListingInputBoundary {

    private final ViewListingDataAccessInterface viewListingDataAccess;
    private final ViewListingUserDataAccessInterface viewListingUserDataAccess;
    private final ViewListingOutputBoundary viewListingOutputBoundary;

    public ViewListingInteractor(ViewListingDataAccessInterface viewListingDataAccess,
                                 ViewListingUserDataAccessInterface viewListingUserDataAccess,
                                 ViewListingOutputBoundary viewListingOutputBoundary) {
        this.viewListingDataAccess = viewListingDataAccess;
        this.viewListingUserDataAccess = viewListingUserDataAccess;
        this.viewListingOutputBoundary = viewListingOutputBoundary;
    }

    @Override
    public void execute(ViewListingInputData viewListingInputData) throws IOException {

        final String listingName = viewListingInputData.getListingName();
        final String listingOwner = viewListingInputData.getListingOwner();
        final JSONObject listing = viewListingDataAccess.getSpecificListingInfo(listingName, listingOwner);
        if (listing == null) {
            viewListingOutputBoundary.prepareFailView();
        }
        else {
            final User ownerUser = viewListingUserDataAccess.getUser(listingOwner);
            final String ownerEmail = (ownerUser != null) ? ownerUser.get_email() : null;
            final JSONArray listingCategoriesJSON = listing.getJSONArray("Categories");
            final List<String> listingCategories = new ArrayList<>();
            for (int i = 0; i < listingCategoriesJSON.length(); i++) {
                listingCategories.add(listingCategoriesJSON.getString(i));
            }

            final ViewListingOutputData viewListingOutputData = new ViewListingOutputData(listingName, listingOwner,
                    ownerEmail, listingCategories, (String) listing.get("Description"));
            viewListingOutputBoundary.switchToListingView(viewListingOutputData);
        }
    }

    @Override
    public void switchToPreviousView() {

        viewListingOutputBoundary.switchToPreviousView();

    }

}
