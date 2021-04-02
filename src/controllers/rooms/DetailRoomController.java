package controllers.rooms;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Accommodation;
import models.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailRoomController extends RoomsController {

    @FXML
    private ListView<Image> imagesListView;
    @FXML
    private TableView<Accommodation> allAccommodationsTableView;
    @FXML
    private TableColumn<Accommodation, String> dateFromCol, dateToCol;
    @FXML
    private TableColumn<Accommodation, Double> priceCol;
    @FXML
    private Label roomLabel, categoryLabel;

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

        if (allAccommodationsTableView != null) {
            // inspiration https://stackoverflow.com/questions/25204068/how-do-i-point-a-propertyvaluefactory-to-a-value-of-a-map
            dateFromCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getDateFromAsString())
            );
            dateToCol.setCellValueFactory(
                    data -> new ReadOnlyStringWrapper(data.getValue().getDateToAsString())
            );
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            allAccommodationsTableView.setItems(FXCollections.observableArrayList(this.selectedRoom.getHistoryAccommodations()));
        }

        roomLabel.setText(this.selectedRoom.getLabel());
        categoryLabel.setText(this.selectedRoom.getCategory());
    }
}
