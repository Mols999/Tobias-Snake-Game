package com.example.snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Point> snakeBody = new ArrayList<Point>();
    private Point snakeHead;

    private int headSize = 1;

    // Constructor that takes in the board size
    public Snake(int boardSize) {
        snakeBody = new ArrayList<>();
        int startPos = boardSize / 2;
        snakeBody.add(new Point(startPos, startPos));
        snakeBody.add(new Point(startPos - 1, startPos));
        snakeBody.add(new Point(startPos - 2, startPos));

        // Set the head of the snake to be the first body part in the list
        snakeHead = snakeBody.get(0);
    }

    // Getter method for the snake body
    public List<Point> getSnakeBody() {
        return snakeBody;
    }

    // Getter method for the snake head
    public Point getSnakeHead() {
        return snakeHead;
    }

    public int getHeadSize() {
        return headSize;
    }

    // Method to add a body part to the snake
    public void addBodyPart(Point point) {
        snakeBody.add(point);
    }

    public Point getLast() {
        return this.snakeBody.get(this.snakeBody.size() - 1);
    }

    public int getBodySize() {
        return this.snakeBody.size();
    }
}
