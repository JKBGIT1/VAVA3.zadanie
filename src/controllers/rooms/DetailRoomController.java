package controllers.rooms;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Accommodation;
import models.Room;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private TextArea taRoomNotes;

    private final Room selectedRoom;

    public DetailRoomController(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.imagesListView != null) {
            ObservableList<Image> allRoomImages = FXCollections.observableArrayList();

            for (int i = 0; i < this.selectedRoom.getGallery().size(); i++) {
                // inspiration https://www.tutorialspoint.com/How-to-convert-Byte-Array-to-Image-in-java
                // create input stream from byte array
                InputStream in = new ByteArrayInputStream(this.selectedRoom.getGallery().get(i));
                // create image from input stream
                Image image = new Image(in, 250, 250, false, false);
                allRoomImages.add(image);
            }

            this.imagesListView.setItems(allRoomImages);
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

        if (taRoomNotes != null) {
            taRoomNotes.setText(this.selectedRoom.getNote());
        }

        roomLabel.setText(this.selectedRoom.getLabel());
        categoryLabel.setText(this.selectedRoom.getCategory());
    }

    public void editRoomNotes() {
        this.selectedRoom.setNote(taRoomNotes.getText());
        this.showSuccessPopUp("Success", "Room note was successfully changed.");
    }
}
