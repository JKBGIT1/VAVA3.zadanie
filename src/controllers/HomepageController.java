package controllers;

import controllers.accommodations.AccommodationsController;
import controllers.customers.CustomersController;
import controllers.rooms.RoomsController;
import controllers.services.ServicesController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomepageController implements Initializable {

    public static final String HOMEPAGE_SCENE = "/fxmls/HomepageScene.fxml";

    public static final String ACCOMMODATIONS_SCENE = "/fxmls/accommodations/AccommodationsScene.fxml";

    public static final String CUSTOMERS_SCENE = "/fxmls/customers/CustomersScene.fxml";
    public static final String ADD_CUSTOMER_SCENE = "/fxmls/customers/AddCustomerScene.fxml";

    public static final String SERVICES_SCENE = "/fxmls/services/ServicesScene.fxml";

    public static final String ROOMS_SCENE = "/fxmls/rooms/RoomsScene.fxml";

    private static final Logger LOGGER = Logger.getLogger(HomepageController.class.getName());

    @FXML
    private Label currentTime;

    private String scenePath;
    private Object controller;

    private ObservableList<Customer> allCustomers;

    public HomepageController(
            ObservableList<Customer> allCustomers
    ) {
        this.allCustomers = allCustomers;
    }

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

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // program has to check if scene contains label with id 'currentTime'
        // because each controller class extends from HomepageController
        // it means, that initialize method is invoke every time when new controller is created
        if (this.currentTime != null) {
            LOGGER.info("Setting current time.");
            this.currentTime.setText("Velmi zle to je.");
        }
    }

    /*
     * Start of methods for setting controller and scenePath when switching between scenes.
     */

    public void homeScene(MouseEvent event) {
        LOGGER.info("Switching to Homepage scene.");
        this.setScenePath(HOMEPAGE_SCENE);
        this.setController(new HomepageController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void accommodationsScene(MouseEvent event) {
        LOGGER.info("Switching to Accommodation scene.");
        this.setScenePath(ACCOMMODATIONS_SCENE);
        this.setController(new AccommodationsController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void customersScene(MouseEvent event) {
        LOGGER.info("Switching to Customers scene.");
        this.setScenePath(CUSTOMERS_SCENE);
        this.setController(new CustomersController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void servicesScene(MouseEvent event) {
        LOGGER.info("Switching to Services scene.");
        this.setScenePath(SERVICES_SCENE);
        this.setController(new ServicesController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void roomsScene(MouseEvent event) {
        LOGGER.info("Switching to Rooms scene.");
        this.setScenePath(ROOMS_SCENE);
        this.setController(new RoomsController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    // When scenePath and controller is set program is going to change a scene on the screen
    public void switchScene(MouseEvent event) {
        try {
            if (!this.getScenePath().equals("")) {
                // This was taken from my school project in second grade on DBS
                // https://github.com/FIIT-DBS2020/project-hlavacka_muller/blob/master/src/GUI/SwitchScene.java
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(this.getScenePath()));
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                loader.setController(this.getController());

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

    /**
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
