package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class JTetronimo extends Tetronimo {
    public JTetronimo(Model model) {
        super(model);
        color = Color.ROYALBLUE;
        blockCoords = new ArrayList<Pair<Integer,Integer>>();
        centerBlock = new Pair<>(3,3);
        blockCoords.add(new Pair<>(2,3));
        blockCoords.add(new Pair<>(3,4));
        blockCoords.add(new Pair<>(3,5));
    }
}
