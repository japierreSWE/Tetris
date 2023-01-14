package javafx_controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;
import view.GameLoopHandler;

import java.nio.file.Paths;

public class StartController {

    public Button startButton = null;

    @FXML
    public void onStartButtonClicked(Event e) {
        Scene scene = startButton.getScene();
        Stage stage = (Stage)scene.getWindow();
        Parent newSceneRoot = null;

        try {
            FXMLLoader loader =  new FXMLLoader(Paths.get("src","gamescene.fxml").toUri().toURL());
            newSceneRoot = loader.load();
            Scene newScene = new Scene(newSceneRoot, 600, 700);
            stage.setScene(newScene);
            GameLoopController controller = (GameLoopController)loader.getController();
            controller.setUpKeyListeners(newScene);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

}
