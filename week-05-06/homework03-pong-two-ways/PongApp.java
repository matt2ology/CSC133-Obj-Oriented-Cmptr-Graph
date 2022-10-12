import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Now that you’re up to speed on JavaFX and the basic linear style, you will
 * want to code your first version of the video game. In the Asteroids game the
 * author uses a simple class hierarchy for his game objects where all game
 * objects extend the abstract class PhysicsObject. For this first version of
 * Pong, it’s not necessary to create a class hierarchy as the objects are so
 * simple. You may simply encode them as fields in the main class, e.g.:
 * 
 * Rectangle paddle = new Rectangle(...);
 * Rectangle ball = new Rectangle(...);
 * 
 * Of course you can separate declaration from initialization if you wish.
 * 
 * The purpose of this first project is for you to code freely and solve all of
 * the simple algorithmic problems such as paddle/ball/wall intersection. You do
 * not have to write the sound class at all, it is provided in the appendix.
 * Your first version should be as close as possible to the given demo. Your
 * application should respond to two keystroke events: The “i” key will display
 * or hid the fps information and the “s” key will enable and disable sound.
 */
public class PongApp extends Application {

    /**
     * In-game timer string format to display the number of frames per second.
     */
    private static final String GAME_TIME_INFO_STRING = "GT: %.2f (s)";

    /**
     * Frame time string format to display the number of frames per
     * millisecond.
     */
    private static final String FRAME_TIME_INFO_STRING = "FT: %.2f (ms)";

    /**
     * The Frames Per Second (FPS) to be displayed.
     */
    private static final String FPS_INFO_STRING = "FPS: %.2f (avg.)";

    /**
     * Game information label is the concatenation of the FPS, frame time, and
     * in-game timer.
     */
    private static final String FPS_DISPLAY_INFO_STRING = FPS_INFO_STRING +
            " , " +
            FRAME_TIME_INFO_STRING +
            " , " +
            GAME_TIME_INFO_STRING;

    /**
     * 1_000.0 is the number of nanoseconds in a millisecond
     */
    private static final double TIME_01_MILLISECOND = 1_000.0;

    /**
     * 1_000_000_000.0 nanoseconds = 1 second
     */
    private static final double TIME_01_SECOND = 1_000_000_000.0;
    private static final String APP_TITLE = "Pong 1.0";
    private static final int APP_H = 600;
    private static final int APP_W = 800;
    private static final String APP_FONT = "Arial";
    private static final Color FPS_INFO_COLOR = Color.BLACK;
    private static final int APP_FONT_SIZE = 24;
    private static boolean newGame = true;

    private static boolean paddleIsLeft = false;
    private static boolean paddleIsStationary = true;
    
    
    private static double avgFpMiliSecond = 0;
    private static double avgFPS = 0;
    private static double secondsElapsedInGame = 0;
    private static final int NUMBER_OF_FRAMES_25 = 25;
    private static double[] frameRateArr = new double[NUMBER_OF_FRAMES_25];
    private static int animationTimerFrameCounter = 0;
    private static long lastTime = 0;
    private static long startTime = System.nanoTime();

    public static boolean isPaddleIsLeft() {
        return paddleIsLeft;
    }

    public static void setPaddleIsLeft(boolean paddleIsLeft) {
        PongApp.paddleIsLeft = paddleIsLeft;
    }

    public static boolean isPaddleIsStationary() {
        return paddleIsStationary;
    }

    public static void setPaddleIsStationary(boolean paddleIsStationary) {
        PongApp.paddleIsStationary = paddleIsStationary;
    }

    public static double getAvgFpMiliSecond() {
        return avgFpMiliSecond;
    }

    public static void setAvgFpMiliSecond(double avgFpMiliSecond) {
        PongApp.avgFpMiliSecond = avgFpMiliSecond;
    }

    public double getAvgFPS() {
        return avgFPS;
    }

    public static void setAvgFPS(double avgFPS) {
        PongApp.avgFPS = avgFPS;
    }

    public static double getSecondsElapsedInGame() {
        return secondsElapsedInGame;
    }

    public static void setSecondsElapsedInGame(double secondsElapsedInGame) {
        PongApp.secondsElapsedInGame = secondsElapsedInGame;
    }

    public static int getAnimationTimerFrameCounter() {
        return animationTimerFrameCounter;
    }

    public static void setAnimationTimerFrameCounter(int aniTimerFrmCtr) {
        PongApp.animationTimerFrameCounter = aniTimerFrmCtr;
    }

    public static long getStartTime() {
        return startTime;
    }

