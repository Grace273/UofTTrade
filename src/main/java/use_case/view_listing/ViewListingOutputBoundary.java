package use_case.view_listing;

public interface ViewListingOutputBoundary {

    /**
     * Switches to the full detailed view of the item listing on user click.
     * @param viewListingOutputData the data used to render the detailed listing.
     */
    void switchToListingView(ViewListingOutputData viewListingOutputData);

    /**
     * Switches to the previous view from the detailed listingwhen a button is pressed.
     */
    void switchToPreviousView();

    /**
     * Throws an exception after not receiving a listing.
     */
    void prepareFailView();

}
