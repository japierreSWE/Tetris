package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Model;
import model.Tetronimo;

public class NextPieceDisplayer {
    Model model;
    Canvas canvas;

    public NextPieceDisplayer(Model model, Canvas canvas) {
        this.model = model;
        this.canvas = canvas;
    }

    private void drawSquare(int r, int c, GraphicsContext gc, Color color) {
        int squareLength = 20;
        double centerOffset = (canvas.getWidth() / 2);
        double x = (c * squareLength) + centerOffset + (squareLength / 2);
        double y = (r * squareLength) + centerOffset;
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        gc.fillRect(x, y, squareLength, squareLength);
        gc.strokeRect(x, y, squareLength, squareLength);
    }

    public void displayNextPiece() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());

        Tetronimo nextTetronimo = model.getNextPiece();

        switch(nextTetronimo.getType()) {
            case I:
                drawSquare(1,0,gc,nextTetronimo.getColor());
                drawSquare(0,0,gc,nextTetronimo.getColor());
                drawSquare(-1,0,gc,nextTetronimo.getColor());
                drawSquare(-2,0,gc,nextTetronimo.getColor());
                break;

            case J:
                drawSquare(0,0, gc, nextTetronimo.getColor());
                drawSquare(0,-1, gc, nextTetronimo.getColor());
                drawSquare(-1,0, gc, nextTetronimo.getColor());
                drawSquare(-2,0, gc, nextTetronimo.getColor());
                break;

            case L:
                drawSquare(0,0, gc, nextTetronimo.getColor());
                drawSquare(0,1, gc, nextTetronimo.getColor());
                drawSquare(-1,0, gc, nextTetronimo.getColor());
                drawSquare(-2,0, gc, nextTetronimo.getColor());
                break;

            case O:
                drawSquare(0,0, gc, nextTetronimo.getColor());
                drawSquare(0,1, gc, nextTetronimo.getColor());
                drawSquare(-1,0, gc, nextTetronimo.getColor());
                drawSquare(-1,1, gc, nextTetronimo.getColor());
                break;

            case S:
                drawSquare(0,0, gc, nextTetronimo.getColor());
                drawSquare(0,-1, gc, nextTetronimo.getColor());
                drawSquare(-1,0, gc, nextTetronimo.getColor());
                drawSquare(-1,1, gc, nextTetronimo.getColor());
                break;

            case T:
                drawSquare(0,0, gc, nextTetronimo.getColor());
                drawSquare(0,-1, gc, nextTetronimo.getColor());
                drawSquare(0,1, gc, nextTetronimo.getColor());
                drawSquare(-1,0, gc, nextTetronimo.getColor());
                break;

            case Z:
                drawSquare(0,0, gc, nextTetronimo.getColor());
                drawSquare(0,1, gc, nextTetronimo.getColor());
                drawSquare(-1,0, gc, nextTetronimo.getColor());
                drawSquare(-1,-1, gc, nextTetronimo.getColor());
                break;
        }
    }
}
