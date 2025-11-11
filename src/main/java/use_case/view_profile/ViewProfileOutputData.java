package use_case.view_profile;
import java.util.List;
public class ViewProfileOutputData {
    private final String username;
    private final List<String> listingNames;  // or actual objects depending on UI needs

    public ViewProfileOutputData(String username, List<String> listingNames) {
        this.username = username;
        this.listingNames = listingNames;
    }

    public String getUsername() { return username; }
    public List<String> getListingNames() { return listingNames; }
}

