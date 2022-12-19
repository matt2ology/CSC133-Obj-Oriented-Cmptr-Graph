import javafx.animation.AnimationTimer;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 * Game
 */
class Game extends Pane {
    /**
     * The initial fuel value is set for playability
     */
    private static final int HELICOPTER_INITIAL_FUEL_CAPACITY = 25000;
    private Helicopter helicopter;
    private Pond pond01;
    private Pond pond02;
    private Pond pond03;
    private Cloud cloud01;
    private Cloud cloud02;
    private Cloud cloud03;

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
        pond01.update();
        pond02.update();
        pond03.update();
        cloud01.update();
        cloud02.update();
        cloud03.update();
    }

    public void play() {
        double previousTime = 0;

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
                pond01 = new Pond(new Point2D(
                        Utility.generateRandomNumberInRange(
                                0, Globals.POND_COORDINATES.getX()),
                        Utility.generateRandomNumberInRange(
                                0, Globals.POND_COORDINATES.getY()))),
                pond02 = new Pond(new Point2D(
                        Utility.generateRandomNumberInRange(
                                0, Globals.POND_COORDINATES.getX()),
                        Utility.generateRandomNumberInRange(
                                0, Globals.POND_COORDINATES.getY()))),
                pond03 = new Pond(new Point2D(
                        Utility.generateRandomNumberInRange(
                                0, Globals.POND_COORDINATES.getX()),
                        Utility.generateRandomNumberInRange(
                                0, Globals.POND_COORDINATES.getY()))),
                cloud01 = new Cloud(new Point2D(
                        Globals.CLOUD_COORDINATES.getX(),
                        Utility.generateRandomNumberInRange(
                                0, Globals.CLOUD_COORDINATES.getY()))),
                cloud02 = new Cloud(new Point2D(
                        Globals.CLOUD_COORDINATES.getX(),
                        Utility.generateRandomNumberInRange(
                                0, Globals.CLOUD_COORDINATES.getY()))),
                cloud03 = new Cloud(new Point2D(
                        Globals.CLOUD_COORDINATES.getX(),
                        Utility.generateRandomNumberInRange(
                                0, Globals.CLOUD_COORDINATES.getY()))),
                helicopter = new Helicopter(
                        Globals.HELIPAD_COORDINATES,
                        HELICOPTER_INITIAL_FUEL_CAPACITY)); // end addAll
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