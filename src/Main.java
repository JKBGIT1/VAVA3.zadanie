import design_patterns.Serialization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.HomepageController;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // deserialize data from booking_data.ser before stage shows
        try {
            Serialization.getInstance();
            // internationalization inspiration: https://stackoverflow.com/questions/45088103/fxml-load-exception-no-resources-specified
            Locale locale = new Locale("en", "UK");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.english_en_UK", locale);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxmls/HomepageScene.fxml"));
            // fxmlLoader loads object of HomepageController
            HomepageController controller = new HomepageController();
            fxmlLoader.setController(controller);
            // fxmlLoader loads internationalization resources
            fxmlLoader.setResources(resourceBundle);

            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/template.css").toExternalForm());

            primaryStage.setTitle("Booking system");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
