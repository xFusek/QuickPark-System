package pl.kul.quickparksystem.utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneChanger {
    private static final Logger LOGGER = Logger.getLogger(SceneChanger.class.getName());

    public static void changeScene(ActionEvent event, String fxmlFile, boolean isParkingScene) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlFile));

            // Ustaw rozmiar sceny w zależności od tego, czy jest to scena parkingowa
            Scene scene = isParkingScene
                    ? new Scene(root, 700, 800) // Dla scen parkingowych
                    : new Scene(root, 500, 400); // Dla pozostałych scen

            stage.setScene(scene);
            stage.show();

            // Wyśrodkowanie sceny
            Platform.runLater(() -> StageUtils.centerStage(stage));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error changing scene", e);
        }
    }
}
