import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class PondPercentageInfoText extends GameText {
    private final Color TEXT_COLOR = Color.WHITE;
    private static final int TEXT_FONT_SIZE = 15;

    public PondPercentageInfoText(double text) {
        // append a percent sign to the text string to indicate it is a percentage value
        super(String.format("%.0f", text) + "%", TEXT_FONT_SIZE);
        this.setFill(TEXT_COLOR);
        this.setFont(Font.font(this.getFont().getFamily(),
                FontWeight.BOLD,
                this.getFont().getSize()));
    }
}