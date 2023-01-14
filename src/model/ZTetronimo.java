package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class ZTetronimo extends Tetronimo {
    public ZTetronimo(Model model) {
        super(model);
        color = Color.RED;
        blockCoords = new ArrayList<Pair<Integer,Integer>>();
        centerBlock = new Pair<>(3,4);
        blockCoords.add(new Pair<>(2,3));
        blockCoords.add(new Pair<>(2,4));
        blockCoords.add(new Pair<>(3,5));
    }
}
