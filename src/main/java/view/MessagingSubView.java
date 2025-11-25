package view;

import interface_adapter.messaging.MessagingViewModel;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class MessagingSubView extends JPanel {

    private final MessagingViewModel viewModel;

    private final JLabel titleLabel;
    private final JLabel infoLabel;
    private final JLabel errorLabel;
    private final JButton openGmailButton;
    private final JButton backButton;

    /**
     * @param viewModel MessagingViewModel
     * @param onBack
     */
    public MessagingSubView(MessagingViewModel viewModel, Runnable onBack) {
        this.viewModel = viewModel;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("Email");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        infoLabel = new JLabel("点击下方按钮在 Gmail 中撰写邮件。");
        openGmailButton = new JButton("在 Gmail 中写邮件");
        openGmailButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(infoLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(openGmailButton);

        add(centerPanel, BorderLayout.CENTER);

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
     * Get data from ViewModel.State
     */
    private void refreshFromState() {
        MessagingViewModel.State s = viewModel.getState();
        if (s == null) return;

        titleLabel.setText(s.title == null ? "Email" : s.title);
        errorLabel.setText(s.error == null ? "" : s.error);


        boolean hasUrl = s.gmailUrl != null && !s.gmailUrl.isBlank();
        openGmailButton.setEnabled(hasUrl);

        if (!hasUrl) {
            infoLabel.setText("No gmail exists.");
        } else {
            infoLabel.setText("Open gmail to write the message.");
        }
    }

    /**
     * Open Gmail compose URL
     */
    private void openGmail() {
        MessagingViewModel.State s = viewModel.getState();
        if (s == null || s.gmailUrl == null || s.gmailUrl.isBlank()) {
            errorLabel.setText("No gmail exists.");
            return;
        }

        try {
            if (!Desktop.isDesktopSupported()) {
                errorLabel.setText("Unsupport enviornment for Gmail");
                return;
            }
            Desktop.getDesktop().browse(new URI(s.gmailUrl));
        } catch (Exception ex) {
            errorLabel.setText("Unable to Open Gmail" +
                    ": " + ex.getMessage());
        }
    }
}
