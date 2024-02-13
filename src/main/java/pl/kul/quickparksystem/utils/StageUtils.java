package pl.kul.quickparksystem.utils;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class StageUtils {

    public static void centerStage(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = screenBounds.getMinX() + (screenBounds.getWidth() - stage.getWidth()) / 2;
        double y = screenBounds.getMinY() + (screenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(x);
        stage.setY(y);
    }
}
