package controllers.customers;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Accommodation;
import models.Customer;
import models.Reservation;
import models.Room;

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

    private Customer selectedCustomer = null;

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
            // Get all customers observable list from Serialization class
            this.customersTableView.setItems(Serialization.getInstance().getAllCustomers());
        }
    }

    public void cancelReservation() {
        // check if user selected customer from table view
        // if he/she did, then change value of this.selectedCustomer
        // otherwise show error popup
        this.checkCustomerSelection();
        if (this.selectedCustomer != null) {
            // check if customer have reservation
            if (this.selectedCustomer.getReservation() == null) {
                // selected customer doesn't have reservation
                // show error popup
                this.showErrorPopUp(
                        "No reservation",
                        "Selected customer doesn't have reservation"
                );
                LOGGER.info("User can't cancel reservation, because selected customer doesn't have one");
                return;
            }

            // customer has reservation so cancel it
            Room reservedRoom = this.selectedCustomer.getReservation().getReservedRoom();
            // firstly free the room
            reservedRoom.setIsFree(true);
            reservedRoom.setTakenFrom(null);
            reservedRoom.setTakenTo(null);
            // then cancel reservation for selected customer
            this.selectedCustomer.setReservation(null);
            // refresh customers table view to display new data
            customersTableView.refresh();
            // show success popup
            this.showSuccessPopUp(
                    "Success",
                    "Reservation was successfully canceled."
            );
            LOGGER.info("User successfully canceled reservation.");
        }
    }

    public void detailCustomerScene(MouseEvent event) {
        // check if user selected customer from table view
        // if he/she did, then change value of this.selectedCustomer
        // otherwise show error popup
        this.checkCustomerSelection();
        if (this.selectedCustomer != null) {
            LOGGER.info("Switching to DetailCustomer scene");
            this.setScenePath(DETAIL_CUSTOMER_SCENE);
            this.setController(new DetailCustomerController(
                    this.selectedCustomer
            ));
            this.switchScene(event);
        }
    }

    public void addCustomerScene(MouseEvent event) {
        LOGGER.info("Switching to AddCustomer scene");
        this.setScenePath(ADD_CUSTOMER_SCENE);
        this.setController(new AddCustomerController());
        this.switchScene(event);
    }

    public void reservationAccommodationScene(MouseEvent event) {
        // check if user selected customer from table view
        // if he/she did, then change value of this.selectedCustomer
        // otherwise show error popup
        this.checkCustomerSelection();
        if (this.selectedCustomer != null) {
            Customer customer = this.customersTableView.getSelectionModel().getSelectedItem();

            LOGGER.info("Switching to Reservation scene");
            this.setScenePath(RESERVATION_ACCOMMODATION_SCENE);
            this.setController(new ReservationAccommodationController(customer));
            this.switchScene(event);
        }
    }

    public void accommodateBasedOnReservation() {
        // check if user selected customer from table view
        // if he/she did, then change value of this.selectedCustomer
        // otherwise show error popup
        this.checkCustomerSelection();
        if (this.selectedCustomer != null) {
            // check if customer have reservation
            if (this.selectedCustomer.getReservation() == null) {
                // user doesn't have reservation
                // show error popup
                this.showErrorPopUp(
                        "No reservation",
                        "Selected customer doesn't have reservation."
                );

                return;
            }

            // customer has reservation make accommodation based on it
            Reservation reservation = this.selectedCustomer.getReservation();
            // create accommodation
            Accommodation accommodation = new Accommodation(
                    reservation.getDateFrom(),
                    reservation.getDateTo(),
                    reservation.getPrice(),
                    reservation.getReservedRoom()
            );

            // set newly created accommodation to customer's currentAccommodation
            this.selectedCustomer.setCurrentAccommodation(accommodation);
            // add newly created accommodation to accommodations history
            this.selectedCustomer.getAccommodations().add(accommodation);
            // delete reservation, because accommodation was created based on it
            this.selectedCustomer.setReservation(null);
            // refresh customers table view to display new data
            customersTableView.refresh();
            LOGGER.info("Accommodation was successfully created based on selectedCustomer's reservation");
            this.showSuccessPopUp(
                    "Success",
                    "Accommodation was successfully created based on customer's reservation"
            );
        }
    }

    public void checkCustomerSelection() {
        // check if user selected customer from table view
        // if he/she did, then change value of this.selectedCustomer
        // otherwise show error popup
        if (customersTableView.getSelectionModel().getSelectedItem() == null) {
            LOGGER.info("Customer wasn't selected.");
            this.showErrorPopUp(
                    "Selection error",
                    "You have to select customer for this process."
            );
        } else {
            this.selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        }
    }
}
