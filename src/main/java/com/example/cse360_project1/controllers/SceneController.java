package com.example.cse360_project1.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private Stage stage;
    private Scene currentScene;
    private Scene previousScene;
    public SceneController(Stage stage) {
        this.stage = stage;
        this.currentScene = stage.getScene();
        previousScene = null;
    }
    public SceneController(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.currentScene = stage.getScene();
        this.previousScene = previousScene;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Scene getCurrentScene() {
        return currentScene;
    }
    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
    public Scene getPreviousScene() {
        return previousScene;
    }
    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void switchScene(Scene newScene) {
        previousScene = this.stage.getScene();
        currentScene = newScene;
        stage.setScene(newScene);
        stage.show();
    }
    public void previousScene() {
        if (previousScene != null) {
            currentScene = previousScene;
            stage.setScene(previousScene);
            stage.show();
        }
    }
    public void setTitle(String title) {
        stage.setTitle(title);
    }

}
