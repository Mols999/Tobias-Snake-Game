package com.example.snake;

public class GameBoard {
    private int rows;
    private int columns;

    // Constructor that takes in the number of rows and columns for the game board
    public GameBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    // Getter method for the number of rows in the game board
    public int getRows() {
        return rows;
    }

    // Getter method for the number of columns in the game board
    public int getColumns() {
        return columns;
    }
}

