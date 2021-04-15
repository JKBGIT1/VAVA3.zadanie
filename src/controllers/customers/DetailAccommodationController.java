package controllers.customers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import models.Accommodation;
import models.Customer;
import models.Payment;
import models.UsedService;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailAccommodationController extends DetailCustomerController {

    @FXML
    private Label roomLabel, categoryLabel, dateFromLabel, dateToLabel, priceLabel, paidLabel, paymentDayLabel;
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
            // display used services for selected accommodation in TableView
            usedServicesTableView.setItems(FXCollections.observableArrayList(this.selectedAccommodation.getUsedServices()));
        }
        // display information about selected accommodation in labels
        roomLabel.setText(this.selectedAccommodation.getReservedRoom().getLabel());
        categoryLabel.setText(this.selectedAccommodation.getReservedRoom().getCategory());
        dateFromLabel.setText(this.selectedAccommodation.getDateFromAsString());
        dateToLabel.setText(this.selectedAccommodation.getDateToAsString());
        priceLabel.setText(String.valueOf(this.selectedAccommodation.getPrice()));
        // ternary operator for displaying if accommodation was paid.
        paidLabel.setText(this.getPaymentString(this.selectedAccommodation.getPayment()));
        // display payment date in paymentDayLabel if customer already paid for accommodation
        paymentDayLabel.setText(this.getPaymentDate(this.selectedAccommodation.getPayment()));
    }

    // this method returns proper string to give right information about payment
    public String getPaymentString(Payment payment) {
        if (payment == null) {
            return "No";
        } else if (payment.isInCash()) {
            return "In cash";
        } else {
            return "Not in cash";
        }
    }

    public String getPaymentDate(Payment payment) {
        if (payment == null) {
            return "None";
        } else {
            return this.convertDateToString(payment.getDate());
        }
    }

    // go back from accommodation detail scene to customer detail scene
    public void detailCustomerScene(MouseEvent event) {
        this.setScenePath(DETAIL_CUSTOMER_SCENE);
        this.setController(new DetailCustomerController(
                this.getSelectedCustomer()
        ));
        this.switchScene(event);
    }
}
