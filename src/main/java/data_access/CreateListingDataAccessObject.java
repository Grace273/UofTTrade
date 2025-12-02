package data_access;

import entity.Category;
import entity.Listing;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import use_case.create_listing.CreateListingUserDataAccessInterface;
import use_case.view_listing.ViewListingDataAccessInterface;

public class CreateListingDataAccessObject implements CreateListingUserDataAccessInterface,
        ViewListingDataAccessInterface {
    /**
     * Saves the listing to the API database.
     * @param listing the listing to save
     */
    @Override
    public void save(Listing listing) throws IOException {
        final String listingID = String.valueOf(listing.get_listingId());

        // 1. Get existing JSON object from Pantry
        final JSONObject listings = getListingData();

        // 2. Check duplicate
        if (listings.has(listingID)) {
            throw new DuplicateListingException(listingID);
        }

        // 3. Build categories as names
        final List<String> categoryNames = new ArrayList<>();
        if (listing.get_categories() != null) {
            for (Category c : listing.get_categories()) {
                categoryNames.add(c.getName());
            }
        }

        // 4. Build the new listing object
        final JSONObject newListing = new JSONObject();
        newListing.put("Name", listing.get_name());
        newListing.put("Description", listing.get_description());
        newListing.put("Categories", categoryNames);
        newListing.put("Owner", listing.get_owner().get_username());
        newListing.put("ListingID", listing.get_listingId());

        // 5. Insert into the existing map
        listings.put(listingID, newListing);

        // 6. PUT the WHOLE object back
        final OkHttpClient client = new OkHttpClient();
        final MediaType mediaType = MediaType.parse("application/json");
        final RequestBody body = RequestBody.create(mediaType, listings.toString());

        final Request request = new Request.Builder()
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

    /**
     * Gets all the relevant information about a listing from its owner and name.
     *
     * @param listingName  the name of the listing
     * @param listingOwner the name of the owner of the listing
     * @return the JSONObject for the matching listing, or {@code null} if no match is found
     * @throws IOException if there is an error reading the listing data
     */
    public JSONObject getSpecificListingInfo(String listingName, String listingOwner)
            throws IOException {

        final JSONObject listingsObject = getListingData();
        JSONObject result = null;

        for (String key : listingsObject.keySet()) {
            final JSONObject listing = listingsObject.getJSONObject(key);
            final boolean isListingName = listing.getString("Name").equals(listingName);
            final boolean isOwner = listing.getString("Owner").equals(listingOwner);

            if (isListingName && isOwner) {
                result = listing;
                break;
            }
        }
        return result;
    }

    /**
     * Fetches the listings from the API database.
     * @return JSON Object of the data
     * @throws IOException if an I/O error occurs while performing the HTTP request
     */
    public JSONObject getListingData() throws IOException {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            final String body = response.body() != null ? response.body().string() : "";

            if (body.isBlank()) {
                // Empty basket → treat as empty map
                return new JSONObject();
            }

            try {
                return new JSONObject(body);
            }
            catch (org.json.JSONException exception) {
                System.err.println("getListingData: invalid JSON from Pantry: " + exception.getMessage());
                // Don't crash callers: behave as if there are no listings
                return new JSONObject();
            }
        }
    }

    /**
     * Returns if listing with the give listingId exists.
     * @param listingID the id of the listing
     */
    @Override
    public boolean existById(String listingID) throws IOException {
        final JSONObject listingData = getListingData();
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
