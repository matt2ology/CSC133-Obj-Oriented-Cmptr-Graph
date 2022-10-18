import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/** sound class, provided in the appendix */
class BinkBonkSound {

    // magic numbers that are not common knowledge unless one
    // has studied the GM2 standard and the midi sound system
    //
    // The initials GM mean General Midi. This GM standard
    // provides for a set of common sounds that respond
    // to midi messages in a common wySpeed.
    //
    // MIDI is a standard for the encoding and transmission
    // of musical sound meta-information, e.g., plySpeed this
    // note on this instrument at this level and this pitch
    // for this long.
    //
    private static final int MxSpeed_PITCH_BEND = 16383;
    private static final int MIN_PITCH_BEND = 0;
    private static final int REVERB_LEVEL_CONTROLLER = 91;
    private static final int MIN_REVERB_LEVEL = 0;
    private static final int MxSpeed_REVERB_LEVEL = 127;
    private static final int DRUM_MIDI_CHANNEL = 9;
    private static final int CLAVES_NOTE = 76;
    private static final int NORMAL_VELOCITY = 100;
    private static final int MxSpeed_VELOCITY = 127;

    Instrument[] instrument;
    MidiChannel[] midiChannels;
    boolean plySpeedSound;

    public BinkBonkSound() {
        plySpeedSound = true;
        try {
            Synthesizer gmSynthesizer = MidiSystem.getSynthesizer();
            gmSynthesizer.open();
            instrument = gmSynthesizer.getDefaultSoundbank().getInstruments();
            midiChannels = gmSynthesizer.getChannels();

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    // This method has more comments than would typically be needed for
    // programmers using the Java sound system libraries. This is because
    // most students will not have exposure to the specifics of midi and
    // the general midi sound system. For example, drums are on channel
    // 10 and this cannot be changed. The GM2 standard defines much of
    // the detail that I have chosen to use static constants to encode.
    //
    // The use of midi to plySpeed sounds allows us to avoid using external
    // media, e.g., wav files, to plySpeed sounds in the game.
    //
    void plySpeed(boolean hiPitch) {
        if (plySpeedSound) {

            // Midi pitch bend is required to plySpeed a single drum note
            // at different pitches. The high and low pongs are two
            // octaves apart. As you recall from high school physics,
            // each additional octave doubles the frequency.
            //
            midiChannels[DRUM_MIDI_CHANNEL]
                    .setPitchBend(hiPitch ? MxSpeed_PITCH_BEND : MIN_PITCH_BEND);

            // Turn the reverb send fully off. Drum sounds plySpeed until they
            // decySpeed completely. Reverb extends the audible decySpeed and,
            // from a gameplySpeed point of view, is distracting.
            //
            midiChannels[DRUM_MIDI_CHANNEL]
                    .controlChange(REVERB_LEVEL_CONTROLLER, MIN_REVERB_LEVEL);

            // PlySpeed the claves on the drum channel at a "normal" volume
            //
            midiChannels[DRUM_MIDI_CHANNEL]
                    .noteOn(CLAVES_NOTE, NORMAL_VELOCITY);
        }
    }

    public void toggleSound() {
        plySpeedSound = !plySpeedSound;
    }
}

/** class that provides the information displySpeed */
class GameTimer extends Label {

}

/** class that provides the score displySpeed */
class ScoreDisplySpeed extends Label {

}

/**
 * Game object base class
 * 
 * A Parent class for all objects used in the game
 * Tracks and models position, velocity, speed
 * Also tracks radius, which allows that to change mid-game
 */
abstract class PhysicsObject {

    // position, velocity, speed
    private double x = 0, y = 0, xSpeed = 0, ySpeed = 0, vx = 0, vy = 0;

    // constructor
    public PhysicsObject(
            double x,
            double y,
            double xSpeed,
            double ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    // getters and setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * velocity is the rate and direction
     * of an object's movement
     * 
     * @param vy
     */
    public void setVx(double vx) {
        this.vx = vx;

    }

    /**
     * velocity is the rate and direction
     * of an object's movement
     * 
     * @param vy
     */
    public double getVy() {
        return vy;
    }

    /**
     * velocity is the rate and direction
     * of an object's movement
     * 
     * @param vy
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * velocity is the rate and direction
     * of an object's movement
     * 
     * @param vy
     */
    public double getVx() {
        return vx;
    }

    /**
     * Speed is the time rate at which an object
     * is moving along a path
     * 
     * @return
     */
    public double getSpeedX() {
        return xSpeed;
    }

    /**
     * Speed is the time rate at which an object
     * is moving along a path
     * 
     * @return
     */
    public void setSpeedX(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Speed is the time rate at which an object
     * is moving along a path
     * 
     * @return
     */
    public double getSpeedY() {
        return ySpeed;
    }

    /**
     * Speed is the time rate at which an object
     * is moving along a path
     * 
     * @return
     */
    public void setSpeedY(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    // abstract methods to be implemented by subclasses
    public abstract void update();

    /**
     * Velocity (v) is a vector quantity that measures displacement (or change in
     * position) over the change in time (Δt)
     * 
     * @return the rate and direction of an object's movement
     */
    public abstract double calculateVx();
}

/**
 * Paddle objects is a rectangle.
 * 
 * A paddle is blue
 * A paddle has a width and height
 * A paddle has a position
 * A paddle has a velocity
 * A paddle has a speed
 * 
 * The paddle doesn't move on its own, it is controlled by the user's mouse
 * The paddle can only be moved horizontally and it can't move off the screen
 * The paddle is moved by the user's mouse location in the game window
 * The Paddle does not move off the screen
 */
// class Paddle extends PhysicsObject {}

/**
 * Ball objects is a rectangle that bounces off the top, left, and right bottom
 * screen, bounces off the Paddle object, and falls through the bottom of the
 * screen.
 */
class Ball extends PhysicsObject {

    /**
     * The size of the of "rectangle" that represents the ball this size is
     * used for both the width and the height of the ball
     */
    private static final int BALL_RADIUS = 20;
    // ball is a rectangle
    private static Rectangle ball;
    // Boolean to track if the ball is traveling up or down
    private boolean isBallUp = false;
    // Boolean to track if the ball is traveling left or right
    private boolean isBallLeft = false;

    public static int getBallRadius() {
        return BALL_RADIUS;
    }

    public Rectangle getObject() {
        return ball;
    }

    private boolean isBallUp() {
        return isBallUp;
    }

    public void setBallUp(boolean isBallUp) {
        this.isBallUp = isBallUp;
    }

    public boolean isBallLeft() {
        return isBallLeft;
    }

    public void setBallLeft(boolean isBallLeft) {
        this.isBallLeft = isBallLeft;
    }

    // constructor
    public Ball(
            double x,
            double y,
            double xSpeed,
            double ySpeed) {
        super(x, y, xSpeed, ySpeed);
        ball = new Rectangle(x, y, BALL_RADIUS, BALL_RADIUS);
        ball.setFill(Color.RED);
    }

    @Override
    public void update() {
        setVx(calculateVx());
        setVy(calculateVy());
        ball.setTranslateX(ball.getTranslateX() + getVx());
        ball.setTranslateY(ball.getTranslateY() + getVy());
    }

    @Override
    public double calculateVx() {
        return ((isBallLeft()) ? -getSpeedX() : getSpeedX());
    }

    public double calculateVy() {
        return ((isBallUp()) ? -getSpeedY() : getSpeedY());
    }
}

/**
 * class Pong contains the game model
 * and the game loop it runs in. All the game logic is here.
 * 
 * Game logic is the code that determines the state of the
 * game at any given time.
 * 
 */
class Pong extends Group {

    /**
     * Initialize the ball's speed along the y-axis
     */
    private static final int BALL_INITAL_Y_SPEED = 5;

    private static final int _1_3RD_APP_HEIGHT_FROM_TOP = OPongApp
            .getAppH() / 3;

    // game objects
    // private Paddle paddle;
    private Ball ball = new Ball(
            Math.random() *
                    (OPongApp.getAppW()
                            - Ball.getBallRadius()),
            _1_3RD_APP_HEIGHT_FROM_TOP,
            0,
            BALL_INITAL_Y_SPEED);

    // create game objects
    // paddle = new Paddle(0, 0, 0, 0);

    public Ball getBall() {
        return ball;
    }

    public void startGame() {
        /**
         * The game loop is a loop that runs until game window is closed.
         */
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                // update the game objects
                ball.update();
                if (ball.getObject().getY() > OPongApp
                        .getAppH()- Ball.getBallRadius()) {
                    ball.setBallUp(true);
                }

                if (ball.getObject().getY() < 0) {
                    ball.setBallUp(false);
                }
                    
                
                System.out.println("ball.getTranslateY() = " + ball.getObject().getTranslateY());


            }
        };
        timer.start();
    }
}

/**
 * class PongApp is the main class for the Pong game in MVC architecture
 * this is "the View"
 * 
 * This class is the main class for the Pong game.
 * It is responsible for creating the game window and starting the game.
 */
public class OPongApp extends Application {

    /**
     * The title of the game window.
     */
    private static final String APP_TITLE = "OPong (Pong 2.0)";
    /**
     * Pong game window width
     */
    private static final int APP_W = 800;
    /**
     * Pong game window height
     */
    private static final int APP_H = 600;

    /**
     * start method is the main entry point for the JavaFX application
     * 
     * The start method allows the application to perform any necessary
     * initialization before the primary stage is shown
     * 
     * @param stage is the game window
     * @throws Exception when the game window can't be created
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // create group as root node
        Group root = new Group();

        // scene with root node and size
        Scene scene = new Scene(root, APP_W, APP_H);

        // stage with scene
        primaryStage.setScene(scene);

        // title
        primaryStage.setTitle(APP_TITLE);

        // create game
        Pong pong = new Pong();

        // add game to root node
        root.getChildren().add(pong.getBall().getObject());

        pong.startGame();

        // show stage
        primaryStage.show();
    }

    public static int getAppW() {
        return APP_W;
    }

    public static int getAppH() {
        return APP_H;
    }
}
