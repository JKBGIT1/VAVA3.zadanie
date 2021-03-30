package controllers.customers;

import controllers.HomepageController;
import javafx.collections.ObservableList;
import models.Customer;

public class AddCustomerController extends HomepageController {
    public AddCustomerController(ObservableList<Customer> allCustomers) {
        super(allCustomers);
    }
}
