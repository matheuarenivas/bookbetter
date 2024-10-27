package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Buyer {
    private final User user;
    private final SceneController sceneController;
    private String tab;
    public Buyer(User user, SceneController sceneController) {
        this.user = user;
        this.sceneController = sceneController;
        this.tab = "BROWSE";
    }
    public Scene getScene() {
        Scene mainScene = sceneController.getCurrentScene();
        sceneController.setTitle("BookBetter - Buyer");

        AnchorPane root = new AnchorPane();
        SidePanel sidePanelObject = new SidePanel(user, sceneController);
        VBox sidePanel = sidePanelObject.getSidePanel();

        AnchorPane.setLeftAnchor(sidePanel, 0.0);
        AnchorPane.setTopAnchor(sidePanel, 0.0);
        AnchorPane.setBottomAnchor(sidePanel, 0.0);
        AnchorPane contentPane;
        if (tab.equals("ORDERS")) contentPane = getOrderHistory(mainScene);
        else contentPane = getBrowseSection(mainScene);
        AnchorPane.setTopAnchor(contentPane, 0.0);
        AnchorPane.setLeftAnchor(contentPane, 200.0);
        AnchorPane.setBottomAnchor(contentPane, 0.0);
        root.getChildren().addAll(sidePanel, contentPane);
        Scene scene = new Scene(root, mainScene.getWidth(), mainScene.getHeight());

        return scene;
    }
    private AnchorPane getBrowseSection(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label booksLabel = new Label("Today's Books");
        booksLabel.getStyleClass().add("h1");
        booksLabel.setPadding(new Insets(20, 20, 20, 20));

        pane.getChildren().add(booksLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(booksLabel, 30.0);
        AnchorPane.setLeftAnchor(booksLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    private AnchorPane getOrderHistory(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label orderHistoryLabel = new Label("Order History");
        orderHistoryLabel.getStyleClass().add("h1");
        orderHistoryLabel.setPadding(new Insets(20, 20, 20, 20));

        pane.getChildren().add(orderHistoryLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(orderHistoryLabel, 30.0);
        AnchorPane.setLeftAnchor(orderHistoryLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }
}
