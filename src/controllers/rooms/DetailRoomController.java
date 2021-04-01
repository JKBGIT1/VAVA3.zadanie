package controllers.rooms;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailRoomController extends RoomsController {

    @FXML
    private ListView<Image> imagesListView;

    private final Room selectedRoom;

    public DetailRoomController(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.imagesListView != null) {
            this.imagesListView.setItems(FXCollections.observableArrayList(this.selectedRoom.getGallery()));
            this.imagesListView.setCellFactory(param -> new ListCell<Image>() {
                private final ImageView imageView = new ImageView();
                @Override
                public void updateItem(Image image, boolean empty) {
                    // call parent updateItem
                    super.updateItem(image, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }
                }
            });
        }
    }
}
