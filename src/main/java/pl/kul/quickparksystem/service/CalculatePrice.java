package pl.kul.quickparksystem.service;

public class CalculatePrice {
    public static double calculatePrice(int durationHours) {
        return switch (durationHours) {
            case 1 -> 5.00;
            case 4 -> 15.00;
            case 6 -> 20.00;
            case 12 -> 40.00;
            default -> 0.00;
        };
    }
}
