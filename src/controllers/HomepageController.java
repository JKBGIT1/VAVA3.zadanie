package controllers;

import controllers.accommodations.AccommodationsController;
import controllers.customers.CustomersController;
import controllers.payments.PaymentsController;
import controllers.reservations.ReservationsController;
import controllers.rooms.RoomsController;
import controllers.services.ServicesController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    public static final String HOMEPAGE_SCENE = "/fxmls/HomepageScene.fxml";

    public static final String ACCOMMODATIONS_SCENE = "/fxmls/accommodations/AccommodationsScene.fxml";

    public static final String RESERVATIONS_SCENE = "/fxmls/reservations/ReservationsScene.fxml";

    public static final String CUSTOMERS_SCENE = "/fxmls/customers/CustomersScene.fxml";
    public static final String ADD_CUSTOMER_SCENE = "/fxmls/customers/AddCustomerScene.fxml";

    public static final String SERVICES_SCENE = "/fxmls/services/ServicesScene.fxml";

    public static final String PAYMENTS_SCENE = "/fxmls/payments/PaymentsScene.fxml";

    public static final String ROOMS_SCENE = "/fxmls/rooms/RoomsScene.fxml";

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
            this.currentTime.setText("Velmi zle to je.");
        }
    }

    /*
     * Start of methods for setting controller and scenePath when switching between scenes.
     */

    public void homeScene(MouseEvent event) {
        this.setScenePath(HOMEPAGE_SCENE);
        this.setController(new HomepageController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void accommodationsScene(MouseEvent event) {
        this.setScenePath(ACCOMMODATIONS_SCENE);
        this.setController(new AccommodationsController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void reservationsScene(MouseEvent event) {
        this.setScenePath(RESERVATIONS_SCENE);
        this.setController(new ReservationsController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void customersScene(MouseEvent event) {
        this.setScenePath(CUSTOMERS_SCENE);
        this.setController(new CustomersController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void servicesScene(MouseEvent event) {
        this.setScenePath(SERVICES_SCENE);
        this.setController(new ServicesController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void paymentsScene(MouseEvent event) {
        this.setScenePath(PAYMENTS_SCENE);
        this.setController(new PaymentsController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    public void roomsScene(MouseEvent event) {
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
}
