package controllers.rooms;

import controllers.HomepageController;
import design_patterns.Serialization;
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
    private TableColumn<RoomCategory, String> categoryCol, descriptionCol;
    @FXML
    private TableColumn<RoomCategory, Double>  priceCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.roomCategoryTableView != null) {
            LOGGER.info("Mapping RoomCategory attributes to roomCategoryTableView columns.");

            categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            // display all room categories into roomCategoryTableView
            this.roomCategoryTableView.setItems(Serialization.getInstance().getAllCategories());
        }
    }

    public void displayRoomsScene() {
        LOGGER.info("Switching to DisplayRooms scene.");
    }

    public void addRoomScene() {
        LOGGER.info("Switching to AddRoom scene.");
    }

    public void addCategoryScene(MouseEvent event) {
        LOGGER.info("Switching to AddCategory scene.");
        this.setScenePath(ADD_CATEGORY_SCENE);
        this.setController(new AddCategoryController());
        this.switchScene(event);
    }
}
