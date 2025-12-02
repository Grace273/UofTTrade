package app;

import data_access.UpdateListingDataAccessObject;
import data_access.UserDataAccessObject;
import data_access.CreateListingDAO;
import entity.Listing;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_listing.CreateListingController;
import interface_adapter.create_listing.CreateListingPresenter;
import interface_adapter.create_listing.CreateListingViewModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.register.RegisterController;
import interface_adapter.register.RegisterPresenter;
import interface_adapter.register.RegisterViewModel;
import interface_adapter.search.SearchListingsViewModel;
import interface_adapter.view_listing.ViewListingController;
import interface_adapter.view_listing.ViewListingPresenter;
import interface_adapter.view_listing.ViewListingViewModel;
import org.json.JSONObject;
import interface_adapter.update_listing.UpdateListingPresenter;
import use_case.create_listing.CreateListingInputBoundary;
import use_case.create_listing.CreateListingInteractor;
import use_case.create_listing.CreateListingOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.register.RegisterInputBoundary;
import use_case.register.RegisterInteractor;
import use_case.register.RegisterOutputBoundary;
import use_case.update_listing.UpdateListingInputBoundary;
import use_case.update_listing.UpdateListingInteractor;
import use_case.update_listing.UpdateListingOutputBoundary;
import use_case.update_listing.UpdateListingUserDataAccessInterface;
import use_case.view_listing.ViewListingInputBoundary;
import use_case.view_listing.ViewListingInteractor;
import use_case.view_listing.ViewListingOutputBoundary;
import view.*;
import interface_adapter.view_profile.ViewProfileViewModel;
import interface_adapter.view_profile.ViewProfileController;
import interface_adapter.view_profile.ViewProfilePresenter;
import use_case.view_profile.ViewProfileInputBoundary;
import use_case.view_profile.ViewProfileOutputBoundary;
import use_case.view_profile.ViewProfileInteractor;
import use_case.search.SearchListingsInteractor;
import interface_adapter.update_listing.UpdateListingController;
import interface_adapter.messaging.MessagingViewModel;
import interface_adapter.messaging.MessagingPresenter;
import interface_adapter.messaging.MessagingController;
import use_case.messaging.MessagingInteractor;
import use_case.messaging.MessagingOutputBoundary;
import use_case.messaging.MessagingInputBoundary;





import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.List;

public class AppBuilder {

    private final JPanel contentPane = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(contentPane, cardLayout, viewManagerModel);

    final UserDataAccessObject userDataAccessObject = new UserDataAccessObject();
    final CreateListingDAO createListingDAO = new CreateListingDAO();

    private RegisterView registerView;
    private RegisterViewModel registerViewModel;
    private LoginViewModel loginViewModel;
    private LoginView loginView;
    private ProfileView profileView;
    private ViewProfileViewModel viewProfileViewModel = new ViewProfileViewModel();
    private ViewListingView viewListingView;
    private ViewListingViewModel viewListingViewModel;

    private HomepageViewModel homepageViewModel;
    private HomepageView homepageView;

    private CreateListingView createListingView;
    private CreateListingViewModel createListingViewModel;
    private UpdateListingController updateListingController;


    private SearchListingsView searchListingsView;
    private SearchListingsViewModel searchListingsViewModel;
    final data_access.SearchListingsDataAccessObject searchDataAccessObject = new data_access.SearchListingsDataAccessObject();

    private MessagingViewModel messagingViewModel;
    private MessagingController messagingController;
    private MessagingSubpageView messagingSubpageView;
    private ViewListingController viewListingController;

    public AppBuilder() throws IOException {
        contentPane.setLayout(cardLayout);
    }

    public AppBuilder addUpdateListingController(UpdateListingController controller) {
        this.updateListingController = controller;
        return this;
    }

    public AppBuilder addRegisterView() {

        registerViewModel = new RegisterViewModel();
        registerView = new RegisterView(registerViewModel);
        contentPane.add(registerView, registerView.getViewName());
        return this;

    }

    public AppBuilder addSearchListingsView() {
        searchListingsViewModel = new interface_adapter.search.SearchListingsViewModel(searchDataAccessObject.getAllCategories());
        searchListingsView = new SearchListingsView(searchListingsViewModel);
        contentPane.add(searchListingsView, searchListingsView.viewName);
        return this;
    }

