import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.scene.shape.Arc;
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

    abstract protected void move(); // all objects moves are differently

    public MoveableObject(Point2D location) {
        super(location);
        this.speed = 0.0;
        this.rotate.setAngle(0);
    }

    /**
     * Converts a direction in degrees (0...360) to x and y coordinates.
     * 
     * @return A Point2D object with x and y coordinates of the vector.
     */
    protected Point2D directionToVector() {
        double radians = Math.toRadians(this.rotate.getAngle());
        // y is negative because y axis is inverted
        return new Point2D(
                (this.speed * Math.sin(radians)),
                -(this.speed * Math.cos(radians)));
    }

    protected void setNormalizedAngle(double compassAngle) {
        if (compassAngle < 0) {
            compassAngle += 360;
        } else if (compassAngle >= 360) {
            compassAngle -= 360;
        }
        this.rotate.setAngle(compassAngle);
    }

    @Override
    public void update() {
        super.update();
    }
}

class Helicopter extends MoveableObject implements Steerable {
    private boolean isIgnitionOn;
    private double HOVER_SPEED_0 = 0;
    private double MAX_FORWARD_SPEED = 10.0;
    private double MAX_REVERSE_SPEED = 2.0;
    private double SPEED_STEP_VALUE = 0.5;
    private double STEERING_ANGLE_INCREMENT = 5;
    private int FUEL_BURN_RATE = 5;
    private int fuelGauge;

    private HelicopterGameInfoText fuelText;

    public Helicopter(Point2D location, int fuelCapacity) {
        super(location);
        this.isIgnitionOn = false;
        this.setFuelGauge(fuelCapacity);
        this.add(new HeloBody());
        this.add(new HeloBlade());
        this.add(fuelText = new HelicopterGameInfoText(
                "Fuel:" + String.valueOf(getFuelGauge())));
    }

    /**
     * @brief increaseSpeed of the helicopter by specified step value
     *        above 0 speed (hover) and below MAX_SPEED. If the helicopter
     *        is in reverse increasing speed will put it in forward.
     */
    public void increaseSpeed() {
        this.speed = Math.min(
                Math.max(HOVER_SPEED_0, this.speed + SPEED_STEP_VALUE),
                MAX_FORWARD_SPEED);
    }

    /**
     * @brief decreaseSpeed of the helicopter by specified step value
     *        above 0 (hover). Decreasing the speed below 0 (hover) will
     *        put the helicopter in reverse by the minimum speed.
     */
    public void decreaseSpeed() {
        this.speed = (this.speed <= HOVER_SPEED_0) // Are we in reverse?
                ? -MAX_REVERSE_SPEED // Put in reverse from 0 speed (hover)
                // decrease speed by step value but not 0 (hover)
                : Math.max(HOVER_SPEED_0, (this.speed - SPEED_STEP_VALUE));
    }

    public void toggleIgnition() {
        this.isIgnitionOn = !isIgnitionOn;
    }

    public int getFuelGauge() {
        return fuelGauge;
    }

    private void setFuelGauge(int fuel) {
        this.fuelGauge = fuel;
    }

    @Override
    public void steerLeft() {
        setNormalizedAngle(this.rotate.getAngle() - STEERING_ANGLE_INCREMENT);
    }

    @Override
    public void steerRight() {
        setNormalizedAngle(this.rotate.getAngle() + STEERING_ANGLE_INCREMENT);
    }

    @Override
    public void move() {
        this.translate.setX(
                this.translate.getX() + this.directionToVector().getX());
        this.translate.setY(
                this.translate.getY() + this.directionToVector().getY());
    }

    @Override
    public void update() {
        if (!isIgnitionOn) {
            return;
        }
        this.move();
        this.setFuelGauge(Math.max(0, getFuelGauge() - FUEL_BURN_RATE));
        this.fuelText.setText("Fuel:" + String.valueOf(getFuelGauge()));
    }

    @Override
    public String toString() {
        return "Helicopter: "
                + "isIgnitionOn: " + this.isIgnitionOn
                + ", Angle: " + this.rotate.getAngle()
                + ", Speed: " + this.speed
                + ", Fuel Gauge: " + this.getFuelGauge();
    }
}

class HelicopterGameInfoText extends GameText {
    private Color FONT_COLOR = Color.RED;
    private static int FONT_SIZE = 15;

