package entity;

/**
 * Factory interface for creating Messaging entities.
 * This is the "Factory Method" creator.
 */
public interface MessagingFactory {

    /**
     * Create a Messaging entity for contacting a user.
     *
     * @param name  the recipient's name or username
     * @param email the recipient's email address
     * @return a Messaging entity containing the URL and metadata
     */
    Messaging createMessaging(String name, String email);
}
