package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AdminView {
    private final User user;
    private final SceneController sceneController;
    private String tab;

    public AdminView(User user, SceneController sceneController) {
        this.user = user;
        this.sceneController = sceneController;
        this.tab = "DASHBOARD";
    }

    public Scene getScene() {
        Scene mainScene = sceneController.getCurrentScene();
        AnchorPane root = new AnchorPane();
        SidePanel sidePanelObject = new SidePanel(user, sceneController);
        AnchorPane sidePanel = sidePanelObject.getSidePanel();

        AnchorPane.setLeftAnchor(sidePanel, 0.0);
        AnchorPane.setTopAnchor(sidePanel, 0.0);
        AnchorPane.setBottomAnchor(sidePanel, 0.0);

        AnchorPane contentPane = getContentPane(mainScene);

        AnchorPane.setTopAnchor(contentPane, 0.0);
        AnchorPane.setLeftAnchor(contentPane, 200.0);
        AnchorPane.setBottomAnchor(contentPane, 0.0);
        root.getChildren().addAll(sidePanel, contentPane);
        Scene scene = new Scene(root, mainScene.getWidth(), mainScene.getHeight());
        sceneController.setTitle("BookBetter - Admin");

        return scene;
    }

    private AnchorPane getContentPane(Scene mainScene) {
        switch (tab) {
            case "DASHBOARD":
                return getDashboard(mainScene);
            case "ORDERS":
                return getOrders(mainScene);
            case "USERS":
                return getUsers(mainScene);
            case "BOOKS":
                return getBooks(mainScene);
            default:
                return getDashboard(mainScene);
        }
    }

    public AnchorPane getDashboard(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("Track and manage all orders");

        pane.getChildren().addAll(titleLabel, subtitleLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public AnchorPane getOrders(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Orders");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("Manage all book orders");

        pane.getChildren().addAll(titleLabel, subtitleLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public AnchorPane getUsers(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Users");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("View and manage users");

        pane.getChildren().addAll(titleLabel, subtitleLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public AnchorPane getBooks(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Books");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("View and manage books");

        pane.getChildren().addAll(titleLabel, subtitleLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
}