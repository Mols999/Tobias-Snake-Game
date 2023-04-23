package com.example.snake;

import javafx.scene.input.KeyCode;

import java.awt.*;
import java.util.List;

public class SnakeGame {
    private Snake snake;
    private Food food;
    private GameBoard gameBoard;
    private int currentDirection = RIGHT;
    private boolean gameOver;
    private int score = 0;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private long foodVisibleTimestamp;
    private static final int FOOD_VISIBILITY_MIN_TIME = 5 * 1000; // 5 seconds
    private static final int FOOD_VISIBILITY_MAX_TIME = 10 * 1000; // 10 seconds
    private static final int FOOD_VISIBILITY_RANGE = FOOD_VISIBILITY_MAX_TIME - FOOD_VISIBILITY_MIN_TIME;

    private boolean insaneMode = false;
    private int insaneModeThreshold = 20;

    private Food.FoodType lastFoodType;

    public SnakeGame(int rows, int columns) {
        this.snake = new Snake(rows);
        this.gameBoard = new GameBoard(rows, columns);
        this.currentDirection = RIGHT; // Initialize the current direction
        generateFood();
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Food.FoodType getLastFoodType() {
        return lastFoodType;
    }

    public void moveSnake() {
        Point snakeHead = snake.getSnakeHead();
        Point snakeTail = new Point(snake.getLast().x, snake.getLast().y);

        // Move the snake's body parts
        for (int i = snake.getBodySize() - 1; i > 0; i--) {
            snake.getSnakeBody().get(i).x = snake.getSnakeBody().get(i - 1).x;
            snake.getSnakeBody().get(i).y = snake.getSnakeBody().get(i - 1).y;
        }

        // Move the snake's head based on its direction
        switch (currentDirection) {
            case UP:
                snakeHead.y -= 1;
                break;
            case DOWN:
                snakeHead.y += 1;
                break;
            case LEFT:
                snakeHead.x -= 1;
                break;
            case RIGHT:
                snakeHead.x += 1;
                break;
        }

        // Check for food collision
        if (food.getX() == snakeHead.x && food.getY() == snakeHead.y) {
            score += 1;
            lastFoodType = food.getType();
            switch (food.getType()) {
                case NORMAL:
                    snake.addBodyPart(new Point(snakeTail.x, snakeTail.y));
                    break;
                case SPEED_UP:
                    break;
            }

            generateFood(); // Generate new food after the current one is eaten
        }

        // Check for collisions with the snake's body or the game board boundaries
        if (snakeIntersects(snakeHead.x, snakeHead.y) || outOfBounds(snakeHead.x, snakeHead.y)) {
            gameOver = true;
        }
    }
    public void setDirection(KeyCode code) {
        if (code == KeyCode.RIGHT || code == KeyCode.D) {
            if (currentDirection != LEFT) {
                currentDirection = RIGHT;
            }
        } else if (code == KeyCode.LEFT || code == KeyCode.A) {
            if (currentDirection != RIGHT) {
                currentDirection = LEFT;
            }
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            if (currentDirection != DOWN) {
                currentDirection = UP;
            }
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            if (currentDirection != UP) {
                currentDirection = DOWN;
            }
        }
    }

    public void generateFood() {
        start:
        while (true) {
            int foodX = (int) (Math.random() * gameBoard.getRows());
            int foodY = (int) (Math.random() * gameBoard.getColumns());

            for (Point snakePart : snake.getSnakeBody()) {
                if (snakePart.getX() == foodX && snakePart.getY() == foodY) {
                    continue start;
                }
            }

            food = new Food(foodX, foodY);
            food.setType(Food.randomFoodType());
            foodVisibleTimestamp = System.currentTimeMillis();
            break;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    private boolean snakeIntersects(int x, int y) {
        List<Point> snakeBody = snake.getSnakeBody();
        int headSize = snake.getHeadSize(); // get the head size
        for (int i = headSize; i < snakeBody.size(); i++) { // start the loop from the head size
            Point snakePart = snakeBody.get(i);
            if (snakePart.getX() == x && snakePart.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean outOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= gameBoard.getRows() || y >= gameBoard.getColumns();
    }

    public boolean shouldActivateInsaneMode() {
        return score % 20 == 0 && score != 0; // Activate insane mode every 5 eaten foods
    }

    public boolean isInsaneMode() {
        return score >= insaneModeThreshold;
    }

    public void checkFoodVisibility() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - foodVisibleTimestamp > FOOD_VISIBILITY_MIN_TIME &&
                currentTime - foodVisibleTimestamp < FOOD_VISIBILITY_MAX_TIME &&
                Math.random() * FOOD_VISIBILITY_RANGE < currentTime - foodVisibleTimestamp) {
            generateFood();
        }
    }
}

