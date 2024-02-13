package pl.kul.quickparksystem.model;

import pl.kul.quickparksystem.service.CalculatePrice;

import java.time.LocalDateTime;

public class Reservation {
    private UserModel user;
    private String parkingSpotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double amount;

    // Konstruktor bezargumentowy
    public Reservation() {
    }

    // Konstruktor ze wszystkimi polami
    public Reservation(UserModel user, String parkingSpotId, LocalDateTime startTime, int durationHours) {
        this.user = user;
        this.parkingSpotId = parkingSpotId;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(durationHours);
        this.amount = CalculatePrice.calculatePrice(durationHours);
    }

    public String getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(String parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Możesz także dodać metody pomocnicze, jeśli potrzebujesz dostępu do konkretnych danych użytkownika
    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    public String getCustomerName() {
        return user != null ? user.getFirstName() + " " + user.getLastName() : null;
    }


}

