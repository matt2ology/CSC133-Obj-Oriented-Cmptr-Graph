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
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

interface Updatable {
    void update();
}

class GameText extends GameObject {
    Text text;

    public GameText(String textString) {
        text = new Text(textString);
        text.setScaleY(-1);
        text.setFont(Font.font(25));
        add(text);
    }

    public GameText() {
        this("");
    }

    public void setText(String textString) {
        text.setText(textString);
    }
}

abstract class GameObject extends Group {
    /**
     * translation is protected so that subclasses can
     * access it directly (for efficiency)
     */
    protected Translate translation;
    /**
     * rotation allows for rotation around a point other than the origin (0,0)
     * of the object (see setRotationPivot). Its scoped as protected so that
     * subclasses can access it directly (for efficiency)
     */
    protected Rotate rotation;
    protected Color coloration; // color of the object (if applicable)
    protected Dimension2D dimension; // size of the object (if applicable)
    protected Point2D coordinates; // location of the object (if applicable)

    /**
     * Constructor that sets the object's position to (0,0) and rotation to 0.
     */
    public GameObject() {
        translation = new Translate();
        rotation = new Rotate();
        this.getTransforms().addAll(translation, rotation);
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

    // gets the local bounds of the object and draws a red rectangle around it
    void drawBounds() {
        Rectangle bounds = new Rectangle(this.getBoundsInLocal().getWidth(), this.getBoundsInLocal().getHeight());
        bounds.setFill(Color.TRANSPARENT);
        bounds.setStroke(Color.RED);
        bounds.setStrokeWidth(6);
        this.add(bounds);
    }

}

abstract class Fixed extends GameObject {
    public Fixed() {
        super();
    }
}

abstract class Moveable extends GameObject implements Updatable {
    protected int speed;
    protected int heading;

    public Moveable() {
        super();
    }

    /**
     * Method that must be implemented by subclasses will have
     * different ways of moving, the move() method is abstract.
     */
    public abstract void move();

    public int getSpeed() {
        return speed;
    }

    public int getHeadingAngle() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }
}

class Pond {

}

class Cloud {

}

/**
 * Helipad - represents the starting and ending location
 * of this first game.
 * 
 * 1. The helicopter will take off from the helipad and after seeding all
 * of the clouds will have to land back on the helipad in order to end the
 * game.
 * 
 * 2. A helicopter is landed on the helipad whenever it is contained within
 * the bounds of the helipad and not moving.
 * 
 * 3. The helipad is represented on screen by a gray square with a gray
 * circle centered within the square.
 * 
 * 4.1.0 There should be a gap between the circle’s edge and the square edge;
 * in addition, the circle must be centered and there must be a clear
 * and visible gap between the circle and the square.
 * 
 * 5. The helipad should be centered along the width of the screen and
 * should be roughly one half of its width above the bottom edge of the screen.
 * 
 * 5.1.0 The helicopter fuel readout should be clearly visible on startup.
 * 
 * Personal specification:
 * 
 * 1. The Final Approach and Takeoff (FATO) must be at least 1.5 times the
 * overall length of the helicopter.
 * 
 * 2. The width of the safety area must be at least 0.33 times the rotor *
 * diameter
 */
class Helipad extends Fixed {
    /**
     * 1/8th of the game height - used to position the helipad in the game
     */
    private static final int GAME_HEIGHT_1_8th = Game.getGameHeight() / 8;
    /**
     * 1/2 of the game width - used to center the helipad
     */
    private static final int GAME_WIDTH_1_2 = Game.getGameWidth() / 2;
    /**
     * Point2D location of the helipad.
     * Helipad is centered on it's TLOF, the Touchdown and Liftoff (TLOF),
     * circle. The Helipad's y-coordinate along the bottom
     * 1/8th of the game window height.
     */
    private static Point2D HELIPAD_CENTER = new Point2D(GAME_WIDTH_1_2,
            GAME_HEIGHT_1_8th);

    public Helipad() {
        super();
        // The Touchdown and Liftoff (TLOF) circle
        HelipadTLOFCircle circle = new HelipadTLOFCircle(HELIPAD_CENTER);

        // The Final Approach and Takeoff (FATO) square
        HelipadFATOSquare square = new HelipadFATOSquare(HELIPAD_CENTER);

        // The Helipad "H" font
        HelipadH helipadH = new HelipadH(HELIPAD_CENTER);

        add(square);
        add(circle);
        add(helipadH);
    } // end constructor

    /**
     * getHelipadCenter - returns the center of the helipad as a Point2D.
     * 
     * This is the center of the TLOF circle.
     * 
     * @return the center of the helipad as a Point2D
     */
    public static Point2D getCenter() {
        return HELIPAD_CENTER;
    }
}

/**
 * FATOSquare - represents the Final Approach and Takeoff (FATO) square of the
 * helipad.
 * 
 */
class HelipadFATOSquare extends Fixed {
    /**
     * Helipad FATO square dimension.
     */
    private static final int SQUARE_DIMENSION = 100;
    /**
     * Helipad FATO square x and y offset to center the square.
     */
    private static final int SQUARE_CENTER_ORIGIN = SQUARE_DIMENSION / 2;

    public HelipadFATOSquare(Point2D center) {
        super();
        Rectangle square = new Rectangle(
                center.getX() - SQUARE_CENTER_ORIGIN,
                center.getY() - SQUARE_CENTER_ORIGIN,
                SQUARE_DIMENSION,
                SQUARE_DIMENSION);
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
class HelipadTLOFCircle extends Fixed {
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
    public HelipadTLOFCircle(Point2D center) {
        super();
        Circle circle = new Circle(center.getX(), center.getY(), RADIUS);
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(2);
        circle.setFill(Color.TRANSPARENT);
        add(circle);
    } // end constructor
}

/**
 * A class to contain the letter "H" in the helipad. It extends the Fixed class
 * so that it can be added to the game world. It uses the JavaFX Text class to
 * create the letter "H".
 * 
 * The Letter "H" is centered on the helipad.
 */
class HelipadH extends Fixed {
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
    public HelipadH(Point2D center) {
        super();
        Text text = new Text("H");
        text.setFont(Font.font(
                "Helvetica",
                FontWeight.BOLD,
                HELIPAD_H_FONT_SIZE));
        text.setFill(Color.WHITE);
        text.setX(center.getX() - (text.getLayoutBounds().getWidth() / 2));
        text.setY(center.getY() + (text.getLayoutBounds().getHeight() / 3));
        text.setScaleY(-1);
        add(text);
    } // end constructor
}

class Game {
    /**
     * The width of the game window.
     */
    private static final int GAME_WIDTH = 400;
    /**
     * The height of the game window.
     */
    private static final int GAME_HEIGHT = 800;

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public static int getGameHeight() {
        return GAME_HEIGHT;
    }

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
     * The title of the game window.
     */
    private static final String WINDOW_TITLE = "RainMaker - A1";

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
        Scene scene = new Scene(root,
                Game.getGameWidth(),
                Game.getGameHeight(),
                Color.BLACK);
        // set the title of the Stage
        primaryStage.setTitle(WINDOW_TITLE);
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
        root.getChildren().clear();
        Helipad helipad = new Helipad();

        root.getChildren().addAll(helipad);
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