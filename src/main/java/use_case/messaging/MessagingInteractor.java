package use_case.messaging;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * The interactor for the Messaging use case.
 * The class implements the application logic of generating a gmail link to compose message to the seller.
 * It verifies that a corrected formated email is provided and retrieves if none email is provided.
 * Then it composes a gmail url for the email given.
 */
public class MessagingInteractor implements MessagingInputBoundary {
    private final MessagingUserDataAccessInterface userDataAccess;
    private final MessagingOutputBoundary presenter;

    /**
     * Constructs a MessagingInteractor with the data access and output.
     * @param userDataAccess the DAO to retrieve or verify the email, must not be null.
     * @param presenter the output boundary used to present success or failure view, must not be null.
     */
    public MessagingInteractor(MessagingUserDataAccessInterface userDataAccess,
                               MessagingOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(MessagingInputData inputData) {
        try {
            String name = inputData.getUsername();
            String email = inputData.getEmail();

            if (email == null || email.isBlank()) {
                email = userDataAccess.getValidEmailForUser(name);
            }

            if (email == null || !userDataAccess.isPlasuibleEmail(email)) {
                presenter.presentFailureView("Invalid email address for user: " + name);
                return;
            }

            String gmailUrl = buildGmailComposeUrl(
                    email,
                    "Contact " + name,
                    ""
            );

            MessagingOutputData outputData =
                    new MessagingOutputData(name, gmailUrl);

            presenter.presentSuccessView(outputData);

        } catch (Exception e) {
            presenter.presentFailureView("Failed to create Gmail link: " + e.getMessage());
        }
    }

    private String buildGmailComposeUrl(String receiver, String subject, String body) {
        final String encTo = URLEncoder.encode(receiver == null ? "" : receiver, StandardCharsets.UTF_8);
        final String encSubject = URLEncoder.encode(subject == null ? "" : subject, StandardCharsets.UTF_8);
        final String encBody = URLEncoder.encode(body == null ? "" : body, StandardCharsets.UTF_8);

        return "https://mail.google.com/mail/?view=cm&fs=1"
                + "&to=" + encTo
                + "&su=" + encSubject
                + "&body=" + encBody;
    }
}

