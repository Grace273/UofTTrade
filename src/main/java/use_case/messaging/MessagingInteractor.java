package use_case.messaging;

import entity.Messaging;
import entity.MessagingFactory;

import java.io.IOException;

/**
 * Interactor for the Messaging use case.
 */
public class MessagingInteractor implements MessagingInputBoundary {

    private final MessagingUserDataAccessInterface userDataAccess;
    private final MessagingOutputBoundary presenter;
    private final MessagingFactory messagingFactory;

    public MessagingInteractor(MessagingUserDataAccessInterface userDataAccess,
                               MessagingOutputBoundary presenter,
                               MessagingFactory messagingFactory) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
        this.messagingFactory = messagingFactory;
    }

    @Override
    public void execute(MessagingInputData inputData) {
        try {
            final String name = inputData.getUsername();
            String email = inputData.getEmail();

            if (email == null || email.isBlank()) {
                email = userDataAccess.getValidEmailForUser(name);
            }

            if (email == null || !userDataAccess.isPlasuibleEmail(email)) {
                presenter.presentFailureView("Invalid email address for user: " + name);
            }
            else {
                // take it into Messaging factory method
                final Messaging messaging = messagingFactory.createMessaging(name, email);

                final MessagingOutputData outputData =
                        new MessagingOutputData(messaging.getName(), messaging.getUrl());

                presenter.presentSuccessView(outputData);
            }
        }
        catch (IOException exception) {
            presenter.presentFailureView("Failed to create Gmail link: " + exception.getMessage());
        }
    }
}
