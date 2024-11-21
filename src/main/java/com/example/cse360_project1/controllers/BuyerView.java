package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.Book;
import com.example.cse360_project1.models.Order;
import com.example.cse360_project1.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.time.LocalDate;

public class BuyerView {
    private final User user;
    private final SceneController sceneController;
    private String tab;

    private ObservableList<Book> cart = FXCollections.observableArrayList();

    public BuyerView(User user, SceneController sceneController) {
        this.user = user;
        this.sceneController = sceneController;
        this.tab = "BROWSE";
    }

    public Scene getScene() {
        Scene mainScene = sceneController.getCurrentScene();
        AnchorPane root = new AnchorPane();
        SidePanel sidePanelObject = new SidePanel(user, sceneController);
        AnchorPane sidePanel = sidePanelObject.getSidePanel();

        AnchorPane.setLeftAnchor(sidePanel, 0.0);
        AnchorPane.setTopAnchor(sidePanel, 0.0);
        AnchorPane.setBottomAnchor(sidePanel, 0.0);

        AnchorPane contentPane = getContentPane(mainScene);

        AnchorPane.setTopAnchor(contentPane, 0.0);
        AnchorPane.setLeftAnchor(contentPane, 200.0);
        AnchorPane.setBottomAnchor(contentPane, 0.0);
        root.getChildren().addAll(sidePanel, contentPane);
        Scene scene = new Scene(root, mainScene.getWidth(), mainScene.getHeight());
        sceneController.setTitle("BookBetter - Buyer");

        return scene;
    }

    private AnchorPane getContentPane(Scene mainScene) {
        switch (tab) {
            case "BROWSE":
                return getBrowseSection(mainScene);
            case "ORDERS":
                return getOrderHistory(mainScene);
            default:
                return getBrowseSection(mainScene);
        }
    }

