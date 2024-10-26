package com.example.cse360_project1.models;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
public class Error extends Pane {
    private Label errorLabel;
    private Button closeButton;
    private String error;

    public Error(String errorMessage) {
        errorLabel = new Label(errorMessage);
        errorLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        error = errorMessage;
//        System.out.println("Error: " + error);
        closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold;");
        closeButton.setOnAction(event -> this.setVisible(false)); // Hide the component on click

        // Create an HBox to hold the label and button
        HBox container = new HBox(10, errorLabel, closeButton);
        container.setStyle("-fx-background-color: #800000; -fx-padding: 10px 20px;  -fx-alignment: center-left; -fx-border-radius: 25px; -fx-background-radius: 25px;");
        // Add the container to the pane
        getChildren().add(container);

        // Set padding for the pane
        setPadding(new Insets(20));

        // Ensure the component is not resized automatically
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public void displayError(AnchorPane AnchorPane, Scene mainScene) {
        AnchorPane.setBottomAnchor(this, 20.0);
        AnchorPane.setLeftAnchor(this, mainScene.getWidth() / 4);
        AnchorPane.getChildren().add(this);

    }
}
