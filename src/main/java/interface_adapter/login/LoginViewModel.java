package interface_adapter.login;

import interface_adapter.ViewModel;

/**
 * The view model for the login view
 */

public class LoginViewModel extends ViewModel<LoginState> {

    public LoginViewModel() {
        super("log in");
        setState(new LoginState());
    }

}
