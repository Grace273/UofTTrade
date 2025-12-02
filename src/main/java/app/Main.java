package app;

import entity.Category;
import interface_adapter.view_profile.ViewProfileController;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Category> categoriesArray = new ArrayList<Category>();
    public static String[] categoriesNameArray = new String[9];

    public static void main(String[] args) throws IOException {
        AppBuilder appBuilder = new AppBuilder();

        // always add "Select a Category" Category as the first category
        Category category1 = new Category("Select a Category");
        Category category2 = new Category("Technology");
        Category category3 = new Category("Furniture");
        Category category4 = new Category("Sports");
        Category category5 = new Category("Textbooks");
        Category category6 = new Category("Clothing");
        Category category7 = new Category("Collectibles");
        Category category8 = new Category("Crafts");
        Category category9 = new Category("Art");
        categoriesNameArray[0] = category1.getName();
        categoriesArray.add(category1);
        categoriesNameArray[1] = category2.getName();
        categoriesArray.add(category2);
        categoriesNameArray[2] = category3.getName();
        categoriesArray.add(category3);
        categoriesNameArray[3] = category4.getName();
        categoriesArray.add(category4);
        categoriesNameArray[4] = category5.getName();
        categoriesArray.add(category5);
        categoriesNameArray[5] = category6.getName();
        categoriesArray.add(category6);
        categoriesNameArray[6] = category7.getName();
        categoriesArray.add(category7);
        categoriesNameArray[7] = category8.getName();
        categoriesArray.add(category8);
        categoriesNameArray[8] = category9.getName();
        categoriesArray.add(category9);

        JFrame application = appBuilder
                .addLoginView()
                .addRegisterView()
                .addHomepageView()
                .addCreateListingView()
                .addSearchListingsView()
                .addViewListingView()
                .addMessagingUseCase()
                .addMessagingView()
                .addCreateListingUseCase()
                .addRegisterUseCase()
                .addLoginUseCase()
                .addViewProfileUseCase()
                .addViewListingUseCase()
                .addSearchUseCase()
                .addUpdateListingUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
