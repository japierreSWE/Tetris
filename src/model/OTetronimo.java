package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class OTetronimo extends Tetronimo {
    public OTetronimo() {
        color = Color.YELLOW;
        blockCoords = new ArrayList<Pair<Integer,Integer>>();
        blockCoords.add(new Pair<>(0,4));
        blockCoords.add(new Pair<>(0,5));
        blockCoords.add(new Pair<>(1,4));
        blockCoords.add(new Pair<>(1,5));
    }
}
