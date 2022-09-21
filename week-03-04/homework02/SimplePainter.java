import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A program where the user can sketch in a variety of colors and tools.
 * A color palette list of paint tools is shown along the right edge of the
 * canvas. The user can select a drawing color by clicking on a color in the
 * palette, and in the column to the left (of the color palette) a set of
 * drawing tools. Under the color palette is a "Clear button" that the user
 * can click to clear the sketch. The user draws, with the selected tool, by
 * clicking and dragging in a large white area that occupies most of the
 * canvas.
 */
public class SimplePainter extends Application {
  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // Constants - static defined constants in lieu of magic numbers
  //
  static final Color TOOL_RECT_FG = Color.LIGHTCORAL;
  static final Color TOOL_RECT_BG = Color.WHITE;
  static final Color TOOL_FG = Color.LEMONCHIFFON;
  static final int CELL_W = 60;
  static final int PADDING = 5;
  static final int APPLICATION_W = 600;
  static final int APPLICATION_H = 400;
  static final int CANVAS_H = 2 * CELL_W + 3 * PADDING;
  static final int CANVAS_W = 3 * CELL_W + 4 * PADDING;

  public static void main(String[] args) {
    Application.launch();
  }

  /**
   * The start() method creates the GUI, sets up event listening, and
   * shows the window on the screen.
   */
  @Override
  public void start(Stage stage) {

    /*
     * Respond to mouse events on the canvas,
     * by calling methods in this class.
     */

    /* Configure the GUI and show the window. */
    // 03 Scene-graph: a hierarchy tree of nodes

    // The root node is a HBox, which contains the canvas and the 2 Vboxes
    HBox root = new HBox();
    // Scene: the root node is the HBox
    Scene scene = new Scene(root, APPLICATION_W, APPLICATION_H, Color.GRAY);
    Canvas canvas = new Canvas(CANVAS_W, CANVAS_H);

    // canvas.setOnMousePressed(e -> mousePressed(e));
    // canvas.setOnMouseDragged(e -> mouseDragged(e));
    // canvas.setOnMouseReleased(e -> mouseReleased(e));

    root.getChildren().add(canvas);
    stage.setScene(scene); // Set the scene in the stage
    stage.setResizable(false);
    stage.setTitle("Simple Paint Objects");
    // Stage: top-level container for GUI (the window of application)
    stage.show();
  }

}

/**
 * A base class for other tool classes
 * 
 * Adds method so that tools can be both "activated" and "deactivated"
 * instantiates a javafx.scene.shape.Rectangle object to represent the
 * background of the tool
 * 
 * Allows the ability to set Rectangle's fill color
 */
abstract class AbstractTool extends StackPane {
  Rectangle rectangle = new Rectangle();
}
