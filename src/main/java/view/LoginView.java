package view;
import use_case.login.LoginInputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel {

    private final JTextField usernameText = new JTextField(15);
    private final JPasswordField passwordText = new JPasswordField(15);

    private final JButton loginButton;
    private final JButton registerButton;

    public LoginView() {

        final JLabel title =  new JLabel("UofTTrade Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Username"));
        usernamePanel.add(usernameText);

        final JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password"));
        passwordPanel.add(passwordText);

        final JPanel buttonsPanel = new JPanel();
        registerButton = new JButton("Register");
        loginButton = new JButton("Login");
        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);

    }

}
