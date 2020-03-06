package com.tsquare.speakfriend.main;

import com.tsquare.speakfriend.crypt.Crypt;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public abstract class Controller {
    @FXML
    public void newScene(String nextScene) throws IOException {
        String resource = "/" + nextScene + ".fxml";
        URL file = Controller.class.getResource(resource);

        Parent scene = FXMLLoader.load(file);
        Stage stage = Main.getStage();
        Scene currentScene = stage.getScene();

        Scene newScene = new Scene(scene, currentScene.getWidth(), currentScene.getHeight());

        stage.setScene(newScene);
    }

    @FXML
    public void transitionScene(String newScene, int duration) {
        PauseTransition pause = new PauseTransition(
                Duration.seconds(duration)
        );
        pause.setOnFinished(e -> {
            try {
                this.newScene(newScene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }

    protected Scene addSceneTimer(Scene scene) {
        PauseTransition transition = Main.getTransition();
        scene.addEventFilter(InputEvent.ANY, evt -> transition.playFromStart());

        return scene;
    }

    protected String getEncryptedText(String key, TextField field) {
        if(!field.getText().isEmpty()) {
            try {
                return Crypt.encrypt(key, field.getText());
            } catch(Exception ignored) {}
        }
        return "";
    }
}
