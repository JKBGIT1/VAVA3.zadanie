package controllers.rooms;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.RoomCategory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCategoryController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(AddCategoryController.class.getName());

    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfCategoryName, tfPrice;

    public void createRoomCategory(MouseEvent event) {
        LOGGER.info("Start of new room category creation.");

        String categoryName = tfCategoryName.getText();
        String priceString = tfPrice.getText();
        String description = taDescription.getText();

        if (categoryName.equals("") || priceString.equals("") || description.equals("")) {
            LOGGER.log(Level.WARNING, "User didn't fill all TextFields for category creation");
            this.showErrorPopUp("Missing data", "All text fields need to be filled.");
        } else {
            try {
                double price = this.convertStringToDouble(priceString);
                LOGGER.info("Creating room category based on entered data.");
                // add new room category to all categories observable list
                Serialization.getInstance().addRoomCategoryAndSerialize(new RoomCategory(
                        categoryName,
                        description,
                        price
                ));
                // show success popup
                this.showSuccessPopUp("Success", "Room category was successfully created.");

                LOGGER.info("After successful room category creation go back to Room scene.");
                // after successful creation switch to Room scene
                this.roomsScene(event);
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
                this.showErrorPopUp("Wrong input", "Price has to be number");
            }
        }
    }
}
