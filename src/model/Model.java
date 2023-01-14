package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    public final static int NUM_COLUMNS = 10;
    public final static int NUM_ROWS = 17 + 2;
    public final static int NUM_VISIBLE_ROWS = NUM_ROWS - 2;

    private Color[][] grid;
    private Tetronimo currentTetronimo;

    private long normalDropTime;
    private long currentDropTime;

    public Model() {
        grid = new Color[NUM_ROWS + 2][NUM_COLUMNS];
        for(Color[] row : grid) {
            Arrays.fill(row, null);
        }

        currentDropTime = normalDropTime = 1000;

        currentTetronimo = new OTetronimo();
        addTetronimo(currentTetronimo);
    }

    /**
     * Adds a tetronimo to the grid.
     * @param t The tetronimo to add to the grid.
     */
    void addTetronimo(Tetronimo t) {
        ArrayList<Pair<Integer,Integer>> blocks = t.getBlockCoords();

        for(Pair<Integer,Integer> block : blocks) {
            grid[block.getKey()][block.getValue()] = t.getColor();
        }
    }

    /**
     * Moves the current tetronimo according to the given vertical/horizontal
     * offsets.
     *
     * Returns true if the tetronimo successfully moved.
     */
    private boolean moveTetronimo(int rOffset, int cOffset) {
        ArrayList<Pair<Integer,Integer>> currentBlocks = currentTetronimo.getBlockCoords();
        ArrayList<Pair<Integer,Integer>> newBlocks = new ArrayList<>();
        boolean result = false;

        for(Pair<Integer,Integer> block : currentBlocks) {
            grid[block.getKey()][block.getValue()] = null;
            newBlocks.add(new Pair<>(block.getKey() + rOffset, block.getValue() + cOffset));
        }

        boolean allBlocksLegal = true;
        for(Pair<Integer, Integer> block : newBlocks) {
            // Don't move the tetronimo if it will be out of bounds
            // or into an already existing block.
            if(block.getValue() < 0
                    || block.getValue() >= NUM_COLUMNS
                    || block.getKey() < 0
                    || block.getKey() >= NUM_ROWS
                    || grid[block.getKey()][block.getValue()] != null) {
                allBlocksLegal = false;
                break;
            }
        }

        if(allBlocksLegal) {
            currentTetronimo.setBlockCoords(newBlocks);
            result = true;
        }
        addTetronimo(currentTetronimo);
        return result;
    }

    /**
     * Moves the current tetronimo horizontally, according
     * to a given offset.
     * @param offset
     */
    public void moveTetronimoHorizontal(int offset) {
        moveTetronimo(0, offset);
    }

    /**
     * Drops the current tetronimo by one space.
     */
    public void dropTetronimo() {
        boolean dropped = moveTetronimo(1, 0);
        if(!dropped) {
            currentTetronimo = new ZTetronimo();
            addTetronimo(currentTetronimo);
        }
    }

    public Color[][] getGrid() {
        return grid;
    }

    public long getCurrentDropTime() {
        return currentDropTime;
    }

    public long getNormalDropTime() {
        return normalDropTime;
    }

    public void setCurrentDropTime(long currentDropTime) {
        this.currentDropTime = currentDropTime;
    }
}
