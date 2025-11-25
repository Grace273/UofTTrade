package view;

import interface_adapter.view_profile.ViewProfileController;
import interface_adapter.view_profile.ViewProfileState;
import interface_adapter.view_profile.ViewProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Consumer;

/**
 * The ProfileView is responsible for rendering the user's profile screen.
 * <p>
 * displays:
 * <ul>
 *     <li>The username title </li>
 *     <li>A scrollable list of listings, each with its photo and a delete button</li>
 *     <li>A "Create Listing" button</li>
 *     <li>A "Home" button</li>
 *     <li>An error message when profile loading fails</li>
 * </ul>
 *
 * This class listens to changes in the {@link ViewProfileViewModel}
 * and updates the Swing components accordingly.
 */
public class ProfileView extends JPanel implements PropertyChangeListener {

    private final ViewProfileViewModel viewModel;
    private final ViewProfileController profileController;

    /** Callback for Create Listing use case (from outside). */
    private final Runnable onCreateListing;

    /** Callback for Home button navigation. */
    private final Runnable onHome;

    /** Callback for Delete Listing use case, accepts listing name. */
    private final Consumer<String> onDeleteListing;

    // UI Components
    private final JLabel titleLabel = new JLabel();
    private final JLabel noListingsLabel = new JLabel();   // << added
    private final JLabel errorLabel = new JLabel();
    private final JPanel listingsPanel = new JPanel();

    /**
     * Constructs a ProfileView screen.
     *
     * @param viewModel          the ViewModel holding the profile state
     * @param controller         the View Profile controller to trigger the use case
     * @param onCreateListing    callback executed when Create Listing is pressed
     * @param onHome             callback executed when Home is pressed
     * @param onDeleteListing    callback executed when Delete is pressed for a specific listing
     */
    public ProfileView(ViewProfileViewModel viewModel,
                       ViewProfileController controller,
                       Runnable onCreateListing,
                       Runnable onHome,
                       Consumer<String> onDeleteListing) {

        this.viewModel = viewModel;
        this.profileController = controller;
        this.onCreateListing = onCreateListing;
        this.onHome = onHome;
        this.onDeleteListing = onDeleteListing;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // ----- Title area -----
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        noListingsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        noListingsLabel.setForeground(Color.GRAY);
        noListingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(noListingsLabel);

        add(titlePanel, BorderLayout.NORTH);

        // ----- Error label -----
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(errorLabel, BorderLayout.SOUTH);

        // ----- Listings panel -----
        listingsPanel.setLayout(new BoxLayout(listingsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listingsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // ----- Bottom buttons -----
        JPanel buttonPanel = new JPanel();

        JButton createBtn = new JButton("Create Listing");
        JButton homeBtn = new JButton("Home");

        createBtn.addActionListener(e -> onCreateListing.run());
        homeBtn.addActionListener(e -> onHome.run());

        buttonPanel.add(createBtn);
        buttonPanel.add(homeBtn);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    /**
     * Called whenever the ViewModel updates its state, typically after
     * the presenter calls firePropertyChanged().
     *
     * @param evt the property change event containing the updated state
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) return;

        ViewProfileState state = (ViewProfileState) evt.getNewValue();

        // Update title
        titleLabel.setText(state.getTitleText());

        // Update "No Listings" message
        if (state.getNoListingsMessage().isEmpty()) {
            noListingsLabel.setText("");
        } else {
            noListingsLabel.setText(state.getNoListingsMessage());
        }

        // Update error message
        if (state.getErrorMessage().isEmpty()) {
            errorLabel.setText("");
        } else {
            errorLabel.setText(state.getErrorMessage());
        }

        // Update listings
        listingsPanel.removeAll();

        List<String> names = state.getListingNames();
        List<BufferedImage> photos = state.getListingPhotos();

        for (int i = 0; i < names.size(); i++) {
            String listingName = names.get(i);
            BufferedImage img = (i < photos.size()) ? photos.get(i) : null;

            JPanel row = new JPanel(new BorderLayout());

            // Listing image
            if (img != null) {
                Image scaled = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                JLabel photoLabel = new JLabel(new ImageIcon(scaled));
                row.add(photoLabel, BorderLayout.WEST);
            }

            // Listing name
            JLabel nameLabel = new JLabel(listingName);
            row.add(nameLabel, BorderLayout.CENTER);

            // Delete button
            JButton deleteBtn = new JButton("Delete");
            deleteBtn.addActionListener(e -> onDeleteListing.accept(listingName));
            row.add(deleteBtn, BorderLayout.EAST);

            listingsPanel.add(row);
        }

        listingsPanel.revalidate();
        listingsPanel.repaint();
    }

    /**
     * Called by another view when the user clicks their Profile button.
     * It triggers the View Profile use case.
     */
    public void loadProfile() {
        profileController.onProfileButtonClicked();
    }
}
