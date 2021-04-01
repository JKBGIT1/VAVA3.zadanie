import design_patterns.Serialization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.HomepageController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // deserialize data from booking_data.ser before stage shows
        Serialization.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxmls/HomepageScene.fxml"));

        HomepageController controller = new HomepageController();
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/template.css").toExternalForm());

        primaryStage.setTitle("Booking system");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
