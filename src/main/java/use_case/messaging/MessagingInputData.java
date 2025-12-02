package use_case.messaging;

/**
 * The input data for executing the Messaging use case
 * This object is created by the controller and passed to the {@link MessagingInputBoundary} to initiate the use case.
 * The parameters are username and the email being contacted
 */
public class MessagingInputData {
    private final String username;
    private final String email;

    /**
     * Constructs a new MessagingInputData object.
     * @param username the username of the user to be contacted, must not be null.
     * @param email the email of the user to be contacted.
     */
    public MessagingInputData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
