import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoveableObjectTest {
    private MoveableObject moveableObject;

    @Before
    public void setUp() throws Exception {
        /**
         * Although MoveableObject is an abstract class, we can still
         * instantiate it because we are not instantiating the abstract
         * class itself, but rather an anonymous subclass of it.
         */
        moveableObject = new MoveableObject() {
            @Override
            public void move() {
            }
        };
    }

    /**
     * @brief Test that MoveableObject can be instantiated.
     */
    @Test
    public void canBeInstantiated() {
        assertNotNull(this.moveableObject);
    }

    /**
     * @brief Test that MoveableObject can set its position in the
     *        constructor via the builder design pattern.
     */
    @Test
    public void setPosition_InConstructor() {
        for (int i = 0; i < Globals.GAME_APP_DIMENSIONS.getHeight(); i++) {
            moveableObject.setPosition(new Point2D(i, i));
            assertEquals(i, moveableObject.translate.getX(), 0.0);
            assertEquals(i, moveableObject.translate.getY(), 0.0);
        }
    }

    /**
     * @brief Test that MoveableObject can set translation (location/position)
     *        after instantiation.
     */
    @Test
    public void setPosition_AfterInstantiation() {
        for (int i = 0; i < Globals.GAME_APP_DIMENSIONS.getHeight(); i++) {
            moveableObject.translate.setX(i);
            moveableObject.translate.setY(i);
            assertEquals(i, moveableObject.translate.getX(), 0.0);
            assertEquals(i, moveableObject.translate.getY(), 0.0);
        }
    }

    /**
     * @brief Test that MoveableObject can set rotation angle after
     *        instantiation.
     */
    @Test
    public void setRotationAngle_AfterInstantiation() {
        for (int i = 0; i <= 360; i++) {
            moveableObject.rotate.setAngle(i);
            assertEquals(i, moveableObject.rotate.getAngle(), 0.0);
        }
    }

    /**
     * @brief test that MoveableObject can add objects to its children list.
     */
    @Test
    public void add_ObjectsToChildrenList() {
        int numOfObjs = 100;
        for (int i = 0; i <= numOfObjs; i++) {
            assertEquals(i, moveableObject.getChildren().size());
            moveableObject.getChildren().add(new MoveableObject() {
                @Override
                public void move() {
                }
            });
        }
    }

    /**
     * @brief Test that MoveableObject can set its speed.
     */
    @Test
    public void setSpeed() {
        for (int i = 0; i <= 100; i++) {
            moveableObject.setSpeed(i);
            assertEquals(i, moveableObject.getSpeed(), 0.0);
        }
    }

    /**
     * @brief Test that MoveableObject can set its heading direction.
     */
    @Test
    public void calculate_HeadingDirectionVectors() {
        for (int i = 0; i <= 360; i++) {
            moveableObject.rotate.setAngle(i);
            moveableObject.update();
            assertEquals(-Math.sin(Math.toRadians(i)), moveableObject.getHeadingVector().getX(), 0.001);
            assertEquals(Math.cos(Math.toRadians(i)), moveableObject.getHeadingVector().getY(), 0.001);
        }
    }

    /**
     * @brief Test that MoveableObject can move.
     */
    @Test
    public void calculate_VelocityVectors() {
        for (int i = 0; i <= 100; i++) {
            moveableObject.setSpeed(i);
            moveableObject.rotate.setAngle(i);
            moveableObject.update();
            assertEquals(i * -Math.sin(Math.toRadians(i)), moveableObject.getVelocityVector().getX(), 0.001);
            assertEquals(i * Math.cos(Math.toRadians(i)), moveableObject.getVelocityVector().getY(), 0.001);
        }
    }

}
