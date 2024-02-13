package pl.kul.quickparksystem.utils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Dialogs {

    public static void showErrorDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInformationDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Optional<Integer> showDurationChoiceDialog() {
        List<Integer> choices = Arrays.asList(1, 4, 6, 12);
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, choices);
        dialog.setTitle("Choose Reservation Duration");
        dialog.setHeaderText("Select the duration of the reservation:");
        dialog.setContentText("Duration in hours:");
        return dialog.showAndWait();
    }

    public static Optional<Boolean> showInvoiceChoiceDialog() {
        Alert invoiceAlert = new Alert(Alert.AlertType.CONFIRMATION);
        invoiceAlert.setTitle("Document Choice");
        invoiceAlert.setHeaderText("Do you want to receive an invoice?");
        invoiceAlert.setContentText("Choose your option:");

        ButtonType buttonReceipt = new ButtonType("Receipt");
        ButtonType buttonInvoice = new ButtonType("Invoice");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        invoiceAlert.getButtonTypes().setAll(buttonReceipt, buttonInvoice, buttonCancel);

        Optional<ButtonType> result = invoiceAlert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonInvoice) {
                return Optional.of(true);
            } else if (result.get() == buttonReceipt) {
                return Optional.of(false);
            }
        }
        return Optional.empty();
    }

}
