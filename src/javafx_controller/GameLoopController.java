package javafx_controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import model.Model;
import view.GameLoopHandler;

public class GameLoopController {
    private GameLoopHandler handler;
    private Model model;
    public Canvas canvas = null;
    public Label levelLabel = null;

    @FXML
    void initialize() {
        model = new Model(this);
        handler = new GameLoopHandler(canvas, model);
        handler.start();
    }

    void setUpKeyListeners(Scene scene) {
        scene.setOnKeyPressed(event -> {
            handler.addKeyPress(event.getCode().toString());
        });
        scene.setOnKeyReleased(event -> {
            handler.removeKeyPress(event.getCode().toString());
        });
    }

    public void setLevel(int level) {
        levelLabel.setText(String.valueOf(level));
    }
}
