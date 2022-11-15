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

    public GameObject setPosition(Point2D coordinates) {
        this.translate.setX(coordinates.getX());
        this.translate.setY(coordinates.getY());
        return this;
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
 * MoveableObject is a GameObject that can move.
 */
abstract class MoveableObject extends GameObject {
    private double speed; // speed of travel (scalar)
    private Point2D headingVector; // direction of travel (unit vector)
    private Point2D velocityVector; // speed and direction of travel (vector)

    abstract public void move(); // Not all MoveableObjects move the same way.

    public MoveableObject() {
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
    public Helicopter(Point2D location, int fuelCapacity) {
        super(location);

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
        boolean isAboveMinSpeed = Math.max(
                this.getSpeed(),
                MIN_SPEED) > MIN_SPEED;
        if (isAboveMinSpeed) {
            this.setSpeed(this.getSpeed() - SPEED_BACKWARDS);
        }
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
}

    public boolean isIgnitionOn() {
        return isIgnitionOn;
    }

    public void toggleIgnition() {
        this.isIgnitionOn = !isIgnitionOn;
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
    public Game() {
        // Flip the y-axis so that up is positive and down is negative
        super.setScaleY(-1);
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
        Game game = new Game();

        primaryStage.setScene(new Scene(
                game,
                Globals.GAME_APP_DIMENSIONS.getWidth(),
                Globals.GAME_APP_DIMENSIONS.getHeight(),
                Color.TAN));
        primaryStage.setTitle(Globals.GAME_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}