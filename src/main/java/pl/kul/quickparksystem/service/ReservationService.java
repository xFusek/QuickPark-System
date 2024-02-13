package pl.kul.quickparksystem.service;

import pl.kul.quickparksystem.model.Reservation;
import pl.kul.quickparksystem.model.UserModel;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private static final String RESERVATIONS_FILE = "database/reservations.txt";
    private static final String HISTORY_FILE = "database/history.txt";
    private final List<Reservation> reservations = new ArrayList<>();

    public void saveReservation(Reservation reservation) throws IOException {
        reservations.add(reservation);
        appendReservationToFile(reservation, RESERVATIONS_FILE);
        appendReservationToFile(reservation, HISTORY_FILE);
    }

    // Nowa metoda do aktualizacji rezerwacji
    public void updateReservations() throws IOException {
        List<Reservation> allReservations = loadAllReservations();
        List<Reservation> completedReservations = allReservations.stream()
                .filter(reservation -> reservation.getEndTime().isBefore(LocalDateTime.now()))
                .toList();

        // Usuwanie zakończonych rezerwacji z reservations.txt
        allReservations.removeAll(completedReservations);
        overwriteReservationsFile(allReservations);
    }

    private void overwriteReservationsFile(List<Reservation> reservations) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESERVATIONS_FILE, false))) {
            for (Reservation reservation : reservations) {
                appendReservationToFile(reservation, RESERVATIONS_FILE);
            }
        }
    }

    private void appendReservationToFile(Reservation reservation, String fileName) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String startTimeStr = reservation.getStartTime().format(formatter);
            String endTimeStr = reservation.getEndTime().format(formatter);
            writer.write(reservation.getUsername() + ";" + reservation.getParkingSpotId() + ";" + startTimeStr + ";" + endTimeStr + ";" + reservation.getAmount());
            writer.newLine();
        }
    }

    private List<Reservation> loadAllReservations() throws IOException {
        List<Reservation> reservations = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 5) {
                    continue;
                }
                Reservation reservation = new Reservation();
                UserModel user = new UserModel();
                user.setUsername(parts[0]);
                reservation.setUser(user);
                reservation.setParkingSpotId(parts[1]);
                reservation.setStartTime(LocalDateTime.parse(parts[2], formatter));
                reservation.setEndTime(LocalDateTime.parse(parts[3], formatter));
                reservation.setAmount(Double.parseDouble(parts[4]));
                reservations.add(reservation);
            }
        }
        return reservations;
    }

}
