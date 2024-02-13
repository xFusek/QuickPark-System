package pl.kul.quickparksystem.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class ParkingManager {
    public String getParkingNameFromSpotId(String spotId) {
        if (spotId.endsWith("A")) {
            return "Parking A";
        } else if (spotId.endsWith("B")) {
            return "Parking B";
        } else if (spotId.endsWith("C")) {
            return "Parking C";
        } else {
            return "Unknown Parking";
        }
    }

    public boolean isSpotOccupied(String spotId) throws IOException {
        Set<String> occupiedSpots = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try (BufferedReader reader = new BufferedReader(new FileReader("database/reservations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                LocalDateTime endTime = LocalDateTime.parse(parts[3], formatter);
                if (endTime.isAfter(LocalDateTime.now())) {
                    occupiedSpots.add(parts[1]);
                }
            }
        }
        return occupiedSpots.contains(spotId);
    }

}