    public AppBuilder addSearchUseCase() {
        use_case.search.SearchListingsOutputBoundary searchPresenter =
                new interface_adapter.search.SearchListingsPresenter(searchListingsViewModel);

        use_case.search.SearchListingsInputBoundary searchInteractor =
                new use_case.search.SearchListingsInteractor(searchDataAccessObject, searchPresenter);

        interface_adapter.search.SearchListingsController searchController =
                new interface_adapter.search.SearchListingsController(searchInteractor);

        searchListingsView.setSearchListingsController(searchController);

        searchListingsView.addBackListener(e -> {
            viewManagerModel.setState(homepageViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        });

        return this;
    }

    public AppBuilder addLoginView() {

        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        contentPane.add(loginView, loginView.getViewName());
        return this;

    }

    public AppBuilder addHomepageView() {

        homepageViewModel = new HomepageViewModel();
        homepageView = new HomepageView(homepageViewModel);
        homepageView.addViewProfileListener(e -> {
            viewManagerModel.setState(viewProfileViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        });

        //temp create listings view
        homepageView.addCreateListingListener(e -> {
            viewManagerModel.setState("create listing");
            viewManagerModel.firePropertyChanged();
        });

        homepageView.addSearchListener(e -> {
            // Switch to the search view
            viewManagerModel.setState(searchListingsViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
        });

        viewListingViewModel = new ViewListingViewModel();
        final ViewListingOutputBoundary viewListingOutputBoundary = new ViewListingPresenter(homepageViewModel,
                viewManagerModel, viewListingViewModel);
        final ViewListingInputBoundary viewListingInteractor = new ViewListingInteractor(
                createListingDAO, userDataAccessObject, viewListingOutputBoundary);

        ViewListingController controller = new ViewListingController(viewListingInteractor);
        homepageView.setViewListingController(controller);

        contentPane.add(homepageView, homepageView.getViewName());
        return this;

    }

    public AppBuilder addRegisterUseCase() {
        final RegisterOutputBoundary RegisterOutputBoundary = new RegisterPresenter(viewManagerModel,
                registerViewModel, loginViewModel);
        final RegisterInputBoundary userRegisterInteractor = new RegisterInteractor(
                userDataAccessObject, RegisterOutputBoundary);

        RegisterController controller = new RegisterController(userRegisterInteractor);
        registerView.setRegisterController(controller);
        return this;
    }

    public AppBuilder addProfileView(ViewProfileController controller) {

        Runnable gotoCreateListing = () -> {
            viewManagerModel.setState("create listing");
            viewManagerModel.firePropertyChanged();
        };

        Runnable gotoHome = () -> {
            viewManagerModel.setState(homepageView.getViewName());
            try {
                final UserDataAccessObject dataAccessObject = new UserDataAccessObject();
                homepageView.getListingPanels(dataAccessObject.getAllListings());
            }
            catch (IOException ex) {
                throw new RuntimeException("Failed to update homepage.");
            }
            viewManagerModel.firePropertyChanged();
        };

        Consumer<String> deleteHandler = rawListingName -> {

            String listingName = rawListingName.replaceAll("\\[|\\]", "").trim();

            User currentUser = viewProfileViewModel.getState().getUser();


            if (currentUser == null) {
                return;
            }

            currentUser.get_listings().forEach(l -> System.out.println(">" + l.get_name() + "<"));

            // Find the listing by name
            Listing listingToDelete = currentUser.get_listings().stream()
                    .filter(l -> l.get_name().equalsIgnoreCase(listingName))
                    .findFirst()
                    .orElse(null);

            if (listingToDelete != null) {
                updateListingController.execute(true, currentUser, listingToDelete);
                //profileView.loadProfile();
            } else {

                // No matching listing found
            }
        };


        profileView = new ProfileView(
                viewProfileViewModel,
                controller,
                gotoCreateListing,
                gotoHome,
                deleteHandler
        );


        homepageView.addViewProfileListener(e -> {
            viewManagerModel.setState(viewProfileViewModel.getViewName());
            viewManagerModel.firePropertyChanged();

        });


        contentPane.add(profileView, viewProfileViewModel.getViewName());


        viewManagerModel.addPropertyChangeListener(evt -> {
            if (evt.getNewValue().equals(viewProfileViewModel.getViewName())) {
                profileView.loadProfile();
            }
        });

        return this;
    }

    public AppBuilder addViewProfileUseCase() {

        ViewProfileOutputBoundary presenter =
                new ViewProfilePresenter(viewProfileViewModel);

        ViewProfileInputBoundary interactor =
                new ViewProfileInteractor(userDataAccessObject, presenter);


        ViewProfileController controller =
                new ViewProfileController(interactor);

        return addProfileView(controller);
    }


    public AppBuilder addCreateListingView() {
        createListingViewModel = new CreateListingViewModel();
        createListingView = new CreateListingView(createListingViewModel);
        contentPane.add(createListingView, createListingView.getViewName());

        return this;
    }

    public AppBuilder addCreateListingUseCase(){
        final CreateListingOutputBoundary createListingOutputBoundary = new CreateListingPresenter(
                createListingViewModel,
                viewProfileViewModel,
                viewManagerModel
        );

        final CreateListingInputBoundary createListingInteractor= new CreateListingInteractor(
                createListingDAO,
                createListingOutputBoundary,
                userDataAccessObject
        );

        CreateListingController controller = new CreateListingController(createListingInteractor);
        createListingView.setCreateListingController(controller);
        return this;
    }


    public AppBuilder addLoginUseCase() {

        contentPane.add(createListingView, createListingView.getViewName());
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homepageViewModel, loginViewModel, registerViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;

    }

    public AppBuilder addViewListingView() {

        viewListingView = new ViewListingView(viewListingViewModel);
        contentPane.add(viewListingView, viewListingView.getViewName());

        return this;

    }

    public AppBuilder addViewListingUseCase() {

        final ViewListingOutputBoundary viewListingOutputBoundary = new ViewListingPresenter(homepageViewModel,
                viewManagerModel, new ViewListingViewModel());
        final ViewListingInputBoundary viewListingInteractor = new ViewListingInteractor(
                createListingDAO, userDataAccessObject, viewListingOutputBoundary);

        final ViewListingController controller = new ViewListingController(viewListingInteractor);
        viewListingView.setViewListingController(controller);
        this.updateSearchDependencies(controller);
        return this;

    }

    public AppBuilder addMessagingUseCase() throws IOException {
        // viewmodel
        messagingViewModel = new MessagingViewModel();
        // presenter
        MessagingOutputBoundary messagingPresenter = new MessagingPresenter(messagingViewModel);
        //Interactor
        MessagingInputBoundary messagingInteractor = new MessagingInteractor(userDataAccessObject, messagingPresenter);
        //Controller
        this.messagingController = new MessagingController(messagingInteractor);

        if(homepageView != null) {
            homepageView.setMessagingDependencies(messagingController, messagingViewModel, viewManagerModel);
            final List<JSONObject> allListings = userDataAccessObject.getAllListings();
            homepageView.getListingPanels(allListings);
        }
        if (viewListingView != null) {
            viewListingView.setMessagingDependencies(messagingController, messagingViewModel, viewManagerModel);
        }
        this.updateSearchDependencies(null);
        return this;
    }

    public AppBuilder addMessagingView() {
        if (messagingViewModel == null || messagingController == null) {
            throw new IllegalStateException("Call the messaging use case before create the messaging view ");
        }

        Runnable onBack = () -> {
            viewManagerModel.setState(homepageView.getViewName());
            viewManagerModel.firePropertyChanged();
        };
        messagingSubpageView = new MessagingSubpageView(messagingViewModel, onBack);
        contentPane.add(messagingSubpageView, messagingViewModel.getViewName());

        return this;
    }

    public AppBuilder addUpdateListingUseCase() {
        UpdateListingUserDataAccessInterface updateListingDAO =
                new UpdateListingDataAccessObject();

        UpdateListingOutputBoundary outputBoundary =
                new UpdateListingPresenter(viewProfileViewModel, viewManagerModel);

        UpdateListingInputBoundary interactor =
                new UpdateListingInteractor(updateListingDAO, outputBoundary);

        this.updateListingController = new UpdateListingController(interactor);

        return this;
    }
    private void updateSearchDependencies(ViewListingController vlc) {
        if (searchListingsView != null) {

            ViewListingController vlcToUse = (vlc != null) ? vlc : this.viewListingController;

            searchListingsView.setDependencies(
                    messagingController,
                    messagingViewModel,
                    viewManagerModel,
                    vlcToUse
            );
        }
    }

    public JFrame build() {
        final JFrame application = new JFrame("UofTTrade");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setBounds(100, 100, 450, 300);
        application.add(contentPane);

        viewManagerModel.setState(registerView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;

    }
}

