package data_access;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import use_case.update_listing.UpdateListingUserDataAccessInterface;

public class UpdateListingDataAccessObject implements UpdateListingUserDataAccessInterface {

    private static final Logger LOGGER = Logger.getLogger(UpdateListingDataAccessObject.class.getName());

    private static final String URL =
            "https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/LISTINGS";

    private final OkHttpClient client = new OkHttpClient();
    private final CreateListingDAO createListingDAO = new CreateListingDAO();

    @Override
    public void updateListing(int listingId) {
        try {
            final String key = String.valueOf(listingId);

            // 1) Fetch current JSON
            final JSONObject listings = createListingDAO.getListingData();

            if (listings.has(key)) {

                // 2) Remove listing
                listings.remove(key);

                // 3) PUT back to Pantry
                final MediaType mediaType = MediaType.parse("application/json");
                final String jsonToSend = listings.toString();

                final RequestBody body = RequestBody.create(jsonToSend, mediaType);

                final Request request = new Request.Builder()
                        .url(URL)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Failed to delete listing: "
                                + response.code() + " " + response.message());
                    }
                }
            }
        }
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Failed to delete listing", exception);
        }
    }
}

