import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
 * Steerable interface for all objects that are steerable in the game world.
 */
interface Steerable {
    public void steerLeft();

    public void steerRight();
}

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

    public GameObject() {
        this.translate = new Translate();
        this.rotate = new Rotate();
        this.getTransforms().addAll(this.translate, this.rotate);
    }

    public GameObject(Point2D location) {
        this.translate = new Translate(location.getX(), location.getY());
        this.rotate = new Rotate();
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
    /**
     * The font of choice Helvetica because the tall x-height
     * makes this font easier to read at a distance.
     */
    private String FONT_OF_CHOICE = "Helvetica";

    /**
     * Constructor for the GameText object.
     * 
     * @param text - the text to display.
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
        this.text.setX(getTextCenterX());
        this.text.setY(getTextCenterY());
        add(this.text);
    }

    /**
     * Constructor for the GameText object that takes text and font size.
     * 
     * @param text         - the text to display.
     * @param textFontSize - the font size of the text.
     */
    public GameText(String text, int textFontSize) {
        this.text = new Text(text);
        this.text.setFont(
                Font.font(
                        FONT_OF_CHOICE,
                        FontWeight.BOLD,
                        textFontSize));
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.text.setFill(Color.WHITE);
        this.text.setScaleY(-1);
        // set origin to center of text
        this.text.setX(getTextCenterX());
        this.text.setY(getTextCenterY());
        add(this.text);
    }

    /**
     * The center y-axis coordinate is obtained by adding the height of the
     * text to the y-axis coordinate of the text and dividing by four.
     * Divided by four because the text is scaled by -1, so the y-axis
     * coordinate is negative.
     * 
     * @return the center y-axis coordinate of the text.
     */
    private double getTextCenterY() {
        return this.text.getY() + this.text.getBoundsInLocal().getHeight() / 3;
    }

    /**
     * The center x-axis coordinate is obtained by subtracting
     * the width of the text from the x-axis coordinate of the
     * text and dividing by half.
     * 
     * @return the center x-axis coordinate of the text.
     */
    private double getTextCenterX() {
        return this.text.getX() - this.text.getBoundsInLocal().getWidth() / 2;
    }

    /**
     * Set the text to display.
     * 
     * @param text - the text to display.
     */
    public void setText(String text) {
        this.text.setText(text);
    }

    /**
     * Set the font size of the text.
     * 
     * @param size - the font size of the text.
     */
    public void setFontSize(int fontSize) {
        this.text.setFont(
                Font.font(
                        FONT_OF_CHOICE,
                        FontWeight.BOLD,
                        fontSize));
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
        scale = new Scale();
        getTransforms().add(scale);
    }

    public FixedObject(Point2D location) {
        super(location);
        scale = new Scale();
        getTransforms().add(scale);
    }
}

/**
 * MoveableObject is a GameObject that can move.
 */
abstract class MoveableObject extends GameObject {
    private double speed; // speed of travel (scalar)
    private Point2D headingVector; // direction of travel (unit vector)
    private Point2D velocityVector; // speed and direction of travel (vector)

    abstract public void move(); // Not all MoveableObjects move the same way.

    public MoveableObject(Point2D location) {
        super(location);
        this.headingVector = new Point2D(0, 0); // set heading to north
        this.rotate.setAngle(0); // set rotation angle to 0 (north)
        this.speed = 0.0;
        this.velocityVector = new Point2D(0.0, 0.0);
    }

    /**
     * @brief calculates the heading vector components
     *        (x and y) from the rotation angle its direction.
     */
    private void calculateHeadingDirection() {
        // normalize angle to 0-359 degrees
        double normalizedAngle = this.rotate.getAngle() % 360;
        this.headingVector = new Point2D(
                // -sin(angle) because y-axis is inverted
                -Math.sin(Math.toRadians(normalizedAngle)),
                Math.cos(Math.toRadians(normalizedAngle)));
    }

    /**
     * @brief calculates the velocity in x and y components
     *        from the speed and heading direction.
     */
    private void calculateVelocity() {
        this.velocityVector = this.headingVector.multiply(getSpeed());
    }

