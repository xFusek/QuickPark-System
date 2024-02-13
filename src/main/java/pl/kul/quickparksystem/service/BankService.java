package pl.kul.quickparksystem.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BankService {

    private static final String BANK_FILE = "database/bank.txt";

    public static double checkBalance(String username) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("database/bank.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(";");
                if (userDetails[0].equals(username)) {
                    // Zamień przecinek na kropkę przed parsowaniem
                    String balanceString = userDetails[1].replace(",", ".");
                    return Double.parseDouble(balanceString);
                }
            }
        }
        return 0.0; // Zwróć 0, jeśli użytkownik nie zostanie znaleziony
    }


    // Metoda do aktualizowania balansu (do użycia przy rezerwacji miejsca)
    public void updateBalance(String username, double amount) throws IOException {
        Path bankFilePath = Paths.get(BANK_FILE);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(bankFilePath, StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            String[] userDetails = fileContent.get(i).split(";");
            if (userDetails[0].equals(username)) {
                double currentBalance = Double.parseDouble(userDetails[1].replace(",", "."));
                double newBalance = currentBalance + amount;
                userDetails[1] = String.format(Locale.US, "%.2f", newBalance);
                fileContent.set(i, String.join(";", userDetails));
                break;
            }
        }

        Files.write(bankFilePath, fileContent, StandardCharsets.UTF_8);
    }

    public static void addInitialBalance(String username, double initialBalance) throws IOException {
        Path path = Paths.get("database/bank.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(username + ";" + String.format(Locale.US, "%.2f", initialBalance));
            writer.newLine();
        }
    }


}
