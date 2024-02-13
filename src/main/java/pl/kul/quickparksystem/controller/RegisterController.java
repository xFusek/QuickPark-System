package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.model.UserModel;
import pl.kul.quickparksystem.service.BankService;
import pl.kul.quickparksystem.service.UserService;
import pl.kul.quickparksystem.utils.Dialogs;
import pl.kul.quickparksystem.utils.SceneChanger;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;


    private final UserService userService = new UserService();

    @FXML
    private void initialize() {
        registerButton.setOnAction(event -> registerUser());
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        UserModel newUser = new UserModel();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        try {
            if (userService.checkUserExists(username)) {
                Dialogs.showErrorDialog("Registration Error", "Failed to Register", "User already exists.");
                return;
            }

            userService.saveUser(newUser);
            BankService.addInitialBalance(username, 100.00);
            Dialogs.showInformationDialog("Registration Successful", null, "You have successfully registered. Please log in.\nEnjoy your free trial with a 100 PLN bonus!");
            showLoginScreen();
        } catch (IOException e) {
            Dialogs.showErrorDialog("Registration Error", "Failed to Register", "Error: " + e.getMessage());
        }
    }

    private void showLoginScreen() {
        ActionEvent event = new ActionEvent(registerButton, null);
        SceneChanger.changeScene(event, "/pl/kul/quickparksystem/view/login.fxml", false);
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        SceneChanger.changeScene(event,"/pl/kul/quickparksystem/view/startscreen.fxml",false);
    }
}
