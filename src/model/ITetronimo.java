package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class ITetronimo extends Tetronimo {
    public ITetronimo() {
        color = Color.AQUA;
        blockCoords = new ArrayList<Pair<Integer, Integer>>();
        blockCoords.add(new Pair<>(2, 4));
        blockCoords.add(new Pair<>(2, 5));
        blockCoords.add(new Pair<>(2, 6));
        blockCoords.add(new Pair<>(2, 7));
    }
}