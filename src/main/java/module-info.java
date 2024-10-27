module com.example.cse360_project1 {
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;


    opens com.example.cse360_project1 to javafx.fxml;
    exports com.example.cse360_project1;
    exports com.example.cse360_project1.services;
    opens com.example.cse360_project1.services to javafx.fxml;
    exports com.example.cse360_project1.models;
    opens com.example.cse360_project1.models to javafx.fxml;
    exports com.example.cse360_project1.controllers;
    opens com.example.cse360_project1.controllers to javafx.fxml;
}