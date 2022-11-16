import javafx.geometry.Point2D;
import javafx.scene.Group;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameObjectTest {
    /**
     *
     */
    private static final Point2D POINT2D = new Point2D(0, 0);
    private GameObject gameObject;

    @Before
    public void setUp() throws Exception {
        // create a class that extends GameObject so we can test it.
        this.gameObject = new GameObject(POINT2D) {
        };
    }

    /**
     * @brief Test that GameObject Position via constructor is set correctly in
     *        constructor.
     * @throws Exception
     */
    @Test
    public void position_setCorrectlyInConstructor() throws Exception {
        for (int i = 0; i < 500; i++) {
            double randomX = Math.random() * 1000;
            double randomY = Math.random() * 1000;
            Point2D coordinates = new Point2D(randomX, randomY);
            GameObject gameObject = new GameObject(coordinates) {
            };
            assertEquals(coordinates.getX(),
                    gameObject.translate.getX(),
                    0.0);
            assertEquals(coordinates.getY(),
                    gameObject.translate.getY(),
                    0.0);
        }
    }

    /**
     * @brief Test that GameObject Position via constructor is set correctly in
     *        constructor.
     * @throws Exception
     */
    @Test
    public void position_SetCorrectlyInConstructor_false() throws Exception {
        for (int i = 0; i < 500; i++) {
            double randomX = Math.random() * 1000;
            double randomY = Math.random() * 1000;
            Point2D coordinates = new Point2D(randomX, randomY);
            GameObject gameObject = new GameObject(coordinates) {
            };
            assertFalse(coordinates.getX() + 1 == gameObject.translate.getX());
            assertFalse(coordinates.getY() + 1 == gameObject.translate.getY());
        }
    }

    /**
     * @brief Test that GameObject extends Group and implements Updatable.
     * @throws Exception
     */
    @Test
    public void class_extendsGroupAndImplementsUpdatable() throws Exception {
        assertTrue(this.gameObject instanceof Group);
        assertTrue(this.gameObject instanceof Updatable);
    }

    /**
     * @brief Test that GameObject has a constructor that takes a Point2D.
     * @throws Exception
     */
    @Test
    public void constructor_TakesPoint2D() throws Exception {
        assertNotNull(this.gameObject);
    }

    /**
     * @brief Test that GameObject has a translate field.
     * @throws Exception
     */
    @Test
    public void hasTranslateField() throws Exception {
        assertNotNull(this.gameObject.translate);
    }

    /**
     * @brief Test that GameObject has a rotate field.
     */
    @Test
    public void hasRotateField() throws Exception {
        assertNotNull(this.gameObject.rotate);
    }

    /**
     * @brief Test that GameObject can set its angle.
     */
    @Test
    public void canSetAngle() throws Exception {
        for (int angle = 0; angle < 720; angle++) {
            this.gameObject.rotate.setAngle(angle);
            assertEquals(angle, this.gameObject.rotate.getAngle(), 0.0);
        }
    }

    /**
     * @brief Test that GameObject has a getTransforms method.
     * @throws Exception
     */
    @Test
    public void hasGetTransformsMethod() throws Exception {
        assertNotNull(this.gameObject.getTransforms());
    }

    /**
     * @brief Test that GameObject has a getChildren method.
     * @throws Exception
     */
    @Test
    public void hasGetChildrenMethod() throws Exception {
        assertNotNull(this.gameObject.getChildren());
    }

}
