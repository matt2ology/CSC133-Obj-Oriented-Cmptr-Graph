import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @brief Game Class is the base of our object hierarchy.
 * @summary It contains methods and fields that manage the common aspects of
 * all game objects in our game.
 */
abstract class GameObject extends Pane {
    

}

/**
 * @brief Game class provides the model for our game.
 * @summary It manages the changing state of our game as we interact with it.
 *          The Game does not know anything about where user input comes from
 *          or how it is generated.
 *          <ol>
 *          <li>This class holds the state of the game</li>
 *          <li>Determines win/lose conditions</li>
 *          <li>instantiates and links the other Game Objects</li>
 *          </ol>
 */
class Game extends Pane {
    /**
     * @brief Game constructor sets up the initial state of the game.
     */
    public Game() {
        /**
         * Flip the y-axis so that up is positive and down is negative
         */
        super.setScaleY(-1);
    }
}

/**
 * @brief Globals class provides a place to store global constants.
 */
class Globals {
    /**
     * @brief The dimensions of the game application.
     */
    public static final Dimension2D GAME_APP_DIMENSIONS = new Dimension2D(
            800,
            800);
}

/**
 * @brief GameApp manages the high-level aspects of our application and
 *        setup and show the initial Scene for your application.
 * 
 * @summary
 *          The GameApp class sets up all keyboard event handlers to invoke
 *          public
 *          methods in Game.
 */
public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game();

        // Set the scene with the game
        primaryStage.setScene(new Scene(
                game,
                Globals.GAME_APP_DIMENSIONS.getWidth(),
                Globals.GAME_APP_DIMENSIONS.getHeight(),
                Color.TAN));
        primaryStage.setTitle("Game");
        primaryStage.setResizable(false); // Game is not resizable
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}