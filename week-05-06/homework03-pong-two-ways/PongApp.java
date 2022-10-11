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

    private static final int NUMBER_OF_FRAMES_25 = 25;
    private static long startTime = System.nanoTime();
    private static long lastTime = 0;
    private static int frameCount = 0; // number of frames since the start of
                                       // the game
    // frame rate per second (FPS) in a sliding window array of 20 frames
    private static double[] frameRateArr = new double[NUMBER_OF_FRAMES_25];
    private static double avgFPS = 0;

    private static double avgFpMiliSecond = 0;

    public double getAvgFPS() {
        return avgFPS;
    }

    public static double getAvgFpMiliSecond() {
        return avgFpMiliSecond;
    }

    public static long getStartTime() {
        return startTime;
    }

    private static double timeElapsed = 0;

    public static double getTimeElapsed() {
        return timeElapsed;
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
        Label fpsLabel = new Label("FPS: ");
        fpsLabel.setFont(new Font(APP_FONT, APP_FONT_SIZE));
        fpsLabel.setTextFill(FPS_INFO_COLOR);
        fpsLabel.setLayoutX(10);
        fpsLabel.setLayoutY(30);

        // add the labels to the scene graph
        root.getChildren().add(fpsLabel);

        // variables for ball object
        final int BALL_H = 20;
        final int BALL_W = BALL_H;

        // variables for paddle object
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
                } else if (event.getCode() == KeyCode.S) {
                    // Toggle sound
                }
            }
        });
        /////////////////////////////
        // Mouse events for paddle //
        /////////////////////////////
        paddleMouseMovementController(
            scene,
            PADDLE_X_MAX,
            PADDLE_X_MIN,
            paddle);

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

                calcGameTimeAndAveFpsAndAvgTf(fpsLabel, now);
                frameCount++; // increment the frame counter
            }

            private void calcGameTimeAndAveFpsAndAvgTf(
                    Label fpsLabel,
                    long now) {
                calculateFPS(now);
                calculateAvgFpMiliSecond();
                calculateSecondsElapsedInGame(now);
                if (frameCount % 25 == 0) {
                    fpsLabel.setText(String.format(
                            "FPS: %.2f (avg.), FT: %.2f (ms), GT:  %.2f (s)",
                            getAvgFPS(),
                            getAvgFpMiliSecond(),
                            getTimeElapsed()));
                }
            }

            private void calculateAvgFpMiliSecond() {
                // calculate the average frame time in milliseconds (ms)
                avgFpMiliSecond = TIME_01_MILLISECOND / getAvgFPS();
            }

            private void calculateSecondsElapsedInGame(long now) {
                timeElapsed = ((now - getStartTime()) / TIME_01_SECOND);
            }

            /**
             * calculate the average frame rate per second (FPS)
             */
            private void calculateFPS(long now) {
                double sum = 0;
                frameRateArr[frameCount % frameRateArr.length] = TIME_01_SECOND
                        / (now - lastTime);
                lastTime = now;
                // calculate the average frame rate
                for (int i = 0; i < frameRateArr.length; i++) {
                    sum += frameRateArr[i];
                }
                avgFPS = sum / frameRateArr.length;
            }

        }; // end of game loop
        timer.start(); // Start the game loop
    }

    private void paddleMouseMovementController(
            Scene scene,
            final int PADDLE_X_MAX,
            final int PADDLE_X_MIN,
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
                // Move the paddle center to the mouse position
                paddle.setTranslateX(event.getX() - paddle.getWidth() / 2);
                // Make sure the paddle stays inside the game window
                if (paddle.getTranslateX() < PADDLE_X_MIN) {
                    paddle.setTranslateX(PADDLE_X_MIN);
                } else if (paddle.getTranslateX() > PADDLE_X_MAX) {
                    paddle.setTranslateX(PADDLE_X_MAX);
                }
                paddle.setFill(Color.BLUE);
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
