package interface_adapter.login;

/**
 * The state for the login view
 */

public class LoginState {

    private String username = "";
    private String email = "";
    private String loginError;
    private String password = "";

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
