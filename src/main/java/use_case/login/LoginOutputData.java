package use_case.login;

/**
 * The data outputted by the login use case
 */

public class LoginOutputData {

    private final String username, email;

    public LoginOutputData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {return username;}

    public String getEmail() {return email;}
}
