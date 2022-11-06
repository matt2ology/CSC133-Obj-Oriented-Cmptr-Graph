import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application; // JavaFX application support
import javafx.event.Event;
import javafx.event.EventType;
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
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Updatable interface for all objects that are updatable in the game world.
 */
interface Updatable {
    public void update();
}

/**
 * GameObject class for all game objects both Fixed and Movable objects
 * derived from this class.
 */
abstract class GameObject extends Group implements Updatable {

    protected Translate translate;
    protected Rotate rotate;

    /**
     * Constructor for the GameObject object,
     * initializes the translate and rotate transforms;
     * so, all objects have a translate and rotate transform.
     */
    public GameObject() {
        translate = new Translate();
        rotate = new Rotate();
        getTransforms().addAll(translate, rotate);
    }

    public void update() {
        for (Node n : getChildren()) { // for each child node of this object
            if (n instanceof Updatable) // if the child node is updatable
                ((Updatable) n).update(); // call the update method
        }
    }

    /**
     * Add a node to the group of nodes in this object
     * 
     * @param node - the node to add
     */
    void add(Node node) {
        this.getChildren().add(node);
    }
}

/**
 * GameText
 */
class GameText extends GameObject {

    /**
     * The default font size.
     */
    private static final int DEFAULT_FONT_SIZE = 15;
    /**
     * The text object.
     */
    private Text text;
    private String FONT_OF_CHOICE = "Helvetica";

    /**
     * Constructor for the GameText object.
     * 
     * @param text - the text to display.
     * @param x    - the x-axis coordinate of the text.
     * @param y    - the y-axis coordinate of the text.
     */
    public GameText(String text) {
        this.text = new Text(text);
        this.text.setFont(
                Font.font(
                        FONT_OF_CHOICE,
                        FontWeight.BOLD,
                        DEFAULT_FONT_SIZE));
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.text.setFill(Color.WHITE);
        this.text.setScaleY(-1);
        // set origin to center of text
        this.text.setX(
                this.text.getX()
                        - this.text.getBoundsInLocal().getWidth() / 2);
        this.text.setY(
                this.text.getY()
                        + this.text.getBoundsInLocal().getHeight() / 4);
        add(this.text);
    }

    /**
     * Set the text to display.
     * 
     * @param text - the text to display.
     */
    public void setText(String text) {
        this.text.setText(text);
    }

    public void setLocation(double x, double y) {
        translate.setX(x);
        translate.setY(y);
    }

    /**
     * Return the text object.
     */
    public Text getText() {
        return text;
    }
}

/**
 * FixedObject class for all fixed objects in the game world
 * derived from this class.
 */
abstract class FixedObject extends GameObject {
    protected Scale scale;

    public FixedObject() {
        super();
        scale = new Scale();
        getTransforms().add(scale);
    }
}

/**
 * CloudsAndPonds class for the clouds and ponds in the game world.
 * all clouds and ponds are fixed objects in the game world.
 * all clouds and ponds are circles.
 */
abstract class PondsAndClouds extends FixedObject {

    // random class
    Random random = new Random();

    private double UPPER_2_3RDS_VERTICAL_SCREEN = random
            .nextDouble(
                    Globals.GAME_HEIGHT / 3);

    private double RANDOM_HORIZONTAL_SCREEN = random
            .nextDouble(
                    Globals.GAME_WIDTH);

    protected Circle circle;

    public PondsAndClouds() {
        super();
        circle = new Circle();
        // set translation
        translate.setX(RANDOM_HORIZONTAL_SCREEN);
        translate.setY(UPPER_2_3RDS_VERTICAL_SCREEN);
    }
}

/**
 * Clouds
 */
class Clouds extends PondsAndClouds {
    /**
     * The upper bound for the random radius of the cloud.
     */
    private static final double CLOUD_SIZE_UPPER_BOUND = 80.0;
    /**
     * The lower bound for the random radius of the cloud.
     */
    private static final double CLOUD_SIZE_LOWER_BOUND = 30.0;
    /**
     * The saturation level of the cloud.
     */
    private double cloudSaturationLevel = 0.0;

    public Clouds() {
        super(
                CLOUD_SIZE_LOWER_BOUND,
                CLOUD_SIZE_UPPER_BOUND,
                Color.WHITE,
                "100");
        percentageText.getText().setFill(Color.BLACK);
        cloudSaturationLevel = 0.0;
    }

    public double getCloudSaturationLevel() {
        return cloudSaturationLevel;
    }

    public void setCloudSaturationLevel(double cloudSaturationLevel) {
        this.cloudSaturationLevel = cloudSaturationLevel;
    }
}

/**
 * Pond class
 * 
 * Pond's percentage is of the normal radius of the pond
 * 
 * The rainfall must increase the area, not the radius: pi*r^2.
 */
class Pond extends PondsAndClouds {

    /**
     * The upper bound for the random radius of the pond.
     */
    private static final int POND_SIZE_UPPER_BOUND = 25;
    /**
     * The lower bound for the random radius of the pond.
     */
    private static final int POND_SIZE_LOWER_BOUND = 10;

    public Pond() {
        super(POND_SIZE_LOWER_BOUND,
                POND_SIZE_UPPER_BOUND,
                Color.BLUE,
                "0");
    }
}

/**
 * Game
 */
class Game extends Pane {

    public Game() {
        setScreenOriginBottomLeftCornerFlipYAxis();
    }

    /**
     * Flips the y-axis so that the origin is in the bottom
     * left corner of the screen
     */
    private void setScreenOriginBottomLeftCornerFlipYAxis() {
        super.setScaleY(-1);
    }

    public void update() {
    }

    public void render() {
    }

    public void start() {
    }

    /**
     * Initialize the game. This method is called when the game is started.
     * It will clear all nodes from the game world and add the game objects
     */
    public void init() {
        super.getChildren().clear();
        super.getChildren().addAll(new Pond(), new Clouds());
    }
}

/**
 * Globals
 */
class Globals {
    /**
     * The width of the game window.
     */
    public static final double GAME_WIDTH = 400;
    /**
     * The height of the game window.
     */
    public static final double GAME_HEIGHT = 800;
    /**
     * The lower 1/3rd of the screen height.
     */
    public static final double GAME_HEIGHT_1_3RD = GAME_HEIGHT / 3;
}

/**
 * Utility class for all utility methods that are used in the game.
 * Utility methods are static methods that are used by other classes in the
 * game, so they are not tied to any particular object.
 */
class Utility {

    // random class is static so it is shared by all instances of this class
    static Random random = new Random();

    /**
     * Generate a random number in the inclusive range [min, max].
     * 
     * @param min - the minimum value of the range
     * @param max - the maximum value of the range
     * @return a random number in the inclusive range [min, max]
     */
    public static double genRandNumInRange(double min, double max) {
        return min + ((max - min) + 1) * random.nextDouble();
    }

}

public class GameApp extends Application {
    private static final String GAME_TITLE = "Rainmaker A1";
    Game game = new Game(); // game object to be displayed in the window

    @Override
    public void start(Stage primaryStage) throws Exception {
        game.init();
        primaryStage.setTitle(GAME_TITLE);
        primaryStage.setScene(new Scene(
                game,
                Globals.GAME_WIDTH,
                Globals.GAME_HEIGHT,
                Color.BLACK));
        primaryStage.show();
        // start game
        // game.start();

        /**
         * Key listener for key pressed events.
         */
        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
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
                    game.init();
                }
            }
        });
    }

    public static void main() {
        Application.launch();
    }
}