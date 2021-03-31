package controllers.customers;

import controllers.HomepageController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Customer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CustomersController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(CustomersController.class.getName());

    @FXML
    private TableView<Customer> customersTableView;
    @FXML
    private TableColumn<Customer, String> firstNameCol, lastNameCol, identificationNumberCol;
    @FXML
    private TableColumn<Customer, String> reservationCol, currentAccommodationCol;

    public CustomersController(ObservableList<Customer> allCustomers) {
        super(allCustomers);
    }

    public void detailCustomerScene(MouseEvent event) {
        LOGGER.info("Switching to DetailCustomer scene");
    }

    public void addCustomerScene(MouseEvent event) {
        LOGGER.info("Switching to AddCustomer scene");
        this.setScenePath(ADD_CUSTOMER_SCENE);
        this.setController(new AddCustomerController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.customersTableView != null) {
            LOGGER.info("Mapping attributes and methods to customersTableView.");
            // map table columns to customer attributes
            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            identificationNumberCol.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));

            // map table columns to customer methods
            // inspiration https://stackoverflow.com/questions/25204068/how-do-i-point-a-propertyvaluefactory-to-a-value-of-a-map
            reservationCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getRoomLabelFromReservation())
            );
            currentAccommodationCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getRoomLabelFromCurrentAccommodation())
            );

            // display customers in TableView
            LOGGER.info("Displaying customers in customersTableView.");
            this.customersTableView.setItems(this.getAllCustomers());
        }
    }
}
