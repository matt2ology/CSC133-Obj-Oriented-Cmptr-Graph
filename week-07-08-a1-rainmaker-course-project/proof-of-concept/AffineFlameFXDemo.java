// Note that this is the code as used in class for the demo. It should help you
// structure your own code; however, you will most likely need to change and adapt
// this code. Do not assume that because I used it in an in-class demo that it is
// appropriate for the final solution of your project.
//
// That said: you have permission to use this to help you get started. If you are stuck
// in an "I don't know what to do loop" then I want you to break out of that and if
// this code helps, that's fine.
//
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

interface Updatable {
    void update();
}

class GameObject extends Group implements Updatable {
    protected Translate myTranslation;
    protected Rotate myRotation;
    protected Scale myScale;

    public GameObject() {
        myTranslation = new Translate();
        myRotation = new Rotate();
        myScale = new Scale();
        this.getTransforms().addAll(myTranslation, myRotation, myScale);
    }

    public void rotate(double degrees) {
        myRotation.setAngle(degrees);
        myRotation.setPivotX(0);
        myRotation.setPivotY(0);
    }

    public void scale(double sx, double sy) {
        myScale.setX(sx);
        myScale.setY(sy);
    }

    public void translate(double tx, double ty) {
        myTranslation.setX(tx);
        myTranslation.setY(ty);
    }

    public double getMyRotation() {
        return myRotation.getAngle();
    }

    public void update() {
        for (Node n : getChildren()) {
            if (n instanceof Updatable)
                ((Updatable) n).update();
        }
    }

    void add(Node node) {
        this.getChildren().add(node);
    }
}

class Body extends GameObject {
    public Body() {
        super();
        Ellipse body = new Ellipse();
        body.setRadiusX(10);
        body.setRadiusY(10);
        body.setFill(Color.MAGENTA);
        add(body);
    }
}

class Flame extends GameObject {
    public Flame() {
        Polygon polygon = new Polygon();
        polygon.setFill(Color.BLUE);
        polygon.getPoints().addAll(new Double[] {
                0.0, 20.0,
                -20.0, -20.0,
                20.0, -20.0 });
        add(polygon);

        GameText text = new GameText("I'm a Flame");
        text.setTranslateY(100);
        text.setTranslateX(-350);
        text.setScaleX(4);
        add(text);

        this.getTransforms().clear();
        this.getTransforms().addAll(myRotation, myTranslation, myScale);
    }

    int offset = 1;

    public void update() {
        myTranslation.setY(myTranslation.getY() + offset);
        if (myTranslation.getY() > 40)
            offset = -offset;
        if (myTranslation.getY() < 10)
            offset = -offset;
    }
}

class GameText extends GameObject {
    Text text;

    public GameText(String textString) {
        text = new Text(textString);
        text.setScaleY(-1);
        text.setFont(Font.font(25));
        add(text);
    }

    public GameText() {
        this("");
    }

    public void setText(String textString) {
        text.setText(textString);
    }
}

class FireOval extends GameObject {
    public FireOval() {
        Body myBody = new Body();
        myBody.setScaleX(2.5);
        myBody.setScaleY(1.5);

        add(makeFlame(0, 40, 0.25, 1, 0));
        add(makeFlame(0, 40, 0.25, 1, -90));
        add(makeFlame(0, 40, 0.25, 1, 180));
        add(makeFlame(0, 40, 0.25, 1, 90));
        add(myBody);
    }

    private Flame makeFlame(double tx, double ty, double sx, double sy, int degrees) {
        Flame flame = new Flame();
        flame.rotate(degrees);
        flame.translate(tx, ty);
        flame.scale(sx, sy);
        return flame;
    }

    public void left() {

    }

    public void right() {
    }
}

public class AffineFlameFXDemo extends Application {
    int counter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        init(root);
        init(root);

        root.setScaleY(-1);
        root.setTranslateX(250);
        root.setTranslateY(-250);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("AffineFlameFX!");

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (counter++ % 2 == 0) {
                    fo.rotate(fo.getMyRotation() + 1);
                    fo.update();
                }
            }
        };
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    fo.left();
                    break;
                case RIGHT:
                    fo.right();
                    break;
                default:
                    ;
            }
        });
        primaryStage.show();
        timer.start();

    }

    FireOval fo;

    public void init(Pane parent) {
        parent.getChildren().clear();
        fo = new FireOval();

        Line xAxis = new Line(-125, 0, 125, 0);

        GameText t = new GameText("More Text!");
        t.translate(25, 125);
        parent.getChildren().add(t);

        Circle c = new Circle(2, Color.RED);
        c.setTranslateX(25);
        c.setTranslateY(125);

        parent.getChildren().add(c);
        parent.getChildren().add(xAxis);
        parent.getChildren().add(fo);
    }
}