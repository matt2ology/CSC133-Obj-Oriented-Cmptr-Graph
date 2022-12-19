import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

class GameText extends Text {
    private String FONT_OF_CHOICE = "Helvetica";
    private Scale scale = new Scale();

    public GameText(String text, int textFontSize) {
        super(text);
        this.setFont(Font.font(FONT_OF_CHOICE, textFontSize));
        this.getTransforms().add(scale);
        // set the origin of the text to the center
        this.setX(-this.getLayoutBounds().getWidth() / 2);
        // 4 is a magic number that works for Helvetica because
        // the letters are taller than it is wide and we want to center it
        this.setY(this.getLayoutBounds().getHeight() / 4);
    }
}