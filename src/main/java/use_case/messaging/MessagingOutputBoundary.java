package use_case.messaging;

/**
 * The output boundary for the Messaging use case.
 * This interface shows the interactor can either present a successful view or a failure view.
 */
public interface MessagingOutputBoundary {
    /**
     * Presents the successful view of Messaging.
     * @param data the output data containing the username and generated gmail url.
     */
    void presentSuccessView(MessagingOutputData data);

    /**
     * Presents the failure view of Messaging.
     * @param errorMessage an error message shows that the use case is failed.
     */
    void presentFailureView(String errorMessage);
}

