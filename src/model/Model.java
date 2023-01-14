package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

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
                    nextTetrominos.push(new ITetronimo(this));
                    break;
                case J:
                    nextTetrominos.push(new JTetronimo(this));
                    break;
                case L:
                    nextTetrominos.push(new LTetromino(this));
                    break;
                case O:
                    nextTetrominos.push(new OTetronimo(this));
                    break;
                case S:
                    nextTetrominos.push(new STetromino(this));
                    break;
                case T:
                    nextTetrominos.push(new TTetromino(this));
                    break;
                case Z:
                    nextTetrominos.push(new ZTetronimo(this));
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
        grid[t.getCenterBlock().getKey()][t.getCenterBlock().getValue()] = t.getColor();
    }

    private void removeTetronimo(Tetronimo t) {
        ArrayList<Pair<Integer,Integer>> blocks = t.getBlockCoords();

        for(Pair<Integer,Integer> block : blocks) {
            grid[block.getKey()][block.getValue()] = null;
        }
        grid[t.getCenterBlock().getKey()][t.getCenterBlock().getValue()] = null;
    }

    /**
     * Returns true if the block is in an illegal position
     * (that is, already occupied by another block or out of bounds)
     */
    private boolean blockIsIllegal(Pair<Integer, Integer> block) {
        return block.getValue() < 0
                || block.getValue() >= NUM_COLUMNS
                || block.getKey() < 0
                || block.getKey() >= NUM_ROWS
                || grid[block.getKey()][block.getValue()] != null;
    }

    /**
     * Moves the current tetronimo according to the given vertical/horizontal
     * offsets.
     *
     * Returns true if the tetronimo successfully moved.
     */
    private boolean moveTetronimo(int rOffset, int cOffset) {
        ArrayList<Pair<Integer,Integer>> currentBlocks = currentTetronimo.getBlockCoords();
        Pair<Integer, Integer> currentCenterBlock = currentTetronimo.getCenterBlock();
        ArrayList<Pair<Integer,Integer>> newBlocks = new ArrayList<>();
        Pair<Integer, Integer> newCenterBlock = new Pair<>(currentCenterBlock.getKey() + rOffset, currentCenterBlock.getValue() + cOffset);
        boolean result = false;

        removeTetronimo(currentTetronimo);

        for(Pair<Integer,Integer> block : currentBlocks) {
            newBlocks.add(new Pair<>(block.getKey() + rOffset, block.getValue() + cOffset));
        }

        boolean allBlocksLegal = true;
        for(Pair<Integer, Integer> block : newBlocks) {
            // Don't move the tetronimo if it will be out of bounds
            // or into an already existing block.
            if(blockIsIllegal(block)) {
                allBlocksLegal = false;
                break;
            }
        }
        if(blockIsIllegal(newCenterBlock)) {
            allBlocksLegal = false;
        }

        if(allBlocksLegal) {
            currentTetronimo.setBlockCoords(newBlocks, newCenterBlock);
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

    /**
     * Rotates the current tetronimo if it is
     * legal to do so.
     */
    public void rotateCurrentTetronimo() {
        removeTetronimo(currentTetronimo);
        List<Pair<Integer, Integer>> rotatedCoords = currentTetronimo.rotatedCoords();
        boolean allBlocksLegal = true;

        for(Pair<Integer, Integer> block : rotatedCoords) {
            if(blockIsIllegal(block)) {
                allBlocksLegal = false;
                break;
            }
        }

        if(allBlocksLegal) {
            ArrayList<Pair<Integer, Integer>> newCoords = new ArrayList<>(rotatedCoords);
            // The center block doesn't move during rotation.
            currentTetronimo.setBlockCoords(newCoords, currentTetronimo.getCenterBlock());
        }
        addTetronimo(currentTetronimo);
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
