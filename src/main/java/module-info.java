module com.example.cse360_project1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.cse360_project1 to javafx.fxml;
    exports com.example.cse360_project1;
}