import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// The Three Rules of TDD
// 1. Write production code only to pass a failing unit test.
// 2. Write no more of a unit test than sufficient to fail
// (compilation failures are failures).
// 3. Write no more production code than necessary to pass
// the one failing unit test. 

public class GameObjectTest {
    private GameObject gameObject;

    @Before
    public void setUp() throws Exception {
        /**
         * Although GameObject is an abstract class, we can still instantiate
         * it because we are not instantiating the abstract class itself,
         * but rather an anonymous subclass of it.
         */
        gameObject = new GameObject() {
        };

    }

    /**
     * @brief Test that GameObject can be instantiated.
     */
    @Test
    public void testGameObjectCanBeInstantiated() {
        assertNotNull(this.gameObject);
    }

    /**
     * @brief Test that GameObject can set its position in the constructor via the
     *        builder design pattern.
     */
    @Test
    public void testGameObjectCanSetPositionInConstructor() {
        Point2D coordinates = new Point2D(99.0, 8.0);
        gameObject.setPosition(coordinates);
        assertEquals(coordinates.getX(), gameObject.translate.getX(), 0.0);
        assertEquals(coordinates.getY(), gameObject.translate.getY(), 0.0);
    }

    /**
     * @brief Test that GameObject can set translation (location/position)
     * after instantiation.
     */
    @Test
    public void testGameObjectCanSetTranslateAfterInstantiation() {
        double height = Globals.GAME_APP_DIMENSIONS.getHeight();
        for (int coordinates = 0; coordinates <= height; coordinates++) {
            gameObject.setPosition(new Point2D(coordinates, coordinates));
            assertEquals(coordinates, gameObject.translate.getX(), 0.0);
            assertEquals(coordinates, gameObject.translate.getY(), 0.0);
          }
    }

    /**
     * @brief Test that GameObject can set rotation angle after instantiation.
     */
    @Test
    public void testGameObjectCanSetRotationAngleAfterInstantiation() {
        for (int degrees = 0; degrees <= 361; degrees++) {
            gameObject.rotate.setAngle(degrees);
            assertEquals(degrees, gameObject.rotate.getAngle(), 0.0);
        }
    }

    /**
     * @brief Test adding a child to a GameObject.
     */
    @Test
    public void testGameObjectAdd_Objects() {
        int count = 50;
        for (int numOfObjs = 0; numOfObjs <= count; numOfObjs++) {
            assertEquals(numOfObjs, gameObject.getChildren().size());
            gameObject.add(new GameObject() {
            });
        }
    }

}
