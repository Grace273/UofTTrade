package use_case.login;

/**
 * The input data for a user to login in to the program
 */

public class LoginInputData {

    private final String username;
    private final String password;
    private final String email;

    public LoginInputData(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getEmail() {
        return email;
    }


}
