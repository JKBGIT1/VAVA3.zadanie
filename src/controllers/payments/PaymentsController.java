package controllers.payments;

import controllers.HomepageController;
import javafx.collections.ObservableList;
import models.Customer;

public class PaymentsController extends HomepageController {
    public PaymentsController(ObservableList<Customer> allCustomers) {
        super(allCustomers);
    }
}
