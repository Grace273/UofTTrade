package view;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomepageView extends JPanel implements PropertyChangeListener {

    private final JPanel optionsPanel;
    private final JPanel homepageContentPanel;
    private final JPanel itemsPanel;
    private final JLabel sidebarTitle;
    private final JLabel userGreetingLabel;
    private final JLabel recentItemsLabel;
    private final JButton viewProfileButton;
    private final JButton search;

    private final String viewName = "logged in";
    private String username = "";
    private final HomepageViewModel homepageViewModel;

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

        viewProfileButton = new JButton("View My Profile");
        GridBagConstraints gbc_viewProfileButton = new GridBagConstraints();
        gbc_viewProfileButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_viewProfileButton.insets = new Insets(0, 0, 5, 0);
        gbc_viewProfileButton.gridx = 0;
        gbc_viewProfileButton.gridy = 3;
        optionsPanel.add(viewProfileButton, gbc_viewProfileButton);

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
        itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        itemsPanel.setPreferredSize(new Dimension(1250, 4320));


        for (int i = 1; i <= 100; i++) {
            itemsPanel.add(createItemPanel("Item " + i, "This is the description for item " + i));
        }
        JScrollPane itemsScroll = new JScrollPane(homepageContentPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(itemsScroll, BorderLayout.CENTER);

        homepageContentPanel.add(itemsPanel);
        homepageContentPanel.revalidate();
        homepageContentPanel.repaint();

    }

    public String getViewName() {
        return viewName;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final HomepageState state = (HomepageState) evt.getNewValue();
            username = state.getUsername();
            userGreetingLabel.setText("Hello, " + username);
        }



    }

    private static JPanel createItemPanel(String name, String description) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(new Color(255, 255, 255));
        panel.setPreferredSize(new Dimension(150, 300));
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Rubik", Font.BOLD, 14));
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Rubik", Font.PLAIN, 10));
        panel.add(nameLabel);
        panel.add(descLabel);

        return panel;

    }

    public void addViewProfileListener(ActionListener listener) {
        viewProfileButton.addActionListener(listener);
    }

}
