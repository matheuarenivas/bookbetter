package com.example.cse360_project1;

import com.example.cse360_project1.Book;
import com.example.cse360_project1.SceneController;
import com.example.cse360_project1.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;
public class SellerDashboard {
    private User user;
    private SceneController sceneController;

    public SellerDashboard(User user, SceneController sceneController) {
        this.user = user;
        this.sceneController = sceneController;
    }

    public Scene getScene() {
        Scene mainScene = sceneController.getCurrentScene();
        sceneController.getStage().setTitle("BookBetter - Seller Dashboard");
        AnchorPane root = new AnchorPane();

//        HBox pieChartBox = categoriesPieChart(user);
//        HBox conditionsPieChart = conditionsPieChart(user);
//        AnchorPane.setBottomAnchor(pieChartBox, 0.0);
//        AnchorPane.setBottomAnchor(conditionsPieChart, mainScene.getHeight() / 2.0);
//        root.getChildren().addAll(pieChartBox, conditionsPieChart);

        root.getChildren().addAll();
        Scene scene = new Scene(root, mainScene.getWidth(), mainScene.getHeight());

        return scene;
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