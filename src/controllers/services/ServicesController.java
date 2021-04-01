package controllers.services;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Service;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ServicesController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(HomepageController.class.getName());

    @FXML
    private TableView<Service> servicesTableView;
    @FXML
    private TableColumn<Service, String> serviceCol, descriptionCol;
    @FXML
    private TableColumn<Service, Double> priceCol;
    @FXML
    private TextField tfServiceName, tfPrice;
    @FXML
    private TextArea taDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.servicesTableView != null) {
            // map columns on Service attributes
            serviceCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            // display all services in table view
            servicesTableView.setItems(Serialization.getInstance().getAllServices());
        }
    }

    public void createService() {
        String serviceName = tfServiceName.getText();
        String serviceDescription = taDescription.getText();
        String priceString = tfPrice.getText();

        // check if user filled all text fields
        if (serviceName.equals("") || serviceDescription.equals("") || priceString.equals("")) {
            LOGGER.info("User didn't fill all TextFields for service creation.");
            // show error popup, because user didn't fill all text fields
            this.showErrorPopUp(
                    "Fill all text field",
                    "You have to fill all text fields to create service."
            );

            return;
        }

        try {
            // convert entered price to double
            double price = this.convertStringToDouble(priceString);
            // add new service to all service observable list
            LOGGER.info("Adding new service to all services lists.");
            Serialization.getInstance().addServiceAndSerialize(new Service(
                    serviceName,
                    serviceDescription,
                    price
            ));
            // refresh table view to display newly added service
            servicesTableView.refresh();
            // display success pop up, because service was created
            this.showSuccessPopUp(
                    "Success",
                    "Service was successfully created."
            );
        } catch (Exception e) {
            e.printStackTrace();
            // price wasn't entered as number
            // show error popup
            LOGGER.info("Price wasn't entered as number");
            this.showErrorPopUp(
                    "Wrong input",
                    "Price has to be number."
            );
        }
    }
}
