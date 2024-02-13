package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.utils.SceneChanger;

import java.io.IOException;
import java.util.Arrays;

public class Parking1Controller extends ParkingControllerBase {

    @FXML private Button spot1A, spot2A, spot3A, spot4A, spot5A, spot6A, spot7A, spot8A, spot9A, spot10A;

    @FXML
    private Button backButton;

    public Parking1Controller() {
        super();
    }

    @FXML
    private void initialize() throws IOException {
        // Aktualizacja stanu miejsc parkingowych
        updateParkingSpotsAvailability();
    }

    private void updateParkingSpotsAvailability() throws IOException {
        for (Button spot : Arrays.asList(spot1A, spot2A, spot3A, spot4A, spot5A, spot6A, spot7A, spot8A, spot9A, spot10A)) {
            if (parkingManager.isSpotOccupied(spot.getId())) {
                if (!spot.getStyleClass().contains("parking-spot-reserved")) {
                    spot.getStyleClass().add("parking-spot-reserved"); // Dodaj klasę dla zarezerwowanych miejsc
                }
                spot.setText(spot.getText() + " (R)");
            }
        }
    }


    @FXML
    private void handleParkingSpotAction(javafx.event.ActionEvent event) throws IOException {
        Button selectedSpot = (Button) event.getSource();
        super.handleParkingSpotAction(selectedSpot);
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        SceneChanger.changeScene(event,"/pl/kul/quickparksystem/view/parkingSelection.fxml",false);
}
}