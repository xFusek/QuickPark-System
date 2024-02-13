package pl.kul.quickparksystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.kul.quickparksystem.utils.StageUtils;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/pl/kul/quickparksystem/view/startscreen.fxml"));
        primaryStage.setTitle("QuickPark-System");

        // Ładowanie ikony
        Image icon = new Image(getClass().getResourceAsStream("/images/app_icon.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
        StageUtils.centerStage(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
