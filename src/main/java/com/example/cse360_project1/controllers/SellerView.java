package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SellerView {
    private final User user;
    private final SceneController sceneController;
    private String tab;

    public SellerView(User user, SceneController sceneController) {
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
        sceneController.setTitle("BookBetter - Seller");

        return scene;
    }

    private AnchorPane getContentPane(Scene mainScene) {
        switch (tab) {
            case "LIST":
                return getListBook(mainScene);
            case "TRANSACTIONS":
                return getTransactions(mainScene);
            default:
                return getDashboard(mainScene);
        }
    }

    public AnchorPane getDashboard(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Hey " + user.getName() + ",");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("Track and manage your orders");

        VBox recentOrders = new VBox();
        recentOrders.getStyleClass().add("blurb");
        recentOrders.getStyleClass().add("wide");
        recentOrders.setSpacing(20.0);

        Label recentOrdersLabel = new Label("Recent Orders");
        recentOrdersLabel.getStyleClass().add("h2");

        Button viewAllButton = new Button("View All");
        viewAllButton.getStyleClass().add("secondary");

        HBox headerBox = new HBox();
        headerBox.setSpacing(820.0);
        headerBox.setPadding(new Insets(20, 20, 20, 20));
        headerBox.getChildren().addAll(recentOrdersLabel, viewAllButton);

        recentOrders.getChildren().add(headerBox);

        pane.getChildren().addAll(titleLabel, subtitleLabel, recentOrders);
        String css = getClass().getResource("/com/example/cse360_project1/css/SellerView.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        AnchorPane.setLeftAnchor(recentOrders, 50.0);

        AnchorPane.setBottomAnchor(recentOrders, 20.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public AnchorPane getListBook(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("List a Book");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("Add a new book to your inventory");

        pane.getChildren().addAll(titleLabel, subtitleLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public AnchorPane getTransactions(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Transactions");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("View your transaction history");

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

    public HBox categoriesPieChart(User user) {
        HBox main = new HBox();
        main.setAlignment(Pos.CENTER); // Aligns children in the center horizontally
        main.setSpacing(10);
        // Get categories sold
        Map<String, Integer> categoryCounts = user.getCategoriesSold();

        // Create PieChart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey() + " - " + entry.getValue(), entry.getValue()));
        }

        // Create PieChart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Books Sold by Category");

        // Set preferred size for PieChart
        pieChart.setPrefSize(300, 300); // Adjust these values as needed

        // Add PieChart to HBox
        main.getChildren().add(pieChart);

        return main;
    }
    public HBox conditionsPieChart(User user) {
        HBox main = new HBox();
        main.setAlignment(Pos.CENTER); // Aligns children in the center horizontally
        main.setSpacing(10);
        // Get categories sold
        Map<String, Integer> conditionsSold = user.getConditionsSold();

        // Create PieChart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : conditionsSold.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey() + " - " + entry.getValue(), entry.getValue()));
        }

        // Create PieChart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Books Sold by Condition");

        // Set preferred size for PieChart
        pieChart.setPrefSize(300, 300); // Adjust these values as needed

        // Add PieChart to HBox
        main.getChildren().add(pieChart);

        return main;
    }

}