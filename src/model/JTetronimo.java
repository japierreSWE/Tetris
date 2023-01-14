package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class JTetronimo extends Tetronimo {
    public JTetronimo() {
        color = Color.ROYALBLUE;
        blockCoords = new ArrayList<Pair<Integer,Integer>>();
        blockCoords.add(new Pair<>(2,3));
        blockCoords.add(new Pair<>(3,3));
        blockCoords.add(new Pair<>(3,4));
        blockCoords.add(new Pair<>(3,5));
    }
}
