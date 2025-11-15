package use_case.messaging;

public class MessagingOutputData {
    private final String name;
    private final String normalizedurl;
    public  MessagingOutputData(String name, String normalizedurl) {
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
