package view;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;
    private LoginController loginController = null;
    private final JPanel imagePanel;
    private final JLabel imageLabel;
    private final JLabel titleLabel;
    private final JLabel userIdentifierLabel;
    private final JTextField userIdentifierField;
    private final JLabel passwordLabel;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final JLabel usernameErrorField = new JLabel();
    private final JPanel loginFormPanel;
    private final JPanel loginInfoPanel;


    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        setBounds(100, 100, 450, 300);
        setBackground(new Color(255, 255, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        imagePanel = new JPanel();
        imagePanel.setBackground(new Color(128, 128, 255));
        add(imagePanel, BorderLayout.WEST);
        imagePanel.setLayout(new BorderLayout(0, 0));

        imageLabel = new JLabel("");
        imageLabel.setIcon(new ImageIcon(
                getClass().getResource("/images/istockphoto-157334765-612x612.jpg")
        ));
        imagePanel.add(imageLabel);

        loginFormPanel = new JPanel();
        loginFormPanel.setBackground(new Color(255, 255, 255));
        add(loginFormPanel, BorderLayout.CENTER);
        loginFormPanel.setLayout(new BorderLayout(0, 0));

        titleLabel = new JLabel("UofTTrade Login");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Rubik", Font.BOLD, 48));
        loginFormPanel.add(titleLabel, BorderLayout.NORTH);

        loginInfoPanel = new JPanel();
        loginInfoPanel.setBackground(new Color(255, 255, 255));
        loginFormPanel.add(loginInfoPanel, BorderLayout.CENTER);
        GridBagLayout gbl_loginInfoPanel = new GridBagLayout();
        gbl_loginInfoPanel.columnWidths = new int[]{0, 0};
        gbl_loginInfoPanel.rowHeights = new int[]{250, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_loginInfoPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_loginInfoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        loginInfoPanel.setLayout(gbl_loginInfoPanel);

        userIdentifierLabel = new JLabel("Username/Email:");
        userIdentifierLabel.setFont(new Font("Rubik", Font.PLAIN, 36));
        GridBagConstraints gbc_userIdentifierLabel = new GridBagConstraints();
        gbc_userIdentifierLabel.insets = new Insets(5, 5, 5, 5);
        gbc_userIdentifierLabel.gridx = 0;
        gbc_userIdentifierLabel.gridy = 4;
        loginInfoPanel.add(userIdentifierLabel, gbc_userIdentifierLabel);

        userIdentifierField = new JTextField();
        userIdentifierField.setFont(new Font("Rubik", Font.PLAIN, 24));
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(5, 5, 5, 5);
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 5;
        loginInfoPanel.add(userIdentifierField, gbc_textField);
        userIdentifierField.setColumns(15);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Rubik", Font.PLAIN, 36));
        GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
        gbc_passwordLabel.insets = new Insets(5, 5, 5, 5);
        gbc_passwordLabel.gridx = 0;
        gbc_passwordLabel.gridy = 7;
        loginInfoPanel.add(passwordLabel, gbc_passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Rubik", Font.PLAIN, 24));
        passwordField.setColumns(15);
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.insets = new Insets(5, 5, 5, 5);
        gbc_passwordField.gridx = 0;
        gbc_passwordField.gridy = 8;
        loginInfoPanel.add(passwordField, gbc_passwordField);

        usernameErrorField.setFont(new Font("Rubik", Font.PLAIN, 12));
        GridBagConstraints gbc_usernameErrorField = new GridBagConstraints();
        gbc_usernameErrorField.insets = new Insets(5, 5, 5, 5);
        gbc_usernameErrorField.gridx = 0;
        gbc_usernameErrorField.gridy = 10;
        loginInfoPanel.add(usernameErrorField, gbc_usernameErrorField);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(128, 128, 255));
        loginButton.setFont(new Font("Rubik", Font.PLAIN, 16));
        GridBagConstraints gbc_loginButton = new GridBagConstraints();
        gbc_loginButton.insets = new Insets(5, 5, 5, 5);
        gbc_loginButton.gridx = 0;
        gbc_loginButton.gridy = 12;
        loginInfoPanel.add(loginButton, gbc_loginButton);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 128, 0));
        registerButton.setFont(new Font("Rubik", Font.PLAIN, 16));
        GridBagConstraints gbc_registerButton = new GridBagConstraints();
        gbc_registerButton.insets = new Insets(5, 5, 5, 5);
        gbc_registerButton.gridx = 0;
        gbc_registerButton.gridy = 14;
        loginInfoPanel.add(registerButton, gbc_registerButton);

        loginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(loginButton)) {
                            final LoginState currentState = loginViewModel.getState();

                            try {
                                loginController.executeLogin(
                                        currentState.getUserIdentifier(),
                                        currentState.getPassword()
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );

        registerButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(registerButton)) {
                            final LoginState currentState = loginViewModel.getState();
                            loginController.switchToRegisterView();
                        }
                    }
                }
        );

        userIdentifierField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUserIdentifier(userIdentifierField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        userIdentifierField.setText(state.getUserIdentifier());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
