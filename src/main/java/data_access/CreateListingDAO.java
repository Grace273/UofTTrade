package data_access;

import entity.Category;
import entity.Listing;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.create_listing.CreateListingUserDataAccessInterface;
import use_case.view_listing.ViewListingDataAccessInterface;

import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CreateListingDAO implements CreateListingUserDataAccessInterface, ViewListingDataAccessInterface {
    /**
     * Saves the listing to the API database.
     * @param listing the listing to save
     */
    @Override
    public void save(Listing listing) throws IOException {
        String listingID = String.valueOf(listing.get_listingId());

        // 1. Get existing JSON object from Pantry
        JSONObject listings = getListingData();  // always returns a JSONObject (we'll fix that too)

        // 2. Check duplicate
        if (listings.has(listingID)) {
            throw new DuplicateListingException(listingID);
        }

        // 3. Build categories as names
        List<String> categoryNames = new ArrayList<>();
        if (listing.get_categories() != null) {
            for (Category c : listing.get_categories()) {
                categoryNames.add(c.getName());
            }
        }

        // 4. Build the new listing object
        JSONObject newListing = new JSONObject();
        newListing.put("Name", listing.get_name());
        newListing.put("Description", listing.get_description());
        newListing.put("Categories", categoryNames);
        newListing.put("Owner", listing.get_owner().get_username());
        newListing.put("ListingID", listing.get_listingId());

        // 5. Insert into the existing map
        listings.put(listingID, newListing);

        // 6. PUT the WHOLE object back
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, listings.toString());

        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to save listing: "
                        + response.code() + " " + response.message());
            }
        }
    }

    public JSONObject getSpecificListingInfo(String listingName, String listingOwner) throws IOException {
        final JSONObject listingsObject = getListingData();
        for (String key: listingsObject.keySet()) {
            final boolean isListingName = listingsObject.getJSONObject(key).getString("Name").equals(listingName);
            final boolean isOwner = listingsObject.getJSONObject(key).getString("Owner").equals(listingOwner);
            if (isListingName && isOwner) {
                return listingsObject.getJSONObject(key);
            }
        }
        return new JSONObject();
    }

    /**
     * Fetches the listings from the API database.
     * @return JSON Object of the data
     */
    public JSONObject getListingData() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body() != null ? response.body().string() : "";

            if (body.isBlank()) {
                // Empty basket → treat as empty map
                return new JSONObject();
            }

            try {
                return new JSONObject(body);
            } catch (org.json.JSONException e) {
                System.err.println("getListingData: invalid JSON from Pantry: " + e.getMessage());
                // Don't crash callers: behave as if there are no listings
                return new JSONObject();
            }
        }
    }

    /**
     * Returns if listing with the give listingId exists
     * @param listingID the id of the listing
     */
    @Override
    public boolean existById(String listingID) throws IOException {
        JSONObject listingData = getListingData();
        if (listingData.keySet().contains(listingID)) {
            return true;
        }
        return false;
    }

    public class DuplicateListingException extends RuntimeException {
        public DuplicateListingException(String listingId) {
            super("Listing already exists: " + listingId);
        }
    }
}
