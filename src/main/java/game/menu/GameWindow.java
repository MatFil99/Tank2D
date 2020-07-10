package game.menu;

import game.engine.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.text.*;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasa GameWindow konfiguruje gre ( wybiera odpowiedni lvl i gre dla 2 graczy lub przeciwko komputerowi )
 */
public class GameWindow {
    Stage stage;
    int lvl=1;
    boolean multiplayerGame;
    boolean endGame = false;

    ArrayList<String> controlKeysPlayerOne = new ArrayList<String>();
    ArrayList<String> controlKeysPlayerTwo = new ArrayList<String>();
    GameComponents gameComponents;
    GraphicsContext gc;

    /**
     * Ustawienie informacji o rodzaju gry (SINGLEPLAYER lub MULTIPLAYER) oraz o lvl; ponadto pobiera stage na ktorym ma zostac wyswietlona rozgrywka
     * @param stage
     * @param checkedLvl
     * @param multiplayerGame
     */
    GameWindow(Stage stage, int checkedLvl, boolean multiplayerGame){
        this.stage = stage;
        lvl = checkedLvl;
        this.multiplayerGame = multiplayerGame;
    }

    public void setGameScene(){
        Canvas canvas = new Canvas(Map.WIDTH_MAP, Map.HEIGHT_MAP);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        Scene gameScene = new Scene(borderPane,800, 650);
        gc = canvas.getGraphicsContext2D();

        gameComponents = new GameComponents( lvl, multiplayerGame );    // inicjalizacja elementow gier

        control(gameScene);

        if( !multiplayerGame ){
            gameLoopSinglePlayer();
        }else{
            gameLoopTwoPlayer();
        }

        stage.setScene(gameScene);
    }


    /**
     * Ustawia sterowanie - escape wraca do menu glownego
     * @param scene
     */
    public void control( Scene scene ){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //System.out.println(keyEvent.getCode().toString());
                switch (keyEvent.getCode().toString()){
                        //player1
                    case "SPACE":
                        gameComponents.setFirePlayerOne(true);   // ustaw chec strzalu
                        break;
                    case "DOWN":
                    case "UP":
                    case "RIGHT":
                    case "LEFT":
                        controlKeysPlayerOne.add(new String(keyEvent.getCode().toString()));
                        break;
                    case "ESCAPE":
                        Main.startMenuStage(stage);
                        break;
                    default: ;
                }
                if( multiplayerGame ){
                    switch (keyEvent.getCode().toString()){
                        //player2:
                        case "A":
                        case "W":
                        case "S":
                        case "D":
                            controlKeysPlayerTwo.add(new String(keyEvent.getCode().toString()));
                            break;
                        case "G":
                            gameComponents.setFirePlayerTwo(true);
                        default: ;
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Iterator<String> key = controlKeysPlayerOne.iterator();
                while( key.hasNext() ){
                    String stringKey = key.next();
                    if(stringKey.equals(keyEvent.getCode().toString())  ){
                        key.remove();
                    }
                }
                if(keyEvent.getCode().toString() == "SPACE" ){
                    gameComponents.setFirePlayerOne(false);
                }
                if( multiplayerGame ){
                    Iterator<String> keyTwo = controlKeysPlayerTwo.iterator();
                    while(keyTwo.hasNext()){
                        String stringKey = keyTwo.next();
                        if(stringKey.equals(keyEvent.getCode().toString())  ){
                            keyTwo.remove();
                        }
                    }

                    if( keyEvent.getCode().toString() == "G" ){
                        gameComponents.setFirePlayerTwo(false);
                    }
                }
            }
        });
    }

    /**
     * Petla gry przeciwko komputerowi
     */
    public void gameLoopSinglePlayer(){
        AnimationTimer animationTimer =  new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gc.fillRect(0, 0,Map.WIDTH_MAP, Map.HEIGHT_MAP);

                gameComponents.updateTimeTanks(currentNanoTime);
                if( !controlKeysPlayerOne.isEmpty() ){
                    gameComponents.movePlayerOne(controlKeysPlayerOne.get( controlKeysPlayerOne.size()-1));
                }else{
                    gameComponents.movePlayerOne("Empty");
                }

                gameComponents.moveTanks(currentNanoTime);
                gameComponents.moveBullets(currentNanoTime);
                gameComponents.displayComponents( gc );
                gameComponents.checkExplotions(currentNanoTime);
                endGame = gameComponents.checkEndGame( currentNanoTime );
                if(endGame){
                    gameEnding();
                    stop();
                }
            }
        };
        animationTimer.start();

    }

    /**
     * petla gry dla rozgrywki 2 graczy
     */
    public void gameLoopTwoPlayer(  ){
        AnimationTimer animationTimer =  new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                gc.fillRect(0, 0,Map.WIDTH_MAP, Map.HEIGHT_MAP);


                gameComponents.updateTimeTanks(currentNanoTime);
                if( !controlKeysPlayerOne.isEmpty() ){
                    gameComponents.movePlayerOne(controlKeysPlayerOne.get( controlKeysPlayerOne.size()-1));
                }else{
                    gameComponents.movePlayerOne("Empty");
                }
                if( !controlKeysPlayerTwo.isEmpty() ){
                    gameComponents.movePlayersTwo(controlKeysPlayerTwo.get(( controlKeysPlayerTwo.size()-1)));
                }else{
                    gameComponents.movePlayersTwo("Empty");
                }

                gameComponents.moveTanks(currentNanoTime);
                gameComponents.moveBullets(currentNanoTime);

                gameComponents.displayComponents( gc );
                gameComponents.checkExplotions(currentNanoTime);
                endGame = gameComponents.checkEndGame( currentNanoTime );
                if(endGame){
                    gameEnding();
                    stop();
                }
            }
        };
        animationTimer.start();
    }

    /**
     * Koniec rogrywki, wyswietlenie wyniku ( wygrana czy przegrana )
     */
    private void gameEnding(){
        gc.setFont(Font.font("Serif", FontWeight.BOLD, 70));
        gc.setFill(Color.RED);
        gc.fillText(gameComponents.getWinner(), 100, 250);
        gc.setFill(Color.BLUE);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        gc.fillText("Press Escape to continue", 350, 570);
    }
    static final boolean MULTIPLAYER = true;
    static final boolean SINGLEPLAYER = false;
}