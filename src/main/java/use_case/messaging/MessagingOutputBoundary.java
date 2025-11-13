package use_case.messaging;

import use_case.create_listing.CreateListingOutputData;

public interface MessagingOutputBoundary {
 void presentsuccessview(CreateListingOutputData outputData);
 void presentfailureview(CreateListingOutputData outputData);
}

