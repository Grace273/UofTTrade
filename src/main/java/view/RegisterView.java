package view;

import interface_adapter.register.RegisterController;
import interface_adapter.register.RegisterState;
import interface_adapter.register.RegisterViewModel;

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

public class RegisterView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "register";
    private RegisterController registerController = null;
    private RegisterViewModel registerViewModel;
    private final JPanel imagePanel;
    private final JPanel registerFormPanel;
    private final JPanel registerInfoPanel;
    private final JLabel usernameLabel;
    private final JLabel emailLabel;
    private final JLabel passwordLabel;
    private final JLabel repeatPasswordLabel;
    private final JLabel titleLabel;
    private final JLabel imageLabel;
    private final JTextField emailField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField repeatPasswordField;
    private final JButton registerButton;
    private final JButton loginButton;


    public RegisterView(RegisterViewModel registerViewModel) {

        this.registerViewModel = registerViewModel;
        registerViewModel.addPropertyChangeListener(this);

        setBackground(new Color(255, 255, 255));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout(0, 0));

        imagePanel = new JPanel();
        imagePanel.setBackground(new Color(128, 128, 255));
        add(imagePanel, BorderLayout.WEST);
        imagePanel.setLayout(new BorderLayout(0, 0));

        imageLabel = new JLabel("");
        imageLabel.setIcon(
                new ImageIcon(getClass().getResource("/images/university-of-toronto-01.jpg"))
        );        imagePanel.add(imageLabel);

        registerFormPanel = new JPanel();
        registerFormPanel.setBackground(new Color(255, 255, 255));
        add(registerFormPanel, BorderLayout.CENTER);
        registerFormPanel.setLayout(new BorderLayout(0, 0));

        titleLabel = new JLabel("UofTTrade Register");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Rubik", Font.BOLD, 48));
        registerFormPanel.add(titleLabel, BorderLayout.NORTH);

        registerInfoPanel = new JPanel();
        registerInfoPanel.setBackground(new Color(255, 255, 255));
        registerFormPanel.add(registerInfoPanel, BorderLayout.CENTER);
        GridBagLayout gbl_registerInfoPanel = new GridBagLayout();
        gbl_registerInfoPanel.columnWidths = new int[]{0, 0};
        gbl_registerInfoPanel.rowHeights = new int[]{125, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_registerInfoPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_registerInfoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        registerInfoPanel.setLayout(gbl_registerInfoPanel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Rubik", Font.PLAIN, 36));
        GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
        gbc_usernameLabel.insets = new Insets(5, 5, 5, 0);
        gbc_usernameLabel.gridx = 0;
        gbc_usernameLabel.gridy = 2;
        registerInfoPanel.add(usernameLabel, gbc_usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Rubik", Font.PLAIN, 24));
        GridBagConstraints gbc_usernameField = new GridBagConstraints();
        gbc_usernameField.insets = new Insets(5, 5, 5, 0);
        gbc_usernameField.gridx = 0;
        gbc_usernameField.gridy = 3;
        registerInfoPanel.add(usernameField, gbc_usernameField);
        usernameField.setColumns(15);

        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Rubik", Font.PLAIN, 36));
        GridBagConstraints gbc_emailLabel = new GridBagConstraints();
        gbc_emailLabel.insets = new Insets(5, 5, 5, 0);
        gbc_emailLabel.gridx = 0;
        gbc_emailLabel.gridy = 4;
        registerInfoPanel.add(emailLabel, gbc_emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Rubik", Font.PLAIN, 24));
        GridBagConstraints gbc_emailField = new GridBagConstraints();
        gbc_emailField.insets = new Insets(5, 5, 5, 0);
        gbc_emailField.gridx = 0;
        gbc_emailField.gridy = 5;
        registerInfoPanel.add(emailField, gbc_emailField);
        emailField.setColumns(15);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Rubik", Font.PLAIN, 36));
        GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
        gbc_passwordLabel.insets = new Insets(5, 5, 5, 0);
        gbc_passwordLabel.gridx = 0;
        gbc_passwordLabel.gridy = 6;
        registerInfoPanel.add(passwordLabel, gbc_passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Rubik", Font.PLAIN, 24));
        passwordField.setColumns(15);
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.insets = new Insets(5, 5, 5, 0);
        gbc_passwordField.gridx = 0;
        gbc_passwordField.gridy = 7;
        registerInfoPanel.add(passwordField, gbc_passwordField);

        repeatPasswordLabel = new JLabel("Confirm Password:");
        repeatPasswordLabel.setFont(new Font("Rubik", Font.PLAIN, 36));
        GridBagConstraints gbc_repeatPasswordLabel = new GridBagConstraints();
        gbc_repeatPasswordLabel.insets = new Insets(5, 5, 5, 0);
        gbc_repeatPasswordLabel.gridx = 0;
        gbc_repeatPasswordLabel.gridy = 8;
        registerInfoPanel.add(repeatPasswordLabel, gbc_repeatPasswordLabel);

        repeatPasswordField = new JPasswordField();
        repeatPasswordField.setFont(new Font("Rubik", Font.PLAIN, 24));
        repeatPasswordField.setColumns(15);
        GridBagConstraints gbc_repeatPasswordField = new GridBagConstraints();
        gbc_repeatPasswordField.insets = new Insets(5, 5, 5, 0);
        gbc_repeatPasswordField.gridx = 0;
        gbc_repeatPasswordField.gridy = 9;
        registerInfoPanel.add(repeatPasswordField, gbc_repeatPasswordField);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 128, 0));
        registerButton.setFont(new Font("Rubik", Font.PLAIN, 16));
        GridBagConstraints gbc_registerButton = new GridBagConstraints();
        gbc_registerButton.insets = new Insets(5, 5, 5, 0);
        gbc_registerButton.gridx = 0;
        gbc_registerButton.gridy = 11;
        registerInfoPanel.add(registerButton, gbc_registerButton);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(128, 128, 255));
        loginButton.setFont(new Font("Rubik", Font.PLAIN, 16));
        GridBagConstraints gbc_loginButton = new GridBagConstraints();
        gbc_loginButton.insets = new Insets(5, 5, 5, 0);
        gbc_loginButton.gridx = 0;
        gbc_loginButton.gridy = 12;
        registerInfoPanel.add(loginButton, gbc_loginButton);

        addUsernameListener();
        addEmailListener();
        addPasswordListener();
        addRepeatPasswordListener();

        registerButton.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(registerButton)) {
                            final RegisterState currentState = registerViewModel.getState();

                            try {
                                registerController.execute(
                                        currentState.getUsername(),
                                        currentState.getEmail(),
                                        currentState.getPassword(),
                                        currentState.getRepeatPassword()
                                );

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        loginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        registerController.switchToLoginView();
                    }
                }
        );

    }

    private void addUsernameListener() {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setUsername(usernameField.getText());
                registerViewModel.setState(currentState);
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

    private void addEmailListener() {
        emailField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setEmail(emailField.getText());
                registerViewModel.setState(currentState);
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

    private void addPasswordListener() {
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                registerViewModel.setState(currentState);
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

    private void addRepeatPasswordListener() {
        repeatPasswordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordField.getPassword()));
                registerViewModel.setState(currentState);
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

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RegisterState state = (RegisterState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setRegisterController(RegisterController controller) {
        this.registerController = controller;
    }


}
