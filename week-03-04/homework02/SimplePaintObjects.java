import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


abstract class AbstractTool extends StackPane {
    public AbstractTool(Color color) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(60);
        rectangle.setHeight(60);
        rectangle.setFill(Color.LIGHTCORAL);
        rectangle.setStroke(Color.WHITE);
        this.getChildren().add(rectangle);
    }
}

class ColorTool extends AbstractTool {
    public ColorTool(Color color) {
        super(color);
    }
}

class ActionTool extends AbstractTool {
    public ActionTool(Color color) {
        super(color);
    }

    /**
     * Creates a tool that is used to perform an action, such as clearing the
     * canvas: the tool is a button with a label that says "Clear".
     * 
     * @param cmdName the text to be displayed on the button
     */
    public ActionTool(String cmdName) {
        super(Color.LIGHTCORAL);
        Label commandName = new Label(cmdName);
        commandName.setTextFill(Color.LEMONCHIFFON);
        commandName.setFont(Font.font("Verdana"));
        this.getChildren().add(commandName);
    }
}

/**
 * A tool that draws a shape on the canvas. The shape is drawn when the
 * user drags the mouse from one point to another. The shape is drawn
 * with the current drawing color.
 */
abstract class ShapeTool extends AbstractTool {
    /**
     * Creates a tool that is used to draw a shape on the canvas.
     * 
     * @param color
     */
    public ShapeTool(Color color) {
        super(color);
    }

    /**
     * Draws a shape on the canvas. The shape is drawn from
     * the point (startX,startY) to the point (endX,endY).
     * <ul>
     * <li>The shape is drawn with the current drawing color.</li>
     * <li>The shape is filled with the current drawing color.</li>
     * </ul>
     * 
     * @param gc    the graphics context for drawing on the canvas
     * @param color the color to use for drawing the shape
     * @param start the starting point for the shape
     * @param end   the ending point for the shape
     */
    abstract public void draw(GraphicsContext gc, Color color, Point2D start, Point2D end);
}

class PointTool extends ShapeTool {
    double penWidth;
    /**
     * Creates a tool that is used to draw a point on the canvas.
     * The point is drawn:
     *      when the user clicks the mouse.
     *      with the current drawing color.
     *      as a small circle with the current drawing color.
     * The size of the circle is determined by the value of the penWidth field.
     * The penWidth field:
     *      is set to 2 when the tool is created.
     *      can be changed by calling the setPenWidth() method.
     *      is used by the draw() method.
     * The draw() method is:
     *      called by the mousePressed() method in the PaintPanel class.
     *      not called by the mouseDragged() method in the PaintPanel class.
     *      not called by the mouseReleased() method in the PaintPanel class.
     *      not called by the mouseMoved() method in the PaintPanel class.
     * @param penWidth the width of the pen to be used for drawing the point
     */
    public PointTool(int penWidth) {
        super(Color.LIGHTCORAL);
        this.penWidth = penWidth;
        Ellipse toolImage = new Ellipse(penWidth, penWidth);
        toolImage.setStroke(Color.LEMONCHIFFON);
        toolImage.setFill(Color.LEMONCHIFFON);
        this.getChildren().add(toolImage);
    }

    @Override
    public void draw(GraphicsContext gc, Color color, Point2D start, Point2D end) {

    }
}

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
public class SimplePaintObjects extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    GraphicsContext gc;

    private final Color[] palette = {
            Color.BLACK, Color.RED, Color.GREEN, Color.BLUE,
            Color.CYAN, Color.MAGENTA, Color.YELLOW
    };

    private Node makeCanvas() {
        Canvas canvas = new Canvas(600, 400);
        gc = canvas.getGraphicsContext2D();
        clearCanvas();
        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        return canvas;
    }

    private Node makeColorPane() {
        VBox colorPane = new VBox();
        for (int i = 0; i < 7; i++) {
            // approach 01
            // colorPane ct = new ColorTool(new ColorTool(palette[i]));
            // ct.setOnMousePressed();
            // colorPane.getChildren().add(ct);

            // approach 02
            colorPane.getChildren().add(
                    addMouseHandlerToColorTool(
                            new ColorTool(palette[i])));
        }
        colorPane.getChildren().add(new ActionTool("Clear"));
        return colorPane;
    }

    // approach 02
    /**
     * Adds a mouse handler to a ColorTool object.
     * The mouse handler is a lambda expression.
     * @param tool the ColorTool object to which the mouse handler is added
     * @return the ColorTool object with the mouse handler added to it
     */
    private ColorTool addMouseHandlerToColorTool(ColorTool tool) {
        tool.setOnMousePressed((e) -> {
            // this.currentColorTool.deactivate();
            // this.currentColorTool = tool;
            // tool.activate();
        });
        return tool;
    }

    /**
     * The current tool that is selected for drawing. This is null if no tool
     * is selected.
     * @return toolPane
     */
    private Node makeToolPane() {
        VBox toolPane = new VBox();
        toolPane.getChildren().add(new PointTool(2));
        toolPane.getChildren().add(new PointTool(4));
        toolPane.getChildren().add(new PointTool(6));
        toolPane.getChildren().add(new PointTool(8));
        return toolPane;
    }

    private Parent makeRootPane() {
        HBox root = new HBox();
        // We want our canvas and our two VBoxes
        root.getChildren().add(makeCanvas()); // small method easy to test
        root.getChildren().add(makeToolPane());
        root.getChildren().add(makeColorPane());
        return root;
    }

    /**
     * Called when the user clicks the mouse on the canvas.
     * This method is not called when the user drags the mouse.
     * @param event
     */
    private void mousePressed(MouseEvent event) {}

    /**
     * Called when the user drags the mouse on the canvas.
     * This method is not called when the user clicks the mouse.
     * @param event
     */
    private void mouseDragged(MouseEvent event) {}

    /**
     * Called when the user releases the mouse button on the canvas.
     * This method is not called when the user clicks the mouse.
     * @param event
     */
    private void mouseReleased(MouseEvent event) {}

    /**
     * Clear the canvas by filling it with white.
     * This is called when the user clicks the "Clear" button.
     * It is also called when the program starts.
     * It is not called when the user is drawing.
     * It is not called when the user is selecting a color or a tool.
     * It is not called when the user is moving the mouse.
     */
    private void clearCanvas() {}

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(makeRootPane()));
        primaryStage.setTitle("Simple Paint Objects");
        primaryStage.show();
    }
}