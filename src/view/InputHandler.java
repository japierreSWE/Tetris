package view;

import model.Model;

import java.util.HashSet;

public class InputHandler {

    private Model model;
    private HashSet<String> keysPressed;
    private final int MOVE_SPEED = 500;

    // Timestamps representing when the user has pressed the left/right keys
    // and when the tetronimo has moved.
    private Long leftKeyTimestamp, rightKeyTimestamp, movedTimestamp = null;
    private Long rotatedTimestamp = null;

    public InputHandler(Model model) {
        this.model = model;
        keysPressed = new HashSet<String>();
    }

    public void addKeyPress(String keyCode) {
        if(keyCode.equals("LEFT") && leftKeyTimestamp == null) {
            leftKeyTimestamp = System.currentTimeMillis();
            movedTimestamp = null;
        } else if(keyCode.equals("RIGHT") && rightKeyTimestamp == null) {
            rightKeyTimestamp = System.currentTimeMillis();
            movedTimestamp = null;
        }

        keysPressed.add(keyCode);
    }

    public void removeKeyPress(String keyCode) {
        if(keyCode.equals("LEFT")) {
            leftKeyTimestamp = null;
        } else if(keyCode.equals("RIGHT")) {
            rightKeyTimestamp = null;
        } else if(keyCode.equals("UP")) {
            rotatedTimestamp = null;
        }

        keysPressed.remove(keyCode);
    }

    public void handleInput(long now) {
        long currentTime = System.currentTimeMillis();

        // The tetronimo must not move if both keys are being pressed
        if(keysPressed.contains("LEFT") && !keysPressed.contains("RIGHT") &&
                (movedTimestamp == null || currentTime - movedTimestamp >= MOVE_SPEED)) {
            model.moveTetronimoHorizontal(-1);
            movedTimestamp = currentTime;
        }

        if(keysPressed.contains("RIGHT") && !keysPressed.contains("LEFT") &&
                (movedTimestamp == null || currentTime - movedTimestamp >= MOVE_SPEED)) {
            model.moveTetronimoHorizontal(1);
            movedTimestamp = currentTime;
        }

        if(keysPressed.contains("UP") && rotatedTimestamp == null) {
            model.rotateCurrentTetronimo();
            rotatedTimestamp = currentTime;
        }

        if(keysPressed.contains("DOWN")) {
            model.setCurrentDropTime(model.getNormalDropTime() / 4);
        } else {
            model.setCurrentDropTime(model.getNormalDropTime());
        }
    }
}
