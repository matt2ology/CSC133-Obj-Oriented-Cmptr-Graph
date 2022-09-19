import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


/**
 * A program where the user can sketch in a varietry of colors and tools, and
 * only commit the shape objects when the mouse is released. However, while the
 * mouse is pressed, the user will still be able to see the object form in real
 * time. This will allow the user to both grow and shrink each shape object
 * until the user are satisfied with the object's size.
 */
public class SimplePainter extends Application{
    public static void main(String[] args) {
        launch();
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constants - static defined constants in lieu of magic numbers
    //
    static final Color TOOL_RECT_FG = Color.LIGHTCORAL;
    static final Color TOOL_RECT_BG = Color.WHITE;
    static final Color TOOL_FG = Color.LEMONCHIFFON;
    static final int CELL_W = 60;
    static final int PADDING = 5;
    // Canvas height as an expression related to other constants
    static final int CANVAS_H = 2 * CELL_W + 3 * PADDING;
    // Canvas width as an expression related to other constants
    static final int CANVAS_W = 3 * CELL_W + 4 * PADDING;
    // root node of the application should be an `Hbox`
    private HBox hBox;

    /**
     * The start() method creates the GUI, sets up event listening, and
     * shows the window on the screen.
     */
     public void start() {
       hBox = new HBox();
        
     }


}
