import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import javafx.geometry.Point2D;

public class FixedObjectTest {
    /**
     *
     */
    private static final int numberOfTestIterations = 500;
    private FixedObject fixedObject;

    @Before
    public void setUp() throws Exception {
        // an anonymous class that extends FixedObject so we can test it.
        this.fixedObject = new FixedObject(new Point2D(0, 0)) {
        };
    }

    /**
     * @brief Test that FixedObject can be instantiated into a
     *        class that extends it.
     * @throws Exception
     */
    @Test
    public void canBe_fromSubClassInstantiated() throws Exception {
        assertNotNull(this.fixedObject);
    }

    /**
     * @brief Test that FixedObject Position via constructor is set correctly
     *        in constructor.
     * @throws Exception
     */
    @Test
    public void position_setCorrectlyInConstructor() throws Exception {
        for (int i = 0; i < numberOfTestIterations; i++) {
            double randomX = Math.random() * 1000;
            double randomY = Math.random() * 1000;
            Point2D coordinates = new Point2D(randomX, randomY);
            FixedObject fixedObject = new FixedObject(coordinates) {
            };
            assertEquals(coordinates.getX(),
                    fixedObject.translate.getX(),
                    0.0);
            assertEquals(coordinates.getY(),
                    fixedObject.translate.getY(),
                    0.0);
        }
    }

    /**
     * @brief Test that FixedObject has field scale
     * @throws Exception
     */
    @Test
    public void hasFieldScale() throws Exception {
        assertNotNull(this.fixedObject.scale);
    }

    /**
     * @brief Test that FixedObject has field translate
     * @throws Exception
     */
    @Test
    public void hasFieldTranslate() throws Exception {
        assertNotNull(this.fixedObject.translate);
    }

    /**
     * @brief Test that FixedObject has field rotate
     * @throws Exception
     */
    @Test
    public void hasFieldRotate() throws Exception {
        assertNotNull(this.fixedObject.rotate);
    }

    /**
     * @brief Test that FixedObject can increase scale
     * @throws Exception
     */
    @Test
    public void increaseScale() throws Exception {
        for (int i = 0; i <= numberOfTestIterations; i++) {
            double randomX = Math.random() * 1000;
            double randomY = Math.random() * 1000;
            this.fixedObject.scale.setX(randomX);
            this.fixedObject.scale.setY(randomY);
            assertEquals(randomX, this.fixedObject.scale.getX(), 0.0);
            assertEquals(randomY, this.fixedObject.scale.getY(), 0.0);
        }
    }
}