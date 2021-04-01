package controllers.accommodations;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Accommodation;
import models.Customer;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AccommodationsController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(AccommodationsController.class.getName());

    @FXML
    private TableView<Customer> customersUnpaidAccommodations;
    @FXML
    private TableColumn<Customer, String> firstNameCol, lastNameCol, identificationNumberCol;
    @FXML
    private TableColumn<Customer, String> roomLabelCol, categoryCol, dateToCol, priceCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (customersUnpaidAccommodations != null) {
            // get all customers
            ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Serialization.getInstance().getAllCustomers());
            ObservableList<Customer> customersWithUnpaidAcc = FXCollections.observableArrayList();
            // get current date for checking condition inside for loop
            Date currentDate = new Date();

            LOGGER.info("Adding all unpaid accommodations to customersWithUnpaidAcc");
            // go through each customer and his/her accommodation history
            // check if he/she paid for his latter accommodations
            for (Customer customer : allCustomers) {
                // go through each customer's accommodations
                System.out.println(customer.getAccommodations());
                for (Accommodation accommodation: customer.getAccommodations()) {
                    // if customer didn't paid for accommodation and it is after accommodation display it in table
                    if (accommodation.getPayment() == null && currentDate.after(accommodation.getDateTo())) {
                        // create customer, who doesn't paid for his accommodation
                        Customer customerWithUnpaidAcc = new Customer(
                                customer.getFirstName(),
                                customer.getLastName(),
                                customer.getIdentificationNumber()
                        );
                        // set unpaid accommodation as a current accommodation to display it in table view
                        customerWithUnpaidAcc.setCurrentAccommodation(accommodation);
                        // add unpaid accommodation to all unpaid accommodations
                        customersWithUnpaidAcc.add(customerWithUnpaidAcc);
                    }
                }
            }

            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            identificationNumberCol.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));

            // map table columns to customer methods
            // inspiration https://stackoverflow.com/questions/25204068/how-do-i-point-a-propertyvaluefactory-to-a-value-of-a-map
            roomLabelCol.setCellValueFactory(
                    // display room label for unpaid accommodation in table column Room label
                    data -> new ReadOnlyStringWrapper(data.getValue().getCurrentAccommodation().getReservedRoom().getLabel())
            );
            categoryCol.setCellValueFactory(
                    // display room category for unpaid accommodation in table column Category
                    data -> new ReadOnlyStringWrapper(data.getValue().getCurrentAccommodation().getReservedRoom().getCategory())
            );
            dateToCol.setCellValueFactory(
                    // convert dateTo into understandable date in String and display it in table column Date To
                    data -> new ReadOnlyStringWrapper(data.getValue().getCurrentAccommodation().getDateToAsString())
            );
            priceCol.setCellValueFactory(
                    // display price of unpaid accommodation in Price table column
                    data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getCurrentAccommodation().getPrice()))
            );

            // set customers with unpaid accommodations to tableview
            customersUnpaidAccommodations.setItems(customersWithUnpaidAcc);
        }
    }

    public void detailUnpaidAccommodation() {
        LOGGER.info("Switching to detail unpaid accommodation scene");
    }
}
