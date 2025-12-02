package interface_adapter.messaging;

import use_case.messaging.MessagingInputBoundary;
import use_case.messaging.MessagingInputData;

/**
 * The controller for the Messaging use case.
 * This class receives input from the UI layer and converts it into.
 * {@link MessagingInputData} objects, which passed to the {@link  MessagingInputBoundary} to execute.
 */
public class MessagingController {
    /**
     * The interactor responsible for executing the Messaging use case.
     */
    public final MessagingInputBoundary interactor;

    /**
     * Constructs a Messaging Controller with the interactor.
     * @param interactor the input boundary for the Messaging use case.
     */
    public MessagingController(MessagingInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Create a gmail compose link by the given name and email.
     * @param name the name of the seller.
     * @param email the email of the seller.
     */
    public void createGmailComposeLink(String name, String email) {
        MessagingInputData inputData = new MessagingInputData(name, email);
        interactor.execute(inputData);
    }
}
