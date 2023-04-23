package com.example.snake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.awt.*;

public class GameRenderer {
    private SnakeGame game;
    private GraphicsContext gc;
    private Canvas canvas;
    private int cellSize;
    private int rotationCounter = 0;
    private int rotationFrequency = 10;
    private boolean canvasRotated = false;


    // Constructor that takes in a SnakeGame object and the cell size for the game board
    public GameRenderer(SnakeGame game, int cellSize) {
        this.game = game;
        this.cellSize = cellSize;

        // Initialize the canvas with the appropriate size
        int canvasWidth = game.getGameBoard().getRows() * cellSize;
        int canvasHeight = game.getGameBoard().getColumns() * cellSize;
        this.canvas = new Canvas(canvasWidth, canvasHeight);

        // Initialize the graphics context for the canvas
        this.gc = canvas.getGraphicsContext2D();
    }

    // Getter method for the canvas
    public Canvas getCanvas() {
        return canvas;
    }

    // Method to render the game on the canvas
    // Method to render the game on the canvas
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawBackground();

        if (game.isInsaneMode()) {
            if (!canvasRotated) {
                double randomAngle = 90.0 * (Math.random() < 0.5 ? 1 : -1); // Randomly rotate 90 degrees clockwise or counterclockwise
                rotateCanvas(randomAngle);
                canvasRotated = true; // Set this flag to true after rotating the canvas
            }

            rotationCounter++;
            if (rotationCounter >= rotationFrequency) {
                resetCanvasRotation();
                double randomAngle = 90.0 * (Math.random() < 0.5 ? 1 : -1); // Randomly rotate 90 degrees clockwise or counterclockwise
                rotateCanvas(randomAngle);
                rotationCounter = 0;
            }
        } else if (!game.isInsaneMode() && canvasRotated) {
            resetCanvasRotation();
            canvasRotated = false; // Set this flag to false after resetting the canvas rotation
        }

        drawSnake();
        drawFood();
    }

    // Private helper method to draw the snake on the canvas
    private void drawSnake() {
        gc.setFill(Color.DARKGREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Iterate through each body part of the snake and draw it on the canvas
        for (Point bodyPart : game.getSnake().getSnakeBody()) {
            gc.fillRect(bodyPart.getX() * cellSize, bodyPart.getY() * cellSize, cellSize, cellSize);
            gc.strokeRect(bodyPart.getX() * cellSize, bodyPart.getY() * cellSize, cellSize, cellSize);
        }
    }

    // Private helper method to draw the food on the canvas
    private void drawFood() {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Get the position of the food and draw it on the canvas
        int foodX = game.getFood().getX();
        int foodY = game.getFood().getY();
        gc.fillRect(foodX * cellSize, foodY * cellSize, cellSize, cellSize);
        gc.strokeRect(foodX * cellSize, foodY * cellSize, cellSize, cellSize);
    }

    public void rotateCanvas(double angle) {
        gc.save();
        gc.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        gc.rotate(angle);
        gc.translate(-canvas.getWidth() / 2, -canvas.getHeight() / 2);
    }

    public void resetCanvasRotation() {
        gc.restore();
    }





    // Private helper method to draw the background on the canvas
    private void drawBackground() {
        Color lightGreen = Color.rgb(170, 215, 81);
        Color darkGreen = Color.rgb(162, 209, 73);

        // Iterate through each cell in the game board and draw the appropriate background color on the canvas
        for (int row = 0; row < game.getGameBoard().getRows(); row++) {
            for (int col = 0; col < game.getGameBoard().getColumns(); col++) {
                gc.setFill((row + col) % 2 == 0 ? lightGreen : darkGreen);
                gc.fillRect(row * cellSize, col * cellSize, cellSize, cellSize);
            }
        }
    }
}