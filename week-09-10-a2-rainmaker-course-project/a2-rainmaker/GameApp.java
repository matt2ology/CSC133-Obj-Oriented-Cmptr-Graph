import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Updatable interface for objects that need to be updated every frame.
 */
interface Updatable {
    public void update();
}

/**
 * @brief Game Class is the base of our object hierarchy.
 * @summary It contains methods and fields that manage the common aspects of
 *          all game objects in our game.
 */
abstract class GameObject extends Group implements Updatable {
    protected Translate translate;
    protected Rotate rotate;

    public GameObject(Point2D location) {
        this.translate = new Translate(location.getX(), location.getY());
        this.rotate = new Rotate(0, 0, 0);
        this.getTransforms().addAll(this.translate, this.rotate);
    }

    /**
     * @brief update iterates through all
     *        children and calls their update method.
     */
    public void update() {
        for (Node node : getChildren()) {
            if (node instanceof Updatable) {
                ((Updatable) node).update();
            }
        }
    }

    /**
     * @brief Add a child to this GameObject.
     */
    public void add(Node node) {
        super.getChildren().add(node);
    }
}

abstract class FixedObject extends GameObject {
    protected Scale scale;

    public FixedObject(Point2D location) {
        super(location);
        this.scale = new Scale();
    }
}

/**
 * Game
 */
class Game extends Pane {

    public Game() {
        /*
         * Flips the y-axis, so that the origin is in the
         * bottom left corner of the screen
         */
        super.setScaleY(-1);
    }

    /**
     * update is called by the game loop, so it may "render" the game
     * with updated object states (e.g. position, velocity, etc.)
     */
    public void update() {

    }

    public void play() {
        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        loop.start();
    }

    /**
     * Initialize the game. This method is called when the game is started.
     * It will clear all nodes from the game world and add the game objects
     */
    public void init() {
        super.getChildren().clear();
        super.getChildren().addAll();
        // print out each object in the game world
        super.getChildren().forEach(System.out::println);
    }

    /**
     * @brief sets the background of the game world.
     */
    public void setMapBackground(Image image) {
        // flip the image vertically so that it is not upside down
        Scale scale = new Scale(1, -1);
        // Pivot is needed to flip around the center
        // of the image instead of the top left corner.
        scale.setPivotY(image.getHeight() / 2);
        super.getTransforms().add(scale); // flip the image vertically
        // Configure the background image to fill the entire game world
        BackgroundImage bg = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT); // fill the entire game world
        super.setBackground(new Background(bg));
    }
}

/**
 * @brief Globals class provides a place to store all global/static constants.
 */
class Globals {
    public static final String GAME_TITLE = "Rainmaker A2";
    public static final Dimension2D GAME_APP_DIMENSIONS = new Dimension2D(
            800,
            800);
    public static final Image GAME_MAP_IMAGE = new Image(
            "textures/map/rainmaker_a2_map_dry_desert.png");
}

/**
 * @brief GameApp manages the high-level aspects of our application and
 *        setup and show the initial Scene for your application.
 * 
 * @summary The GameApp class sets up all keyboard event handlers to invoke
 *          public
 *          methods in Game.
 */
public class GameApp extends Application {
    Game game = new Game();

    @Override
    public void start(Stage primaryStage) throws Exception {
        game.setMapBackground(Globals.GAME_MAP_IMAGE);
        primaryStage.setScene(new Scene(
                game,
                Globals.GAME_APP_DIMENSIONS.getWidth(),
                Globals.GAME_APP_DIMENSIONS.getHeight()));
        primaryStage.setTitle(Globals.GAME_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}