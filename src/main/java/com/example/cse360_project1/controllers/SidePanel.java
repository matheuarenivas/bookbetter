package com.example.cse360_project1.controllers;

import com.example.cse360_project1.LoginRegisterPage;
import com.example.cse360_project1.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SidePanel {
    private final User user;
    private final SceneController sceneController;
    public SidePanel(User user, SceneController sceneController) {
        this.sceneController = sceneController;
        this.user = user;

    }
    public VBox getSidePanel() {
        VBox sidePanel = new VBox();
        String imagePath = getClass().getResource("/com/example/cse360_project1/images/BookBetterCard.png").toExternalForm();
        Image BookBetterCard = new Image(imagePath, 155.0, 30.0, true, true);
        ImageView bookBetterImageView = new ImageView(BookBetterCard);
        bookBetterImageView.setFitWidth(155.0);
        bookBetterImageView.setFitHeight(30.0);

        sidePanel.setAlignment(Pos.TOP_CENTER);
        sidePanel.setPrefWidth(200);
        sidePanel.setSpacing(20);
        sidePanel.setPadding(new Insets(20, 20, 20, 0));
        sidePanel.setPrefHeight(1280);
        sidePanel.getStyleClass().add("gray-sidebar");

        VBox generalArea = new VBox(10);
        generalArea.setPadding(new Insets(20, 20, 20, 20));
        Label generalLabel = new Label("General".toUpperCase());
        generalArea.getChildren().add(generalLabel);
        if (user.getUserType().equals("SELLER")) {
            Button dashboard = new Button("Dashboard");
            dashboard.getStyleClass().add("sidepanel-button");
            Button list = new Button("List a book");
            list.getStyleClass().add("sidepanel-button");
            Button transactions = new Button("Transactions");

            dashboard.setOnAction(e -> {

            });

            transactions.getStyleClass().add("sidepanel-button");
            generalArea.getChildren().addAll(dashboard, list, transactions);

        } else if (user.getUserType().equals("ADMIN")) {
            Button dashboard = new Button("Dashboard");
            dashboard.getStyleClass().add("sidepanel-button");

            Button orders = new Button("Orders");
            orders.getStyleClass().add("sidepanel-button");

            Button users = new Button("Users");
            users.getStyleClass().add("sidepanel-button");

            Button books = new Button("Books");
            books.getStyleClass().add("sidepanel-button");

            generalArea.getChildren().addAll(dashboard, orders, users, books);

        } else if (user.getUserType().equals("BUYER")) {
            Button browse = new Button("Browse");
            browse.getStyleClass().add("sidepanel-button");

            Button orderHistory = new Button("Order History");
            orderHistory.getStyleClass().add("sidepanel-button");
            generalArea.getChildren().addAll(browse, orderHistory);


        }
        VBox supportArea = new VBox(10);
        supportArea.setPadding(new Insets(20, 20, 20, 20));
        Label supportLabel = new Label("Support".toUpperCase());

        Button settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("sidepanel-button");
        settingsButton.getStyleClass().add("selected");

        Button supportButton = new Button("Support");
        supportButton.getStyleClass().add("sidepanel-button");
        supportArea.getChildren().addAll(generalArea, supportLabel, supportButton, settingsButton);


        VBox userArea = new VBox(5);
        if (user.getUserType().equals("ADMIN")) {
            userArea.setPadding(new Insets(sceneController.getCurrentScene().getHeight() / 5, 20, 20, 20));

        } else {
            userArea.setPadding(new Insets(sceneController.getCurrentScene().getHeight() / 3.5, 20, 20, 20));
        }
        Label userLabel = new Label(user.getName());

        userLabel.getStyleClass().add("focus");

        Label typeLabel = new Label(user.getUserType().toUpperCase());
        typeLabel.getStyleClass().add("type-label");

        Button logOutButton = new Button("Log out");
        logOutButton.getStyleClass().add("sidepanel-button");
        logOutButton.getStyleClass().add("secondary");
        userArea.getChildren().addAll(userLabel, typeLabel, logOutButton);

        userArea.setAlignment(Pos.BOTTOM_CENTER);
        sidePanel.getChildren().addAll(bookBetterImageView, generalArea, supportArea, userArea);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        sidePanel.getStylesheets().add(css);


        logOutButton.setOnAction(e -> {
            LoginRegisterPage loginRegisterPage = new LoginRegisterPage(sceneController);
            sceneController.switchScene(loginRegisterPage.getScene(sceneController.getCurrentScene()));
        });
        return sidePanel;
    }

}
