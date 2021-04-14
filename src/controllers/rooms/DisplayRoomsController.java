package controllers.rooms;

import controllers.HomepageController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Room;
import models.RoomCategory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DisplayRoomsController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(DisplayRoomsController.class.getName());

    @FXML
    private TableView<Room> roomsTableView;
    @FXML
    private Label tableViewLabel;
    @FXML
    private TableColumn<Room, String> roomLabelCol, noteCol;
    @FXML
    private TableColumn<Room, String> takenFromCol, takenToCol;
    @FXML
    private TableColumn<Room, Boolean> freeCol;
    @FXML
    private TableColumn<Room, Double> priceCol;

    private final RoomCategory selectedCategory;

    public DisplayRoomsController(RoomCategory roomCategory) {
        this.selectedCategory = roomCategory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // if label over tableview isn't null, display text
        if (this.tableViewLabel != null) {
            this.tableViewLabel.setText("All rooms in " + this.selectedCategory.getCategoryName() + " category: ");
        }

        // if TableView for rooms isn't null, map columns on Room attributes and display items
        if (this.roomsTableView != null) {
            this.roomLabelCol.setCellValueFactory(new PropertyValueFactory<>("label"));
            this.noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
            this.takenFromCol.setCellValueFactory(new PropertyValueFactory<>("takenFrom"));
            this.takenToCol.setCellValueFactory(new PropertyValueFactory<>("takenTo"));
            this.freeCol.setCellValueFactory(new PropertyValueFactory<>("isFree"));
            this.priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            LOGGER.info("Displaying rooms in roomsTableView.");
            this.roomsTableView.setItems(FXCollections.observableArrayList(this.selectedCategory.getRoomsInCategory()));
        }
    }

    public void detailRoomScene(MouseEvent event) {
        if (this.roomsTableView.getSelectionModel().getSelectedItem() == null) {
            LOGGER.info("User didn't select room for detail info.");
            this.showErrorPopUp(
                    "Select room",
                    "You have to select room, which you want to display in detail"
            );
        } else {
            Room selectedRoom = this.roomsTableView.getSelectionModel().getSelectedItem();

            LOGGER.info("Switching to RoomDetail scene.");
            this.setScenePath(DETAIL_ROOM_SCENE);
            this.setController(new DetailRoomController(selectedRoom));
            this.switchScene(event);
        }
    }
}
