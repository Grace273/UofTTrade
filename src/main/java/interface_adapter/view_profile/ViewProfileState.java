package interface_adapter.view_profile;

import entity.User;

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
    private List<String> listingDescriptions = new ArrayList<>();
    private User user;

    private String errorMessage = "";

    // Message to display when there are zero listings
    private String noListingsMessage = "";

    public String getUsername() {
        return username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<String> getListingDescriptions() {
        return listingDescriptions;
    }
    public void setListingDescriptions(List<String> listingDescriptions) {
        this.listingDescriptions = listingDescriptions;
    }
}

