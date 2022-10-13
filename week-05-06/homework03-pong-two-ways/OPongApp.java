import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    // to midi messages in a common way.
    //
    // MIDI is a standard for the encoding and transmission
    // of musical sound meta-information, e.g., play this
    // note on this instrument at this level and this pitch
    // for this long.
    //
    private static final int MAX_PITCH_BEND = 16383;
    private static final int MIN_PITCH_BEND = 0;
    private static final int REVERB_LEVEL_CONTROLLER = 91;
    private static final int MIN_REVERB_LEVEL = 0;
    private static final int MAX_REVERB_LEVEL = 127;
    private static final int DRUM_MIDI_CHANNEL = 9;
    private static final int CLAVES_NOTE = 76;
    private static final int NORMAL_VELOCITY = 100;
    private static final int MAX_VELOCITY = 127;
    Instrument[] instrument;
    MidiChannel[] midiChannels;
    boolean playSound;

    public BinkBonkSound() {
        playSound = true;
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
    // The use of midi to play sounds allows us to avoid using external
    // media, e.g., wav files, to play sounds in the game.
    //
    void play(boolean hiPitch) {
        if (playSound) {
            // Midi pitch bend is required to play a single drum note
            // at different pitches. The high and low pongs are two
            // octaves apart. As you recall from high school physics,
            // each additional octave doubles the frequency.
            //
            midiChannels[DRUM_MIDI_CHANNEL]
                    .setPitchBend(hiPitch ? MAX_PITCH_BEND : MIN_PITCH_BEND);
            // Turn the reverb send fully off. Drum sounds play until they
            // decay completely. Reverb extends the audible decay and,
            // from a gameplay point of view, is distracting.
            //
            midiChannels[DRUM_MIDI_CHANNEL]
                    .controlChange(REVERB_LEVEL_CONTROLLER, MIN_REVERB_LEVEL);
            // Play the claves on the drum channel at a "normal" volume
            //
            midiChannels[DRUM_MIDI_CHANNEL]
                    .noteOn(CLAVES_NOTE, NORMAL_VELOCITY);
        }
    }

    public void toggleSound() {
        playSound = !playSound;
    }
}

/** class that provides the information display
 * Displays the game's Frame Rate Per Second (FPS), the Frame Timer in
 * milliseconds, and the number of seconds that have elapsed since the game
 * started.
 */
class GameTimer {
    // calculate the in-game time elapsed
    private long startTime;
    private long lastTime;
    private long currentTime;
    private long elapsedTime;

    // calculate the frame time in milliseconds
    private long lastFrameTime;
    private long currentFrameTime;
    private long frameTime;

    // calculate the frame rate per second
    private long lastFrameRateTime;
    private long currentFrameRateTime;
    private long frameRateTime;
    private long frameCount;
    private long frameRate;

    // method to calculate in-game time elapsed
    public void start() {
        startTime = System.nanoTime();
        lastTime = startTime;
    }

    public void update() {
        currentTime = System.nanoTime();
        // convert to milliseconds from nanoseconds (1_000_000 ns = 1 ms)
        elapsedTime = (currentTime - startTime) / 1_000_000;
        lastTime = currentTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    // method to calculate frame time in milliseconds
    public void startFrame() {
        lastFrameTime = System.nanoTime();
    }

    public void endFrame() {
        currentFrameTime = System.nanoTime();
        // convert to milliseconds from nanoseconds (1_000_000 ns = 1 ms)
        frameTime = (currentFrameTime - lastFrameTime) / 1_000_000;
        lastFrameTime = currentFrameTime;
    }

    public long getFrameTime() {
        return frameTime;
    }

    // method to calculate frame rate per second
    public void startFrameRate() {
        lastFrameRateTime = System.nanoTime();
    }

    public void endFrameRate() {
        currentFrameRateTime = System.nanoTime();
        // convert to milliseconds from nanoseconds (1_000_000 ns = 1 ms)
        frameRateTime = (currentFrameRateTime - lastFrameRateTime) / 1_000_000;
        lastFrameRateTime = currentFrameRateTime;
        frameCount++;
        if (frameRateTime >= 1000) {
            frameRate = frameCount;
            frameCount = 0;
        }
    }

    public long getFrameRate() {
        return frameRate;
    }

    // Method to add a stackpane to the root pane and display the game timer,
    // frame rate, and frame time.
    public void displayGameTimer(StackPane root) {
        // create a stackpane to hold the game timer
        StackPane gameTimerPane = new StackPane();
        gameTimerPane.setPrefSize(200, 50);
        gameTimerPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        gameTimerPane.setPadding(new Insets(10, 10, 10, 10));
        gameTimerPane.setAlignment(Pos.CENTER);

        // create a label to display the game timer
        Label gameTimerLabel = new Label();
        gameTimerLabel.setTextFill(Color.WHITE);
        gameTimerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gameTimerLabel.setText("Game Timer: " + getElapsedTime() + " ms");

        // create a label to display the frame rate
        Label frameRateLabel = new Label();
        frameRateLabel.setTextFill(Color.WHITE);
        frameRateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        frameRateLabel.setText("Frame Rate: " + getFrameRate() + " fps");

        // create a label to display the frame time
        Label frameTimeLabel = new Label();
        frameTimeLabel.setTextFill(Color.WHITE);
        frameTimeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        frameTimeLabel.setText("Frame Time: " + getFrameTime() + " ms");

        // add the labels to the stackpane
        gameTimerPane.getChildren().addAll(gameTimerLabel, frameRateLabel,
                frameTimeLabel);

        // add the stackpane to the root pane
        root.getChildren().add(gameTimerPane);
    }


}

/** class that provides the score display */
class ScoreDisplay {

}

/** the ball */
class Ball {
}

/** the bat */
class Bat {
}

/** the game model */
class Pong {
}

/**
 * the main application
 */
public class OPongApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create group as root node
        Group root = new Group();
        // scene with root node and size
        Scene scene = new Scene(root, 800, 600);
        // stage with scene
        primaryStage.setScene(scene);
        // title
        primaryStage.setTitle("OPong");

        // animation loop to update the ball, bat, score, and game timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        };

        // title
        primaryStage.setTitle("OPong");
        // show stage
        primaryStage.show();


    }

}
