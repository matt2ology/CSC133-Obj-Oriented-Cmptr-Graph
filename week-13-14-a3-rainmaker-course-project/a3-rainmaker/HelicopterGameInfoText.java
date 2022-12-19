import javafx.scene.paint.Color;

class HelicopterGameInfoText extends GameText {
    private Color FONT_COLOR = Color.RED;
    private static final int INFO_STATUS_TEXT_OFFSET = 50;
    private static int FONT_SIZE = 15;

    public HelicopterGameInfoText(String text) {
        super(text, FONT_SIZE);
        this.setFill(FONT_COLOR);
        this.setTranslateY(INFO_STATUS_TEXT_OFFSET);
    }
}