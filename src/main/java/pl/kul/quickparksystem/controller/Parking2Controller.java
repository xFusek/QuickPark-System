package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.utils.SceneChanger;

import java.io.IOException;
import java.util.Arrays;

public class Parking2Controller extends ParkingControllerBase {
    @FXML private Button spot1B, spot2B, spot3B, spot4B, spot5B, spot6B, spot7B, spot8B, spot9B, spot10B, spot11B, spot12B, spot13B, spot14B, spot15B, spot16B, spot17B, spot18B, spot19B, spot20B;

    @FXML
    private Button backButton;

    public Parking2Controller(){
        super();
    }

    @FXML
    private void initialize() throws IOException {
        // Aktualizacja stanu miejsc parkingowych
        updateParkingSpotsAvailability();
    }

    private void updateParkingSpotsAvailability() throws IOException {
        for (Button spot : Arrays.asList(spot1B, spot2B, spot3B, spot4B, spot5B, spot6B, spot7B, spot8B, spot9B, spot10B, spot11B, spot12B, spot13B, spot14B, spot15B, spot16B, spot17B, spot18B, spot19B, spot20B)) {
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
