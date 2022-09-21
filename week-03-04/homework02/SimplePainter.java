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

/**
 * <h2>2.3.1.3 ActionTool</h2>
 * A tool that performs an action when an action is clicked on
 * (i.e. <b>clear button</b>).
 * <ol>
 * <li>The tool is <b>activated</b> when the user clicks on it.</li>
 * <li>The tool is <b>deactivated</b> when the user release their click of
 * it.</li>
 * <li>Actions outside of Tool Class hierarchy will have object of type
 * <b>Runnable</b> (e.g., <code>this::myClearAction</code> as the parameter
 * to the constructor)</li>
 * </ol>
 * <b>main class</b> implements a method <code>myClearAction()</code>
 * that implements the clear functionality. To execute the action on a
 * mouse press, you will need to call the <code>run()</code> method on the
 * <b>Runnable</b> object stored within <code>ActionTool</code>
 */
class ActionTool extends AbstractTool {

}

/**
 * <h2>2.3.1.4 ColorTool</h2>
 * ColorTool sets the color within the application.
 * <ol>
 * <li>When a color <b>activates</b>, it should deactivate any other
 * <code>ColorTool</code> objects.</li>
 * </ol>
 * <h3>Implementation</h3>
 * Store the active ColorTool object in your main class.
 * On color change: deactivate the current tool, set the current tool to the
 * new ColorTool object, then activate the new tool.
 */
class ColorTool extends AbstractTool {
  /**
   * Constructor for ColorTool
   * 
   * @param color
   */
  ColorTool(Color color) {
    rectangle.setFill(color);
  }

  /**
   * Gets the fill color of the rectangle
   * 
   * @param color
   */
  Color getColor() {
    return (Color) rectangle.getFill();
  }

}

/**
 * <h2>2.3.1.5 ShapeTool</h2>
 * Represents one of the shapes to be drawn, will actively track the current
 * shape tool in main class sets up the tool's rectangle with the
 * appropriate color, and provides minor services the derived classes may
 * need
 */
abstract class ShapeTool extends AbstractTool {
  /**
   * Constructor for ShapeTool - simply sets up the tool rectangle with the 
   * appropriate color
   * 
   * @param color
   */
  ShapeTool() {
    super(TOOL_RECT_BG);
  }
  
  /**
   * The <code>draw()</code> method must
   * be implemented in each of the derived shape tool classes.
   * It is this method that draws the shape on the canvas.
   * Within the tool hierarchy, <code>draw()</code> delegates the actual
   * drawing to the <code>ShapeObject</code> hierarchy.
   * <br>
   * <br>
   * At minimum: the <code>GraphicsContext</code> must be passed in
   * <br>
   * <br>
   * but it will also need:
   * <ol> 
   * <li> To know the <b>start</b> and <b>end points</b> of the shape</li>
   * <li> To know the <b>color</b> of the shape</li>
   * </ol> 
   * @param gc - GraphicsContext
   * @param x - x coordinate
   * @param y - y coordinate
   */
  abstract void draw(GraphicsContext gc);
}