    @Override
    public void update() {
        calculateHeadingDirection();
        calculateVelocity();
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Point2D getHeadingVector() {
        return this.headingVector;
    }

    public Point2D getVelocityVector() {
        return this.velocityVector;
    }
}

/**
 * @brief Helicopter class is a MoveableObject that can be steered.
 */
class Helicopter extends MoveableObject implements Steerable {
    private boolean isIgnitionOn;
    private int fuelGauge;
    private static final double MAX_SPEED = 10.0;
    private static final double MIN_SPEED = 0.0;
    private static final double SPEED_INCREMENT = (1.0/10.0);
    private static final double SPEED_BACKWARDS = -2;
    private static final double STEERING_ANGLE_INCREMENT = 15.0;
    private static final int FUEL_BURN_RATE = 5;
    private HelicopterBlipCircle blipCircle;
    private HelicopterGameInfoText gameInfoText;

    public Helicopter(Point2D location, int fuelCapacity) {
        super(location);
        this.isIgnitionOn = false;
        this.setSpeed(0.0);
        setFuelGauge(fuelCapacity);
        super.add(new HelicopterHeadingIndicator());
        super.add(blipCircle = new HelicopterBlipCircle());
        super.add(gameInfoText = new HelicopterGameInfoText(
                "Fuel:" + String.valueOf(getFuelGauge())));
        rotate.setPivotX(blipCircle.getHelicopterBlipCircle().getCenterX());
        rotate.setPivotY(blipCircle.getHelicopterBlipCircle().getCenterY());
    }

    /**
     * @brief increases the speed of the helicopter - clamp speed to max speed
     *        if necessary (speed cannot be greater than max speed)
     */
    public void increaseSpeed() {
        boolean isBelowMaxSpeed = Math.min(
                this.getSpeed(),
                MAX_SPEED) < MAX_SPEED;
        if (isBelowMaxSpeed) {
            this.setSpeed(this.getSpeed() + SPEED_INCREMENT);
        }
    }

    /**
     * @brief decreases the speed of the helicopter - clamp speed to min speed
     *        if necessary (speed cannot be less than min speed)
     */
    public void decreaseSpeed() {
        boolean isAboveMinSpeed = Math.max(MIN_SPEED,
                this.getSpeed()) > MIN_SPEED;
        if (isAboveMinSpeed) {
            this.setSpeed(this.getSpeed() - SPEED_BACKWARDS);
        }
    }

    @Override
    public void move() {
        this.translate.setX(
                this.translate.getX() + this.getVelocityVector().getX());
        this.translate.setY(
                this.translate.getY() + this.getVelocityVector().getY());
    }

    @Override
    public void steerLeft() {
        this.rotate.setAngle(
                (this.rotate.getAngle() + STEERING_ANGLE_INCREMENT) % 360);
    }

    @Override
    public void steerRight() {
        this.rotate.setAngle(
                (this.rotate.getAngle() - STEERING_ANGLE_INCREMENT) % 360);
    }

    @Override
    public void update() {
        if (!isIgnitionOn) {
            return;
        }
        move();
        // burn fuel at a rate second and wont go below zero
        setFuelGauge(Math.max(0, getFuelGauge() - FUEL_BURN_RATE));
    }

    public int getFuelGauge() {
        return fuelGauge;
    }

    public void setFuelGauge(int fuel) {
        this.fuelGauge = fuel;
    }

    public boolean isIgnitionOn() {
        return isIgnitionOn;
    }

    public void toggleIgnition() {
        this.isIgnitionOn = !isIgnitionOn;
    }

