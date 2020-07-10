package game.menu;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*;

/**
 * Klasa wlacza aplikacje javafx i tworzy menu, ktore wyswietla
 * @author Filip
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        startMenuStage(primaryStage);
    }

    static public void startMenuStage( Stage primaryStage ){
        Menu menu = new Menu(primaryStage);
        Scene scene = new Scene(new Pane(), 800, 650);
        menu.showMenu(scene);

        primaryStage.setTitle("Tank 2D");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}