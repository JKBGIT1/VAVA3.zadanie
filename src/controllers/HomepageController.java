package controllers;

import controllers.accommodations.AccommodationsController;
import controllers.customers.CustomersController;
import controllers.rooms.RoomsController;
import controllers.services.ServicesController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Room;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomepageController implements Initializable {

    public static final String HOMEPAGE_SCENE = "/fxmls/HomepageScene.fxml";

    public static final String ACCOMMODATIONS_SCENE = "/fxmls/accommodations/AccommodationsScene.fxml";

    public static final String CUSTOMERS_SCENE = "/fxmls/customers/CustomersScene.fxml";
    public static final String ADD_CUSTOMER_SCENE = "/fxmls/customers/AddCustomerScene.fxml";
    public static final String RESERVATION_ACCOMMODATION_SCENE = "/fxmls/customers/ReservationAccommodationScene.fxml";
    public static final String DETAIL_CUSTOMER_SCENE = "/fxmls/customers/DetailCustomerScene.fxml";
    public static final String DETAIL_ACCOMMODATION_SCENE = "/fxmls/customers/DetailAccommodationScene.fxml";

    public static final String SERVICES_SCENE = "/fxmls/services/ServicesScene.fxml";

    public static final String ROOMS_SCENE = "/fxmls/rooms/RoomsScene.fxml";
    public static final String ADD_ROOM_SCENE = "/fxmls/rooms/AddRoomScene.fxml";
    public static final String ADD_CATEGORY_SCENE = "/fxmls/rooms/AddCategoryScene.fxml";
    public static final String DISPLAY_ROOMS_SCENE = "/fxmls/rooms/DisplayRoomsScene.fxml";
    public static final String DETAIL_ROOM_SCENE = "/fxmls/rooms/DetailRoomScene.fxml";

    private static final Logger LOGGER = Logger.getLogger(HomepageController.class.getName());

    @FXML
    private Label currentTime;
    @FXML
    private ComboBox<String> languageComboBox;

    private String scenePath;
    private Object controller;

    /*
     * Start of getters and setters
     */

    public String getScenePath() {
        return scenePath;
    }

    public void setScenePath(String scenePath) {
        this.scenePath = scenePath;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    /*
     * End of getters and setters
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // program has to check if scene contains label with id 'currentTime'
        // because each controller class extends from HomepageController
        // it means, that initialize method is invoke every time when new controller is created
        if (currentTime != null) {
            LOGGER.info("Setting current time.");
            currentTime.setText("Velmi zle to je.");
        }
        // if scene contains languageComboBox, then set items to it
        if (languageComboBox != null) {
            languageComboBox.setItems(FXCollections.observableArrayList(
                    "SK", "EN", "US"
            ));
        }
    }

    /*
     * Start of methods for setting controller and scenePath when switching between scenes.
     */

    public void homeScene(MouseEvent event) {
        LOGGER.info("Switching to Homepage scene.");
        this.setScenePath(HOMEPAGE_SCENE);
        this.setController(new HomepageController());
        this.switchScene(event);
    }

    public void accommodationsScene(MouseEvent event) {
        LOGGER.info("Switching to Accommodation scene.");
        this.setScenePath(ACCOMMODATIONS_SCENE);
        this.setController(new AccommodationsController());
        this.switchScene(event);
    }

    public void customersScene(MouseEvent event) {
        LOGGER.info("Switching to Customers scene.");
        this.setScenePath(CUSTOMERS_SCENE);
        this.setController(new CustomersController());
        this.switchScene(event);
    }

    public void servicesScene(MouseEvent event) {
        LOGGER.info("Switching to Services scene.");
        this.setScenePath(SERVICES_SCENE);
        this.setController(new ServicesController());
        this.switchScene(event);
    }

    public void roomsScene(MouseEvent event) {
        LOGGER.info("Switching to Rooms scene.");
        this.setScenePath(ROOMS_SCENE);
        this.setController(new RoomsController());
        this.switchScene(event);
    }

    // When scenePath and controller is set program is going to change a scene on the screen
    public void switchScene(MouseEvent event) {
        try {
            if (!this.getScenePath().equals("")) {
                // This was taken from my school project in second grade on DBS
                // https://github.com/FIIT-DBS2020/project-hlavacka_muller/blob/master/src/GUI/SwitchScene.java
                Locale locale = new Locale("en", "UK");
                ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.english_en_UK", locale);
                // create new FXMLloader and set location
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(this.getScenePath()));
                // get stage from event
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                // fxmlLoader loads controller
                loader.setController(this.getController());
                // fxmlLoader loads internationalization resources
                loader.setResources(resourceBundle);

                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                // Load css
                scene.getStylesheets().add(getClass().getResource("/css/template.css").toExternalForm());
                window.setScene(scene);
                window.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * End of methods for setting controller and scenePath when switching between scenes.
     */

    public void quit() {
        LOGGER.info("End of the program.");
        System.exit(0);
    }

    /*
     * Mapping attributes to columns in TableView
     */

    public void mapCustomers(
        TableColumn<Room, String> roomLabel,
        TableColumn<Room, String> categoryCol,
        TableColumn<Room, Double> priceCol,
        TableColumn<Room, Date> dateFromCol,
        TableColumn<Room, Date> dateToCol,
        TableColumn<Room, Boolean> freeCol
    ) {
        roomLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateFromCol.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
        dateToCol.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
        freeCol.setCellValueFactory(new PropertyValueFactory<>("isFree"));
    }

    /*
     * Converting methods
     */

    // NOTE: This function needs to be used inside try and catch
    public double convertStringToDouble(String stringNumber) {
        // Before parse replace commas with dots to prevent error
        double doubleNumber = Double.parseDouble(stringNumber.replaceAll(",","."));

        // If user didn't use zero as an input then we floor this number on two decimals
        if (doubleNumber != 0) {
            doubleNumber = Math.floor(doubleNumber * 100) / 100; // Taken from https://stackoverflow.com/questions/7747469/how-can-i-truncate-a-double-to-only-two-decimal-places-in-java
        }
        return doubleNumber;
    }

    // NOTE: This function needs to be used inside try and catch
    public String convertDateToString(Date date) {
        // Inspiration from https://www.javatpoint.com/java-date-to-string
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    // NOTE: This function needs to be used inside try and catch
    public String convertDoubleToString(double price) {
        return String.valueOf(price);
    }

    public Date convertStringToDate(String stringDate) {
        try {
            // Try to handle more types of date format
            stringDate = stringDate.replaceAll("/", ".");
            stringDate = stringDate.replaceAll("-", ".");
            stringDate = stringDate.replaceAll(",", ".");

            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(stringDate); // Inspiration from https://www.javatpoint.com/java-string-to-date
            return date;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * End of Converting methods
     */


    /*
     * Start of PopUps
     */

    // Inspiration for PopUp windows on https://stackoverflow.com/questions/26341152/controlsfx-dialogs-deprecated-for-what/32618003#32618003
    public void showSuccessPopUp(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Information");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public void showErrorPopUp(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public int showInputDialog(String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Fill text field");
        dialog.setHeaderText("INPUT");
        dialog.setContentText(contentText);

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
                return Integer.parseInt(result.get());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return 1; // Default value will be 1, if user didn't enter int.
    }

    public boolean showConfirmationPopUp(String headerText, String contentText ) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Choose an option");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            // If condition under this comment is true, user confirm action.
            if (result.get().getText().equals("OK")) {
                return true;
            }
        }

        return false;
    }
}
