import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HelicopterTest {

    /**
     * Test ability to toggle ignition on and off.
     * 
     */
    @Test
    public void testToggleHelicopterIgnition() {
        Helicopter helicopter = new Helicopter();
        assertFalse(helicopter.isIgnitionOn());
        helicopter.toggleHelicopterIgnition();
        assertTrue(helicopter.isIgnitionOn());
        helicopter.toggleHelicopterIgnition();
        assertFalse(helicopter.isIgnitionOn());
    }

    /**
     * Test acceleration of helicopter to increase speed from zero to 10.
     */
    @Test
    public void testAccelerateWhenIgnitionIsNotOn() {
        Helicopter helicopter = new Helicopter();
        assertFalse(helicopter.isIgnitionOn());
        for (int i = 0; i < 10; i++) {
            helicopter.accelerate();
        }
        assertTrue(helicopter.getSpeed() == 0.0);
    }

    /**
     * Test acceleration of helicopter to increase speed from zero to 10.
     */
    @Test
    public void testAccelerateWhenIgnitionIsOn() {
        Helicopter helicopter = new Helicopter();
        helicopter.toggleHelicopterIgnition();
        assertTrue(helicopter.isIgnitionOn());
        for (int i = 0; i < 1000; i++) {
            helicopter.accelerate();
        }
        assertTrue("Speed: " + String.valueOf(helicopter.getHelicopter().getSpeed()), helicopter.getSpeed() <= 10.0);
    }
}
