package pl.kul.quickparksystem.service;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceGenerator {

    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    private String calculateTimeRange(int durationHours) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(durationHours);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "od [" + startTime.format(formatter) + "] do [" + endTime.format(formatter) + "]";
    }

    public void generateDocument(String customerName, String username, int durationHours, double amount, String parkingPlace, String parkingSpot, String docType) {
        String timestamp = getCurrentTimestamp();
        String filename = docType.toLowerCase() + "_" + username + "_" + timestamp + ".pdf";
        String timeRange = calculateTimeRange(durationHours);
        String docPath = "database/" + docType.toLowerCase() + "s/" + filename;

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText(docType + " for: " + customerName);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Parking location: " + parkingPlace + " | at spot: " + parkingSpot);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Reservation time: " + durationHours + "h");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Ticket validity: " + timeRange);
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Amount: " + amount + " PLN");
                contentStream.endText();
            }
            document.save(docPath);
        } catch (IOException e) {
            System.err.println("Error while creating " + docType.toLowerCase() + ": " + e.getMessage());
        }
    }

    // Faktura
    public void generateInvoice(String customerName, String username, int durationHours, double amount, String parkingPlace, String parkingSpot) {
        generateDocument(customerName, username , durationHours, amount, parkingPlace, parkingSpot, "Invoice");
    }

    // Paragon
    public void generateReceipt(String customerName, String username, int durationHours, double amount, String parkingPlace, String parkingSpot) {
        generateDocument(customerName, username, durationHours, amount, parkingPlace, parkingSpot, "Receipt");
    }
}
