import javafx.scene.Group;
import javafx.scene.paint.Color;

class HeloBody extends Group {
    private Color bodyColor = Color.DARKORANGE;
    private Color skidColor = Color.GRAY;
    private static final int SKID_POSITION_X = 20;
    private static final int SKID_POSITION_Y = -35;
    private static final int SKID_CROSS_HEAD_POSITION_Y = -25;

    HeloLandingSkid skidLeft = new HeloLandingSkid(skidColor);
    HeloLandingSkid skidRight = new HeloLandingSkid(skidColor);
    HeloLandingSkidCross skidCrossHead = new HeloLandingSkidCross(bodyColor);
    HeloLandingSkidCross skidCrossTail = new HeloLandingSkidCross(bodyColor);
    HeloCabin cabin = new HeloCabin(bodyColor);
    HeloCockpit cockpit = new HeloCockpit();
    HeloEngineComponent engine = new HeloEngineComponent(bodyColor);
    HeloTailBoom tailBoom = new HeloTailBoom(bodyColor);

    public HeloBody() {
        super();
        // magic number offset to center w/ skid cross attach point
        skidLeft.setTranslateX((-SKID_POSITION_X) - (5));
        skidLeft.setTranslateY(SKID_POSITION_Y);
        // magic number offset to center w/ skid cross attach point
        skidRight.setTranslateX(SKID_POSITION_X + (2));
        skidRight.setTranslateY(SKID_POSITION_Y);
        skidCrossHead.setTranslateY(SKID_CROSS_HEAD_POSITION_Y);
        // magic number offset for the cabin from the forward, head, skid cross
        cabin.setTranslateY(SKID_CROSS_HEAD_POSITION_Y + (5));
        cockpit.setTranslateY(SKID_CROSS_HEAD_POSITION_Y);

        getChildren()
                .addAll(skidLeft,
                        skidRight,
                        skidCrossHead,
                        skidCrossTail,
                        cabin,
                        cockpit,
                        tailBoom,
                        engine);
    }
}