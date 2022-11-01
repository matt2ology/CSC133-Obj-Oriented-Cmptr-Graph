import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application; // JavaFX application support
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

interface Updatable {
    void update();
}

class GameText {

}

abstract class GameObject extends Group {

}

class Helicopter {

}

class Pond {

}

class Cloud {

}

class Helipad {

}

class Game {

    public Game() {

    }

}

/**
 * This class manages the high-level aspects of the
 * application, and setup and show the initial Scene for the application.
 * <p>
 * The <code>GameApp</code> class sets up all keyboard event handlers to
 * invoke public methods in Game.
 * </p>
 * 
 * @author Matthew Mendoza
 * @version A1
 */
public class GameApp extends Application {
    /**
     * The width of the game window.
     */
    private static final int GAME_WIDTH = 400;
    /**
     * The height of the game window.
     */
    private static final int GAME_HEIGHT = 800;
    /**
     * The title of the game window.
     */
    private static final String GAME_WINDOW_TITLE = "RainMaker - A1";

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public static int getGameHeight() {
        return GAME_HEIGHT;
    }

    /**
     * This method is called by the JavaFX Application class to start the
     * application. This method sets up the initial Scene for the application.
     * 
     * @param primaryStage the primary Stage for this application, onto which
     *                     the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create Pane as root for all nodes in the scene graph
        Pane root = new Pane();
        init(root);
        // show the initial Scene for your application
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);
        // set the title of the Stage
        primaryStage.setTitle(GAME_WINDOW_TITLE);
        // add the scene to the Stage
        primaryStage.setScene(scene);
        /*
         * Scaling the main pane by (-1) to flip the y-axis
         * so that the origin is in the bottom left corner
         * and the y-axis increases upwards this is done so
         * that the y-axis is consistent with the
         * mathematical y-axis. (0,0) is the bottom left
         * corner of the screen so we operate in the first
         * quadrant
         */
        root.setScaleY(-1);
        // prevent window resizing by user (optional)
        primaryStage.setResizable(false);
        // display the Stage (window) to the user and start the JavaFX runtime
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // Left Arrow Changes the heading of the helicopter by 15 degrees to the left.
                if (event.getCode() == KeyCode.LEFT) {
                    System.out.println("Left Arrow: <-");
                }

                // Right Arrow Changes the heading of the helicopter by 15 degrees to the right.
                if (event.getCode() == KeyCode.RIGHT) {
                    System.out.println("Right Arrow: ->");
                }

                // Up Arrow Increases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.UP) {
                    System.out.println("Up Arrow: ^");
                }

                // Down Arrow Decreases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.DOWN) {
                    System.out.println("Down Arrow: v");
                }

                // 'i' Turns on the helicopter ignition.
                if (event.getCode() == KeyCode.I) {
                    System.out.println("I - Turns on the helicopter ignition");
                }

                // 'b' [optional] shows bounding boxes around objects.
                if (event.getCode() == KeyCode.B) {
                    System.out.println("B - shows bounding boxes around objects");
                }

                // 'r' Reinitialize the game
                if (event.getCode() == KeyCode.R) {
                    System.out.println("R - Reinitialize the game");
                    GameApp.init(root);
                }
            }
        });
    } // end of start()

    /**
     * This method is called by the start method to initialize the game.
     * 
     * We (Re)initialize the game by clear the Pane of all previously
     * added nodes from a previous game play session (if any)
     * 
     * @param root the root node of the scene graph
     */
    private static void init(Pane root) {

    }

    /**
     * This method is the main entry point for the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch();
    }

}