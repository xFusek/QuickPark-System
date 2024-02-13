package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.utils.SceneChanger;

public class ParkingSelectionController {

    @FXML
    private Button myAccount, parking1Button, parking2Button, parking3Button;

    @FXML
    private void selectParking(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String fxmlFile = switch (clickedButton.getId()) {
            case "parking1Button" -> "/pl/kul/quickparksystem/view/parking1.fxml";
            case "parking2Button" -> "/pl/kul/quickparksystem/view/parking2.fxml";
            case "parking3Button" -> "/pl/kul/quickparksystem/view/parking3.fxml";
            default -> throw new IllegalStateException("Unexpected value: " + clickedButton.getId());
        };

        SceneChanger.changeScene(event, fxmlFile, true); // true oznacza, że jest to scena parkingowa
    }

    @FXML
    private void myAccount(ActionEvent event) {
        SceneChanger.changeScene(event, "/pl/kul/quickparksystem/view/dashboard.fxml", false);
    }

}
