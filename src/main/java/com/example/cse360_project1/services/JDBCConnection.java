package com.example.cse360_project1.services;

import com.example.cse360_project1.*;
import com.example.cse360_project1.controllers.SceneController;
import com.example.cse360_project1.controllers.UserSettingsPage;
import com.example.cse360_project1.models.Book;
import com.example.cse360_project1.models.User;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;

public class JDBCConnection {
    Connection connection;
    ResultSet result;
    Exception error;
    public JDBCConnection() {

    }
    private Connection getConnection() throws SQLException {
        this.connection =  DriverManager.getConnection("jdbc:mysql://bookbetter-aws.czoua2woyqte.us-east-2.rds.amazonaws.com:3306/user", "admin", "!!mqsqlhubbard2024");
        return connection;
    }
    public ResultSet fetchQuery(String query) throws SQLException {
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
    public int updateQuery(String query) throws SQLException {
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


                    User user = new User(id, username, type, password);
                    System.out.println(user.toString());
                    return user;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User registerUser(String username, String password, String type) {
        try {
            ResultSet results = fetchQuery("SELECT COUNT(*) FROM users;");
            if (results.next()) {

                int id = results.getInt(1) + 1;
                int updateResult = updateQuery("INSERT INTO users (id, username, password, type) VALUES ('" + id + "', '" + username + "', '" + password + "', '" + type + "')");
                User newUser = new User(id, username, type, password);
                // Get the user info scene and pass the main scene for returning
                return newUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean uploadImage(File image, int id)  {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        if (image != null) {
            try {
                Image newImage = new Image(image.toURI().toString());
                imageView.setImage(newImage);
                try (Connection currentConnection = getConnection()) {
                    FileInputStream inputStream = new FileInputStream(image);
                    String query = "UPDATE books SET book_image = ? WHERE book_id = ?";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setBinaryStream(1, inputStream, (int) image.length());
                    preparedStatement.setInt(2, id);
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public Book getBook(int id) {
        try {
            this.result = fetchQuery("SELECT * FROM books WHERE id = " + id);
            if (result.next()) {
                int book_id = result.getInt("book_id");
                int collection_id = result.getInt("collection_id");
                String book_name = result.getString("book_name");
                String book_author = result.getString("book_author");
                String book_condition = result.getString("book_condition");
                String categories = result.getString("book_categories");
                Book book = new Book(book_id, book_name, book_author, book_condition, categories, collection_id);
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            this.result = fetchQuery("SELECT * FROM books;");
            while (result.next()) {
                int book_id = result.getInt("book_id");
                int collection_id = result.getInt("collection_id");
                String book_name = result.getString("book_name");
                String book_author = result.getString("book_author");
                String book_condition = result.getString("book_condition");
                String categories = result.getString("book_categories");
                Book book = new Book(book_id, book_name, book_author, book_condition, categories, collection_id);
                books.add(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return books;
    }
    public ArrayList<Book> getBookCollection(User user) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            this.result = fetchQuery("SELECT * FROM users u JOIN book_collections bc ON u.id = bc.user_id JOIN  books b ON bc.collection_id = b.collection_id WHERE u.id ='" + user.getId() + "';");
            if (result.next()) {
                int book_id = result.getInt("book_id");
                int collection_id = result.getInt("collection_id");
                String book_name = result.getString("book_name");
                String book_author = result.getString("book_author");
                String book_condition = result.getString("book_condition");
                String categories = result.getString("categories");
                Book book = new Book(book_id, book_name, book_author, book_condition, categories, collection_id);
                books.add(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}
