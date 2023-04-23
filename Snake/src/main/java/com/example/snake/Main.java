package com.example.snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int BOARD_SIZE = 20;
    private static final int CELL_SIZE = 20;
    private SnakeGame snakeGame;
    private GameRenderer gameRenderer;
    private Button playAgainButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        snakeGame = new SnakeGame(BOARD_SIZE, BOARD_SIZE);
        gameRenderer = new GameRenderer(snakeGame, CELL_SIZE);

        Text scoreText = new Text();
        scoreText.setFill(Color.WHITE);
        scoreText.setStroke(Color.BLACK);
        scoreText.setStrokeWidth(0.3);
        scoreText.setFont(new Font("Arial", 25));
        scoreText.setTranslateX(10);
        scoreText.setTranslateY(25);

        Text gameOverText = new Text("Game Over!");
        gameOverText.setFill(Color.RED);
        gameOverText.setFont(new Font("Arial Bold", 60));
        gameOverText.setStroke(Color.BLACK);
        gameOverText.setTranslateX(BOARD_SIZE * CELL_SIZE / 2 - 175);
        gameOverText.setTranslateY(BOARD_SIZE * CELL_SIZE / 2);
        gameOverText.setVisible(false);





        Pane root = new Pane();
        root.getChildren().addAll(gameRenderer.getCanvas(), scoreText, gameOverText);

        Scene scene = new Scene(root, BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE, Color.BLACK);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> snakeGame.setDirection(keyEvent.getCode()));

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> {
            // Reset the game and start a new game
            snakeGame = new SnakeGame(BOARD_SIZE, BOARD_SIZE);
            gameRenderer = new GameRenderer(snakeGame, CELL_SIZE);
            root.getChildren().set(0, gameRenderer.getCanvas());
            gameOverText.setVisible(false);
            scoreText.setTranslateY(25);
            scoreText.setTranslateX(10);
            scoreText.setFont(new Font("Arial", 25));
            scoreText.setStrokeWidth(0.3);
        });

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private long updateFrequency = 100_000_000; // Update every 100 milliseconds

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= updateFrequency) {
                    snakeGame.moveSnake();
                    // Change the update frequency based on the last food type eaten
                    if (snakeGame.getLastFoodType() == Food.FoodType.SPEED_UP) {
                        updateFrequency = 50_000_000; // Increase the speed of the snake
                    } else {
                        updateFrequency = 100_000_000; // Set the default speed of the snake
                    }
                    gameRenderer.render();
                    scoreText.setText("Score: " + snakeGame.getScore());

                    if (snakeGame.isGameOver()) {
                        gameOverText.setVisible(true);
                        scoreText.setTranslateY(BOARD_SIZE * CELL_SIZE / 2 + 60);
                        scoreText.setTranslateX(BOARD_SIZE * CELL_SIZE / 2 - 110);
                        scoreText.setFont(new Font("Arial", 60));
                        scoreText.setStrokeWidth(1);
                        stop();
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }
}