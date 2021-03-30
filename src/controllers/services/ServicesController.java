package controllers.services;

import controllers.HomepageController;
import javafx.collections.ObservableList;
import models.Customer;

public class ServicesController extends HomepageController {

    public ServicesController(ObservableList<Customer> allCustomers) {
        super(allCustomers);
    }
}
