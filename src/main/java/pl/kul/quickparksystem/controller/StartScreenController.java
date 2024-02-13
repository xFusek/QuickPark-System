package pl.kul.quickparksystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.kul.quickparksystem.utils.SceneChanger;

public class StartScreenController {

    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML private void handleLogin() {
        ActionEvent event = new ActionEvent(loginButton, null);
        SceneChanger.changeScene(event, "/pl/kul/quickparksystem/view/login.fxml", false);
    }

    @FXML private void handleRegister() {
        ActionEvent event = new ActionEvent(registerButton, null);
        SceneChanger.changeScene(event, "/pl/kul/quickparksystem/view/register.fxml", false);
    }
}

