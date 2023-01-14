package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Model {
    public final static int NUM_COLUMNS = 10;
    public final static int NUM_ROWS = 17 + 2;
    public final static int NUM_VISIBLE_ROWS = NUM_ROWS - 2;

    private Color[][] grid;
    private Tetronimo currentTetronimo;

    private long normalDropTime;
    private long currentDropTime;

    private LinkedList<Tetronimo> nextTetrominos;

    enum Tetrominos {
        I,
        J,
        L,
        O,
        S,
        T,
        Z
    }

    public Model() {
        grid = new Color[NUM_ROWS + 2][NUM_COLUMNS];
        for(Color[] row : grid) {
            Arrays.fill(row, null);
        }

        currentDropTime = normalDropTime = 1000;
        nextTetrominos = new LinkedList<>();
        populateNextTetrominos();

        currentTetronimo = nextTetrominos.pop();
        addTetronimo(currentTetronimo);
    }

    /**
     * Populates nextTetronimos with a random permutation
     * of the 7 tetronimos,
     */
    private void populateNextTetrominos() {
        for(Tetrominos tEnum : Tetrominos.values()) {
            switch(tEnum) {
                case I:
                    nextTetrominos.push(new ITetronimo());
                    break;
                case J:
                    nextTetrominos.push(new JTetronimo());
                    break;
                case L:
                    nextTetrominos.push(new LTetromino());
                    break;
                case O:
                    nextTetrominos.push(new OTetronimo());
                    break;
                case S:
                    nextTetrominos.push(new STetromino());
                    break;
                case T:
                    nextTetrominos.push(new TTetromino());
                    break;
                case Z:
                    nextTetrominos.push(new ZTetronimo());
                    break;
            }
        }
        Collections.shuffle(nextTetrominos);
    }

    /**
     * Adds a tetronimo to the grid.
     * @param t The tetronimo to add to the grid.
     */
    private void addTetronimo(Tetronimo t) {
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

            if(nextTetrominos.isEmpty()) {
                populateNextTetrominos();
            }

            currentTetronimo = nextTetrominos.pop();
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
