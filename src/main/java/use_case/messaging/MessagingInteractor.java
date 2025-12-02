package use_case.messaging;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * The interactor for the Messaging use case.
 * The class implements the application logic of generating a gmail link to compose message.
 * It verifies that a corrected formated email is provided and retrieves if none email is provided.
 * Then it composes a gmail url for the email given.
 */
public class MessagingInteractor implements MessagingInputBoundary {
    private final MessagingUserDataAccessInterface userDataAccess;
    private final MessagingOutputBoundary presenter;

    /**
     * Constructs a MessagingInteractor with the data access and output.
     *
     * @param userDataAccess the DAO to retrieve or verify the email, must not be null.
     *
     * @param presenter the output boundary used to present success or failure view；
     *                 must not be null.
     */
    public MessagingInteractor(MessagingUserDataAccessInterface userDataAccess,
                               MessagingOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
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
                final String gmailUrl = buildGmailComposeUrl(
                        email,
                        "Contact " + name
                );

                final MessagingOutputData outputData =
                        new MessagingOutputData(name, gmailUrl);

                presenter.presentSuccessView(outputData);
            }

        }
        catch (IOException ioException) {
            presenter.presentFailureView("Failed to create Gmail link: " + ioException.getMessage());
        }
    }

    private String buildGmailComposeUrl(String receiver, String subject) {
        final String encTo;
        if (receiver == null) {
            encTo = "";
        }
        else {
            encTo = URLEncoder.encode(receiver, StandardCharsets.UTF_8);
        }

        final String encSubject;
        if (subject == null) {
            encSubject = "";
        }
        else {
            encSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8);
        }

        final String encBody;
        encBody = URLEncoder.encode("", StandardCharsets.UTF_8);

        return "https://mail.google.com/mail/?view=cm&fs=1&to=" + encTo
                + "&su=" + encSubject
                + "&body=" + encBody;
    }

}

