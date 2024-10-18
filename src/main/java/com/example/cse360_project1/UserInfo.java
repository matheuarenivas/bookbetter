package com.example.cse360_project1;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class UserInfo {

    private User user;
    private SceneController sceneController;

    public UserInfo(User user, SceneController sceneController) {
        this.user = user;
        this.sceneController = sceneController;
    }

    public Scene getScene() throws SQLException {
        // Create labels to display user information
        Scene mainScene = sceneController.getCurrentScene();
        Label idLabel = new Label("ID: " + user.getId());
        Label nameLabel = new Label("Name: " + user.getName());
        Label userTypeLabel = new Label("User Type: " + user.getUserType());
        Label passwordLabel = new Label("Change password");
        TextField passwordField = new TextField();

        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(200);

        Button changePasswordButton = new Button("Change Password");
        // Create a return button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> sceneController.switchScene(mainScene));

        // Layout setup
        HBox main = new HBox();
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(idLabel, nameLabel, userTypeLabel, returnButton, passwordLabel, passwordField);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(vbox, returnButton);
        UserConnection userConnection = new UserConnection();
        userConnection.getBooks(user);

        VBox orderHistoryLayout = new VBox(10);
        for (Book book : user.getBooks()) {
            HBox bookOrder = createBookOrderComponent(book);
            orderHistoryLayout.getChildren().add(bookOrder);
        }
        Button dashboardButton = new Button("Dashboard");
        SellerDashboard sellerDashboard = new SellerDashboard(user, sceneController);
//        Scene dashboardScene = sellerDashboard.getScene();
//
//        dashboardButton.setOnAction(e -> sceneController.switchScene(dashboardScene));
        main.getChildren().addAll(layout, orderHistoryLayout, dashboardButton);
        Scene scene = new Scene(main, mainScene.getWidth(), mainScene.getHeight());

        // Load and apply the CSS file
        String css = getClass().getResource("/com/example/cse360_project1/css/UserInfo.css").toExternalForm();
        scene.getStylesheets().add(css);

        return scene;
    }
    private HBox createBookOrderComponent(Book book) {
        HBox bookOrder = new HBox(10);
        bookOrder.getStyleClass().add("book-order");
        Label nameLabel = new Label(book.getName());
        Label priceLabel = new Label("$" + String.format("%.2f", book.getPrice()));

        Button viewReceiptButton = new Button("View");
        viewReceiptButton.getStyleClass().add("view-receipt-button");
        viewReceiptButton.setOnAction(event -> {
            // Action to view full receipt
            System.out.println("Viewing receipt for: " + book.toString());
            // You can open a new window or dialog here to show the full receipt
        });

        bookOrder.getChildren().addAll(nameLabel, priceLabel, viewReceiptButton);
        return bookOrder;
    }
}