    public HelicopterGameInfoText(String text) {
        super(text, FONT_SIZE);
        this.setFill(FONT_COLOR);
        this.setTranslateY(50);
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
                HeloBlade.this.setRotate(
                        HeloBlade.this.getRotate() + bladeSpeed);
            }
        };
        timer.start();
    }
}

class HeloBody extends Group {
    private Color bodyColor = Color.DARKORANGE;
    private Color skidColor = Color.GRAY;
    private static final int SKID_POSITION_X = 20;
    private static final int SKID_POSITION_Y = -35;
    private static final int SKID_CROSS_HEAD_POSITION_Y = -25;

    HeloLandingSkid skidLeft = new HeloLandingSkid(skidColor);
    HeloLandingSkid skidRight = new HeloLandingSkid(skidColor);
    HeloLandingSkidCross skidCrossHead = new HeloLandingSkidCross(bodyColor);
    HeloLandingSkidCross skidCrossTail = new HeloLandingSkidCross(bodyColor);
    HeloCabin cabin = new HeloCabin(bodyColor);
    HeloCockpit cockpit = new HeloCockpit();
    HeloEngineComponent engine = new HeloEngineComponent(bodyColor);
    HeloTailBoom tailBoom = new HeloTailBoom(bodyColor);

    public HeloBody() {
        super();
        // magic number offset to center w/ skid cross attach point
        skidLeft.setTranslateX((-SKID_POSITION_X) - (5));
        skidLeft.setTranslateY(SKID_POSITION_Y);
        // magic number offset to center w/ skid cross attach point
        skidRight.setTranslateX(SKID_POSITION_X + (2));
        skidRight.setTranslateY(SKID_POSITION_Y);
        skidCrossHead.setTranslateY(SKID_CROSS_HEAD_POSITION_Y);
        // magic number offset for the cabin from the forward, head, skid cross
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
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
    }
}

class HeloEngineComponent extends Rectangle {
    public HeloEngineComponent(Color componentColor) {
        super(36, 12, componentColor);
        // set the origin to the center of the rectangle for rotation
        this.setX(-this.getWidth() / 2);
        this.setY(-this.getHeight() / 2);
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
    /**
     * The initial fuel value is set for playability
     */
    private static final int HELICOPTER_INITIAL_FUEL_CAPACITY = 25000;
    private Helicopter helicopter;

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
        helicopter.update();
    }

    public void play() {
        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                System.err.println(helicopter.toString());
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
                helicopter = new Helicopter(
                        Globals.HELIPAD_COORDINATES,
                        HELICOPTER_INITIAL_FUEL_CAPACITY));
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

    /**
     * @brief Sets the helicopter for the game.
     */
    public Helicopter getHelicopter() {
        return helicopter;
    }

    @Override
    public String toString() {
        return "Game{" + "helicopter=" + this.helicopter + '}';
    }
}

/**
 * @brief Globals class provides a place to store all global/static constants.
 */
class Globals {
    public static final String GAME_TITLE = "Rainmaker A3";
    public static final Dimension2D GAME_APP_DIMENSIONS = new Dimension2D(
            800,
            800);
    public static final Image GAME_MAP_IMAGE = new Image(
            "textures/map/rainmaker_a3_map_dry_desert.png");
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
        game.play();

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // Left Arrow Changes heading of the helicopter to the left.
                if (event.getCode() == KeyCode.LEFT) {
                    game.getHelicopter().steerLeft();
                }

                // Right Arrow Changes heading of the helicopter to the right.
                if (event.getCode() == KeyCode.RIGHT) {
                    game.getHelicopter().steerRight();
                }

                // Up Arrow Increases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.UP) {
                    game.getHelicopter().increaseSpeed();
                }

                // Down Arrow Decreases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.DOWN) {
                    game.getHelicopter().decreaseSpeed();
                }

                // 'i' Turns on the helicopter ignition.
                if (event.getCode() == KeyCode.I) {
                    game.getHelicopter().toggleIgnition();
                }

                // 'b' [optional] shows bounding boxes around objects.
                if (event.getCode() == KeyCode.B) {
                    System.err.println(
                            "B - shows bounding boxes around objects");
                }

                // 'r' Reinitialize the game
                if (event.getCode() == KeyCode.R) {
                    System.err.println();
                    System.err.println("R - Reinitialize the game");
                    game.init();
                }
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}