    @Override
    public String toString() {
        return "Helicopter{" +
                "isIgnitionOn=" + isIgnitionOn +
                ", fuelGauge=" + fuelGauge +
                ", speed=" + getSpeed() +
                ", headingVector=" + getHeadingVector() +
                ", velocityVector=" + getVelocityVector() +
                '}';
    }

}

class HelicopterGameInfoText extends GameText {
    public HelicopterGameInfoText(String text) {
        super(text, 15);
        this.getText().setFill(Color.RED);
        this.setTranslateY(-20);
    }
}

class HelicopterHeadingIndicator extends FixedObject {
    /**
     * The radius of the helicopter heading indicator on the map (in-game)
     */
    private static Dimension2D headingIndicator = new Dimension2D(
            5,
            30);

    public HelicopterHeadingIndicator() {
        super();
        Rectangle rectangle = new Rectangle(
                headingIndicator.getWidth(),
                headingIndicator.getHeight());
        rectangle.setFill(Color.YELLOW);
        // set origin to center of rectangle
        rectangle.setX(-headingIndicator.getWidth() / 2);
        super.add(rectangle);
    }

    public Dimension2D getHeadingIndicatorDimensions() {
        return headingIndicator;
    }
}

/**
 * HelicopterBlip class for the helicopter blip on the map.
 */
class HelicopterBlipCircle extends FixedObject {
    private Circle blipCircle;
    /**
     * The radius of the helicopter blip on the map (in-game)
     */
    private static final double blibCircleRadius = 10;

    public HelicopterBlipCircle() {
        blipCircle = new Circle(blibCircleRadius);
        blipCircle.setFill(Color.YELLOW);
        super.add(blipCircle);
    }

    public Circle getHelicopterBlipCircle() {
        return blipCircle;
    }
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
     * The initial fuel value is set for playability
     */
    private static final int INITIAL_FUEL = 25000;
    /**
     * Initialize Helicopter object in the game world.
     * This is called composition because the helicopter
     * is a part of the game world and not a subclass of it (inheritance)
     */
    private Helicopter helicopter;

    public Game() {
        // Flip the y-axis so that up is positive and down is negative
        super.setScaleY(-1);
    }

    public void init() {
        super.getChildren().clear();
        super.getChildren().addAll(
                helicopter = new Helicopter(
                        new Point2D(Globals.GAME_APP_DIMENSIONS.getWidth() / 2,
                                Globals.GAME_APP_DIMENSIONS.getHeight() / 2),
                        INITIAL_FUEL));
        // print out each object in the game world
        super.getChildren().forEach(System.out::println);
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

    public void update() {
        helicopter.update();
    }

    public Helicopter getHelicopter() {
        return helicopter;
    }

    @Override
    public String toString() {
        return "Game{" +
                "helicopter=" + helicopter +
                '}';
    }
}

/**
 * Utility
 */
class Utility {
    /**
     * @brief Utility to normalize angle to 0-360 degrees range (0-2pi radians)
     * @param angle in degrees
     * @return normalized angle in degrees
     */
    public static double normalizeAngle(double angle) {
        return ((angle) % 360);
    }

}

/**
 * @brief Globals class provides a place to store global constants.
 */
class Globals {
    public static final String GAME_TITLE = "Rainmaker A2";
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
    Game game = new Game(); // the game model (state)

    @Override
    public void start(Stage primaryStage) throws Exception {
        game.init();
        primaryStage.setScene(new Scene(
                game,
                Globals.GAME_APP_DIMENSIONS.getWidth(),
                Globals.GAME_APP_DIMENSIONS.getHeight(),
                Color.TAN));
        primaryStage.setTitle(Globals.GAME_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
        // start game
        game.play();

        /**
         * Key listener for key pressed events.
         */
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
                    // get helicopter in the game world according to their type (class)
                }

                // Up Arrow Increases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.UP) {
                    game.getHelicopter().increaseSpeed();
                }

                // Down Arrow Decreases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.DOWN) {
                    System.err.println("Down Arrow: v");
                    game.getHelicopter().decreaseSpeed();
                }

                // 'i' Turns on the helicopter ignition.
                if (event.getCode() == KeyCode.I) {
                    game.getHelicopter().toggleIgnition();
                    System.err.println("I - Toggles the helicopter ignition: "
                            + game.getHelicopter().isIgnitionOn());

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