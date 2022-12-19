import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;

class HeloCockpit extends Arc {

    /**
     * The cockpit glass is a linear gradient that goes from blue to light blue
     */
    private LinearGradient cockpitGlass = new LinearGradient(
            0,
            0,
            0,
            1,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(1, Color.BLUE),
            new Stop(0, Color.LIGHTBLUE));

    public HeloCockpit() {
        super(0,
                0,
                13,
                13,
                0,
                180);
        setFill(cockpitGlass);
    }
}