package entity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Concrete factory that creates Messaging entities for Gmail compose links.
 */
public class GmailMessagingFactory implements MessagingFactory {

    @Override
    public Messaging createMessaging(String name, String email) {
        final String subject = "Contact " + name;
        final String body = "";

        final String encTo = safeEncode(email);
        final String encSubject = safeEncode(subject);
        final String encBody = safeEncode(body);

        final String url = "https://mail.google.com/mail/?view=cm&fs=1"
                + "&to=" + encTo
                + "&su=" + encSubject
                + "&body=" + encBody;

        return new Messaging(name, url, "gmail");
    }

    /**
     * Encode text for use in URL query parameters. Null is treated as empty string.
     *
     * @param text input text
     * @return encoded text, or empty string if input is null
     */
    private String safeEncode(String text) {
        if (text == null) {
            return "";
        }
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
