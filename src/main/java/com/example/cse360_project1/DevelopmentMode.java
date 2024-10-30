package com.example.cse360_project1;

import com.example.cse360_project1.controllers.LoginRegisterPage;
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
        primaryStage.setScene(mainScene);
        sceneController = new SceneController(primaryStage);
        sceneController.setCurrentScene(mainScene);
        JDBCConnection connection = new JDBCConnection();
//        User buyer = connection.logInReturnUser("user", "pass");
//        User admin = connection.logInReturnUser("admin", "bookbetter1");
        User seller = connection.logInReturnUser("username", "password");
        LoginRegisterPage loginRegisterPage = new LoginRegisterPage(sceneController);
        loginRegisterPage.redirectUser(seller, sceneController);
//        loginRegisterPage.redirectUser(admin, sceneController);
//        loginRegisterPage.redirectUser(buyer, sceneController);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
