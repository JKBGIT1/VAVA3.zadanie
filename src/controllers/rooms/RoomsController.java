package controllers.rooms;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.RoomCategory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RoomsController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(RoomsController.class.getName());

    @FXML
    private TableView<RoomCategory> roomCategoryTableView;
    @FXML
    private TableColumn<RoomCategory, String> categoryCol, descriptionCol, roomsNumberCol;
    @FXML
    private TableColumn<RoomCategory, Double>  priceCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.roomCategoryTableView != null) {
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            roomsNumberCol.setCellValueFactory(
                    // display number of rooms in category
                    data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getRoomsInCategory().size()))
            );

            // display all room categories into roomCategoryTableView
            this.roomCategoryTableView.setItems(Serialization.getInstance().getAllCategories());
        }
    }

    public void displayRoomsScene(MouseEvent event) {
        if (this.roomCategoryTableView.getSelectionModel().getSelectedItem() == null) {
            LOGGER.info("User didn't select room category.");
            this.showErrorPopUp(
                    "Select category",
                    "You need to select category to display it's rooms"
            );
        } else {
            RoomCategory roomCategory = this.roomCategoryTableView.getSelectionModel().getSelectedItem();
            LOGGER.info("Switching to DisplayRooms scene.");

            this.setScenePath(DISPLAY_ROOMS_SCENE);
            this.setController(new DisplayRoomsController(roomCategory));
            this.switchScene(event);
        }
    }

    public void addRoomScene(MouseEvent event) {
        if (this.roomCategoryTableView.getSelectionModel().getSelectedItem() == null) {
            LOGGER.info("User didn't select room category.");
            this.showErrorPopUp(
                    "Select category",
                    "You have to select category, where you want to add room."
            );
        } else {
            RoomCategory roomCategory = this.roomCategoryTableView.getSelectionModel().getSelectedItem();

            LOGGER.info("Switching to AddRoom scene.");
            this.setScenePath(ADD_ROOM_SCENE);
            this.setController(new AddRoomController(roomCategory));
            this.switchScene(event);
        }
    }

    public void addCategoryScene(MouseEvent event) {
        LOGGER.info("Switching to AddCategory scene.");
        this.setScenePath(ADD_CATEGORY_SCENE);
        this.setController(new AddCategoryController());
        this.switchScene(event);
    }
}
