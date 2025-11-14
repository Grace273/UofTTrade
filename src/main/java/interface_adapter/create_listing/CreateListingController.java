package interface_adapter.create_listing;

import use_case.create_listing.CreateListingInputBoundary;
import use_case.create_listing.CreateListingInputData;
import use_case.create_listing.CreateListingInteractor;

import java.awt.image.BufferedImage;


public class CreateListingController {
    private final CreateListingInputBoundary createListingUseCaseInteractor;

    public CreateListingController(CreateListingInputBoundary createListingUseCaseInteractor) {
        this.createListingUseCaseInteractor = createListingUseCaseInteractor;
    }

    public void execute(String name, BufferedImage image) {
        final CreateListingInputData createListingInputData = new CreateListingInputData(name, image);
    }
}
