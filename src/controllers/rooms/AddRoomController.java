package controllers.rooms;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import models.Room;
import models.RoomCategory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AddRoomController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(AddRoomController.class.getName());

    @FXML
    private ListView<String> imagesListView;
    @FXML
    private TextField tfRoomLabel;
    @FXML
    private TextArea taRoomNotes;

    private final RoomCategory selectedCategory;

    private final ObservableList<String> uploadedImagesText = FXCollections.observableArrayList();
    private final HashMap<String, byte[]> stringImageHashMap = new HashMap<>();

    public AddRoomController(RoomCategory roomCategory) {
        this.selectedCategory = roomCategory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.imagesListView != null) {
            this.imagesListView.setItems(this.uploadedImagesText);
            // Taken from https://stackoverflow.com/questions/33592308/javafx-how-to-put-imageview-inside-listview
            this.imagesListView.setCellFactory(param -> new ListCell<String>() {
                private final ImageView imageView = new ImageView();
                @Override
                public void updateItem(String name, boolean empty) {
                    // call parent updateItem
                    super.updateItem(name, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        // inspiration https://www.tutorialspoint.com/How-to-convert-Byte-Array-to-Image-in-java
                        // get byte array from hashmap with it's key
                        InputStream in = new ByteArrayInputStream(stringImageHashMap.get(name));
                        // create image
                        Image image = new Image(in, 250, 250, false, false);
                        // display image in list cell
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }
                }
            });
        }
    }

    public void removeImage() {
        // check if user selected image, which he/she wants to remove from room gallery
        if (this.imagesListView.getSelectionModel().getSelectedItems() == null) {
            this.showErrorPopUp(
                    "Select image",
                    "You have to select image, which you want to remove"
            );
        } else {
            // get observable list of all selected items in listview
            ObservableList<String> imageKey = this.imagesListView.getSelectionModel().getSelectedItems();
            // delete these images from hash map and their indexes from observable list
            for (String item : imageKey) {
                this.stringImageHashMap.remove(item);
                this.uploadedImagesText.remove(item);
            }
            LOGGER.info("User removed uploaded images.");
        }
    }

    public void uploadImage() {
        try {
            // inspiration for FileChooser from https://stackoverflow.com/questions/60184035/java-fx-image-upload-from-file
            FileChooser fileChooser = new FileChooser();

            // create extensions for FileChooser
            // user will be able to choose only these types of files from filesystem
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG", "*.PNG");
            FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg", "*.jpg");
            FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png", "*.png");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterjpg, extFilterpng);

            // show new dialog and store selected image into File object
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                // create byte array from file
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                // get list index of this byte array and store it under this index into hashmap
                String index = String.valueOf(this.uploadedImagesText.size());
                // store byte array of image under String index in hashmap
                this.stringImageHashMap.put(index, fileBytes);
                // add image index to uploadedImagesText observable list
                // because program will be displaying images based on indexes in this list
                this.uploadedImagesText.add(index);
                LOGGER.info("Image was uploaded.");
                return;
            }

            LOGGER.info("User didn't select an image for upload.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Something went wrong during file uploading. Here is message " + e.getMessage());
        }
    }

    public void createRoom(MouseEvent event) {
        String roomLabel = tfRoomLabel.getText();
        // check if user filled room label
        if (roomLabel.equals("")) {
            LOGGER.info("User didn't enter room label.");
            this.showErrorPopUp("Enter Label", "Each room has to have label.");
        } else {
            // check if room label doesn't exists in this category
            for (Room room: this.selectedCategory.getRoomsInCategory()) {
                if (roomLabel.equals(room.getLabel())) {
                    LOGGER.info("User entered room label, which already exists.");
                    this.showErrorPopUp(
                            "Already exists",
                            "The room label, which you have entered already exists."
                    );
                    return;
                }
            }

            ArrayList<byte[]> roomGallery = new ArrayList<>();
            // create arraylist of byte arrays, which will be converted in images
            // these byte arrays works as a room gallery
            for (String index : this.uploadedImagesText) {
                roomGallery.add(this.stringImageHashMap.get(index));
            }
            // add newly create room in category
            selectedCategory.getRoomsInCategory().add(new Room(
                    roomLabel,
                    taRoomNotes.getText(),
                    this.selectedCategory.getCategoryName(),
                    this.selectedCategory.getPrice(),
                    roomGallery
            ));
            // serialize newly created room
            Serialization.getInstance().serializeData();
            // show successful popup
            this.showSuccessPopUp("Success", "Room was successfully created.");
            // switch back to room scene
            this.roomsScene(event);
        }
    }
}
