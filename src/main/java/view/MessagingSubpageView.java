package view;

import interface_adapter.messaging.MessagingViewModel;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class MessagingSubpageView extends JPanel {

    private final MessagingViewModel viewModel;

    private final JLabel titleLabel;
    private final JLabel infoLabel;
    private final JLabel errorLabel;
    private final JButton openGmailButton;
    private final JButton backButton;

    /**
     * @param viewModel MessagingViewModel
     * @param onBack    Return to former page when clicking the Back button.
     */
    public MessagingSubpageView(MessagingViewModel viewModel, Runnable onBack) {
        this.viewModel = viewModel;

        //Initialize the board
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        titleLabel = new JLabel("Email");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        add(titleLabel, BorderLayout.NORTH);

        // Middle
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        infoLabel = new JLabel("Click the button to Open Gmail in a new page.");
        openGmailButton = new JButton("Compose the email in Gmail.");
        openGmailButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(infoLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(openGmailButton);

        add(centerPanel, BorderLayout.CENTER);

        // Button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        backButton = new JButton("返回");
        bottomPanel.add(errorLabel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);


        openGmailButton.addActionListener(e -> openGmail());

        backButton.addActionListener(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });

        this.viewModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                refreshFromState();
            }
        });
        refreshFromState();
    }

    /**
     * Read the data from the ViewModel.State and refresh the page
     */
    private void refreshFromState() {
        MessagingViewModel.State s = viewModel.getState();
        if (s == null) return;

        titleLabel.setText(s.title == null ? "Email" : s.title);
        errorLabel.setText(s.error == null ? "" : s.error);

        boolean hasUrl = s.gmailUrl != null && !s.gmailUrl.isBlank();
        openGmailButton.setEnabled(hasUrl);

        if (!hasUrl) {
            infoLabel.setText("Unavailable gmail link, please try again.");
        } else {
            infoLabel.setText("Click to compose the email.");
        }
    }

    /**
     * Open the Gmail through the default server
     */
    private void openGmail() {
        MessagingViewModel.State s = viewModel.getState();
        if (s == null || s.gmailUrl == null || s.gmailUrl.isBlank()) {
            errorLabel.setText("No gmail link available.");
            return;
        }

        try {
            if (!Desktop.isDesktopSupported()) {
                errorLabel.setText("Unsupported desktop.");
                return;
            }
            Desktop.getDesktop().browse(new URI(s.gmailUrl));
        } catch (Exception ex) {
            errorLabel.setText("Unable to open Gmail: " + ex.getMessage());
        }
    }
}
