package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.Error;
import com.example.cse360_project1.models.User;
import com.example.cse360_project1.services.JDBCConnection;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRegisterPage {
    private final SceneController sceneController;
    public LoginRegisterPage(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public Scene getScene(Scene mainScene) {
        AnchorPane root = new AnchorPane();


        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(100);

        String imagePath = getClass().getResource("/com/example/cse360_project1/images/BookBetterCard.png").toExternalForm();
        Image BookBetterCard = new Image(imagePath, 620.0, 120.0, true, true);
        ImageView bookBetterLogo = new ImageView(BookBetterCard);
        bookBetterLogo.setFitWidth(310.0);
        bookBetterLogo.setFitHeight(60.0);

        // Login section
        HBox boxes = new HBox();
        VBox loginBox = new VBox(20);
        Label loginLabel = new Label("Login");

        TextField loginUsername = new TextField();
        loginUsername.setPromptText("User ID");

        PasswordField loginPassword = new PasswordField();
        loginPassword.setPromptText("Password");

        Button loginButton = new Button("Login");

        loginBox.setAlignment(Pos.TOP_CENTER);
        loginBox.getChildren().addAll(loginLabel, loginUsername, loginPassword, loginButton);

        // Register section
        VBox registerBox = new VBox(20);
        Label registerLabel = new Label("Register");
        TextField registerUsername = new TextField();
        registerUsername.setPromptText("User ID");

        PasswordField registerPassword = new PasswordField();
        registerPassword.setPromptText("Password");
        registerPassword.getStyleClass().add("password");

        PasswordField registerConfirmPassword = new PasswordField();
        registerConfirmPassword.setPromptText("Confirm Password");
        registerConfirmPassword.getStyleClass().add("password");

        ComboBox<String> registerType = new ComboBox();
        registerType.setValue("Choose Account Type");
        registerType.getItems().addAll("Buyer", "Seller");
        Button registerButton = new Button("Create Account");
        registerBox.setAlignment(Pos.CENTER);
        registerBox.getChildren().addAll(registerLabel, registerUsername, registerPassword, registerConfirmPassword, registerType, registerButton);

        boxes.getChildren().addAll(loginBox, registerBox);
        boxes.setSpacing(100);
        boxes.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(bookBetterLogo, boxes);
        root.getChildren().add(mainBox);
        AnchorPane.setTopAnchor(mainBox, mainScene.getHeight() / 6.5);
        AnchorPane.setLeftAnchor(mainBox, mainScene.getWidth() / 3.5);

        loginButton.setOnAction(event -> {
            String userID = loginUsername.getText();
            String password = loginPassword.getText();
            if (userID.isEmpty() || password.isEmpty()) {
                Error authError = new Error("Authentication failed: Incorrect ID or Password");
                authError.displayError(root, mainScene);
            }
            JDBCConnection connection = new JDBCConnection();
            User user = connection.logInReturnUser(userID, password);

            if (user == null) {
                Error authError = new Error("Authentication failed: Incorrect ID or Password");
                authError.displayError(root, mainScene);
            } else {

                    redirectUser(user, sceneController);

            }
        });

        registerButton.setOnAction(event -> {
            String userID = registerUsername.getText();
            String password = registerPassword.getText();
            String confirmPassword = registerConfirmPassword.getText();
            String accountType = registerType.getSelectionModel().getSelectedItem();
            // Error handling
            if (userID.isEmpty() || password.isEmpty()) {
                Error registerError = new Error("Registration failed: Empty required field");
                registerError.displayError(root, mainScene);
            }

            else if (accountType.equals("Choose Account Type")) {
                Error accountTypeError = new Error("Registration failed: Account type invalid");
                accountTypeError.displayError(root, mainScene);
            }

            else if (confirmPassword.isEmpty() || confirmPassword != password) {
                Error registerError = new Error("Registration failed: Confirm password not matching");
                registerError.displayError(root, mainScene);
            }

            if (password.equals(confirmPassword)) {
                JDBCConnection connection = new JDBCConnection();
                User user = connection.registerUser(userID, password, accountType);
                if (user == null) {
                    Error registerError = new Error("Registration failed, try again.");
                    registerError.displayError(root, mainScene);
                } else {
                        redirectUser(user, sceneController);

                }
            }
        });
        Scene loginRegisterScene = new Scene(root, mainScene.getWidth(), mainScene.getHeight());
        String css = getClass().getResource("/com/example/cse360_project1/css/LoginRegister.css").toExternalForm();
        loginRegisterScene.getStylesheets().add(css);
        return loginRegisterScene;
    }
    public void redirectUser(User user, SceneController sceneController) {
        if (user.getUserType().equals("BUYER")) {
            BuyerView newBuyerView = new BuyerView(user, sceneController);
            newBuyerView.setTab("BROWSE");
            Scene buyerView = newBuyerView.getScene();
            sceneController.switchScene(buyerView);
        } else if (user.getUserType().equals("SELLER")) {
            SellerView newSellerView = new SellerView(user, sceneController);
            Scene sellerView = newSellerView.getScene();
            sceneController.switchScene(sellerView);
        } else {
            AdminView newAdminView = new AdminView(user, sceneController);
            newAdminView.setTab("DASHBOARD");
            sceneController.switchScene(newAdminView.getScene());
        }
    }
}
