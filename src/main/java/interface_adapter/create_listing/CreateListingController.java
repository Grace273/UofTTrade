package interface_adapter.create_listing;

import entity.Category;
import entity.User;
import use_case.create_listing.CreateListingInputBoundary;
import use_case.create_listing.CreateListingInputData;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class CreateListingController {
    private final CreateListingInputBoundary createListingInteractor;

    public CreateListingController(CreateListingInputBoundary createListingInteractor) {
        this.createListingInteractor = createListingInteractor;
    }


    /**
     * Executes the Create Listing Use Case.
     * @param name the name of the listing
     * @param categories the categories of the listing
     */
    public void execute(String name, List<Category> categories) throws IOException {
        final CreateListingInputData createListingInputData = new CreateListingInputData(name, categories);

        createListingInteractor.execute(createListingInputData);
    }

    /**
     * Executes the View Profile Use Case.
     */
    public void switchToProfileView() {
        createListingInteractor.switchToProfileView();
    }
}
