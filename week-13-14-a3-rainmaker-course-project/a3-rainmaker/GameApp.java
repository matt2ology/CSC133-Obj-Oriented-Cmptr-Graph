import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @brief GameApp manages the high-level aspects of our application and
 *        setup and show the initial Scene for your application.
 * 
 * @summary The GameApp class sets up all keyboard event handlers to invoke
 *          public
 *          methods in Game.
 */
public class GameApp extends Application {
    Game game = new Game();

    @Override
    public void start(Stage primaryStage) throws Exception {
        game.setMapBackground(Globals.GAME_MAP_IMAGE);
        primaryStage.setScene(new Scene(
                game,
                Globals.GAME_APP_DIMENSIONS.getWidth(),
                Globals.GAME_APP_DIMENSIONS.getHeight()));
        primaryStage.setTitle(Globals.GAME_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
        game.init();
        game.play();

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // Left Arrow Changes heading of the helicopter to the left.
                if (event.getCode() == KeyCode.LEFT) {
                    game.getHelicopter().steerLeft();
                }

                // Right Arrow Changes heading of the helicopter to the right.
                if (event.getCode() == KeyCode.RIGHT) {
                    game.getHelicopter().steerRight();
                }

                // Up Arrow Increases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.UP) {
                    game.getHelicopter().increaseSpeed();
                }

                // Down Arrow Decreases the speed of the helicopter by 0.1.
                if (event.getCode() == KeyCode.DOWN) {
                    game.getHelicopter().decreaseSpeed();
                }

                // 'i' Turns on the helicopter ignition.
                if (event.getCode() == KeyCode.I) {
                    game.getHelicopter().toggleIgnition();
                }

                // 'b' [optional] shows bounding boxes around objects.
                if (event.getCode() == KeyCode.B) {
                    System.err.println(
                            "B - shows bounding boxes around objects");
                }

                // 'r' Reinitialize the game
                if (event.getCode() == KeyCode.R) {
                    System.err.println();
                    System.err.println("R - Reinitialize the game");
                    game.init();
                }

                // 'space' Seeding the cloud
                if (event.getCode() == KeyCode.SPACE) {
                    System.err.println();
                    System.err.println("SPACE - Seeding the cloud");
                }
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}