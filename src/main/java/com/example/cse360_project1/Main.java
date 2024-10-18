package com.example.cse360_project1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    static SceneController sceneController;
    static Scene userInfoScene;
    @Override
    public void start(Stage primaryStage) {
        // Initialize SceneController with the primary stage

        // Create the main scene
        VBox mainLayout = new VBox();
        Button changeButton = new Button("Go to User Info");
        mainLayout.getChildren().add(changeButton);
        Scene mainScene = new Scene(mainLayout, 1000, 750);

        // Create a User and UserInfo scene
//        User currentUser = new User(1, "John Doe", "Admin", "password123");
//        UserInfo userInfoCreator = new UserInfo(currentUser, sceneController);
//
//        // Get the user info scene and pass the main scene for returning
//        Scene userInfoScene = userInfoCreator.getScene();

        LoginRegisterPage loginRegisterPage = new LoginRegisterPage(sceneController);
        Scene loginRegisterScene = loginRegisterPage.getScene(mainScene);
        // Set up button action to switch to the user info scene
        changeButton.setOnAction(e -> sceneController.switchScene(userInfoScene));

        // Set the initial scene and show the stage
        primaryStage.setTitle("BookBetter");
        primaryStage.setScene(loginRegisterScene);
        sceneController = new SceneController(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}