package entity;

public class Messaging {
    private final String name;
    private final String url;
    private final String source;
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
