package se.hernebring.day8;

import java.util.Arrays;
import java.util.List;

public class Grid {
    private int[][] grid;
    private int yMax, xMax;
    private int currentScore;
    private int counter;
    private int highScore;
    private boolean visible;

    public Grid(List<String> input) {
        yMax = input.size();
        grid = new int[yMax][];
        xMax = input.get(0).trim().length();
        Arrays.setAll(grid, row -> new int[xMax]);
        populateGrid(input);
    }

    private void populateGrid(List<String> lines) {
        for(int y = 0; y < yMax; y++) {
            String current = lines.get(y);
            for(int x = 0; x < xMax; x++)
                grid[y][x] = Character.getNumericValue(current.charAt(x));

        }
    }

    public void calculateVisibility() {
        counter = 0;
        highScore = 0;
        for(int y = 0; y < yMax; y++) {
            for(int x = 0; x < xMax; x++) {
                if(edge(x, y))
                    counter++;
                else {
                    calculatePointVisibility(x, y);
                    if(visible)
                        counter++;

                    if(currentScore > highScore)
                        highScore = currentScore;

                }
            }
        }
    }

    public int getCounter() {
        return counter;
    }

    public int getHighScore() {
        return highScore;
    }

    private boolean edge(int x, int y) {
        return y == 0 | y == yMax - 1 || x == 0 | x == xMax - 1;
    }

    private void calculatePointVisibility(int x, int y) {
        visible = false;
        currentScore = 1;
        for(Direction d : Direction.values())
            calculatePointVisibility(x, y, d);

    }

    private void calculatePointVisibility(int x, int y, Direction d) {
        int directionScore = 0;
        boolean isVisibleInDirection = true;
        for(int dX = x + d.getEast(), dY = y + d.getSouth();
            dX < xMax & dY < yMax & dX >= 0 & dY >= 0;
            dX += d.getEast(), dY += d.getSouth())
        {
            directionScore++;
            if(grid[dY][dX] >= grid[y][x]) {
                isVisibleInDirection = false;
                break;
            }
        }
        currentScore *= directionScore;
        if(!visible)
            visible = isVisibleInDirection;

    }
}