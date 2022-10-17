import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Game object base class
 * 
 * In this hierarchy the commonality between Bat and Ball objects would be
 * placed in PhysicsObject and accessed via inheritance but the relationship
 * between Bat/Ball and Rectangle would be one of composition, i.e., a hasA
 * relationship as opposed to an isA relationship.
 */
abstract class PhysicsObject {

}

/**
 * Bat objects is a rectangle controlled by the user and move side-to-side
 * (left and right) the screen.
 */
class Bat extends PhysicsObject {

}

/**
 * Ball objects is a square that bounces off the top and bottom of the screen
 * and off the Bat objects. The Ball object also bounces off the left and right
 * edges of the screen.
 */
class Ball extends PhysicsObject {

}

/**
 * Game model class Model–View–Controller (MVC) pattern
 * The Pong class implements the business rules of the application, e.g., how
 * the elements interact, and when the score has increased.
 * 
 * The PongApp class sets up the scene and stage as well as the event handling
 * for all scene based mouse interaction. When the mouse enters the scene,
 * the mouse cursor disappears.
 */
class Pong {

}

/**
 * The PongApp (Pong 1.0) is the main class that
 * - It also handles the game loop.
 * - It creates the game window and starts the game.
 * - Sets the event handling for all scene based mouse interaction
 * - Sets up the scene and stage
 * 
 * The game loop is a loop that runs until game window is closed.
 * In each iteration of the loop:
 * - The game state is updated and the game is rendered.
 * - The loop runs at a fixed rate, which means that the game runs at the same
 * speed on all computers.
 * - The game loop is started when the game window is shown.
 * - The game loop is stopped when the game window is closed.
 * 
 * Respond to two keystroke events:
 * - The “i” key will display or hide the FPS information
 * - The “s” key will enable and disable sound effects.
 * 
 */
public class OPongApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // create group as root node
        Group root = new Group();
        // scene with root node and size
        Scene scene = new Scene(root, 800, 600);
        // stage with scene
        primaryStage.setScene(scene);
        // title
        primaryStage.setTitle("OPong");

        // animation loop to update the ball, bat, score, and game timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        };

        // title
        primaryStage.setTitle("OPong");
        // show stage
        primaryStage.show();

    }

}
