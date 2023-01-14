package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class TTetromino extends Tetronimo {
    public TTetromino(Model model) {
        super(model);
        color = Color.PURPLE;
        blockCoords = new ArrayList<Pair<Integer, Integer>>();
        centerBlock = new Pair<>(3, 5);
        blockCoords.add(new Pair<>(2, 5));
        blockCoords.add(new Pair<>(3, 4));
        blockCoords.add(new Pair<>(3, 6));
    }
}
