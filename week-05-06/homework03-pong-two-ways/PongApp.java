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
 * PongApp is the main class for the Pong game.
 * - It creates the game window and starts the game.
 * - It also handles the game loop.
 * 
 * The game loop is a loop that runs until the game is over. In each iteration
 * of the loop:
 * - The game state is updated and the game is rendered.
 * - The loop runs at a fixed rate, which means that the game runs at the same
 * speed on all computers.
 * - The loop is implemented using a JavaFX Timeline.
 * - The game loop is started when the game window is shown.
 * - The game loop is stopped when the game window is closed.
 * 
 * Respond to two keystroke events:
 * The “i” key will display or hide the FPS information
 * The “s” key will enable and disable sound effects.
 */
public class PongApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pong");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}