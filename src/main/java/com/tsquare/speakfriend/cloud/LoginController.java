package com.tsquare.speakfriend.cloud;

import com.tsquare.speakfriend.api.Api;
import com.tsquare.speakfriend.api.ApiResponse;
import com.tsquare.speakfriend.auth.Auth;
import com.tsquare.speakfriend.main.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoginController extends Controller {
    @FXML TextField email;
    @FXML PasswordField password;
    @FXML Text notice_text;

    @FXML
    public void submitAction(KeyEvent event) throws ParseException {
        if(event.getCode().equals(KeyCode.ENTER)) {
            this.loginAction();
        }
    }

    @FXML
    public void loginAction() throws ParseException {
        if (email.getText().equals("")) {
            notice_text.setText("Please enter your email address");
            return;
        }
        if (password.getText().equals("")) {
            notice_text.setText("Please enter your password");
            return;
        }

        Auth auth = new Auth();
        Api api = new Api();

        ApiResponse response = api.login(email.getText(), password.getText());

        if (response.getResponseMessage().equals("OK")) {
            String body = response.getResponseBody();
            JSONParser parser = new JSONParser();

            JSONObject requestObject = (JSONObject) parser.parse(body);

            try {
                if (requestObject.get("message").equals("Invalid Credentials")) {
                    notice_text.setText("Invalid credentials");
                    return;
                }
            } catch (Exception ignored) {}

            String token = (String) requestObject.get("access_token");

            auth.setApiToken(token);

            notice_text.setText("Login successful");

            goBackAction(2);

            return;
        }

        notice_text.setText(response.getErrors().toString());
    }

    @FXML
    public void goBackAction() {
        goBack();
    }

    public void goBackAction(Integer delay) {
        goBack(delay);
    }
}
