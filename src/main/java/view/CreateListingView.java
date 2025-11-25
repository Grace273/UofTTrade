package view;

import data_access.CreateListingDAO;
import data_access.UserDataAccessObject;
import entity.Category;
import entity.User;
import interface_adapter.create_listing.CreateListingViewModel;
import interface_adapter.create_listing.CreateListingState;
import interface_adapter.create_listing.CreateListingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import app.Main;

/**
 * The View for when the user is creating a new listing
 */
public class CreateListingView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "create listing";
    private final CreateListingViewModel createListingViewModel;

    private final JTextField listingNameInputField = new JTextField(50);

    private JLabel chosenImageLabel = new JLabel("No file selected");
    private final JButton imgFileChooserButton = new JButton();
    private final JFileChooser imgFileChooser = new JFileChooser();
    private File file = null;
    private String selectedImageBase64;

    private final JComboBox<String> listingCategory1ComboBox = new JComboBox(Main.categoriesNameArray);

    private final JComboBox<String> listingCategory2ComboBox = new JComboBox(Main.categoriesNameArray);

    private final JButton publishListingButton;
    private final JButton cancelButton;

    private CreateListingController createListingController = null;

    private final JLabel nameErrorField = new JLabel();
    private final JLabel photoErrorField = new JLabel();

    private JLabel errorLabel = new JLabel("");
    private String errorMessage = "";

    public CreateListingView(CreateListingViewModel createListingViewModel) {
        this.createListingViewModel = createListingViewModel;
        createListingViewModel.addPropertyChangeListener(this);

        // ---------------------- MAIN COLUMN (centered) ----------------------
        JPanel mainColumn = new JPanel();
        mainColumn.setLayout(new BoxLayout(mainColumn, BoxLayout.Y_AXIS));
        mainColumn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainColumn.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40)); // form padding


        // ======= SPACING UTILITIES =======
        int vSpace = 14;
        int hSpace = 10;

        // ---------------------- NAME FIELD ----------------------
        final LabelTextPanel listingNameInfo = new LabelTextPanel(
                new JLabel(CreateListingViewModel.NAME_LABEL),
                listingNameInputField
        );
        listingNameInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, vSpace, 0));
        listingNameInfo.setMaximumSize(listingNameInfo.getPreferredSize());
        mainColumn.add(listingNameInfo);


        // ---------------------- IMAGE PICKER ----------------------
        final JPanel listingImagePanel = new JPanel();
        listingImagePanel.setLayout(new BoxLayout(listingImagePanel, BoxLayout.Y_AXIS));
        listingImagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        listingImagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, vSpace, 0));

        JLabel imgLabel = new JLabel(CreateListingViewModel.IMG_LABEL);
        imgLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, hSpace));

        listingImagePanel.add(imgLabel);
        listingImagePanel.add(imgFileChooserButton);
        listingImagePanel.add(chosenImageLabel);

        listingImagePanel.setMaximumSize(listingImagePanel.getPreferredSize());
        mainColumn.add(listingImagePanel);


        // ---------------------- CATEGORIES ----------------------
        // label
        final JPanel listingCategoryWrapperPanel = new JPanel();
        listingCategoryWrapperPanel.setLayout(new BoxLayout(listingCategoryWrapperPanel, BoxLayout.X_AXIS));
        listingCategoryWrapperPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel categoriesLabel = new JLabel(CreateListingViewModel.CATEGORIES_LABEL);
        listingCategoryWrapperPanel.add(categoriesLabel);

        listingCategoryWrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        listingCategoryWrapperPanel.setMaximumSize(listingCategoryWrapperPanel.getPreferredSize());
        mainColumn.add(listingCategoryWrapperPanel);


        // combobox sizes
        Dimension comboSize = new Dimension(200, 28);
        listingCategory1ComboBox.setPreferredSize(comboSize);
        listingCategory1ComboBox.setMaximumSize(comboSize);

        listingCategory2ComboBox.setPreferredSize(comboSize);
        listingCategory2ComboBox.setMaximumSize(comboSize);

        final JPanel listingCategoryDropdownPanel = new JPanel();
        listingCategoryDropdownPanel.setLayout(new BoxLayout(listingCategoryDropdownPanel, BoxLayout.Y_AXIS));
        listingCategoryDropdownPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        listingCategoryDropdownPanel.add(listingCategory1ComboBox);
        listingCategoryDropdownPanel.add(Box.createVerticalStrut(6));
        listingCategoryDropdownPanel.add(listingCategory2ComboBox);

        listingCategoryDropdownPanel.setMaximumSize(listingCategoryDropdownPanel.getPreferredSize());
        mainColumn.add(listingCategoryDropdownPanel);

        // ---------------------- ERROR LABEL ----------------------
        mainColumn.add(errorLabel);

        // ---------------------- BUTTONS ----------------------
        publishListingButton = new JButton(CreateListingViewModel.PUBLISH_LISTING_BUTTON_LABEL);
        cancelButton = new JButton(CreateListingViewModel.CANCEL_BUTTON_LABEL);

        final JPanel buttons = new JPanel();
        buttons.add(publishListingButton);
        buttons.add(Box.createHorizontalStrut(12));
        buttons.add(cancelButton);

        buttons.setBorder(BorderFactory.createEmptyBorder(vSpace, 0, 0, 0));
        buttons.setMaximumSize(buttons.getPreferredSize());
        mainColumn.add(buttons);

        // other
        publishListingButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        CreateListingDAO createListingDataAccessObject = new CreateListingDAO();

