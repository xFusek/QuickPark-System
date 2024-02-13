package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.utils.SceneChanger;

import java.io.IOException;
import java.util.Arrays;

public class Parking3Controller extends ParkingControllerBase {
    @FXML private Button spot1C, spot2C, spot3C, spot4C, spot5C, spot6C, spot7C, spot8C, spot9C, spot10C, spot11C, spot12C, spot13C, spot14C, spot15C, spot16C, spot17C, spot18C, spot19C, spot20C, spot21C, spot22C, spot23C, spot24C, spot25C, spot26C, spot27C, spot28C, spot29C, spot30C, spot31C, spot32C, spot33C, spot34C, spot35C, spot36C, spot37C, spot38C;

    @FXML
    private Button backButton;

    public Parking3Controller() {
        super();
    }

    @FXML
    private void initialize() throws IOException {
        // Aktualizacja stanu miejsc parkingowych
        updateParkingSpotsAvailability();
    }

    private void updateParkingSpotsAvailability() throws IOException {
        for (Button spot : Arrays.asList(spot1C, spot2C, spot3C, spot4C, spot5C, spot6C, spot7C, spot8C, spot9C, spot10C, spot11C, spot12C, spot13C, spot14C, spot15C, spot16C, spot17C, spot18C, spot19C, spot20C, spot21C, spot22C, spot23C, spot24C, spot25C, spot26C, spot27C, spot28C, spot29C, spot30C, spot31C, spot32C, spot33C, spot34C, spot35C, spot36C, spot37C, spot38C)) {
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
