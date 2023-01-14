package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class OTetronimo extends Tetronimo {
    public OTetronimo(Model model) {
        super(model);
        color = Color.YELLOW;
        blockCoords = new ArrayList<Pair<Integer,Integer>>();
        centerBlock = new Pair<>(2,4);
        blockCoords.add(new Pair<>(2,5));
        blockCoords.add(new Pair<>(3,4));
        blockCoords.add(new Pair<>(3,5));
    }

    // O Tetronimo doesn't rotate.
    @Override
    public List<Pair<Integer, Integer>> rotatedCoords() {
        return this.blockCoords;
    }
}