//                        // determine what the ListingID will be
//                        UserDataAccessObject userDataAccess = new UserDataAccessObject();
//                        User owner = userDataAccess.getCurrentLoggedInUser();
//                        String name = listingNameInputField.getText();
//                        int listingID = generateListingId(owner, name);

                        try {
                            if (listingNameInputField.getText() == null || listingNameInputField.getText().length() == 0) {
                                System.out.println(listingNameInputField.getText());
                                errorMessage = "A listing with a null name";
                                errorLabel.setText(errorMessage);
                                errorLabel.setForeground(Color.RED);
                                CreateListingState state = createListingViewModel.getState();
                                state.set_photo_error(errorMessage);
                                createListingViewModel.setState(state);
                            }
//                            else if (createListingDataAccessObject.existsByListingID(listingID)) {
//                                errorMessage = "You already have a listing with this name";
//                                errorLabel.setText(errorMessage);
//                                errorLabel.setForeground(Color.RED);
//                                CreateListingState state = createListingViewModel.getState();
//                                state.set_photo_error(errorMessage);
//                                createListingViewModel.setState(state);
//                            }
                            else if (file == null || chosenImageLabel.getText() == "No file selected") {
                                errorMessage = "The listing image is empty";
                                errorLabel.setText(errorMessage);
                                errorLabel.setForeground(Color.RED);
                                CreateListingState state = createListingViewModel.getState();
                                state.set_photo_error(errorMessage);
                                createListingViewModel.setState(state);
                            }
                            else if (file != null) {
                                String filename = file.getName().toLowerCase();

                                if (!(filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png"))) {
                                    errorMessage = "File must be one of jpg, jpeg, png.";

                                    // set photo error state
                                    CreateListingState state = createListingViewModel.getState();
                                    state.set_photo_error(errorMessage);
                                    createListingViewModel.setState(state);

                                    // display message on UI
                                    errorLabel.setText(errorMessage);
                                    errorLabel.setForeground(Color.RED);
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "Listing published successfully!");

                                    // save the inputs into the state
                                    String name = listingNameInputField.getText();
                                    String img_in_base_64 = encodeImageToBase64(file);
                                    List<Category> categories = new ArrayList<Category>();
                                    categories.add(Main.categoriesArray.get(0)); // always add "Select a Category" Category
                                    categories.add(Main.categoriesArray.get(listingCategory1ComboBox.getSelectedIndex()));
                                    categories.add(Main.categoriesArray.get(listingCategory2ComboBox.getSelectedIndex()));

                                    CreateListingState state = createListingViewModel.getState();
                                    state.set_name(name);
                                    state.set_photo(img_in_base_64);
                                    state.set_categories(categories);

                                    //reset all input fields
                                    errorLabel.setText("");
                                    errorMessage = "";
                                    listingNameInputField.setText("");
                                    listingCategory1ComboBox.setSelectedIndex(0);
                                    listingCategory2ComboBox.setSelectedIndex(0);
                                    chosenImageLabel.setText("");

                                    // execute the usecase
                                    createListingController.execute(
                                            name,
                                            img_in_base_64,
                                            categories
                                    );
                                }

                            }
                        }
                        catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        );

        cancelButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //reset listing inputs
                        errorLabel.setText("");
                        errorMessage = "";
                        listingNameInputField.setText("");
                        listingCategory1ComboBox.setSelectedIndex(0);
                        listingCategory2ComboBox.setSelectedIndex(0);
                        chosenImageLabel.setText("");

                        createListingController.switchToProfileView();
                    }
                }
        );

        imgFileChooserButton.addActionListener(e -> {
            if (imgFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.file = imgFileChooser.getSelectedFile();
                chosenImageLabel.setText("Selected: " + file.getName());

                // clear any error messages
                errorLabel.setText("");
                errorMessage = "";

                try {
                    this.selectedImageBase64 = encodeImageToBase64(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        listingNameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final CreateListingState currentState = createListingViewModel.getState();
                currentState.set_name(listingNameInputField.getText());
                createListingViewModel.setState(currentState);
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

        // put panels together
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(mainColumn, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateListingState state = (CreateListingState) evt.getNewValue();
        setFields(state);
        nameErrorField.setText(state.get_name_error());
        photoErrorField.setText(state.get_photo_error());
    }

    private String encodeImageToBase64(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);  // always jpg
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private void setFields(CreateListingState state) {
        nameErrorField.setText(state.get_name_error());
        photoErrorField.setText(state.get_photo_error());
    }

    public String getViewName() { return viewName; }

    public void setCreateListingController(CreateListingController createListingController) {
        this.createListingController = createListingController;
    }

//    private int generateListingId(User owner, String name) {
//        int result = owner.get_username().hashCode() + name.hashCode();
//        return result;
//    }
}
