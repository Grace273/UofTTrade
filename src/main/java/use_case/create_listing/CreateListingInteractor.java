package use_case.create_listing;

import entity.Category;

import java.awt.image.BufferedImage;
import java.util.List;

public class CreateListingInteractor implements CreateListingInputBoundary{
    private final CreateListingUserDataAccessInterface createListingDataAccess;
    private final CreateListingOutputBoundary createListingPresenter;

    public CreateListingInteractor(CreateListingUserDataAccessInterface createListingDataAccess, CreateListingOutputBoundary createListingPresenter) {
        this.createListingDataAccess = createListingDataAccess;
        this.createListingPresenter = createListingPresenter;
    }

    @Override
    public void execute(CreateListingInputData createListingInputData) {
        final String name = createListingInputData.get_name();
        final BufferedImage photo = createListingInputData.get_img();
        final List<Category> categories = createListingInputData.get_categories();

        if(createListingDataAccess.nameFieldIsNull(name) || createListingDataAccess.photoFieldIsNull(photo)){
            createListingPresenter.prepareFailView("Item name or photo is missing.");
        }
        else {
            final CreateListingOutputData createListingOutputData;

            if(createListingDataAccess.categoryFieldIsNull(categories)){
                createListingOutputData = new CreateListingOutputData(createListingInputData.get_name(), createListingInputData.get_img());
            }
            else {
                createListingOutputData = new CreateListingOutputData(createListingInputData.get_name(), createListingInputData.get_img(), createListingInputData.get_categories());

            }
            createListingPresenter.prepareSuccessView(createListingOutputData);
        }
    }
}
