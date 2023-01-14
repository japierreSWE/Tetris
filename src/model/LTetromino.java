package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class LTetromino extends Tetronimo {
    public LTetromino() {
        color = Color.ORANGE;
        blockCoords = new ArrayList<Pair<Integer,Integer>>();
        blockCoords.add(new Pair<>(2,5));
        blockCoords.add(new Pair<>(3,5));
        blockCoords.add(new Pair<>(3,4));
        blockCoords.add(new Pair<>(3,3));
    }
}
