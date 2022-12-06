import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
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
    public void update();
}

abstract class GameObject extends Group {
    protected Translate translate;

    public GameObject(Point2D location) {
        translate = new Translate(location.getX(), location.getY());
        getTransforms().add(translate);

    }

    // bounding box for collision detection purposes to be implemented by subclasses
    public Rectangle createBoundsRectangle(Bounds bounds) {
        Rectangle rect = new Rectangle();

        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(2);

        rect.setX(super.getBoundsInLocal().getMinX());
        rect.setY(super.getBoundsInLocal().getMinY());
        rect.setWidth(bounds.getWidth());
        rect.setHeight(bounds.getHeight());

        return rect;
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

class HomeBase extends GameObject {
    public HomeBase(Point2D location) {
        super(location);
        Rectangle rect = new Rectangle(-50, -50, 100, 100);
        rect.setFill(Color.GREEN);
        add(rect);

        Text text = new Text("Home");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        text.setScaleY(-1);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTranslateX(-25);
        text.setTranslateY(10);
        text.setFill(Color.WHITE);
        add(text);
    }

    @Override
    public String toString() {
        return "HomeBase" + getBoundsInParent() + " " + getBoundsInLocal();
    }
}

class Player extends GameObject implements Updatable {
    private static final double SPEED = 5.0;
    private static final double ROTATION_SPEED = 15;

    private Rotate rotate;
    private Dimension2D bounds;

    public Player(Point2D location, Dimension2D bounds) {
        super(location);
        this.bounds = bounds;

        rotate = new Rotate(0, 0, 0);
        getTransforms().add(rotate);

        // Create a rectangle to represent the player's centered origin point.
        Rectangle rect = new Rectangle(-5, 0, 10, 25);
        rect.setFill(Color.BLUE);
        add(rect);

        // Create a circle to represent the player.
        Circle circle = new Circle(0, 0, 10);
        circle.setFill(Color.RED);
        add(circle);

        // create a local inbounds rectangle for collision detection purposes
        Rectangle inbounds = createBoundsRectangle(getBoundsInLocal());
        add(inbounds);
    }

    @Override
    public void update() {

        if (translate.getX() < 0) { // Left wall collision detection.
            translate.setX(bounds.getWidth()); // Teleport to the right wall.
        } else if (translate.getX() > bounds.getWidth()) { // Right wall collision detection.
            translate.setX(0); // Teleport to the left wall.
        }
        if (translate.getY() < 0) { // Top wall collision detection.
            translate.setY(bounds.getHeight()); // Teleport to the bottom wall.
        } else if (translate.getY() > bounds.getHeight()) { // Bottom wall collision detection.
            translate.setY(0); // Teleport to the top wall.
        }
    }

    public void moveForward() {
        translate.setX(translate.getX() + (-Math.sin(Math.toRadians(rotate.getAngle())) * SPEED));
        translate.setY(translate.getY() + Math.cos(Math.toRadians(rotate.getAngle())) * SPEED);
    }

    public void moveBackward() {
        translate.setX(translate.getX() - (-Math.sin(Math.toRadians(rotate.getAngle())) * SPEED));
        translate.setY(translate.getY() - Math.cos(Math.toRadians(rotate.getAngle())) * SPEED);
    }

    public void rotateLeft() {
        rotate.setAngle(rotate.getAngle() + ROTATION_SPEED);
    }

    public void rotateRight() {
        rotate.setAngle(rotate.getAngle() - ROTATION_SPEED);
    }

    @Override
    public String toString() {
        return "Player [x=" + translate.getX() + ", y=" + translate.getY() + ", angle=" + rotate.getAngle() + "]";
    }
}

/**
 * @see https://stackoverflow.com/questions/32804161/javafx-collision-detection-between-objects-that-have-different-parents
 */
class Game extends Pane {

    private static final int GAME_HEIGHT = 300;
    private static final int GAME_WIDTH = 300;

    private Player player = new Player(new Point2D(GAME_HEIGHT / 2, GAME_WIDTH / 8),
            new Dimension2D(GAME_WIDTH, GAME_HEIGHT));

    private HomeBase homeBase = new HomeBase(new Point2D(GAME_WIDTH / 2, GAME_HEIGHT / 2));

    // text to display when the player is in the home base
    private Text collisionText = new Text();

    public Game() {
        super.setScaleY(-1);
        collisionText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        collisionText.setTextAlignment(TextAlignment.CENTER);
        collisionText.setTranslateX(GAME_WIDTH / 3);
        collisionText.setTranslateY(GAME_HEIGHT - 50);
        collisionText.setFill(Color.BLACK);
        collisionText.setScaleY(-1);
    }

    public void init() {
        super.getChildren().clear();
        super.getChildren().addAll(homeBase, player, collisionText);
        super.getChildren().forEach(System.out::println);
    }

    public void play() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                ////////////////////////////////////////////////////////////////
                // check for collision between the player and the home base
                // This is where the collision detection happens. The player
                // is checked against the home base.
                ////////////////////////////////////////////////////////////////
                if (player.getBoundsInParent().intersects(homeBase.getBoundsInParent())) {
                    collisionText.setText("Collision!");
                    System.err.println("Collision!");
                } else {
                    collisionText.setText("No collision.");
                    System.err.println("No collision.");
                }

            }
        };
        timer.start();
    }

    public void update() {
        player.update();

    }

    public static int getGameHeight() {
        return GAME_HEIGHT;
    }

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public Player getPlayer() {
        return player;
    }
}

public class ObjectCollision extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game();
        game.init();
        primaryStage.setScene(new Scene(
                game, Game.getGameWidth(),
                Game.getGameHeight()));

        primaryStage.setTitle("Object Collision");
        primaryStage.show();
        game.play();

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W) {
                    game.getPlayer().moveForward();
                } else if (event.getCode() == KeyCode.S) {
                    game.getPlayer().moveBackward();
                } else if (event.getCode() == KeyCode.A) {
                    game.getPlayer().rotateLeft();
                } else if (event.getCode() == KeyCode.D) {
                    game.getPlayer().rotateRight();
                }
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}