package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class BuyerView {
    private final User user;
    private final SceneController sceneController;
    private String tab;

    public BuyerView(User user, SceneController sceneController) {
        this.user = user;
        this.sceneController = sceneController;
        this.tab = "BROWSE";
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
        sceneController.setTitle("BookBetter - Buyer");

        return scene;
    }

    private AnchorPane getContentPane(Scene mainScene) {
        switch (tab) {
            case "BROWSE":
                return getBrowseSection(mainScene);
            case "ORDERS":
                return getOrderHistory(mainScene);
            default:
                return getBrowseSection(mainScene);
        }
    }

    public AnchorPane getBrowseSection(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label booksLabel = new Label("Today's Books");
        booksLabel.getStyleClass().add("h1");
        booksLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("Browse and purchase books");

        VBox filters = new VBox();
        filters.getStyleClass().add("text-lg");
        Label filtersLabel = new Label("Filters");
        filtersLabel.getStyleClass().add("h2");

        VBox categoriesBox = new VBox();
        categoriesBox.setSpacing(5.0);
        Label categoriesSubtitleLabel = new Label("Categories");
        categoriesSubtitleLabel.getStyleClass().add("h3");
        categoriesSubtitleLabel.getStyleClass().add("subtitle-label");

        CheckBox natScienceCheckBox = new CheckBox("Natural Science");
        CheckBox computerCheckBox = new CheckBox("Computer");
        CheckBox mathCheckBox = new CheckBox("Math");
        CheckBox englishLanguageCheckBox = new CheckBox("English Language");
        CheckBox scifiCheckBox = new CheckBox("Sci-Fi");
        CheckBox artCheckBox = new CheckBox("Art");
        CheckBox novelCheckBox = new CheckBox("Novel");
        categoriesBox.getChildren().addAll(categoriesSubtitleLabel, natScienceCheckBox, computerCheckBox, mathCheckBox, englishLanguageCheckBox, artCheckBox, novelCheckBox);

        VBox conditionsBox = new VBox();
        conditionsBox.setSpacing(5.0);
        Label conditionsSubtitleLabel = new Label("Condition");
        conditionsSubtitleLabel.getStyleClass().add("h3");
        conditionsSubtitleLabel.getStyleClass().add("subtitle-label");

        ToggleGroup conditionGroup = new ToggleGroup();
        RadioButton usedButton = new RadioButton("Used Like New");
        RadioButton moderateButton = new RadioButton("Moderately Used");
        RadioButton heavilyButton = new RadioButton("Heavily Used");
        usedButton.setToggleGroup(conditionGroup);
        moderateButton.setToggleGroup(conditionGroup);
        heavilyButton.setToggleGroup(conditionGroup);
        conditionsBox.getChildren().addAll(conditionsSubtitleLabel, usedButton, moderateButton, heavilyButton);

        filters.setSpacing(14.0);
        filters.getChildren().addAll(filtersLabel, categoriesBox, conditionsBox);

        pane.getChildren().addAll(booksLabel, subtitleLabel, filters);
        String css = getClass().getResource("/com/example/cse360_project1/css/BuyerView.css").toExternalForm();
        AnchorPane.setTopAnchor(booksLabel, 30.0);
        AnchorPane.setLeftAnchor(booksLabel, 50.0);

        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);

        AnchorPane.setTopAnchor(filters, 160.0);
        AnchorPane.setLeftAnchor(filters, 50.0);

        pane.getStylesheets().add(css);
        return pane;
    }

    public AnchorPane getOrderHistory(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label orderHistoryLabel = new Label("Order History");
        orderHistoryLabel.getStyleClass().add("h1");
        orderHistoryLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("View your past orders");

        pane.getChildren().addAll(orderHistoryLabel, subtitleLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(orderHistoryLabel, 30.0);
        AnchorPane.setLeftAnchor(orderHistoryLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
}