package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Tetronimo {
    // A list of pairs of coordinates (row,column)
    // representing each block in the tetronimo.
    protected ArrayList<Pair<Integer,Integer>> blockCoords;
    protected Color color;

    public ArrayList<Pair<Integer, Integer>> getBlockCoords() {
        return blockCoords;
    }

    public void setBlockCoords(ArrayList<Pair<Integer, Integer>> blockCoords) {
        this.blockCoords = blockCoords;
    }

    public Color getColor() {
        return color;
    }
}
