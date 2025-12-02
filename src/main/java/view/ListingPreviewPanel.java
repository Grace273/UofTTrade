package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.messaging.MessagingController;
import interface_adapter.messaging.MessagingViewModel;
import interface_adapter.view_listing.ViewListingController;
import interface_adapter.view_listing.ViewListingPresenter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

public class ListingPreviewPanel extends JPanel {

    private static final int TITLE_FONT_SIZE = 14;
    private static final int OTHER_FONT_SIZE = 10;
    private static final int PANEL_WIDTH = 150;
    private static final int PANEL_HEIGHT = 300;
    private static final String FONT = "Rubik";
    private final JSONObject listing;
    private final ViewListingController viewListingController;
    private final MessagingController messagingController;
    private final MessagingViewModel messagingViewModel;
    private final ViewManagerModel viewManagerModel;

    public ListingPreviewPanel(JSONObject listing, ViewListingController viewListingController,
                               MessagingController messagingController, MessagingViewModel messagingViewModel,
                               ViewManagerModel viewManagerModel) {
        this.listing = listing;
        this.viewListingController = viewListingController;
        this.messagingController = messagingController;
        this.messagingViewModel = messagingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        final String OWNER_NAME = (String) listing.get("Owner");
        final JLabel nameLabel = new JLabel((String) listing.get("Name"));
        nameLabel.setFont(new Font(FONT, Font.BOLD, TITLE_FONT_SIZE));
        final StringBuilder categories = new StringBuilder();
        final JSONArray categoryList = (JSONArray) listing.get("Categories");
        for (int i = 0; i < categoryList.length() - 1; i++) {

            categories.append((String) categoryList.get(i)).append(", ");
        }
        categories.append((String) categoryList.get(categoryList.length() - 1));
        final JLabel categoryLabel = new JLabel("Categories: " + categories);
        categoryLabel.setFont(new Font(FONT, Font.PLAIN, OTHER_FONT_SIZE));

        final JLabel ownerLabel = new JLabel("Owner: " + OWNER_NAME);
        ownerLabel.setFont(new Font(FONT, Font.PLAIN, OTHER_FONT_SIZE));

        JButton contactButton = new JButton("Contact seller");
        contactButton.addActionListener(event -> {
            if (messagingController == null || messagingViewModel == null || viewManagerModel == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Messaging is not available yet",
                        "error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            final String sellerIdentifier = OWNER_NAME;
            final String sellerEmail = listing.optString("Email", null);
            messagingController.createGmailComposeLink(sellerIdentifier, sellerEmail);

            viewManagerModel.setState(MessagingViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        this.add(nameLabel);
        this.add(categoryLabel);
        this.add(ownerLabel);
        this.add(contactButton);

        final MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    viewListingController.execute((String) listing.get("Name"), (String) listing.get("Owner"));
                }
                catch (ViewListingPresenter.ListingNotFoundException | IOException _) {
                    JOptionPane.showMessageDialog(null, "The listing you're looking for does "
                            + "not exist", "Listing Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        this.addMouseListener(ml);

    }

}
