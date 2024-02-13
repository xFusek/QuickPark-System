package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.service.BankService;
import pl.kul.quickparksystem.utils.Dialogs;
import pl.kul.quickparksystem.utils.SceneChanger;

import java.io.IOException;

public class DashboardController {


    @FXML
    private Button checkReservationButton;
    @FXML
    private Button reserveSpotButton;
    @FXML
    private Button checkBalanceButton;


    @FXML
    private void handleCheckBalance(ActionEvent event) {
        try {
            double balance = BankService.checkBalance(LoginController.getLoggedInUsername());
            Dialogs.showInformationDialog("Balance Information", null, "Your current balance is: " + balance + " PLN");
        } catch (IOException e) {
            Dialogs.showErrorDialog("Balance Error", "Could not fetch balance", e.getMessage());
        }
    }

    @FXML
    private void handleCheckReservation(ActionEvent event) {
        SceneChanger.changeScene(event, "/pl/kul/quickparksystem/view/reservationDetails.fxml", false);
    }

    @FXML
    private void handleReserveSpot(ActionEvent event) {
        SceneChanger.changeScene(event, "/pl/kul/quickparksystem/view/parkingSelection.fxml", false);
    }
}
