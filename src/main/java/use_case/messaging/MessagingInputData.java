package use_case.messaging;

public class MessagingInputData {
    private final String url;
    private final String name;
    public MessagingInputData(String url, String name) {
    this.url = url;
    this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }
}
