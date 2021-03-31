package controllers.rooms;

import controllers.HomepageController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AddRoomController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(AddRoomController.class.getName());

    @FXML
    private TableView<Image> imagesTableView;
    @FXML
    private TableColumn<Image, Image> imageCol;

    private ObservableList<Image> uploadedImages;

    // TODO: Find out how to upload display multiple images
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set observable list for ListView
        if (this.imagesTableView != null) {
            // Taken from https://stackoverflow.com/questions/22005115/how-to-add-an-image-into-a-javafx-tableview-column
            imageCol.setCellFactory(param -> {
                // My explanation to code
                // Firstly program creates ImageView object,
                // which will be in tableColumn and sets it's height and width
                ImageView imageview = new ImageView();
                imageview.setFitHeight(200);
                imageview.setFitWidth(200);

                // Now program creates TableCell object for Employer class with Image object content
                TableCell<Image, Image> cell = new TableCell<Image, Image>() {
                    // In this function it will set image to previously created imageview
                    public void updateItem(Image item, boolean empty) {
                        if (item != null) {
                            imageview.setImage(item);
                        }
                    }
                };
                // this will attach the image to the cell
                cell.setGraphic(imageview);
                return cell;
            });
            // set logo attribute as CellValueFactory
            imageCol.setCellValueFactory(new PropertyValueFactory<>(""));

            // Fill tableView with Employers data.
            imagesTableView.setItems(this.uploadedImages);
        }
    }

    public void removeImage() {

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
            // create new image
            Image image = new Image(file.toURI().toString(), 200, 200, false, false);
            // add new image
            this.uploadedImages.add(image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createRoom(MouseEvent event) {
        LOGGER.info("Start of the room creation.");
        this.roomsScene(event);
    }
}
