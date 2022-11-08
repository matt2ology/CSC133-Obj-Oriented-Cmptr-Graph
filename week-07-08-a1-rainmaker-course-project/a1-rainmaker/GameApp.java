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
 * Steerable interface for all objects that are steerable in the game world.
 */
interface Steerable {
    public void steerLeft();

    public void steerRight();
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

    public GameObject(Point2D location) {
        translate = new Translate(location.getX(), location.getY());
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
        super();
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
 * MovableObject class for all movable objects in the game world
 * are derived from this class.
 */
abstract class MovableObject extends GameObject {
    private double speed;
    private double velocityX;
    private double velocityY;

    /**
     * Abstract method for moving the object
     * because each object moves differently.
     */
    public abstract void move();

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public MovableObject() {
        super();
    }

    public MovableObject(Point2D location) {
        super(location);
        speed = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    /**
     * method that converts the rotation angle to a compass direction:
     * N, NW, W, SW, S, SE, E, NE
     * 
     * regardless of the rotation angle in both the positive and negative
     * 
     * @TODO (2022-11-07) FIX THIS METHOD REDUCE LOGIC
     * @return the compass direction
     */
    public String getCompassDirection() {
        String compassDirection = "";
        if (rotate.getAngle() >= 0) {
            if (rotate.getAngle() >= 0 && rotate.getAngle() < 22.5) {
                compassDirection = "N";
            } else if (rotate.getAngle() >= 22.5
                    && rotate.getAngle() < 67.5) {
                compassDirection = "NW";
            } else if (rotate.getAngle() >= 67.5
                    && rotate.getAngle() < 112.5) {
                compassDirection = "W";
            } else if (rotate.getAngle() >= 112.5
                    && rotate.getAngle() < 157.5) {
                compassDirection = "SW";
            } else if (rotate.getAngle() >= 157.5
                    && rotate.getAngle() < 202.5) {
                compassDirection = "S";
            } else if (rotate.getAngle() >= 202.5
                    && rotate.getAngle() < 247.5) {
                compassDirection = "SE";
            } else if (rotate.getAngle() >= 247.5
                    && rotate.getAngle() < 292.5) {
                compassDirection = "E";
            } else if (rotate.getAngle() >= 292.5
                    && rotate.getAngle() < 337.5) {
                compassDirection = "NE";
            } else if (rotate.getAngle() >= 337.5
                    && rotate.getAngle() < 360) {
                compassDirection = "N";
            }
        } else {
            if (rotate.getAngle() >= -22.5 && rotate.getAngle() < 0) {
                compassDirection = "N";
            } else if (rotate.getAngle() >= -67.5
                    && rotate.getAngle() < -22.5) {
                compassDirection = "NE";
            } else if (rotate.getAngle() >= -112.5
                    && rotate.getAngle() < -67.5) {
                compassDirection = "E";
            } else if (rotate.getAngle() >= -157.5
                    && rotate.getAngle() < -112.5) {
                compassDirection = "SE";
            } else if (rotate.getAngle() >= -202.5
                    && rotate.getAngle() < -157.5) {
                compassDirection = "S";
            } else if (rotate.getAngle() >= -247.5
                    && rotate.getAngle() < -202.5) {
                compassDirection = "SW";
            } else if (rotate.getAngle() >= -292.5
                    && rotate.getAngle() < -247.5) {
                compassDirection = "W";
            } else if (rotate.getAngle() >= -337.5
                    && rotate.getAngle() < -292.5) {
                compassDirection = "NW";
            } else if (rotate.getAngle() >= -360
                    && rotate.getAngle() < -337.5) {
                compassDirection = "N";
            }
        }
        return compassDirection;
    }
}

class Helicopter extends MovableObject implements Steerable {
    /**
     * Burn rate of fuel
     */
    private static final int FUEL_BURN_RATE = 5;
    private static final double MAX_SPEED = 10.0;
    private static final double MIN_SPEED = -2.0;
    private int fuelGauge;
    private boolean isIgnitionOn;
    private HelicopterBlipCircle blipCircle;
    private HelicopterGameInfoText gameInfoText;

    public Helicopter() {
        super();
        isIgnitionOn = false;
    }

    public Helicopter(int fuelCapacity) {
        super(Globals.HELIPAD_COORDINATES);
        isIgnitionOn = false;
        setSpeed(0);
        this.setFuelGauge(fuelCapacity);
        super.add(new HelicopterHeadingIndicator());
        super.add(blipCircle = new HelicopterBlipCircle());
        super.add(gameInfoText = new HelicopterGameInfoText(
                "Fuel:" + String.valueOf(getFuelGauge())));
        rotate.setAngle(0); // set initial rotation angle to 0 (north)
        rotate.setPivotX(blipCircle.getHelicopterBlipCircle().getCenterX());
        rotate.setPivotY(blipCircle.getHelicopterBlipCircle().getCenterY());
    }

    /**
     * Increase speed.
     */
    public void accelerate() {
        if (!isIgnitionOn) {
            return;
        } else if (getSpeed() < MAX_SPEED) {
            setSpeed(getSpeed() + 0.1);
        }
    }

    /**
     * Decrease speed.
     */
    public void decelerate() {
        if (!isIgnitionOn) {
            return;
        } else if (getSpeed() > MIN_SPEED) {
            setSpeed(getSpeed() - 0.1);
        }
    }

    /**
     * Break: full stop helicopter and hold position.
     */
    public void stopAndHover() {
        if (!isIgnitionOn) {
            return;
        } else {
            setSpeed(0);
        }
    }

    /**
     * Rotate the helicopter to the left (counter-clockwise)
     * Modulo 360 to keep the angle between 0 and 360
     */
    @Override
    public void steerLeft() {
        rotate.setAngle(
                (rotate.getAngle()
                        + Globals.HELICOPTER_ROTATION_DEGREE)
                        % 360);
    }

    /**
     * Rotate the helicopter to the right (clockwise)
     * Modulo 360 to keep the angle between 0 and 360
     */
    @Override
    public void steerRight() {
        rotate.setAngle(
                (rotate.getAngle()
                        - Globals.HELICOPTER_ROTATION_DEGREE)
                        % 360);
    }

    /**
     * Move the helicopter object to the corresponding location
     * based on the current speed and direction of the helicopter
     * 
     * @TODO (2022-11-07) Find a way to abstract the velocity
     *       calculation to the MovableObject class so that
     *       it can be reused by other classes that extend
     *       MovableObject class (e.g. EnemyHelicopter)
     * 
     *       This should simply move the helicopter object
     *       to the corresponding location and nothing more
     */
    @Override
    public void move() {
        // calculate the velocity
        Point2D heading = Utility
                .directionToVector(
                        rotate.getAngle(),
                        getSpeed());
        // update the location
        translate.setX(translate.getX() + heading.getX());
        translate.setY(translate.getY() + heading.getY());
    }

    @Override
    public void update() {
        if (!isIgnitionOn) {
            return;
        }
        move();
        setFuelGauge(getFuelGauge() - FUEL_BURN_RATE); // update the fuel gauge
        gameInfoText.setText("Fuel:" + String.valueOf(getFuelGauge()));
    }

    public void setFuelGauge(int fuelGauge) {
        this.fuelGauge = fuelGauge;
    }

    public int getFuelGauge() {
        return fuelGauge;
    }

    public boolean isIgnitionOn() {
        return isIgnitionOn;
    }

    public void toggleHelicopterIgnition() {
        this.isIgnitionOn = !isIgnitionOn;
    }

    /**
     * Return Helicopter object
     */
    public Helicopter getHelicopter() {
        return this;
    }

    @Override
    public String toString() {
        return "Helicopter ["
                + "fuelGauge="
                + fuelGauge
                + ", isIgnitionOn="
                + isIgnitionOn
                + ", getSpeed()="
                // gets the speed with 2 decimal places (e.g. 1.00)
                + String.format("%.2f", getSpeed())
                + ", Compass(Direction,Angle)=("
                + getCompassDirection()
                + ","
                + rotate.getAngle()
                + ")"
                + "]";
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

class Helipad extends FixedObject {
    Point2D location = new Point2D(0, 0);

    public Helipad(Point2D location) {
        /**
         * Call the constructor of the parent class, FixedObject,
         * to set the location of the helipad.
         */
        super(location);
        this.location = location;
        add(new HelipadFATOSquare()); // The Final Approach and Takeoff (FATO)
        add(new HelipadTLOFCircle()); // The Touchdown and Liftoff (TLOF)
        add(new HelipadH()); // The Helipad "H" font
    }

    @Override
    public String toString() {
        return "Helipad [location=" + location + "]";
    }
}

/**
 * FATOSquare - represents the Final Approach and Takeoff (FATO) square of the
 * helipad.
 */
class HelipadFATOSquare extends FixedObject {
    /**
     * Helipad FATO square dimension.
     */
    private static final int SQUARE_DIMENSION = 100;
    /**
     * Helipad FATO square x and y offset to center the square.
     * 
     * Multiply by -1 to make the offset negative to center
     * the square on the origin.
     * 
     * we need -1 because the square is scaled by -1 to make
     * it appear upright on the screen.
     */
    private static final int SQUARE_CENTER_ORIGIN = -(SQUARE_DIMENSION / 2);

    public HelipadFATOSquare() {
        Rectangle square = new Rectangle(
                SQUARE_CENTER_ORIGIN, // x-offset so to center the square
                SQUARE_CENTER_ORIGIN, // y-offset so to center the square
                SQUARE_DIMENSION, // width of square
                SQUARE_DIMENSION); // height of square
        square.setStroke(Color.YELLOW);
        square.setFill(Color.TRANSPARENT);
        add(square);
    } // end constructor

    /**
     * getFATO_SQUARE_DIMENSION - returns the dimension of
     * the FATO square of the helipad.
     * 
     * @return the dimension of the FATO square of the helipad
     */
    public static int getSquareDimension() {
        return SQUARE_DIMENSION;
    }
}

/**
 * TLOFCircle - Represents the Touchdown and Liftoff (TLOF) circle of the
 * helipad.
 * 
 * The Circle's diameter is based on the helipad's FATO square dimension.
 */
class HelipadTLOFCircle extends FixedObject {
    /**
     * The gap between the circle and the square edge
     */
    private static final int PADDING = 5;
    /**
     * Represents both the x and y radius of the Touchdown
     * and Liftoff (TLOF) circle.
     * 
     * The radius is half the width of the FATO square with a gap, so that the
     * edge of the circle and the side of the square are not touching.
     * 
     * The radius is scaled by the FATO square dimension and the padding.
     */
    private static final int RADIUS = (HelipadFATOSquare
            .getSquareDimension() / 2)
            - PADDING;

    /**
     * Constructor for the Helipad TLOF circle.
     * 
     * @param center - the center of the circle in the
     *               x and y direction of the game
     * @param RADIUS - the radius of the circle
     */
    public HelipadTLOFCircle() {
        Circle circle = new Circle(RADIUS);
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(2);
        circle.setFill(Color.TRANSPARENT);
        super.add(circle);
    } // end constructor
}

/**
 * A class to contain the letter "H" in the helipad. It extends the Fixed class
 * so that it can be added to the game world. It uses the JavaFX Text class to
 * create the letter "H".
 * 
 * The Letter "H" is centered on the helipad.
 */
class HelipadH extends FixedObject {
    /**
     * The helipad "H" font size.
     */
    private static final int HELIPAD_H_FONT_SIZE = 75;

    /**
     * add Text "H" to the center of the helipad by getting the text width
     * and height, computing the center of the helipad, and then
     * subtracting half of the text width and height from the center of the
     * helipad to get the top left corner of the text.
     */
    public HelipadH() {
        GameText text = new GameText("H", HELIPAD_H_FONT_SIZE);
        text.setFontSize(HELIPAD_H_FONT_SIZE);
        super.add(text);
    } // end constructor
}

/**
 * CloudsAndPonds class for the clouds and ponds in the game world.
 * all clouds and ponds are fixed objects in the game world.
 * all clouds and ponds are circles.
 */
abstract class PondsAndClouds extends FixedObject {
    /**
     * The percentage symbol for the clouds or ponds text.
     */
    private static final String PERCENT_SYMBOL = "%";
    /**
     * Circles represents clouds or ponds.
     */
    protected Circle circle;
    /**
     * The percentage text for the clouds or ponds.
     */
    protected GameText percentageText;
    /**
     * Random screen x-axis coordinate for the cloud or pond.
     */
    private double RANDOM_SCREEN_X_COORDINATE = Utility.random
            .nextDouble(Globals.GAME_WIDTH);
    /**
     * Random upper 2/3rds of the screen in the y-axis
     * coordinate for the cloud or pond.
     */
    private double RANDOM_SCREEN_Y_COORDINATE_UPPER_2_3RDS = Utility
            .genRandNumInRange(Globals.GAME_HEIGHT_1_3RD, Globals.GAME_HEIGHT);

    /**
     * Constructor for the clouds and ponds. The circle
     * represents a cloud or pond set to a random radius
     * defined by the derived class.
     * 
     * @param circleMinimumRadius - the minimum radius of the cloud or pond.
     * @param circleMaximumRadius - the maximum radius of the cloud or pond.
     * @param fill                - the color of the cloud or pond.
     */
    public PondsAndClouds(double circleMinimumRadius,
            double circleMaximumRadius,
            Color fill,
            String percentageNumber) {
        super();
        circle = new Circle(
                Utility.genRandNumInRange(
                        circleMinimumRadius,
                        circleMaximumRadius));
        circle.setFill(fill);
        add(circle);
        percentageText = new GameText(
                percentageNumber + PERCENT_SYMBOL);
        add(percentageText);
        translate.setX(RANDOM_SCREEN_X_COORDINATE);
        translate.setY(RANDOM_SCREEN_Y_COORDINATE_UPPER_2_3RDS);
    }

    /**
     * Return circle attributes
     */
    public Circle getCircle() {
        return circle;
    }
}

/**
 * Clouds
 * 
 * Cloud's percentage text is its saturation level.
 * 
 * As the cloud becomes more saturated the color turns from
 * WHITE [rgb(255,255,255)] to GRAY [rgb(155,155,155)]
 * (100 shades of gray)
 * 
 * When the saturation reaches 30% the rainfall will start to fill the pond at
 * a rate proportional to the cloud’s saturation.
 * 
 * The cloud will automatically lose saturation when it’s not being seeded at
 * a rate that allows the percentage to drop about 1%/second
 */
class Cloud extends PondsAndClouds {
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

    public Cloud() {
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

    /**
     * Return Cloud object
     */
    public Cloud getCloud() {
        return this;
    }

    @Override
    public String toString() {
        return "Clouds [cloudSaturationLevel=" + cloudSaturationLevel + "]";
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

    @Override
    public String toString() {
        return "Pond [percentageText=" + percentageText + "]";
    }
}

/**
 * Game
 */
class Game extends Pane {
    /**
     * The initial fuel value is set for playability
     */
    private static final int INITIAL_FUEL = 25000;
    /**
     * Initialize Cloud object in the game world so that
     * we can access it later like this: game.getCloud()
     * And determine if the helicopter is over the cloud
     */
    private Cloud cloud;
    /**
     * Initialize Helicopter object in the game world.
     * This is called composition because the helicopter
     * is a part of the game world and not a subclass of it (inheritance)
     */
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
     * 
     * @TODO - add the pond and cloud for update
     */
    public void update() {
        // update the helicopter
        helicopter.update();
        // update the cloud
        // cloud.update();
        // update the pond
        // pond.update();
    }

    public void play() {
        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                System.err.println(helicopter.toString());
                System.err.println(isHelicopterInCloud());
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
                new Pond(),
                cloud = new Cloud(),
                new Helipad(Globals.HELIPAD_COORDINATES),
                helicopter = new Helicopter(INITIAL_FUEL));
        // print out each object in the game world
        super.getChildren().forEach(System.out::println);
    }

    /**
     * Get the helicopter object in the game world for the game.
     * 
     * @return the helicopter object in the game world.
     */
    public Helicopter getHelicopter() {
        return helicopter;
    }

    /**
     * Method to determine if the helicopter is in the cloud.
     */
    public boolean isHelicopterInCloud() {
        return cloud.getCloud().getBoundsInParent().intersects(
                helicopter.getHelicopter().getBoundsInParent());
    }
}

/**
 * Globals class contains all the global variables used in the game.
 * We have this class so that we can easily change the game window
 * size and other global variables.
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
    /**
     * The x-axis coordinate of the center of the game window.
     */
    public static final double GAME_WIDTH_1_HALF = GAME_WIDTH / 2;
    /**
     * 1/8th of the game height along the y-axis coordinate.
     */
    public static final double GAME_HEIGHT_1_8TH = GAME_HEIGHT / 8;
    /**
     * The coordinates to set the center of the Helipad.
     * The Helipad's y-coordinate is set along the bottom
     * 1/8th of the game window height and x-coordinate is
     * set to the center 1/2 of the game window.
     */
    public static Point2D HELIPAD_COORDINATES = new Point2D(
            GAME_WIDTH_1_HALF,
            GAME_HEIGHT_1_8TH);
    /**
     * Helicopter's degree of incremental rotation.
     */
    public static final double HELICOPTER_ROTATION_DEGREE = 15;
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

    /**
     * Convert a direction and speed to a vector.
     * The direction is in degrees and the speed is in pixels per second.
     * 
     * @param angleDegree - the direction in degrees
     * @param speed       - the speed in pixels per second
     * @return a vector in the form of a Point2D object
     * 
     * @TODO - Consider if this should be placed in Moveable class
     */
    public static Point2D directionToVector(double angleDegree, double speed) {
        double angleRadian = Math.toRadians(angleDegree);
        /**
         * sin will calculate the x-component of the vector
         * cos will calculate the y-component of the vector
         * -cos is used because the y-axis is flipped
         */
        Point2D heading = new Point2D(
                // sin because x-axis is not flipped
                -speed * Math.sin(angleRadian),
                // -cos because y-axis is flipped
                speed * Math.cos(angleRadian));
        return heading;
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
                if (event.getCode() == KeyCode.LEFT
                        || event.getCode() == KeyCode.A) {
                    game.getHelicopter().steerLeft();
                }

                // Right Arrow Changes heading of the helicopter to the right.
                if (event.getCode() == KeyCode.RIGHT
                        || event.getCode() == KeyCode.D) {
                    game.getHelicopter().steerRight();
                }

                // Up Arrow Increases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.UP
                        || event.getCode() == KeyCode.W) {
                    game.getHelicopter().accelerate();
                }

                // Down Arrow Decreases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.DOWN
                        || event.getCode() == KeyCode.S) {
                    System.err.println("Down Arrow: v");
                    game.getHelicopter().decelerate();
                }

                // 'i' Turns on the helicopter ignition.
                if (event.getCode() == KeyCode.I) {
                    game.getHelicopter().toggleHelicopterIgnition();
                    System.err.println("I - Toggles the helicopter ignition: "
                            + game.getHelicopter().isIgnitionOn());

                }
                // 'h' Stops the helicopter from moving.
                if (event.getCode() == KeyCode.H) {
                    game.getHelicopter().stopAndHover();
                    System.err.println(
                            "H - Stops the helicopter from moving");

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

    public static void main() {
        Application.launch();
    }
}