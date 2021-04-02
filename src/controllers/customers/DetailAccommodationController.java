package controllers.customers;

import controllers.HomepageController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Accommodation;
import models.Customer;
import models.UsedService;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailAccommodationController extends DetailCustomerController {

    @FXML
    private Label roomLabel, categoryLabel, dateFromLabel, dateToLabel, priceLabel, paidLabel;
    @FXML
    private TableView<UsedService> usedServicesTableView;
    @FXML
    private TableColumn<UsedService, String> dateCol, serviceDescriptionCol, servicePriceCol;

    private final Accommodation selectedAccommodation;

    public DetailAccommodationController(Customer customer, Accommodation accommodation) {
        super(customer);
        this.selectedAccommodation = accommodation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (usedServicesTableView != null) {
            // inspiration https://stackoverflow.com/questions/25204068/how-do-i-point-a-propertyvaluefactory-to-a-value-of-a-map
            dateCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getDateAsString())
            );
            serviceDescriptionCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getService().getDescription())
            );
            servicePriceCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getService().getPrice()))
            );

            usedServicesTableView.setItems(FXCollections.observableArrayList(this.selectedAccommodation.getUsedServices()));
        }
        // display information about selected accommodation in labels
        roomLabel.setText(this.selectedAccommodation.getReservedRoom().getLabel());
        categoryLabel.setText(this.selectedAccommodation.getReservedRoom().getCategory());
        dateFromLabel.setText(this.selectedAccommodation.getDateFromAsString());
        dateToLabel.setText(this.selectedAccommodation.getDateToAsString());
        priceLabel.setText(String.valueOf(this.selectedAccommodation.getPrice()));
        // ternary operator for displaying if accommodation was paid.
        paidLabel.setText(this.selectedAccommodation.getPayment() == null ? "No" : "Yes");
    }
}
