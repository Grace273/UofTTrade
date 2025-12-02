package use_case.messaging;

/**
 * The output data for the Messaging use case
 * This data transfer object is created by the interactor.
 * It contains the normalized gmail url and the name of the user being contacted
 * It passes to the MessagingOutputBoundary.
 */
public class MessagingOutputData {
    private final String name;
    private final String normalizedurl;

    /**
     * Constructs a MessagingOutputData object.
     * @param name the username of the seller.
     * @param normalizedurl the normalized gmail url for user to compose.
     */
    public MessagingOutputData(String name, String normalizedurl) {
        this.name = name;
        this.normalizedurl = normalizedurl;
    }

    public String getName() {
        return name;
    }

    public String getNormalizedurl() {
        return normalizedurl;
    }
}
