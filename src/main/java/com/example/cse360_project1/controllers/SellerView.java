package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.User;
import com.example.cse360_project1.services.JDBCConnection;
import com.example.cse360_project1.models.Error;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
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

        VBox totalRevenue = new VBox();
        totalRevenue.getStyleClass().add("blurb");
        totalRevenue.getStyleClass().add("mini");
        totalRevenue.setSpacing(20);

        Label totalRevenueLabel = new Label("Total Revenue");
        totalRevenueLabel.getStyleClass().add("h2");
        totalRevenue.setPadding(new Insets(20, 20, 20, 20));

        Label errorLabel = new Label("No data found.");
        errorLabel.getStyleClass().add("text-lg");

        totalRevenue.getChildren().addAll(totalRevenueLabel, errorLabel);


        VBox recentOrders = new VBox();
        recentOrders.getStyleClass().add("blurb");
        recentOrders.getStyleClass().add("wide");
        recentOrders.setSpacing(20.0);

        Label recentOrdersLabel = new Label("Recent Orders");
        recentOrdersLabel.getStyleClass().add("h2");

        Button viewAllButton = new Button("View All");
        viewAllButton.getStyleClass().add("secondary");
        viewAllButton.setPadding(new Insets(10, 15, 10, 15));
        viewAllButton.setOnAction(e -> {
            this.tab = "TRANSACTIONS";
            sceneController.switchScene(getScene());
        });
        HBox headerBox = new HBox();
        headerBox.setPadding(new Insets(20, 20, 20, 20));
        // Keep gap in between
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        headerBox.getChildren().addAll(recentOrdersLabel, spacer, viewAllButton);

        recentOrders.getChildren().add(headerBox);

        pane.getChildren().addAll(titleLabel, subtitleLabel, totalRevenue, recentOrders);
        String css = getClass().getResource("/com/example/cse360_project1/css/SellerView.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);

        AnchorPane.setTopAnchor(totalRevenue, 150.0);
        AnchorPane.setLeftAnchor(totalRevenue, 50.0);

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

        Label subtitleLabel = new Label("Sell a new book.");

        VBox listBlurb = new VBox();
        listBlurb.getStyleClass().add("blurb");
        listBlurb.getStyleClass().add("tall");
        listBlurb.setSpacing(10.0);
        listBlurb.setPadding(new Insets(20, 20, 20, 20));

        VBox bookName = new VBox();
        bookName.setSpacing(4.0);

        Label bookNameLabel = new Label("Book Name");
        bookNameLabel.getStyleClass().add("h3");

        TextField bookNameInput = new TextField();
        bookNameInput.setPromptText("Enter a book name");
        bookNameInput.getStyleClass().addAll("gray-border", "text-lg", "input");

        bookName.getChildren().addAll(bookNameLabel, bookNameInput);

        VBox author = new VBox();
        author.setSpacing(4.0);

        Label authorNameLabel = new Label("Author");
        authorNameLabel.getStyleClass().add("h3");

        TextField authorNameInput = new TextField();
        authorNameInput.setPromptText("Enter the author name");
        authorNameInput.getStyleClass().addAll("gray-border", "text-lg", "input");

        author.getChildren().addAll(authorNameLabel, authorNameInput);

        VBox conditionContainer = new VBox();
        conditionContainer.setSpacing(4.0);

        Label conditionNameLabel = new Label("Condition");
        conditionNameLabel.getStyleClass().add("h3");

        HBox condition = new HBox();
        condition.setSpacing(10);
        ComboBox<String> conditionCombo = new ComboBox();
        conditionCombo.getStyleClass().addAll("gray-border", "text-lg", "input");


        conditionCombo.setValue("Choose Account Type");
        conditionCombo.getItems().addAll("Lightly used", "Moderately used", "Heavily used ");

        Button chooseImageButton = new Button("Choose Image + ");
        chooseImageButton.getStyleClass().add("secondary");
        chooseImageButton.setPrefWidth(150);
        chooseImageButton.setPrefHeight(60);


        chooseImageButton.setOnAction(e -> {
            FileChooser imageChooser = new FileChooser();
            imageChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
            );
            File file = imageChooser.showOpenDialog(sceneController.getStage());
            JDBCConnection connection = new JDBCConnection();
            if (!connection.uploadImage(file, 1)) {
                Error uploadError = new Error("Image upload error: try again.");
                uploadError.displayError(pane, mainScene);
            }
        });


        condition.getChildren().addAll((Node) conditionCombo, chooseImageButton);
        conditionContainer.getChildren().addAll(conditionNameLabel, condition);

        ToggleButton natScienceButton = new ToggleButton("Natural Science");
        natScienceButton.getStyleClass().add("toggle-button");
        ToggleButton computerButton = new ToggleButton("Computer");
        computerButton.getStyleClass().add("toggle-button");

        ToggleButton mathButton = new ToggleButton("Math");
        mathButton.getStyleClass().add("toggle-button");

        ToggleButton englishLangButton = new ToggleButton("English Language");
        englishLangButton.getStyleClass().add("toggle-button");

        ToggleButton scifiButton = new ToggleButton("Sci-Fi");
        scifiButton.getStyleClass().add("toggle-button");

        ToggleButton artButton = new ToggleButton("Art");
        artButton.getStyleClass().add("toggle-button");

        ToggleButton novelButton = new ToggleButton("Novel");
        novelButton.getStyleClass().add("toggle-button");

        VBox categories = new VBox();
        categories.setSpacing(5);
        Label categoriesLabel = new Label("Categories");
        categoriesLabel.getStyleClass().add("h3");
        HBox categoriesBox1 = new HBox(10, natScienceButton, computerButton);
        HBox categoriesBox2 = new HBox(10, mathButton, englishLangButton);
        HBox categoriesBox3 = new HBox(10, scifiButton, artButton, novelButton);
        categories.getChildren().addAll(categoriesLabel, categoriesBox1, categoriesBox2, categoriesBox3);

        listBlurb.getChildren().addAll(bookName, author, conditionContainer, categories);

        pane.getChildren().addAll(titleLabel, subtitleLabel, listBlurb);
        String css = getClass().getResource("/com/example/cse360_project1/css/SellerView.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);

        AnchorPane.setTopAnchor(listBlurb, 120.0);
        AnchorPane.setLeftAnchor(listBlurb, 50.0);
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