import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

class GameText extends Text {
    private String FONT_OF_CHOICE = "Helvetica";
    private Scale scale = new Scale();
    public GameText(String text, int textFontSize) {
        super(text);
        this.setFont(Font.font(FONT_OF_CHOICE, textFontSize));
        this.scale.setY(-1);
        this.getTransforms().add(scale);
        // set the origin of the text to the center
        this.setX(-this.getLayoutBounds().getWidth() / 2);
        // 4 is a magic number that works for Helvetica because
        // the letters are taller than it is wide and we want to center it
        this.setY(this.getLayoutBounds().getHeight() / 4);
    }
}

/**
 * @see https://heliportlighting.com/heliport-design/
 *      FATO must be at least 1.5 times the overall length of the helicopter
 */
class Helipad extends FixedObject {

    public Helipad(Point2D location, Dimension2D size) {
        super(location);
        this.add(new HelipadFATOSquare(size));
        this.add(new HelipadTLOFCircle(size));
        this.add(new HelipadH());
    }
}

/**
 * @brief Represents the Final Approach and Takeoff (FATO) square of the
 *        helipad.
 */
class HelipadFATOSquare extends Rectangle {

    public HelipadFATOSquare(Dimension2D size) {
        super(0, 0,
                size.getWidth(),
                size.getHeight());
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(5);
        // Center of the square is at the center of the
        // helipad by setTranslate because we want to
        // translate the rectangle relative to the helipad object
        this.setTranslateX(-size.getWidth() / 2);
        this.setTranslateY(-size.getHeight() / 2);
    }
}

/**
 * @brief Represents the Touchdown and Liftoff (TLOF) circle of the
 *        helipad.
 * 
 *        The Circle's diameter is based on the helipad's FATO square dimension.
 */
class HelipadTLOFCircle extends Circle {
    private static final int PADDING_BETWEEN_SQUARE_AND_CIRCLE = 5;

    public HelipadTLOFCircle(Dimension2D size) {
        super(0,
                0,
                ((size.getWidth() / 2) - PADDING_BETWEEN_SQUARE_AND_CIRCLE));
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.YELLOW);
        this.setStrokeWidth(3);
    }
}

class HelipadH extends GameText {

    public HelipadH() {
        super("H", 75);
        // set font color to white
        this.setFill(Color.WHITE);
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
    /**
     * @brief The Helipad centered on half the width
     *        and lower 1/8th the height of the game world.
     */
    public static final Point2D HELIPAD_COORDINATES = new Point2D(
            (GAME_APP_DIMENSIONS.getWidth() / 2),
            (GAME_APP_DIMENSIONS.getHeight()
                    - (GAME_APP_DIMENSIONS.getHeight() / 7)));
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