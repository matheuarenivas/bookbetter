package com.example.cse360_project1;

import javafx.scene.Scene;

import java.sql.DriverManager;
import java.sql.*;

public class JDBCConnection {
    Connection connection;
    ResultSet result;
    Exception error;
    public JDBCConnection() {
//        try {
//            this.connection =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user_schema", "root", "!!mqsqlhubbard2024");
//
//            Statement statement = connection.createStatement();
//            this.result = statement.executeQuery(query);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Connection Started");
    }

    public ResultSet fetchQuery(String query) {
        try {
            this.connection =  DriverManager.getConnection("jdbc:mysql://bookbetter-aws.czoua2woyqte.us-east-2.rds.amazonaws.com:3306/user", "admin", "!!mqsqlhubbard2024");

            Statement statement = connection.createStatement();
            this.result = statement.executeQuery(query);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connection Started");

        return null;
    }

    public ResultSet logIn(String username, String password) {
        try {
            this.connection =  DriverManager.getConnection("jdbc:mysql://bookbetter-aws.czoua2woyqte.us-east-2.rds.amazonaws.com:3306/user", "admin", "!!mqsqlhubbard2024");
            Statement statement = connection.createStatement();
            this.result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");

            if (result.next()) {
                String pass = result.getString("password");
                if (password.equals(pass)) {
                    int id = result.getInt("id");
                    String type = result.getString("type");

                    User user = new User((int) id, username, type, password);
                    System.out.println(user.toString());
                    SceneController sceneController = Main.sceneController;
                    UserSettingsPage userSettingsPage = new UserSettingsPage(user, sceneController);

                    // Get the user info scene and pass the main scene for returning
                    sceneController.switchScene(userSettingsPage.getScene());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User logInReturnUser(String username, String password) {
        try {
            this.connection =  DriverManager.getConnection("jdbc:mysql://bookbetter-aws.czoua2woyqte.us-east-2.rds.amazonaws.com:3306/user", "admin", "!!mqsqlhubbard2024");
            Statement statement = connection.createStatement();
            this.result = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");

            if (result.next()) {
                String pass = result.getString("password");
                if (password.equals(pass)) {
                    int id = result.getInt("id");
                    String type = result.getString("type");


                    User user = new User((int) id, username, type, password);
                    System.out.println(user.toString());
                    SceneController sceneController = Main.sceneController;
                    UserSettingsPage userSettingsPage = new UserSettingsPage(user, sceneController);
                    sceneController.switchScene(userSettingsPage.getScene());

                    // Get the user info scene and pass the main scene for returning
//                    Scene userInfoScene = userInfoCreator.getScene();
//                    sceneController.switchScene(userInfoScene);
                    return user;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet registerUser(String username, String password, String type) {
        try {
            Connection connection =  DriverManager.getConnection("jdbc:mysql://bookbetter-aws.czoua2woyqte.us-east-2.rds.amazonaws.com:3306/user", "admin", "!!mqsqlhubbard2024");
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT COUNT(*) FROM users;");
            if (results.next()) {

                int id = results.getInt(1) + 1;
                int updateResult = statement.executeUpdate("INSERT INTO users (id, username, password, type) VALUES ('" + id + "', '" + username + "', '" + password + "', '" + type + "')");
                User newUser = new User(id, username, type, password);

                SceneController sceneController = Main.sceneController;
                UserInfo userInfoCreator = new UserInfo(newUser, sceneController);
                UserSettingsPage userSettingsPage = new UserSettingsPage(newUser, sceneController);
                // Get the user info scene and pass the main scene for returning
                sceneController.switchScene(userSettingsPage.getScene());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
