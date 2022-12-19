import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

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
    public static Point2D POND_COORDINATES = new Point2D(
            (GAME_APP_DIMENSIONS.getWidth()),
            (GAME_APP_DIMENSIONS.getHeight()
                    - (GAME_APP_DIMENSIONS.getHeight() / 3)));
    public static Point2D CLOUD_COORDINATES = new Point2D(
            (GAME_APP_DIMENSIONS.getWidth() / 2),
            (GAME_APP_DIMENSIONS.getHeight()
                    - (GAME_APP_DIMENSIONS.getHeight() / 3)));
}