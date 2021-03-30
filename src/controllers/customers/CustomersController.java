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

public class CustomersController extends HomepageController {

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

    }

    public void addCustomerScene(MouseEvent event) {
        this.setScenePath(ADD_CUSTOMER_SCENE);
        this.setController(new AddCustomerController(
                this.getAllCustomers()
        ));
        this.switchScene(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.customersTableView != null) {
            // map table columns to customer attributes
            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            identificationNumberCol.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));

            // map table columns to customer methods
            reservationCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getRoomLabelFromReservation())
            );
            currentAccommodationCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getRoomLabelFromCurrentAccommodation())
            );

            // display customers in TableView
            this.customersTableView.setItems(this.getAllCustomers());
        }
    }
}
