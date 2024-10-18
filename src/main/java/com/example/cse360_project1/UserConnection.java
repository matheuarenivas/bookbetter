package com.example.cse360_project1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserConnection extends JDBCConnection {
    public UserConnection() {
        super();
    }
    public void getBooks(User user) throws SQLException {
        try {
            int user_id = user.getId();
            ArrayList<Book> books = new ArrayList<>();
            JDBCConnection connection = new JDBCConnection();
            ResultSet result = connection.fetchQuery(
                    "SELECT * FROM user_schema.users u JOIN user_schema.book_collections bc ON u.id = bc.user_id JOIN user_schema.books b ON bc.collection_id = b.collection_id WHERE u.id =" + user_id + ";"
            );
            while (result.next()) {
                int id = result.getInt("book_id");
                String name = result.getString("book_name");
                String author = result.getString("book_author");
                String condition = result.getString("book_condition");
                String categoriesJSON = result.getString("book_categories");
                int collectionId = result.getInt("collection_id");
//                System.out.println("book_name: " + name + ", author: " + author + ", condition: " + condition + ", categories: " + categoriesJSON);
                Book book = new Book(id, name, author, condition, categoriesJSON, collectionId);
                user.addBook(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