    /**
     * The game loop is a loop that runs until game window is closed.
     * In each iteration of the loop:
     * 
     * The game state is updated and the game is rendered.
     * 
     * The loop runs at a fixed rate, which means that the game runs at
     * the same speed on all computers.
     * 
     * The game loop is started when the game window is shown.
     * The game loop is stopped when the game window is closed.
     * 
     * Respond to two keystroke events:
     * 
     * The “i” key will display or hide the FPS information and
     * the “s” key will enable and disable sound effects.
     * 
     * @param primaryStage the stage to be shown when the application is
     *                     started
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, APP_W, APP_H);
        scene.setFill(Color.WHITE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();

        // Labels for Frames Per Second (FPS) information
        Label fpsDisplayInfoLabel = new Label("FPS: ");
        fpsDisplayInfoLabel.setFont(new Font(APP_FONT, APP_FONT_SIZE));
        fpsDisplayInfoLabel.setTextFill(FPS_INFO_COLOR);
        fpsDisplayInfoLabel.setLayoutX(10);
        fpsDisplayInfoLabel.setLayoutY(10);

        // add the labels to the scene graph
        root.getChildren().add(fpsDisplayInfoLabel);

        // variables for ball object
        final int BALL_H = 20;
        final int BALL_W = BALL_H;

        // variables for paddle object
        final boolean isBallUp = false;
        final double BALL_RESPAWN_X_CORD = (Math.random() * (APP_W - BALL_W));
        // 1/3 of the screen
        final int BALL_RESPOWN_Y_CORD = APP_H / 3 - BALL_H / 2;
        final int BALL_SPEED_X = 5;
        final int BALL_VELOCITY_Y = (isBallUp ? -BALL_SPEED_Y : BALL_SPEED_Y);
        final int BALL_X_MAX = APP_W - BALL_W;
        final int PADDLE_H = 20;
        final int PADDLE_W = 150;
        final int PADDLE_X_MAX = APP_W - PADDLE_W;
        final int PADDLE_X_MIN = 0;
        /**
         * Paddle objects are rectangles that move up and down the screen.
         */
        Rectangle paddle = new Rectangle(PADDLE_W, PADDLE_H);
        paddle.setFill(Color.RED);
        paddle.setTranslateY(APP_H - paddle.getHeight());
        root.getChildren().add(paddle);

        /**
         * Ball objects are rectangles that move around the screen.
         */
        Rectangle ball = new Rectangle(BALL_W, BALL_H);
        ball.setFill(Color.BLUE);
        ball.setTranslateX(BALL_RESPAWN_X_CORD);
        ball.setTranslateY(BALL_RESPOWN_Y_CORD);
        root.getChildren().add(ball);

