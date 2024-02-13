package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.kul.quickparksystem.service.ReservationService;
import pl.kul.quickparksystem.service.UserService;
import pl.kul.quickparksystem.utils.Dialogs;
import pl.kul.quickparksystem.utils.SceneChanger;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    private Button backButton;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    private final UserService userService = new UserService();
    private static String loggedInUsername;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (userService.checkPassword(username, password)) {
                loggedInUsername = username; // zapisz zalogowanego użytkownika

                ReservationService reservationService = new ReservationService();
                reservationService.updateReservations();

                SceneChanger.changeScene(event, "/pl/kul/quickparksystem/view/dashboard.fxml", false);
            } else {
                Dialogs.showErrorDialog("Login Error","Failed to log in", "Incorrect username or password");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Problem with logging in", e);
            Dialogs.showErrorDialog("Login Error","Failed to log in", "Incorrect username or password");
        }
    }

    // Metoda statyczna do pobierania nazwy zalogowanego użytkownika
    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        SceneChanger.changeScene(event,"/pl/kul/quickparksystem/view/startscreen.fxml", false);
    }
}
