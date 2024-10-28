package com.example.cse360_project1;

import com.example.cse360_project1.controllers.SceneController;
import com.example.cse360_project1.controllers.SellerView;
import com.example.cse360_project1.controllers.UserSettingsPage;
import com.example.cse360_project1.models.User;
import com.example.cse360_project1.services.JDBCConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DevelopmentMode extends Application {
    static SceneController sceneController;
    static Scene userInfoScene;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BookBetter - Login");
        VBox mainLayout = new VBox();
        Scene mainScene = new Scene(mainLayout, 1280, 830);
//        LoginRegisterPage loginRegisterPage = new LoginRegisterPage(sceneController);
//        Scene loginRegisterScene = loginRegisterPage.getScene(mainScene);
        sceneController = new SceneController(primaryStage);
        sceneController.setCurrentScene(mainScene);
        JDBCConnection connection = new JDBCConnection();
        User user = connection.logInReturnUser("username", "password");
//        UserSettingsPage userSettingsPage = new UserSettingsPage(user, sceneController);
        SellerView sellerView = new SellerView(user, sceneController);
        primaryStage.setScene(sellerView.getScene());
        primaryStage.show();



    }
    public static void main(String[] args) {
        launch(args);
    }
}
