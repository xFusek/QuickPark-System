package pl.kul.quickparksystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.model.Reservation;
import pl.kul.quickparksystem.model.UserModel;
import pl.kul.quickparksystem.service.*;
import pl.kul.quickparksystem.utils.Dialogs;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public abstract class ParkingControllerBase {

    protected InvoiceGenerator invoiceGenerator;
    protected ReservationService reservationService;
    protected ParkingManager parkingManager;
    protected UserService userService;
    protected BankService bankService;
    @FXML
    private Button selectedSpot;

    public ParkingControllerBase() {
        invoiceGenerator = new InvoiceGenerator();
        reservationService = new ReservationService();
        parkingManager = new ParkingManager();
        userService = new UserService();
        bankService = new BankService();
    }

    protected void handleParkingSpotAction(Button selectedSpot) throws IOException {

        if (parkingManager.isSpotOccupied(selectedSpot.getId())) {
            Dialogs.showInformationDialog("Spot Unavailable", null, "This spot is already reserved.");
            return;
        }

        Optional<Integer> durationResult = Dialogs.showDurationChoiceDialog();
        if (durationResult.isEmpty()) {
            // User clicked "Cancel" or closed the dialog, so we exit the method
            return;
        }
        int duration = durationResult.get();

        Optional<Boolean> invoiceOption = Dialogs.showInvoiceChoiceDialog();
        if (invoiceOption.isEmpty()) {
            // User clicked "Cancel" or closed the dialog, so we exit the method
            return;
        }

        reserveSpot(selectedSpot, duration, invoiceOption.get());
    }

    protected void reserveSpot(Button selectedSpot, int duration, boolean wantInvoice) throws IOException {
        double price = CalculatePrice.calculatePrice(duration);
        UserModel currentUser = userService.getUserDetails(LoginController.getLoggedInUsername());
        if (currentUser != null) {
            double currentBalance = BankService.checkBalance(currentUser.getUsername());
            if (currentBalance < price) {
                Dialogs.showInformationDialog("Insufficient balance", null,
                        "You do not have the funds to pay this reservation.");
                return;
            }

            bankService.updateBalance(currentUser.getUsername(), -price);
            performReservation(selectedSpot, duration, wantInvoice, currentUser, price);
        } else {
            Dialogs.showErrorDialog("Error", null,
                    "Failed to retrieve your account information.");
        }
    }

    private void performReservation(Button selectedSpot, int duration, boolean wantInvoice, UserModel user, double price) throws IOException {
        String parkingSpot = selectedSpot.getText();
        String parkingPlace = parkingManager.getParkingNameFromSpotId(selectedSpot.getId());
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(duration);

        Reservation reservation = new Reservation();
        reservation.setParkingSpotId(selectedSpot.getId());
        reservation.setUser(user);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setAmount(price);
        reservationService.saveReservation(reservation);

        selectedSpot.getStyleClass().add("parking-spot-reserved");
        selectedSpot.setText(selectedSpot.getText() + " (R)");

        if (wantInvoice) {
            invoiceGenerator.generateInvoice(reservation.getCustomerName(), user.getUsername(), duration, price, parkingPlace, parkingSpot);
        } else {
            invoiceGenerator.generateReceipt(reservation.getCustomerName(), user.getUsername(), duration, price, parkingPlace, parkingSpot);
        }
    }



}