        /////////////////////////////
        // Key events for sound //
        // effects and fps display //
        /////////////////////////////
        /**
         * Respond to two keystroke events:
         * The “i” key will display or hide the FPS information and
         * the “s” key will enable and disable sound effects.
         */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.I) {
                    // Toggle FPS display information
                    fpsDisplayInfoLabel.setVisible(
                            !fpsDisplayInfoLabel.isVisible());
                } else if (event.getCode() == KeyCode.S) {
                    // Toggle sound
                }
            }
        });
        /////////////////////////////
        // Mouse events for paddle //
        /////////////////////////////

        /**
         * Respond to mouse movement events.
         * The paddle's center is set to the mouse's x coordinate.
         * The paddle will not go off the screen (stays within the window).
         * 
         * When the mouse is moved off the screen
         * - the paddle will stop moving
         * - stay in the last position it was in
         * - change color to red.
         * 
         * When the mouse is moved back on the screen
         * - the paddle will move to the latest cursor position
         * - the paddle's middle will center on the cursor's position
         */
        paddleMouseMovementController(scene, paddle);

        /**
         * The game loop is a loop that runs until game window is closed.
         */
        AnimationTimer timer = new AnimationTimer() {

            /**
             * The game loop is a loop that runs until game window is closed.
             * In each iteration of the loop:
             * - The game state is updated and the game is rendered.
             * - The loop runs at a fixed rate, which means that the game
             * runs at the same speed on all computers.
             * - The game loop is started when the game window is shown.
             * - The game loop is stopped when the game window is closed.
             * 
             * @param now the current time in nanoseconds
             *            (1 billionth of a second)
             */
            @Override
            public void handle(long now) {

                // every new game the ball respawns in the middle of the screen
                if (newGame) {
                    respawnBallToRandomLocation();
                    /*
                     * every new game the ball only moves vertically,
                     * no velocity in the x-axis, along the y-axis down the
                     * screen
                     */
                    newGame = false;
                }

                ball.setTranslateY(ball.getTranslateY() + BALL_VELOCITY_Y);
                // game resets when ball hits the bottom of the screen
                ballHitBottomOfScreenResetGame();

                setInGameTimeAndAvgFrameTimeAndFPS(now);
                updateFPSDisplayInformation();
                animationTimerFrameCounter++; // increment the frame counter
            }

            /**
             * Respawns the ball to a random location on the screen.
             *
             */
            private void respawnBallToRandomLocation() {
                // random location in app x-axis (the width of app window)
                ball.setTranslateX(generateRandomBallRespawnXCord());
                ball.setTranslateY(BALL_RESPOWN_Y_CORD);
            }

            /**
             * Update the FPS display information based on the current
             * calculations of the FPS, average frame time, and in-game time.
             * 
             */
            private void updateFPSDisplayInformation() {
                // Intermittently update the FPS display information
                if (animationTimerFrameCounter % NUMBER_OF_FRAMES_25 == 0) {
                    fpsDisplayInfoLabel.setText(String.format(
                            FPS_DISPLAY_INFO_STRING,
                            getAvgFPS(),
                            getAvgFpMiliSecond(),
                            getSecondsElapsedInGame()));
                }
            }

            /**
             * A wrapper method that calls all the methods to calculate the
             * in-game time, the average frame time, and the average frames
             * per second.
             * 
             * @param now
             */
            private void setInGameTimeAndAvgFrameTimeAndFPS(long now) {
                setAvgFPS(calculateAvgFPS(now));
                setAvgFpMiliSecond(calculateAvgFpMiliSecond());
                setSecondsElapsedInGame(calculateSecondsElapsedInGame(now));
            }

            /**
             * Generate an x-coordinate for the ball to respawn that is within
             * the width of the app window
             * 
             * from (the left side of the app window) to the width of the app
             * window minus the width of the ball (the right side of the app
             * window).
             * @return
             */
            private double generateRandomBallRespawnXCord() {
                return Math.random() * (BALL_X_MAX);
            }

            /**
             * When the ball hits the bottom of the screen, the game resets.
             * 
             * @param ball
             */
            private void ballHitBottomOfScreenResetGame() {
                if (ball.getTranslateY() >= APP_H - ball.getHeight()) {
                    newGame = true;
                    System.err.println("Game Over");
                }
            }

            /**
             * Calculates the average frame time in milliseconds.
             * 
             * @return
             */
            private double calculateAvgFpMiliSecond() {
                // calculate the average frame time in milliseconds (ms)
                return (TIME_01_MILLISECOND / getAvgFPS());
            }

            /**
             * Calculates the in game time elapsed in seconds.
             * 
             * @param now The current time in nanoseconds
             *            (1 billionth of a second)
             * @return the in game time elapsed in seconds
             */
            private double calculateSecondsElapsedInGame(long now) {
                // convert nanoseconds to seconds
                return (((now - getStartTime()) / TIME_01_SECOND));
            }

            /**
             * calculate the average frame rate per second (FPS)
             */
            private double calculateAvgFPS(long now) {
                double sum = 0;
                long deltaCurTimePrevTime = now - lastTime;
                // calculate the average FPS
                frameRateArr[animationTimerFrameCounter %
                        frameRateArr.length] = TIME_01_SECOND
                                / deltaCurTimePrevTime;
                lastTime = now;
                // calculate the average frame rate
                for (int i = 0; i < frameRateArr.length; i++) {
                    sum += frameRateArr[i];
                }
                return (sum / frameRateArr.length);
            }

        }; // end of game loop
        timer.start(); // Start the game loop
    }

    /**
     * Defines the mouse movements to control the paddle movement and color.
     * 
     * @param scene
     * @param paddle
     */
    private void paddleMouseMovementController(
            Scene scene, // the scene to add the event handler
            Rectangle paddle) {

        /**
         * the paddle is blue when the mouse enters the scene
         */
        scene.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paddle.setFill(Color.BLUE);
                // Mouse courser disappears when it enters the scene
                scene.setCursor(Cursor.NONE);
            }
        });

        /**
         * The paddle color is red when the mouse is outside the game window.
         */
        scene.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                paddle.setFill(Color.RED);
            }
        });

        /**
         * The paddle moves left and right when the user's mouse enters the
         * game window
         */
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // So paddle doesn't go off screen to the left
                double PADDLE_X_MIN = 0;
                // So paddle doesn't go off screen to the right
                double PADDLE_X_MAX = APP_W - paddle.getWidth();

                // Move the paddle center to the mouse position
                setCursorToCenterOfPaddle(paddle, event);
                // Make sure the paddle stays inside the game window
                setPaddleInGameBoundaries(paddle, PADDLE_X_MIN, PADDLE_X_MAX);
                paddle.setFill(Color.BLUE);
            }

            /**
             * Sets the paddle in the game window boundaries.
             * If the paddle is at the left or right edge of the game window,
             * the paddle will not move.
             * 
             * @param paddle       the paddle to set in the game window
             * @param PADDLE_X_MIN the minimum x-coordinate of the paddle
             * @param PADDLE_X_MAX the maximum x-coordinate of the paddle
             */
            private void setPaddleInGameBoundaries(
                    Rectangle paddle,
                    double PADDLE_X_MIN,
                    double PADDLE_X_MAX) {
                if (paddle.getTranslateX() < PADDLE_X_MIN) {
                    paddle.setTranslateX(PADDLE_X_MIN);
                } else if (paddle.getTranslateX() > PADDLE_X_MAX) {
                    paddle.setTranslateX(PADDLE_X_MAX);
                }
            }

            /**
             * Sets the mouse cursor to the center of the paddle.
             * 
             * @param paddle the paddle to set the mouse cursor
             * @param event  the mouse event to get the mouse cursor location
             */
            private void setCursorToCenterOfPaddle(
                    Rectangle paddle,
                    MouseEvent event) {
                paddle.setTranslateX(event.getX() - paddle.getWidth() / 2);
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
