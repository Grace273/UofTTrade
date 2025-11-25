package interface_adapter.view_profile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * State object for the View Profile screen.
 * Stores the username, title text, listing names/photos, the "no listings"
 * message, and any error message.
 */
public class ViewProfileState {

    private String username = "";
    private String titleText = "";

    private List<String> listingNames = new ArrayList<>();
    private List<BufferedImage> listingPhotos = new ArrayList<>();

    private String errorMessage = "";

    // NEW: Message to display when there are zero listings
    private String noListingsMessage = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public List<String> getListingNames() {
        return listingNames;
    }

    public void setListingNames(List<String> listingNames) {
        this.listingNames = listingNames;
    }

    public List<BufferedImage> getListingPhotos() {
        return listingPhotos;
    }

    public void setListingPhotos(List<BufferedImage> listingPhotos) {
        this.listingPhotos = listingPhotos;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // NEW getters/setters
    public String getNoListingsMessage() {
        return noListingsMessage;
    }

    public void setNoListingsMessage(String noListingsMessage) {
        this.noListingsMessage = noListingsMessage;
    }
}

