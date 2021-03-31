package controllers.customers;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Customer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCustomerController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(AddCustomerController.class.getName());

    @FXML
    private TextField tfFirstName, tfLastName, tfIdentificationNumber;

    public void createCustomer(MouseEvent event) {
        LOGGER.info("Start of customer creation");
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String identificationNumber = tfIdentificationNumber.getText();

        if (firstName.equals("") || lastName.equals("") || identificationNumber.equals("")) {
            LOGGER.log(Level.WARNING, "User doesn't fill all TextFields for customer creation.");
            this.showErrorPopUp("Missing data", "All text fields need to be filled.");
        } else {
            LOGGER.info("Creating customer based on data in TextFields");
            // Create new customer and add him/her to observable list of all customers in serialization class
            Serialization.getInstance().addCustomerAndSerialize(new Customer(
                    firstName,
                    lastName,
                    identificationNumber
            ));

            // successful creation popup
            this.showSuccessPopUp("Success", "Customer was successfully created");

            // switching back to customers scene
            LOGGER.info("After customer creation switching back to customers scene.");
            this.customersScene(event);
        }
    }
}