    public AnchorPane getBrowseSection(Scene mainScene) {
        AnchorPane pane = new AnchorPane();

        Label booksLabel = new Label("Today's Books");
        booksLabel.getStyleClass().add("h1");
        booksLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("Browse and purchase books");

        VBox filters = new VBox();
        filters.getStyleClass().add("text-lg");
        Label filtersLabel = new Label("Filters");
        filtersLabel.getStyleClass().add("h2");

        VBox categoriesBox = new VBox(5);
        Label categoriesSubtitleLabel = new Label("Categories");
        categoriesSubtitleLabel.getStyleClass().add("h3");

        CheckBox natScienceCheckBox = new CheckBox("Natural Science");
        CheckBox computerCheckBox = new CheckBox("Computer");
        CheckBox mathCheckBox = new CheckBox("Math");
        CheckBox englishLanguageCheckBox = new CheckBox("English Language");
        CheckBox scifiCheckBox = new CheckBox("Sci-Fi");
        CheckBox artCheckBox = new CheckBox("Art");
        CheckBox novelCheckBox = new CheckBox("Novel");
        categoriesBox.getChildren().addAll(categoriesSubtitleLabel, natScienceCheckBox, computerCheckBox,
                mathCheckBox, englishLanguageCheckBox, scifiCheckBox, artCheckBox, novelCheckBox);

        VBox conditionsBox = new VBox(5);
        Label conditionsSubtitleLabel = new Label("Condition");
        conditionsSubtitleLabel.getStyleClass().add("h3");

        ToggleGroup conditionGroup = new ToggleGroup();
        RadioButton usedButton = new RadioButton("Used Like New");
        RadioButton moderateButton = new RadioButton("Moderately Used");
        RadioButton heavilyButton = new RadioButton("Heavily Used");
        usedButton.setToggleGroup(conditionGroup);
        moderateButton.setToggleGroup(conditionGroup);
        heavilyButton.setToggleGroup(conditionGroup);
        conditionsBox.getChildren().addAll(conditionsSubtitleLabel, usedButton, moderateButton, heavilyButton);

        filters.setSpacing(14);
        filters.getChildren().addAll(filtersLabel, categoriesBox, conditionsBox);

        VBox booksGrid = new VBox(10);
        booksGrid.setPadding(new Insets(20));
        booksGrid.setPrefWidth(600);

        // Add mock data for initial display
        ObservableList<Book> allBooks = FXCollections.observableArrayList(
                new Book(1, "Dune", "Frank Herbert", "Used Like New", "[\"Natural Science\", \"Sci-Fi\"]", 1, new File("/Users/matheuarenivas/Downloads/bookbetter-master/src/main/java/com/example/cse360_project1/controllers/book.jpg")),
                new Book(2, "The Maze Runner", "James Dashner", "Moderately Used", "[\"Sci-Fi\"]", 2, new File("/Users/matheuarenivas/Downloads/bookbetter-master/src/main/java/com/example/cse360_project1/controllers/book.jpg")),
                new Book(3, "Calculating Stars", "Mary Kowal", "Heavily Used", "[\"Math\", \"Sci-Fi\"]", 3, new File("/Users/matheuarenivas/Downloads/bookbetter-master/src/main/java/com/example/cse360_project1/controllers/book.jpg")),
                new Book(4, "The Martian", "Andy Weir", "Used Like New", "[\"Sci-Fi\"]", 4, new File("/Users/matheuarenivas/Downloads/bookbetter-master/src/main/java/com/example/cse360_project1/controllers/book.jpg"))
        );

        populateBooksGrid(booksGrid, allBooks);

        Button filterButton = new Button("Filter Books");
        filterButton.setOnAction(e -> {
            ObservableList<Book> filteredBooks = allBooks.filtered(book -> {
                boolean matchesCategory =
                        (natScienceCheckBox.isSelected() && book.getCategories().contains("Natural Science")) ||
                                (computerCheckBox.isSelected() && book.getCategories().contains("Computer")) ||
                                (mathCheckBox.isSelected() && book.getCategories().contains("Math")) ||
                                (englishLanguageCheckBox.isSelected() && book.getCategories().contains("English Language")) ||
                                (scifiCheckBox.isSelected() && book.getCategories().contains("Sci-Fi")) ||
                                (artCheckBox.isSelected() && book.getCategories().contains("Art")) ||
                                (novelCheckBox.isSelected() && book.getCategories().contains("Novel"));

                String selectedCondition =
                        conditionGroup.getSelectedToggle() != null ?
                                ((RadioButton) conditionGroup.getSelectedToggle()).getText() : "";

                boolean matchesCondition = selectedCondition.isEmpty() || book.getCondition().equals(selectedCondition);

                return matchesCategory && matchesCondition;
            });

            booksGrid.getChildren().clear();
            populateBooksGrid(booksGrid, filteredBooks);
        });

        VBox filterSection = new VBox(10, filters, filterButton);
        HBox content = new HBox(20, filterSection, booksGrid);
        content.setPadding(new Insets(20));

        AnchorPane.setTopAnchor(booksLabel, 30.0);
        AnchorPane.setLeftAnchor(booksLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        AnchorPane.setTopAnchor(content, 120.0);
        AnchorPane.setLeftAnchor(content, 50.0);
        AnchorPane.setRightAnchor(content, 50.0);

        pane.getChildren().addAll(booksLabel, subtitleLabel, content);

        String css = getClass().getResource("/com/example/cse360_project1/css/BuyerView.css").toExternalForm();
        pane.getStylesheets().add(css);

        return pane;
    }

    private void populateBooksGrid(VBox booksGrid, ObservableList<Book> books) {
        for (Book book : books) {
            HBox bookItem = new HBox(10);

            // Create ImageView for Book Image
            ImageView bookImageView;
            if (book.getImage() != null && book.getImage().exists()) {
                System.out.println("Loading image for: " + book.getName());
                bookImageView = new ImageView(new Image(book.getImage().toURI().toString()));
            } else {
                System.out.println("No image found for: " + book.getName());
                bookImageView = new ImageView(new Image("/Users/matheuarenivas/Downloads/bookbetter-master/src/main/java/com/example/cse360_project1/controllers/book.jpg"));
            }
            bookImageView.setFitWidth(100);
            bookImageView.setFitHeight(150);

            Label bookDetails = new Label(book.getName() + " by " + book.getAuthor() + " - $" + book.getPrice());
            Button addToCartButton = new Button("Add");
            addToCartButton.setOnAction(event -> addToCart(book));

            bookItem.getChildren().addAll(bookImageView, bookDetails, addToCartButton);
            booksGrid.getChildren().add(bookItem);
        }
    }

    private void addToCart(Book book) {
        cart.add(book);
        System.out.println(book.getName() + " added to cart.");
    }


    public AnchorPane getOrderHistory(Scene mainScene) {
        AnchorPane pane = new AnchorPane();

        Label orderHistoryLabel = new Label("Order History");
        orderHistoryLabel.getStyleClass().add("h1");
        orderHistoryLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("View your past orders");
        subtitleLabel.setPadding(new Insets(10, 20, 20, 20));

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select a date");
        datePicker.setPadding(new Insets(10, 10, 10, 10));

        TableView<Order> orderTable = new TableView<>();
        TableColumn<Order, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Order, String> titleColumn = new TableColumn<>("Book Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Order, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable.getColumns().addAll(dateColumn, titleColumn, quantityColumn, priceColumn);

        // Mock order data
        ObservableList<Order> allOrders = FXCollections.observableArrayList(
                new Order("2024-11-21", "Book 1", 1, 15.99),
                new Order("2024-11-20", "Book 2", 2, 29.99),
                new Order("2024-11-20", "Book 3", 1, 12.50),
                new Order("2024-11-19", "Book 4", 1, 18.75)
        );

        orderTable.setItems(allOrders);

        datePicker.setOnAction(event -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                String selectedDateString = selectedDate.toString();
                ObservableList<Order> filteredOrders = allOrders.filtered(order -> order.getDate().equals(selectedDateString));
                orderTable.setItems(filteredOrders);
            } else {
                orderTable.setItems(allOrders);
            }
        });

        AnchorPane.setTopAnchor(orderHistoryLabel, 30.0);
        AnchorPane.setLeftAnchor(orderHistoryLabel, 50.0);

        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);

        AnchorPane.setTopAnchor(datePicker, 120.0);
        AnchorPane.setLeftAnchor(datePicker, 50.0);

        AnchorPane.setTopAnchor(orderTable, 170.0);
        AnchorPane.setLeftAnchor(orderTable, 50.0);
        AnchorPane.setRightAnchor(orderTable, 50.0);
        AnchorPane.setBottomAnchor(orderTable, 50.0);

        pane.getChildren().addAll(orderHistoryLabel, subtitleLabel, datePicker, orderTable);

        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        pane.getStylesheets().add(css);

        return pane;
    }


    public void setTab(String tab) {
        this.tab = tab;
    }
}