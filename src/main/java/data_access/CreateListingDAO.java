package data_access;

import entity.Listing;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.create_listing.CreateListingUserDataAccessInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateListingDAO implements CreateListingUserDataAccessInterface {
    /**
     * Saves the listing to the API database.
     * @param listing the listing to save
     */
    @Override
    public void save(Listing listing) throws IOException {

        String listingID = String.valueOf(listing.get_listingId());
        JSONObject listings  = getListingData();
        if (listings.has(listingID)) {
            throw new DuplicateListingException(listingID);
        }
        // create a JSON object of the new listing
        JSONObject newListing = new JSONObject();
        newListing.put("Name", listing.get_name());
        newListing.put("Categories", listing.get_categories());
        newListing.put("Owner", listing.get_owner());
        newListing.put("ListingID", listing.get_listingId());

        // map the listing to the ID
        JSONObject updatedListings = new JSONObject();
        updatedListings.put(listingID, newListing);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, updatedListings.toString());
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }

    /**
     * Fetches the listings from the API database.
     * @return JSON Object of the data
     */
    private JSONObject getListingData() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }

    public class DuplicateListingException extends RuntimeException {
        public DuplicateListingException(String listingId) {
            super("Listing already exists: " + listingId);
        }
    }
}
