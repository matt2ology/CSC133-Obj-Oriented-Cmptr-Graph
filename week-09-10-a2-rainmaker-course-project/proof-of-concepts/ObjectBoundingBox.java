import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.geometry.BoundingBox;

class GameObject extends Group {
    protected Translate translate;
    protected Rotate rotate;

    public GameObject(Point2D location) {
        this.translate = new Translate(location.getX(), location.getY());
        this.rotate = new Rotate();
        getTransforms().addAll(translate, rotate);
    }

    public Rectangle createBoundsRectangle(Bounds bounds) {
        Rectangle rect = new Rectangle();

        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(3);

        rect.setWidth(bounds.getWidth());
        rect.setHeight(bounds.getHeight());

        return rect;
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

class Player extends GameObject {
    public Player(Point2D location) {
        super(location);
        getChildren().add(new PlayerBlib(location));
        getChildren().add(new PlayerHeadingIndicator(location));
    }

}

class PlayerBlib extends GameObject{
    private Circle blipCircle;
    private static final double blipRadius = 10;

    public PlayerBlib() {
        super(new Point2D(0, 0));
        blipCircle = new Circle(blipRadius, Color.RED);
        blipCircle.setStroke(Color.BLACK);
        blipCircle.setStrokeWidth(3);
    }
}

class PlayerHeadingIndicator extends GameObject {
    private static Dimension2D headingIndicatorDimension = new Dimension2D(5, 30);
    private Rectangle headingIndicator;

    public PlayerHeadingIndicator() {
        super(new Point2D(0, 0));
        headingIndicator = new Rectangle(headingIndicatorDimension.getWidth(), headingIndicatorDimension.getHeight(),
                Color.BLACK);
        headingIndicator.setStroke(Color.BLACK);
        headingIndicator.setStrokeWidth(3);
    }
}

class Game extends Pane {

    private Player player;

    public Game() {
        player = new Player(new Point2D(400, 250));
        player.rotate.setPivotX(player.getBoundsInLocal().getWidth() / 2);
        player.rotate.setPivotY(player.getBoundsInLocal().getHeight() / 2);
        // set pivot point to center of player
        getChildren().add(player);
    }

    public Player getPlayer() {
        return player;
    }
}

public class ObjectBoundingBox extends Application {
    Game game = new Game();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setCenter(game);

        Scene scene = new Scene(root, 800, 600);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    // rotate player left
                    game.getPlayer().rotate.setAngle(game.getPlayer().rotate.getAngle() - 15);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    // rotate play right
                    game.getPlayer().rotate.setAngle(game.getPlayer().rotate.getAngle() + 15);
                } else if (event.getCode() == KeyCode.UP) {
                    game.getPlayer().setTranslateX(game.getPlayer().getTranslateX() + 10);
                } else if (event.getCode() == KeyCode.DOWN) {
                    game.getPlayer().setTranslateX(game.getPlayer().getTranslateX() - 10);
                }
            }
        });

        primaryStage.setTitle("Object Bounding Box");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}