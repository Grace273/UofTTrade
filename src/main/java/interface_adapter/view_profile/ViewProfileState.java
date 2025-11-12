package interface_adapter.view_profile;

import java.util.ArrayList;
import java.util.List;

public class ViewProfileState {
    private String username = "";
    private List<String> listingNames = new ArrayList<>();
    private String titleText = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.titleText = username + "'s Account";
    }

    public List<String> getListingNames() {
        return listingNames;
    }

    public void setListingNames(List<String> listingNames) {
        this.listingNames = listingNames;
    }

    public String getTitleText() {
        return titleText;
    }
}