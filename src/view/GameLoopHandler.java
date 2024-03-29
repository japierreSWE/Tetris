package view;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;
import model.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameLoopHandler extends AnimationTimer {
    private Canvas canvas;
    private Model model;
    private InputHandler inputHandler;
    private NextPieceDisplayer nextPieceDisplayer;
    private Long lastDropTimestamp = null;
    private Long timeSinceDrop = null;

    public GameLoopHandler(Canvas canvas, Model model, Canvas nextPieceCanvas) {
        this.canvas = canvas;
        this.model = model;
        inputHandler = new InputHandler(model, this);
        nextPieceDisplayer = new NextPieceDisplayer(this.model, nextPieceCanvas);
    }

    private void drawGrid(GraphicsContext gc) {
        double columnOffset = canvas.getWidth() / Model.NUM_COLUMNS;
        double rowOffset = canvas.getHeight() / Model.NUM_VISIBLE_ROWS;
        Color[][] grid = model.getGrid();

        drawGhostBlocks(gc);

        //Draw blocks.
        for(int r = 2; r<Model.NUM_ROWS; r++) {
            for(int c = 0; c<Model.NUM_COLUMNS; c++) {
                if(grid[r][c] != null) {
                    double rowY = (r-2) * rowOffset;
                    double columnX = c * columnOffset;

                    gc.setFill(grid[r][c]);
                    gc.fillRect(columnX, rowY, columnOffset, rowOffset);
                }
            }
        }

        gc.setStroke(Color.BLACK);

        for(int i = 0; i< Model.NUM_COLUMNS + 1; i++) {
            double columnX = i * columnOffset;
            gc.strokeLine(columnX, 0, columnX, canvas.getHeight());
        }

        for(int i = 0; i<Model.NUM_VISIBLE_ROWS + 1; i++) {
            double rowY = i * rowOffset;
            gc.strokeLine(0, rowY, canvas.getWidth(), rowY);
        }
    }

    private void drawGhostBlocks(GraphicsContext gc) {
        List<Pair<Integer,Integer>> ghostBlocks = model.getGhostBlocks();
        double columnOffset = canvas.getWidth() / Model.NUM_COLUMNS;
        double rowOffset = canvas.getHeight() / Model.NUM_VISIBLE_ROWS;

        for(Pair<Integer,Integer> block : ghostBlocks) {
            int rowIndex = block.getKey();
            int columnIndex = block.getValue();
            double rowY = (rowIndex-2) * rowOffset;
            double columnX = columnIndex * columnOffset;

            gc.setFill(Color.LIGHTGREY);
            gc.fillRect(columnX, rowY, columnOffset, rowOffset);
        }
    }

    private void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setFont(Font.font("Arial", 35));
        gc.fillText("Game Over", canvas.getWidth()/2 - 100, canvas.getHeight()/2);
        gc.fillText("Press R to restart", canvas.getWidth()/2 - 140, canvas.getHeight()/2 + 40);
        gc.strokeText("Game Over", canvas.getWidth()/2 - 100, canvas.getHeight()/2);
        gc.strokeText("Press R to restart", canvas.getWidth()/2 - 140, canvas.getHeight()/2 + 40);
    }

    private void drawPauseScreen(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setFont(Font.font("Arial", 35));
        gc.fillText("Paused", canvas.getWidth()/2 - 60, canvas.getHeight()/2);
        gc.fillText("Press P to unpause", canvas.getWidth()/2 - 140, canvas.getHeight()/2 + 40);
        gc.strokeText("Paused", canvas.getWidth()/2 - 60, canvas.getHeight()/2);
        gc.strokeText("Press P to unpause", canvas.getWidth()/2 - 140, canvas.getHeight()/2 + 40);

    }

    public void togglePause() {
        if(timeSinceDrop == null) {
            timeSinceDrop = System.currentTimeMillis() - lastDropTimestamp;
        } else {
            lastDropTimestamp = System.currentTimeMillis() - timeSinceDrop;
            timeSinceDrop = null;
        }
    }

    public void addKeyPress(String keyCode) {
        inputHandler.addKeyPress(keyCode);
    }

    public void removeKeyPress(String keyCode) {
        inputHandler.removeKeyPress(keyCode);
    }

    @Override
    public void handle(long now) {
        inputHandler.handleInput(now);
        nextPieceDisplayer.displayNextPiece();

        long currentTime = System.currentTimeMillis();
        if(lastDropTimestamp == null) {
            lastDropTimestamp = System.currentTimeMillis();
        }

        if(currentTime - lastDropTimestamp >= model.getCurrentDropTime() && model.isActive()) {
            model.dropTetronimo();
            lastDropTimestamp = currentTime;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        drawGrid(gc);

        if(model.isPaused()) {
            drawPauseScreen(gc);
        }

        if(model.hasLost()) {
            drawGameOver(gc);
        }
    }
}
