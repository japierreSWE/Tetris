package view;

import model.Model;

import java.util.HashSet;

public class InputHandler {

    private Model model;
    private GameLoopHandler glh;
    private HashSet<String> keysPressed;
    private final int MOVE_SPEED = 500;

    // Timestamps representing when the user has pressed the left/right keys
    // and when the tetronimo has moved.
    private Long leftKeyTimestamp, rightKeyTimestamp, movedTimestamp = null;
    private Long rotatedTimestamp = null;
    boolean pauseButtonDown = false;

    public InputHandler(Model model, GameLoopHandler glh) {
        this.model = model;
        this.glh = glh;
        keysPressed = new HashSet<String>();
    }

    public void addKeyPress(String keyCode) {
        if(keyCode.equals("LEFT") && leftKeyTimestamp == null) {
            leftKeyTimestamp = System.currentTimeMillis();
            movedTimestamp = null;
        } else if(keyCode.equals("RIGHT") && rightKeyTimestamp == null) {
            rightKeyTimestamp = System.currentTimeMillis();
            movedTimestamp = null;
        } else if(keyCode.equals("R") && model.hasLost()) {
            model.reset();
        } else if(keyCode.equals("P") && !pauseButtonDown) {
            model.togglePause();
            glh.togglePause();
            pauseButtonDown = true;
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
        } else if(keyCode.equals("P")) {
            pauseButtonDown = false;
        }

        keysPressed.remove(keyCode);
    }

    public void handleInput(long now) {
        long currentTime = System.currentTimeMillis();

        if(model.isActive()) {
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
}
