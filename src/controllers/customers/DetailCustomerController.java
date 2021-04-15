package controllers.customers;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.*;
import system_time.SystemTime;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class DetailCustomerController extends HomepageController {

    // table view and columns for all services
    @FXML
    private TableView<Service> servicesTableView;
    @FXML
    private TableColumn<Service, String> serviceDescriptionCol;
    @FXML
    private TableColumn<Service, Double> servicePriceCol;
    // table view and columns for all customer's accommodations
    @FXML
    private TableView<Accommodation> accommodationsTableView;
    @FXML
    private TableColumn<Accommodation, String> roomLabelCol, categoryCol, dateToCol, dateFromCol;
    @FXML
    private TableColumn<Accommodation, Double> priceCol;

    private final Customer selectedCustomer;

    public DetailCustomerController(Customer customer) {
        this.selectedCustomer = customer;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (servicesTableView != null) {
            // map Service attributes to columns
            serviceDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            // display all services in tableview
            servicesTableView.setItems(Serialization.getInstance().getAllServices());
        }

        if (accommodationsTableView != null) {
            // map table columns to accommodation methods
            // inspiration https://stackoverflow.com/questions/25204068/how-do-i-point-a-propertyvaluefactory-to-a-value-of-a-map
            roomLabelCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getReservedRoom().getLabel())
            );
            categoryCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getReservedRoom().getCategory())
            );
            dateToCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getDateToAsString())
            );
            dateFromCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getDateFromAsString())
            );
            // map Accommodation attribute to table column
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            // display all customer's accommodations in table view
            accommodationsTableView.setItems(FXCollections.observableArrayList(this.selectedCustomer.getAccommodations()));
        }
    }

    public void accommodationDetail(MouseEvent event) {
        // if user didn't select accommodation, which he/she wants to display in detail, show error popup
        if (accommodationsTableView.getSelectionModel().getSelectedItem() == null) {
            this.showErrorPopUp(
                    "Select accommodation",
                    "Select accommodation, which you want to display in detail."
            );
        } else {
            // get selected accommodation from TableView
            Accommodation accommodation = accommodationsTableView.getSelectionModel().getSelectedItem();
            // go to scene, where data about selected accommodation will be displayed in detail
            this.setScenePath(DETAIL_ACCOMMODATION_SCENE);
            this.setController(new DetailAccommodationController(
                    this.selectedCustomer,
                    accommodation
            ));
            this.switchScene(event);
        }
    }

    public void addService() {
        // check if user selected service from table
        if (servicesTableView.getSelectionModel().getSelectedItem() == null) {
            this.showErrorPopUp(
                    "Select service",
                    "You have to choose service, which customer wants to use."
            );
        } else {
            // check if customer is currently accommodated
            if (this.selectedCustomer.getCurrentAccommodation() == null) {
                this.showErrorPopUp(
                        "Customer isn't accommodated",
                        "Customer can't use services, because he/she isn't currently accommodated."
                );
            } else {
                // remove customer from all customers list
                Serialization.getInstance().getAllCustomers().remove(this.selectedCustomer);
                // get selected service
                Service selectedService = servicesTableView.getSelectionModel().getSelectedItem();
                // get customer's current accommodation
                Accommodation accommodation = this.selectedCustomer.getCurrentAccommodation();
                // increase total price for accommodation
                accommodation.setPrice(accommodation.getPrice() + selectedService.getPrice());
                // add used service to accommodation
                accommodation.getUsedServices().add(new UsedService(
                        selectedService,
                        new Date(SystemTime.getInstance().getCurrentProgramTime())) // get current time in program
                );
                // add customer to all customers list with new UsedService
                Serialization.getInstance().addCustomerAndSerialize(this.selectedCustomer);
                // refresh table view to display data
                accommodationsTableView.refresh();
                // show success popup
                this.showSuccessPopUp(
                        "Success",
                        "Service was successfully added to customer's current accommodation."
                );
            }
        }
    }

    public void makePayment() {
        // check if user select accommodation for payment
        if (accommodationsTableView.getSelectionModel().getSelectedItem() == null) {
            this.showErrorPopUp(
                    "Select accommodation",
                    "You have to select accommodation for payment."
            );
            return;
        }
        // get selected accommodation from TableView for which customer will pay
        Accommodation accommodation = accommodationsTableView.getSelectionModel().getSelectedItem();
        // get info about payment in cash or other method
        boolean paymentInCast = this.showConfirmationPopUp(
                "Payment type",
                "Payment in cash?"
        );
        // set payment to accommodation
        accommodation.setPayment(new Payment(
                new Date(SystemTime.getInstance().getCurrentProgramTime()),
                paymentInCast)
        );
        this.showSuccessPopUp(
                "Success",
                "Payment was successful."
        );
        // serialize data about payment
        Serialization.getInstance().serializeData();
    }
}
