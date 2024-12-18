package com.example.cse360_project1.controllers;

import com.example.cse360_project1.models.Book;
import com.example.cse360_project1.models.Error;
import com.example.cse360_project1.models.Transaction;
import com.example.cse360_project1.models.User;
import com.example.cse360_project1.services.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SellerView {
    private final User user;
    private final SceneController sceneController;
    private String tab;

    public SellerView(User user, SceneController sceneController) {
        this.user = user;
        this.sceneController = sceneController;
        this.tab = "DASHBOARD";
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
        sceneController.setTitle("BookBetter - Seller");

        return scene;
    }

    private AnchorPane getContentPane(Scene mainScene) {
        switch (tab) {
            case "LIST":
                return getListBook(mainScene);
            case "TRANSACTIONS":
                return getTransactions(mainScene);
            case "LIST_SUCCESS":
                return getListBookSuccess();
            default:
                return getDashboard(mainScene);
        }
    }

    public HBox getOrderHBox(Transaction transaction) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 0, 10, 0));
        hBox.setSpacing(10);
        Label dateLabel = new Label(transaction.getDate().toString());

        Label orderNumLabel = new Label("#" + transaction.getId());

        Label bookName = new Label(transaction.getBook().getName());

        Label statusLabel = new Label(transaction.getStatus());

        Label priceLabel = new Label("" + transaction.getPrice());

        Button actionButton = new Button("View");
        actionButton.getStyleClass().add("button");
        actionButton.getStyleClass().add("maroon");

        hBox.getChildren().addAll(dateLabel, orderNumLabel, bookName, statusLabel, priceLabel, actionButton);

        return hBox;
    }

    public VBox getAllOrders(User user) {
        VBox orderVBox = new VBox();

        return orderVBox;
    }

    public AnchorPane getDashboard(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Hey " + user.getName() + ",");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("Track and manage your orders");

        VBox totalRevenue = new VBox();
        totalRevenue.getStyleClass().add("blurb");
        totalRevenue.getStyleClass().add("mini");
        totalRevenue.setSpacing(20);

        Label totalRevenueLabel = new Label("Total Revenue");
        totalRevenueLabel.getStyleClass().add("h2");
        totalRevenue.setPadding(new Insets(20, 20, 20, 20));

        Label errorLabel = new Label("No data found.");
        errorLabel.getStyleClass().add("text-lg");

        totalRevenue.getChildren().addAll(totalRevenueLabel, errorLabel);


        VBox recentOrders = new VBox();
        recentOrders.getStyleClass().add("blurb");
        recentOrders.getStyleClass().add("wide");
        recentOrders.setPadding(new Insets(20, 20, 20, 20));
        Label recentOrdersLabel = new Label("Recent Orders");
        recentOrdersLabel.getStyleClass().add("h2");

        Button viewAllButton = new Button("View All");
        viewAllButton.getStyleClass().add("secondary");
        viewAllButton.setPadding(new Insets(10, 15, 10, 15));
        viewAllButton.setOnAction(e -> {
            this.tab = "TRANSACTIONS";
            sceneController.switchScene(getScene());
        });

        HBox headerBox = new HBox();
        headerBox.setPadding(new Insets(0, 0, 10, 0));
        // Keep gap in between
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        headerBox.getChildren().addAll(recentOrdersLabel, spacer, viewAllButton);

        TableView orderTable = new TableView();

        orderTable.setEditable(false);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn dateColumn = new TableColumn("Date");
        TableColumn orderNumColumn = new TableColumn("Order num");
        TableColumn bookNameColumn = new TableColumn("Book name");
        TableColumn statusColumn = new TableColumn("Status");
        TableColumn priceColumn = new TableColumn("Price");

        ObservableList<Transaction> data = FXCollections.observableArrayList();
        orderTable.getColumns().addAll(dateColumn, orderNumColumn, bookNameColumn, statusColumn, priceColumn);

        recentOrders.getChildren().addAll(headerBox, orderTable);


        pane.getChildren().addAll(titleLabel, subtitleLabel, totalRevenue, recentOrders);

        String css = getClass().getResource("/com/example/cse360_project1/css/SellerView.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);

        AnchorPane.setTopAnchor(totalRevenue, 150.0);
        AnchorPane.setLeftAnchor(totalRevenue, 50.0);

        AnchorPane.setLeftAnchor(recentOrders, 50.0);

        AnchorPane.setBottomAnchor(recentOrders, 20.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public AnchorPane getListBook(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("List a Book");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));

        Label subtitleLabel = new Label("Sell a new book.");

        VBox listBlurb = new VBox();
        listBlurb.getStyleClass().add("blurb");
        listBlurb.getStyleClass().add("tall");
        listBlurb.setSpacing(10.0);
        listBlurb.setPadding(new Insets(20, 20, 20, 20));

        VBox bookNameVBox = new VBox();
        bookNameVBox.setSpacing(4.0);

        Label bookNameLabel = new Label("Book Name");
        bookNameLabel.getStyleClass().add("h3");

        TextField bookNameInput = new TextField();
        bookNameInput.setPromptText("Enter a book name");
        bookNameInput.getStyleClass().addAll("gray-border", "text-lg", "input");

        bookNameVBox.getChildren().addAll(bookNameLabel, bookNameInput);

        VBox author = new VBox();
        author.setSpacing(4.0);

        Label authorNameLabel = new Label("Author");
        authorNameLabel.getStyleClass().add("h3");

        TextField authorNameInput = new TextField();
        authorNameInput.setPromptText("Enter the author name");
        authorNameInput.getStyleClass().addAll("gray-border", "text-lg", "input");

        author.getChildren().addAll(authorNameLabel, authorNameInput);

        VBox conditionContainer = new VBox();
        conditionContainer.setSpacing(4.0);

        Label conditionNameLabel = new Label("Condition");
        conditionNameLabel.getStyleClass().add("h3");

        HBox condition = new HBox();
        condition.setSpacing(10);
        ComboBox<String> conditionCombo = new ComboBox();
        conditionCombo.getStyleClass().addAll("gray-border", "text-lg", "input");


        conditionCombo.setValue("Choose Account Type");
        conditionCombo.getItems().addAll("Lightly used", "Moderately used", "Heavily used ");

        Button chooseImageButton = new Button("Choose Image + ");
        chooseImageButton.getStyleClass().add("secondary");
        chooseImageButton.setPrefWidth(150);
        chooseImageButton.setPrefHeight(60);

        AtomicReference<File> imageFile = new AtomicReference<>();
        chooseImageButton.setOnAction(e -> {
            FileChooser imageChooser = new FileChooser();
            imageChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
            );
            imageFile.set(imageChooser.showOpenDialog(sceneController.getStage()));
            if (imageFile.get() != null) {
                chooseImageButton.setText(imageFile.get().getName());
            }
        });


        condition.getChildren().addAll((Node) conditionCombo, chooseImageButton);
        conditionContainer.getChildren().addAll(conditionNameLabel, condition);
        ArrayList<ToggleButton> allCategories = new ArrayList<>();
        ArrayList<String> selectedCategories = new ArrayList<>();
        ToggleButton natScienceButton = new ToggleButton("Natural Science");
        natScienceButton.getStyleClass().add("toggle-button");
        allCategories.add(natScienceButton);
        ToggleButton computerButton = new ToggleButton("Computer");
        computerButton.getStyleClass().add("toggle-button");
        allCategories.add(computerButton);

        ToggleButton mathButton = new ToggleButton("Math");
        mathButton.getStyleClass().add("toggle-button");
        allCategories.add(mathButton);

        ToggleButton englishLangButton = new ToggleButton("English Language");
        englishLangButton.getStyleClass().add("toggle-button");
        allCategories.add(englishLangButton);

        ToggleButton scifiButton = new ToggleButton("Sci-Fi");
        scifiButton.getStyleClass().add("toggle-button");
        allCategories.add(scifiButton);

        ToggleButton artButton = new ToggleButton("Art");
        artButton.getStyleClass().add("toggle-button");
        allCategories.add(artButton);

        ToggleButton novelButton = new ToggleButton("Novel");
        novelButton.getStyleClass().add("toggle-button");
        allCategories.add(novelButton);

        for (ToggleButton button: allCategories) {
            button.setOnAction(e -> {
                if (button.isSelected()) {
                    selectedCategories.add(button.getText());
                }
                else {
                    selectedCategories.remove(button.getText());

                }
            });
        }
        VBox categories = new VBox();
        categories.setSpacing(5);
        Label categoriesLabel = new Label("Categories");
        categoriesLabel.getStyleClass().add("h3");
        HBox categoriesBox1 = new HBox(10, natScienceButton, computerButton);
        HBox categoriesBox2 = new HBox(10, mathButton, englishLangButton);
        HBox categoriesBox3 = new HBox(10, scifiButton, artButton, novelButton);
        categories.getChildren().addAll(categoriesLabel, categoriesBox1, categoriesBox2, categoriesBox3);

        Button submitButton = new Button("List your book");
        submitButton.getStyleClass().add("h3");
        submitButton.getStyleClass().add("button");
        submitButton.setStyle("-fx-pref-width: 300px; -fx-background-color: #640000; -fx-text-fill: white; -fx-pref-height: 50px;");

        HBox submitArea = new HBox();
        submitArea.setPadding(new Insets(20, 20, 20, 20));
        Region spacerLeft =  new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        Region spacerRight =  new Region();
        HBox.setHgrow(spacerRight, Priority.ALWAYS);
        submitArea.getChildren().addAll(spacerLeft, submitButton, spacerRight);
        listBlurb.getChildren().addAll(bookNameVBox, author, conditionContainer, categories, submitArea);

        pane.getChildren().addAll(titleLabel, subtitleLabel, listBlurb);
        String css = getClass().getResource("/com/example/cse360_project1/css/SellerView.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);

        AnchorPane.setTopAnchor(listBlurb, 120.0);
        AnchorPane.setLeftAnchor(listBlurb, 50.0);
        pane.getStylesheets().add(css);

        submitButton.setOnAction(e -> {
           String bookName = bookNameInput.getText();
           String bookAuthor = authorNameInput.getText();
           String bookCondition = conditionCombo.getValue();
           String bookCategories = Arrays.toString(selectedCategories.toArray());
           if (bookName.isEmpty() || bookAuthor.isEmpty() || bookCondition.isEmpty() || bookCategories.isEmpty() || bookCondition.equals("Choose Account Type")) {
               Error emptyFieldError = new Error("Submit error: One or more empty field");
               emptyFieldError.displayError(pane, mainScene);
           } else if (imageFile.get() == null) {
               Error imageError = new Error("Submit error: Image failed");
               imageError.displayError(pane, mainScene);
           } else {
               Book newBook = new Book(user.getId(), bookName, bookAuthor, bookCondition, bookCategories, user.getId(), imageFile.get());
               JDBCConnection connection = new JDBCConnection();
               if (connection.addBook(newBook)) {
                   System.out.println("Book added: " + newBook.getName());

                   this.tab = "LIST_SUCCESS";
                   sceneController.switchScene(getScene());
               }
           }
        });
        return pane;
    }

    public AnchorPane getListBookSuccess() {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Congrats!");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));

        VBox successBox = new VBox();
        successBox.setSpacing(5);
        successBox.setPadding(new Insets(20, 20, 20, 20));
        successBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);

        buttonBox.setSpacing(20);

        Button listNewBookButton = new Button("List new Book");
        listNewBookButton.getStyleClass().add("button");
        listNewBookButton.getStyleClass().add("maroon");
        listNewBookButton.getStyleClass().add("text-lg");

        listNewBookButton.setOnAction(e -> {
            this.tab = "LIST";
            sceneController.switchScene(getScene());
        });

        Button returnButton = new Button("Return to dashboard");
        returnButton.getStyleClass().add("button");
        returnButton.getStyleClass().add("secondary");
        returnButton.getStyleClass().add("text-lg");

        returnButton.setOnAction(e -> {
            this.tab = "DASHBOARD";
            sceneController.switchScene(getScene());
        });

        buttonBox.getChildren().addAll(listNewBookButton, returnButton);

        Label subLabel = new Label("Your book has been listed successfully. It is pending approval.");

        subLabel.getStyleClass().add("h2");
        subLabel.setPadding(new Insets(20, 20, 20, 20));

        successBox.getChildren().addAll(titleLabel, subLabel, buttonBox);

        pane.getChildren().addAll(successBox);

        AnchorPane.setTopAnchor(successBox, 280.0);
        AnchorPane.setLeftAnchor(successBox, 200.0);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        pane.getStylesheets().add(css);

        return pane;
    }

    public AnchorPane getEditListings(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Edit Listings");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));

        VBox contentBox = new VBox();
        contentBox.setPadding(new Insets(20, 20, 20, 20));
        contentBox.getStyleClass().add("blurb");

        //Get the user's listed books
        JDBCConnection jdbcConnection = new JDBCConnection();
        ArrayList<Book> userBooks = jdbcConnection.getBookCollection(user);
        //If user does not have books listed
        if (userBooks.isEmpty()) {
            // No books listed
            Label noBooksLabel = new Label("You currently have no books listed for sale.");
            noBooksLabel.getStyleClass().add("h2");
            contentBox.getChildren().add(noBooksLabel);
        }
        else{
            //List of books
            Label selectBookLabel = new Label("Select a Book");
            selectBookLabel.getStyleClass().add("h3");
            ListView<Book> bookListView = new ListView<>();
            bookListView.getItems().addAll(userBooks);

            Button editButton = new Button("Edit Selected Book");
            editButton.getStyleClass().add("primary");

            // Book details
            VBox detailsForm = new VBox(10);

            editButton.setOnAction(e -> {
                Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
                if (selectedBook == null) {
                    Error noSelectionError = new Error("Please select a book to edit.");
                    noSelectionError.displayError(pane, mainScene);
                }
                else{
                    populatedEditForm(detailsForm, selectedBook, jdbcConnection);
                }
            });

            contentBox.getChildren().addAll(selectBookLabel, bookListView, editButton, detailsForm);
        }

        pane.getChildren().addAll(titleLabel, contentBox);
        AnchorPane.setTopAnchor(titleLabel, 300.0);
        AnchorPane.setLeftAnchor(titleLabel, 200.0);
        AnchorPane.setTopAnchor(contentBox, 300.0);
        return pane;
    }

    private void populatedEditForm(VBox detailsForm, Book book, JDBCConnection jdbcConnection) {
        detailsForm.getChildren().clear();

        //Name of book
        Label bookNameLabel = new Label("Book Name:");
        bookNameLabel.getStyleClass().add("h3");
        TextField bookNameField = new TextField(book.getName());
        bookNameField.getStyleClass().addAll("gray-border", "input");

        //Author of book
        Label authorLabel = new Label("Author Name:");
        bookNameLabel.getStyleClass().add("h3");
        TextField authorField = new TextField(book.getAuthor());
        bookNameField.getStyleClass().addAll("gray-border", "input");

        //Condition of book
        Label conditionLabel = new Label("Book Condition:");
        bookNameLabel.getStyleClass().add("h3");
        ComboBox<String> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll("Lightly used", "moderately used", "Heavily used");
        conditionComboBox.setValue(book.getCondition());
        conditionComboBox.getStyleClass().addAll("gray-border", "input");
    }
    public AnchorPane getTransactions(Scene mainScene) {
        AnchorPane pane = new AnchorPane();
        Label titleLabel = new Label("Transactions");
        titleLabel.getStyleClass().add("h1");
        titleLabel.setPadding(new Insets(20, 20, 20, 20));
        Label subtitleLabel = new Label("View your transaction history");

        pane.getChildren().addAll(titleLabel, subtitleLabel);
        String css = getClass().getResource("/com/example/cse360_project1/css/UserSettings.css").toExternalForm();
        AnchorPane.setTopAnchor(titleLabel, 30.0);
        AnchorPane.setLeftAnchor(titleLabel, 50.0);
        AnchorPane.setTopAnchor(subtitleLabel, 75.0);
        AnchorPane.setLeftAnchor(subtitleLabel, 50.0);
        pane.getStylesheets().add(css);
        return pane;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public HBox categoriesPieChart(User user) {
        HBox main = new HBox();
        main.setAlignment(Pos.CENTER); // Aligns children in the center horizontally
        main.setSpacing(10);
        // Get categories sold
        Map<String, Integer> categoryCounts = user.getCategoriesSold();

        // Create PieChart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey() + " - " + entry.getValue(), entry.getValue()));
        }

        // Create PieChart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Books Sold by Category");

        // Set preferred size for PieChart
        pieChart.setPrefSize(300, 300); // Adjust these values as needed

        // Add PieChart to HBox
        main.getChildren().add(pieChart);

        return main;
    }
    public HBox conditionsPieChart(User user) {
        HBox main = new HBox();
        main.setAlignment(Pos.CENTER); // Aligns children in the center horizontally
        main.setSpacing(10);
        // Get categories sold
        Map<String, Integer> conditionsSold = user.getConditionsSold();

        // Create PieChart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : conditionsSold.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey() + " - " + entry.getValue(), entry.getValue()));
        }

        // Create PieChart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Books Sold by Condition");

        // Set preferred size for PieChart
        pieChart.setPrefSize(300, 300); // Adjust these values as needed

        // Add PieChart to HBox
        main.getChildren().add(pieChart);

        return main;
    }

}