package view;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.view_listing.ViewListingController;
import org.json.JSONObject;

import interface_adapter.ViewManagerModel;
import interface_adapter.messaging.MessagingController;
import interface_adapter.messaging.MessagingViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class HomepageView extends JPanel implements PropertyChangeListener {

    private final JPanel optionsPanel;
    private final JPanel homepageContentPanel;
    private JPanel itemsPanel;
    private final JLabel sidebarTitle;
    private final JLabel userGreetingLabel;
    private final JLabel recentItemsLabel;
    private final JButton viewProfileButton;
    private final JButton createListing;
    private final JButton search;
    //private final JButton logout; maybe this could be implemented

    private final String viewName = "logged in";
    private String username = "";
    private final HomepageViewModel homepageViewModel;
    private ViewListingController viewListingController = null;

    private MessagingController messagingController;
    private MessagingViewModel messagingViewModel;
    private ViewManagerModel viewManagerModel;

    public HomepageView(HomepageViewModel homepageViewModel) {

        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(0, 0));

        optionsPanel = new JPanel();
        optionsPanel.setBackground(new Color(128, 128, 128));
        GridBagLayout gbl_optionsPanel = new GridBagLayout();
        gbl_optionsPanel.columnWidths = new int[]{0, 0};
        gbl_optionsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_optionsPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_optionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        optionsPanel.setLayout(gbl_optionsPanel);
        optionsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(optionsPanel, BorderLayout.WEST);

        sidebarTitle = new JLabel("UofTTrade");
        sidebarTitle.setFont(new Font("Rubik", Font.PLAIN, 30));
        GridBagConstraints gbc_sidebarTitle = new GridBagConstraints();
        gbc_sidebarTitle.fill = GridBagConstraints.BOTH;
        gbc_sidebarTitle.insets = new Insets(0, 0, 5, 0);
        gbc_sidebarTitle.gridx = 0;
        gbc_sidebarTitle.gridy = 0;
        optionsPanel.add(sidebarTitle, gbc_sidebarTitle);

        userGreetingLabel = new JLabel("Hello, " + username);
        userGreetingLabel.setFont(new Font("Rubik", Font.PLAIN, 15));
        GridBagConstraints gbc_userGreetingLabel = new GridBagConstraints();
        gbc_userGreetingLabel.insets = new Insets(0, 0, 5, 0);
        gbc_userGreetingLabel.gridx = 0;
        gbc_userGreetingLabel.gridy = 1;
        optionsPanel.add(userGreetingLabel, gbc_userGreetingLabel);

        //View My Profile Button
        viewProfileButton = new JButton("View My Profile");
        GridBagConstraints gbc_viewProfileButton = new GridBagConstraints();
        gbc_viewProfileButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_viewProfileButton.insets = new Insets(0, 0, 5, 0);
        gbc_viewProfileButton.gridx = 0;
        gbc_viewProfileButton.gridy = 3;
        optionsPanel.add(viewProfileButton, gbc_viewProfileButton);

        //Create Listings Button
        createListing = new JButton("Create Listing");
        GridBagConstraints gbc_createListing = new GridBagConstraints();
        gbc_createListing.fill = GridBagConstraints.HORIZONTAL;
        gbc_createListing.insets = new Insets(0, 0, 5, 0);
        gbc_createListing.gridx = 0;
        gbc_createListing.gridy = 5;
        optionsPanel.add(createListing, gbc_createListing);

        //Search Listings Button
        search = new JButton("Search Listings");
        GridBagConstraints gbc_search = new GridBagConstraints();
        gbc_search.fill = GridBagConstraints.BOTH;
        gbc_search.insets = new Insets(0, 0, 5, 0);
        gbc_search.gridx = 0;
        gbc_search.gridy = 9;
        optionsPanel.add(search, gbc_search);

        homepageContentPanel = new JPanel();
        homepageContentPanel.setBackground(new Color(240, 240, 240));
        add(homepageContentPanel, BorderLayout.CENTER);
        homepageContentPanel.setLayout(new BoxLayout(homepageContentPanel, BoxLayout.Y_AXIS));
        recentItemsLabel = new JLabel("Recently Added");
        recentItemsLabel.setFont(new Font("Rubik", Font.PLAIN, 40));
        homepageContentPanel.add(recentItemsLabel);
        recentItemsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    }

    public String getViewName() {
        return viewName;
    }

    public void getListingPanels(List<JSONObject> allListings) {

        itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        itemsPanel.setPreferredSize(new Dimension(1250, 4320));

        for (int i = 0; i < allListings.size(); i++) {
            itemsPanel.add(createItemPanel(allListings.get(i), viewListingController, messagingController,
                    messagingViewModel, viewManagerModel));
        }
        JSONObject obj = new JSONObject();
        List<String> list = new ArrayList<String>();
        list.add("Category 1");
        list.add("Category 2");
        obj.put("Owner", "abcdefghij");
        obj.put("Name", "abcde");
        obj.put("Categories", list);
        itemsPanel.add(createItemPanel(obj, viewListingController, messagingController,
                messagingViewModel, viewManagerModel));
        JScrollPane itemsScroll = new JScrollPane(homepageContentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(itemsScroll, BorderLayout.CENTER);

        homepageContentPanel.add(itemsPanel);
        homepageContentPanel.revalidate();
        homepageContentPanel.repaint();

    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final HomepageState state = (HomepageState) evt.getNewValue();
            username = state.getUsername();
            userGreetingLabel.setText("Hello, " + username);
        }

    }

    public void setViewListingController(ViewListingController viewListingController) {
        this.viewListingController = viewListingController;
    }

    private JPanel createItemPanel(JSONObject listing, ViewListingController viewListingController,
                                   MessagingController messagingController, MessagingViewModel messagingViewModel,
                                   ViewManagerModel viewManagerModel) {

        return new ListingPreviewPanel(listing, viewListingController, messagingController, messagingViewModel,
                viewManagerModel);

    }

    public void addSearchListener(ActionListener listener) {
        search.addActionListener(listener);
    }
    public void addViewProfileListener(ActionListener listener) {
        viewProfileButton.addActionListener(listener);
    }
    public void addCreateListingListener(ActionListener listener) {
        createListing.addActionListener(listener);
    }
    public void setMessagingDependencies(MessagingController messagingController,
                                         MessagingViewModel messagingViewModel,
                                         ViewManagerModel viewManagerModel) {
        this.messagingController = messagingController;
        this.messagingViewModel = messagingViewModel;
        this.viewManagerModel = viewManagerModel;
    }

}
