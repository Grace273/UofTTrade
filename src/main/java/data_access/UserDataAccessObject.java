package data_access;

import entity.Category;
import entity.Listing;
import entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.login.LoginUserDataAccessInterface;
import okhttp3.*;
import java.util.ArrayList;
import java.util.List;

import use_case.messaging.MessagingUserDataAccessInterface;
import use_case.register.RegisterUserDataAccessInterface;
import use_case.view_profile.ViewProfileUserDataAccessInterface;
import use_case.view_listing.ViewListingUserDataAccessInterface;
import java.util.Iterator;
import java.io.IOException;

public class UserDataAccessObject implements LoginUserDataAccessInterface, RegisterUserDataAccessInterface,
        ViewProfileUserDataAccessInterface, MessagingUserDataAccessInterface, ViewListingUserDataAccessInterface {

    private String username;
    private String email;
    private User currentLoggedInUser;



    @Override
    public boolean userExists(String userIdentifier) throws IOException {
        JSONArray userInfo = getUserData();
        for (int i = 0; i < userInfo.length(); i++) {
            JSONObject user = userInfo.getJSONObject(i);
            if (user.getString("Email").equals(userIdentifier) || user.getString("Username").equals(userIdentifier)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUser(String userIdentifier) throws IOException {
        JSONArray userInfo = getUserData();
        JSONObject userData = new JSONObject();
        for (int i = 0; i < userInfo.length(); i++) {
            JSONObject user = userInfo.getJSONObject(i);
            if (user.getString("Email").equals(userIdentifier) || user.getString("Username").equals(userIdentifier)) {
                userData = userInfo.getJSONObject(i);
                break;
            }
        }

        try {
            String username = userData.getString("Username");
            String email = userData.getString("Email");
            String password = userData.getString("Password");

            return new User(username, password, email);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void save(User user) throws IOException {

        JSONArray currentUsers = getUserData();
        JSONObject userData = new JSONObject();
        userData.put("Username", user.get_username());
        userData.put("Email", user.get_email());
        userData.put("Password", user.get_password());
        currentUsers.put(userData);
        JSONObject updatedUsers = new JSONObject();
        updatedUsers.put("Users", currentUsers);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, updatedUsers.toString());
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/USERS")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

    }

    /** Called by login interactor when login succeeds */
    public void setCurrentLoggedInUser(User user) {
        this.currentLoggedInUser = user;
    }

    @Override
    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    private JSONArray getListingData() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject listings = new JSONObject(response.body().string());
        return listings.getJSONArray("Listings");
    }

    @Override
    public List<Listing> getUserListings(String username) {

        List<Listing> result = new ArrayList<>();

        try {
            CreateListingDataAccessObject listingDAO = new CreateListingDataAccessObject();
            JSONObject data = listingDAO.getListingData();

            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = data.get(key);
                if (!(value instanceof JSONObject)) {
                    // skip anything that isn't a listing object
                    continue;
                }

                JSONObject listingJSON = (JSONObject) value;

                String ownerUsername = listingJSON.optString("Owner", "");
                if (!ownerUsername.equals(username)) {
                    continue;
                }

                String name = listingJSON.optString("Name", "Unknown");
                String description = listingJSON.optString("Description", "");

                // categories are saved as array of strings
                List<Category> cats = new ArrayList<>();
                if (listingJSON.has("Categories")) {
                    JSONArray catArr = listingJSON.getJSONArray("Categories");
                    for (int i = 0; i < catArr.length(); i++) {
                        String catName = catArr.getString(i);
                        cats.add(new Category(catName));
                    }
                }

                User owner = new User(ownerUsername, "", "");
                Listing listing = new Listing(name, description, cats, owner);
                result.add(listing);
            }

        } catch (Exception e) {
            System.err.println("Error loading user listings: " + e.getMessage());
        }

        return result;
    }

    public void setUsername(String username) {this.username = username;}

    public String getUsername() {return username;}

    public void setEmail(String email) {this.email = email;}

    public String getEmail() {return email;}

    public List<JSONObject> getAllListings() throws IOException {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();
            final Response response = client.newCall(request).execute();
            final JSONObject listings = new JSONObject(response.body().string());
            listings.remove("Listings");
            final List<JSONObject> allListings = new ArrayList<>();
            for (String listing : listings.keySet()) {
                allListings.add(listings.getJSONObject(listing));
            }

            return allListings;

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    private JSONArray getUserData() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/USERS")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject users = new JSONObject(response.body().string());
        return users.getJSONArray("Users");

    }

    @Override
    public String getValidEmailForUser(String userIdentifier) throws IOException {
        User currentLoggedInUser = getUser(userIdentifier);
        if  (currentLoggedInUser == null) {
            return null;
        }
        String email = currentLoggedInUser.get_email();
        if (isPlasuibleEmail(email)){
            return email;
        }
        return null;
    }

    @Override
    public boolean isPlasuibleEmail(String email) {
        if(email == null){
            return false;
        }
        return email.contains("@") && email.contains(".") && email.indexOf("@") > 0;

    }

}
