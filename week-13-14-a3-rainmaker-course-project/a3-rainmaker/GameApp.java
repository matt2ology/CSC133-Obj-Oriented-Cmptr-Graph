import java.sql.Time;
import java.util.Timer;

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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Shadow;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
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
 * Steerable interface for objects that need to be steered.
 */
interface Steerable {

    public void steerLeft();

    public void steerRight();
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

/**
 * @brief MoveableObject is a GameObject that can move.
 */
abstract class MoveableObject extends GameObject {
    protected double speed;
    protected double heading;

    public MoveableObject(Point2D location) {
        super(location);
    }
}

class Helicopter extends MoveableObject {
    HeloBody body = new HeloBody();
    HeloBlade rotorMain = new HeloBlade();

    public Helicopter(Point2D location) {
        super(location);
        this.add(body);
        this.add(rotorMain);
    }

    @Override
    public String toString() {
        return "Helicopter: "
                + "Dimensions[ Width: "
                + this.getBoundsInLocal().getWidth()
                + ", Height: "
                + this.getBoundsInLocal().getHeight()
                + " ]";
    }

    public Helicopter getHelicopter() {
        return this;
    }
}

class HeloBlade extends Rectangle {
    private int bladeSpeed;

    public HeloBlade() {
        super(90, 4);
        this.bladeSpeed = 0;
        this.setFill(Color.GRAY);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
        // The origin of the rotation is the center of the rectangle.
        this.setX(-this.getWidth() / 2);
        this.setY(-this.getHeight() / 2);
        // Helicopter Blade is self animating with it's own animation timer.
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (bladeSpeed <= 120) {
                    bladeSpeed++;
                }
                HeloBlade.this.setRotate(HeloBlade.this.getRotate() + bladeSpeed);
            }
        };
        timer.start();
    }
}

class HeloBody extends Group {
    private Color bodyColor = Color.DARKORANGE;
    private static final int SKID_POSITION_X = 20;
    private static final int SKID_POSITION_Y = -35;
    private static final int SKID_CROSS_HEAD_POSITION_Y = -25;

    HeloLandingSkid skidLeft = new HeloLandingSkid(bodyColor);
    HeloLandingSkid skidRight = new HeloLandingSkid(bodyColor);
    HeloLandingSkidCross skidCrossHead = new HeloLandingSkidCross(bodyColor);
    HeloLandingSkidCross skidCrossTail = new HeloLandingSkidCross(bodyColor);
    HeloCabin cabin = new HeloCabin(bodyColor);
    HeloCockpit cockpit = new HeloCockpit();
    HeloEngineComponent engine = new HeloEngineComponent(bodyColor);
    HeloTailBoom tailBoom = new HeloTailBoom(bodyColor);

    public HeloBody() {
        super();
        // 5 is magic number offset
        skidLeft.setTranslateX(-SKID_POSITION_X - (5));
        skidLeft.setTranslateY(SKID_POSITION_Y);
        // 2 is a magic # offset
        skidRight.setTranslateX(SKID_POSITION_X + (2));
        skidRight.setTranslateY(SKID_POSITION_Y);
        skidCrossHead.setTranslateY(SKID_CROSS_HEAD_POSITION_Y);
        // 5 is magic number offset
        cabin.setTranslateY(SKID_CROSS_HEAD_POSITION_Y + (5));
        cockpit.setTranslateY(SKID_CROSS_HEAD_POSITION_Y);

        getChildren()
                .addAll(skidLeft,
                        skidRight,
                        skidCrossHead,
                        skidCrossTail,
                        cabin,
                        cockpit,
                        tailBoom,
                        engine);
    }
}

class HeloCabin extends Circle {
    public HeloCabin(Color componentColor) {
        super(18, componentColor);
    }
}

class HeloCockpit extends Arc {
    private LinearGradient cockpitGlass = new LinearGradient(
            0,
            0,
            0,
            1,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(1, Color.BLUE),
            new Stop(0, Color.LIGHTBLUE));

    public HeloCockpit() {
        super(
                0,
                0,
                13,
                13,
                0,
                180);
        setFill(cockpitGlass);
    }
}

class HeloLandingSkidCross extends Rectangle {
    public HeloLandingSkidCross(Color componentColor) {
        super(45, 3);
        setFill(componentColor);
        // center the cross on the skid cross point (0,0)
        setTranslateX(-getWidth() / 2);
    }
}

class HeloLandingSkid extends Rectangle {

    public HeloLandingSkid(Color componentColor) {
        super(3, 50);
        this.setFill(componentColor);
    }
}

class HeloEngineComponent extends Rectangle {
    public HeloEngineComponent(Color componentColor) {
        super(36, 12, componentColor);
        // set the origin to the center of the rectangle for rotation
        this.setX(-this.getWidth() / 2);
        this.setY(-this.getHeight() / 2);
    }

    // getter for the center of the engine component
    public Point2D getCenter() {
        return new Point2D(
                this.getX()
                        + (this.getWidth() / 2),
                this.getY()
                        + (this.getHeight() / 2));
    }
}

class HeloTailBoom extends Rectangle {
    public HeloTailBoom(Color componentColor) {
        super(5, 40, componentColor);
        this.setX(-this.getWidth() / 2);
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
        super.getChildren().addAll(
                new Helipad(
                        Globals.HELIPAD_COORDINATES,
                        new Dimension2D(100, 100)),
                new Helicopter(Globals.HELIPAD_COORDINATES));
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
     *        and lower 1/7th the height of the game world.
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
        game.init();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}