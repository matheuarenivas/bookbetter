package com.example.cse360_project1;

import javafx.scene.Scene;

import java.sql.DriverManager;
import java.sql.*;

public class JDBCConnection {
    Connection connection;
    ResultSet result;
    Exception error;
    public JDBCConnection() {

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
    public int updateQuery(String query) {
        try {
            this.connection =  DriverManager.getConnection("jdbc:mysql://bookbetter-aws.czoua2woyqte.us-east-2.rds.amazonaws.com:3306/user", "admin", "!!mqsqlhubbard2024");

            Statement statement = connection.createStatement();
            int update = statement.executeUpdate(query);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connection Started");

        return -1;
    }
    public ResultSet logIn(String username, String password) {
        try {
            this.result = fetchQuery("SELECT * FROM users WHERE username = '" + username + "'");

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
            this.result = fetchQuery("SELECT * FROM users WHERE username = '" + username + "'");

            if (result.next()) {
                String pass = result.getString("password");
                if (password.equals(pass)) {
                    int id = result.getInt("id");
                    String type = result.getString("type");


                    User user = new User((int) id, username, type, password);
                    System.out.println(user.toString());
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
            ResultSet results = fetchQuery("SELECT COUNT(*) FROM users;");
            if (results.next()) {

                int id = results.getInt(1) + 1;
                int updateResult = updateQuery("INSERT INTO users (id, username, password, type) VALUES ('" + id + "', '" + username + "', '" + password + "', '" + type + "')");
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
