package app;

import data_access.UserDataAccessObject;
import data_access.CreateListingDAO;
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
import use_case.create_listing.CreateListingInputBoundary;
import use_case.create_listing.CreateListingInteractor;
import use_case.create_listing.CreateListingOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.register.RegisterInputBoundary;
import use_case.register.RegisterInteractor;
import use_case.register.RegisterOutputBoundary;
import view.*;
import interface_adapter.view_profile.ViewProfileViewModel;
import interface_adapter.view_profile.ViewProfileController;
import interface_adapter.view_profile.ViewProfilePresenter;
import use_case.view_profile.ViewProfileInputBoundary;
import use_case.view_profile.ViewProfileOutputBoundary;
import use_case.view_profile.ViewProfileInteractor;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

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


    private HomepageViewModel homepageViewModel;
    private HomepageView homepageView;

    private CreateListingView createListingView;
    private CreateListingViewModel createListingViewModel;

    public AppBuilder() throws IOException {
        contentPane.setLayout(cardLayout);
    }

    public AppBuilder addRegisterView() {

        registerViewModel = new RegisterViewModel();
        registerView = new RegisterView(registerViewModel);
        contentPane.add(registerView, registerView.getViewName());
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

        // --- navigation callbacks ---
        Runnable gotoCreateListing = () -> {
            // replace "create listing" with the real view name if different
            viewManagerModel.setState("create listing");
            viewManagerModel.firePropertyChanged();
        };

        Runnable gotoHome = () -> {
            viewManagerModel.setState(homepageView.getViewName());
            viewManagerModel.firePropertyChanged();
        };

        // delete handler: currently prints — replace this Consumer with your real delete wiring
        Consumer<String> deleteHandler = listingName -> {
            System.out.println("Delete " + listingName);
        };

        profileView = new ProfileView(
                viewProfileViewModel,
                controller,
                gotoCreateListing,
                gotoHome,
                deleteHandler
        );

        // Ensure homepageView exists before adding listener. If homepageView is null, this will NPE
        // which indicates you must call addHomepageView() before addViewProfileUseCase() in Main.
        homepageView.addViewProfileListener(e -> {
            // navigate to profile screen and trigger the use case to load data
            viewManagerModel.setState(viewProfileViewModel.getViewName());
            viewManagerModel.firePropertyChanged();
            profileView.loadProfile();
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

        // Pass homepageViewModel into the controller
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

        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homepageViewModel, loginViewModel, registerViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;

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

