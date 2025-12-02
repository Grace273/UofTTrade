package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.messaging.MessagingController;
import interface_adapter.messaging.MessagingViewModel;
import interface_adapter.search.SearchListingsController;
import interface_adapter.search.SearchListingsState;
import interface_adapter.search.SearchListingsViewModel;
import interface_adapter.view_listing.ViewListingController;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SearchListingsView extends JPanel implements PropertyChangeListener {
    public final String viewName = "search listings";
    private final SearchListingsViewModel viewModel;
    private SearchListingsController controller;

    // Dependencies needed for ListingPreviewPanel
    private MessagingController messagingController;
    private MessagingViewModel messagingViewModel;
    private ViewManagerModel viewManagerModel;
    private ViewListingController viewListingController;

    private final JTextField keywordField = new JTextField(15);
    private final JComboBox<String> categoryBox;
    private final JButton searchButton = new JButton("Search");
    private final JButton backButton = new JButton("Back to Home");
    private final JPanel resultsPanel = new JPanel();
    private final JLabel messageLabel = new JLabel("");

    public SearchListingsView(SearchListingsViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // --- Top Panel ---
        final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        categoryBox = new JComboBox<>();
        if (viewModel.getCategories() != null) {
            for (String cat : viewModel.getCategories()) {
                categoryBox.addItem(cat);
            }
        }

        topPanel.add(new JLabel("Keyword:"));
        topPanel.add(keywordField);
        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryBox);
        topPanel.add(searchButton);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // --- Results Panel ---
        resultsPanel.setLayout(new GridLayout(0, 4, 10, 10));
        resultsPanel.setBackground(new Color(245, 245, 245));

        final JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        final JPanel centerContainer = new JPanel(new BorderLayout());
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        centerContainer.add(messageLabel, BorderLayout.NORTH);
        centerContainer.add(scrollPane, BorderLayout.CENTER);
        add(centerContainer, BorderLayout.CENTER);

        // --- Bottom Panel ---
        final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Listeners ---
        searchButton.addActionListener(exception -> {
            if (controller != null) {
                controller.execute(keywordField.getText(), (String) categoryBox.getSelectedItem());
            }
        });
    }

    public void setSearchListingsController(SearchListingsController controller) {
        this.controller = controller;
    }

    /**
     * Injects the dependencies required for this controller to handle messaging
     * and view transitions.
     *
     * @param messagingController  the controller responsible for messaging actions
     * @param messagingViewModel   the view model holding messaging state
     * @param viewManagerModel     the model that manages the active view
     * @param viewListingController the controller used to display listing details
     */
    public void setDependencies(MessagingController messagingController,
                                MessagingViewModel messagingViewModel,
                                ViewManagerModel viewManagerModel,
                                ViewListingController viewListingController) {
        this.messagingController = messagingController;
        this.messagingViewModel = messagingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewListingController = viewListingController;
    }

    /**
     * Adds the given listener to be notified when the back button is pressed.
     *
     * @param listener the action listener to attach to the back button
     */
    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            updateView((SearchListingsState) evt.getNewValue());
        }
    }

    /**
     * Triggers a search for all listings (empty keyword, default category).
     * Call this after the view is fully initialized.
     */
    public void loadAllListings() {
        if (controller != null) {
            // "Select a Category" is your default "All Categories" option
            controller.execute("", "Select a Category");
        }
    }

    private void updateView(SearchListingsState state) {
        resultsPanel.removeAll();
        final List<SearchListingsState.ListingViewModel> results = state.getResults();

        if (results.isEmpty()) {
            final String error = state.getErrorMessage();
            messageLabel.setText((error != null && !error.isEmpty()) ? error : "No results found.");
            messageLabel.setForeground((error != null && !error.isEmpty()) ? Color.RED : Color.BLACK);
        }
        else {
            final String msg = state.isShowingFallbackResults()
                    ? "No exact match. Showing: " + state.getCategoryName()
                    : "Results found: " + results.size();
            messageLabel.setText(msg);
            messageLabel.setForeground(Color.BLACK);

            for (SearchListingsState.ListingViewModel item : results) {
                final JSONObject json = new JSONObject();
                json.put("Name", item.getName());
                json.put("Owner", item.getOwner());
                json.put("Description", item.getDescription());

                // Convert comma-separated string back to JSONArray for the panel
                final JSONArray catArray = new JSONArray();
                final String[] cats = item.getCategorySummary().split(", ");
                for (String c : cats) {
                    catArray.put(c);
                }
                json.put("Categories", catArray);

                resultsPanel.add(new ListingPreviewPanel(
                        json,
                        viewListingController,
                        messagingController,
                        messagingViewModel,
                        viewManagerModel
                ));
            }
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}
