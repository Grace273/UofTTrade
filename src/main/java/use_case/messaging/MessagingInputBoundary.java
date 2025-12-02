package use_case.messaging;

/**
 * The input boundary for the Messaging use case.
 * This interface represents the entry point for the Messaging Interactor.
 */
public interface MessagingInputBoundary {
    /**
     * Executes the Messaging use case.
     * @param messagingInputData the data required for executing the use case, must not be null
     */
    void execute(use_case.messaging.MessagingInputData messagingInputData);
}
