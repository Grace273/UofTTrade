package entity;

/**
 * Represents a Messaging entity.
 * Though it's not used for now,
 * but it will work when there are other source of messaging, like Facebook.
 */
public class Messaging {

    private final String name;
    private final String url;
    private final String source;

    /**
     * Constructs a new Messaging entity.
     * @param name the username of the seller.
     * @param url the contact url.
     * @param source the source describing where the message data is from.
     */
    public Messaging(String name, String url, String source) {
        this.name = name;
        this.url = url;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }
}
