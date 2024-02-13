package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.kul.quickparksystem.service.ParkingManager;
import pl.kul.quickparksystem.utils.Dialogs;
import pl.kul.quickparksystem.utils.SceneChanger;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ReservationDetailsController {

    @FXML
    private Button backButton;
    @FXML
    private VBox reservationsContainer; // Kontener na przyciski rezerwacji
    @FXML
    private Button prevPageButton, nextPageButton;
    @FXML
    private Label currentPageLabel;

    private static final int RESERVATIONS_PER_PAGE = 6;
    private final List<Button> allReservationButtons = new ArrayList<>();
    private int currentPage = 1;


    // Metoda initialize jest wywoływana automatycznie po załadowaniu kontrolera
    @FXML
    public void initialize() throws IOException {
        String currentUsername = LoginController.getLoggedInUsername();
        loadReservationsForUser(currentUsername);
        updatePage();
    }


    private void loadReservationsForUser(String username) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("database/history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(";");
                if (details[0].equals(username)) {
                    // Tworzenie przycisku dla każdej rezerwacji
                    Button reservationButton = getReservationButton(details);
                    allReservationButtons.add(reservationButton);
                }
                updatePage();
            }
        }
    }

    private Button getReservationButton(String[] details) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(details[2], formatter);
        LocalDateTime endTime = LocalDateTime.parse(details[3], formatter);
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);
        double price = Double.parseDouble(details[4]);

        String spotNumber = details[1].replaceAll("spot", "").replaceAll(".$", "");
        ParkingManager parkingManager = new ParkingManager();
        String parkingName = parkingManager.getParkingNameFromSpotId(details[1]);

        String buttonLabel = parkingName + " | " + formattedStartTime + " - " + formattedEndTime;
        Button reservationButton = new Button(buttonLabel);
        reservationButton.setOnAction(event -> Dialogs.showInformationDialog("Reservation Details",
                "Reservation for spot: " + spotNumber + " at " + parkingName,
                "From: " + formattedStartTime + "\nTo: " + formattedEndTime + "\nPrice: " + String.format("%.2f PLN", price)));
        return reservationButton;
    }


    @FXML
    private void handleBackAction(ActionEvent event) {
        SceneChanger.changeScene(event,"/pl/kul/quickparksystem/view/dashboard.fxml",false);
    }


    private void updatePage() {
        reservationsContainer.getChildren().clear();
        int start = (currentPage - 1) * RESERVATIONS_PER_PAGE;
        int end = Math.min(start + RESERVATIONS_PER_PAGE, allReservationButtons.size());
        reservationsContainer.getChildren().addAll(allReservationButtons.subList(start, end));

        currentPageLabel.setText(Integer.toString(currentPage));
        prevPageButton.setDisable(currentPage == 1);
        nextPageButton.setDisable((currentPage * RESERVATIONS_PER_PAGE) >= allReservationButtons.size());
    }

    @FXML
    private void handlePrevPage(ActionEvent event) {
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    }

    @FXML
    private void handleNextPage(ActionEvent event) {
        if ((currentPage - 1) * RESERVATIONS_PER_PAGE < allReservationButtons.size()) {
            currentPage++;
            updatePage();
        }
    }



}



