package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Tetronimo {
    // A list of pairs of coordinates (row,column)
    // representing each block in the tetronimo.
    protected ArrayList<Pair<Integer,Integer>> blockCoords;
    protected Color color;
    // The block used as the rotation point.
    protected Pair<Integer, Integer> centerBlock;
    protected Model model;

    public Tetronimo(Model model) {
        this.model = model;
    }

    public ArrayList<Pair<Integer, Integer>> getBlockCoords() {
        return blockCoords;
    }

    public void setBlockCoords(ArrayList<Pair<Integer, Integer>> blockCoords, Pair<Integer, Integer> centerBlock) {
        this.blockCoords = blockCoords;
        this.centerBlock = centerBlock;
    }

    public Color getColor() {
        return color;
    }

    public Pair<Integer, Integer> getCenterBlock() {
        return centerBlock;
    }

    /**
     * Returns the coordinates of this tetronimo's blocks after
     * a rotation has taken place.
     */
    public List<Pair<Integer,Integer>> rotatedCoords() {
        List<Pair<Integer,Integer>> normalizedBlockCoords = blockCoords.stream().map(block -> {
            return new Pair<>(block.getKey() - centerBlock.getKey(), block.getValue() - centerBlock.getValue());
        }).collect(Collectors.toList());

        return normalizedBlockCoords.stream().map(block -> {
            return new Pair<>(block.getValue() + centerBlock.getKey(), (-block.getKey()) + centerBlock.getValue());
        }).collect(Collectors.toList());
    }

    public enum Tetrominos {
        I,
        J,
        L,
        O,
        S,
        T,
        Z
    }

    public abstract Tetrominos getType();
}
