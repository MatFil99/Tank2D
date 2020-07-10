package game.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;


/**
 * Klasa tworzaca menu gry:
 * po lewej pojemnik z przyciskami, po prawej miejsce na wyswietlanie dostepnych lvli, sterowania, opisu gry
 */

public class Menu {
    Stage stage;
    GridPane mainGrid = new GridPane();
    VBox buttonBox = new VBox();
    ButtonsMenu buttonsMenu;
    GridPane rightPane = new GridPane();
    Menu(Stage primStage){
        stage = primStage;
        mainGrid.setAlignment(Pos.CENTER);
        mainGrid.setHgap(25);
        mainGrid.setPadding(new Insets(30,30,30,30));
        mainGrid.setBackground(new Background( new BackgroundImage( new Image("background.jpg") ,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        rightPane.setBackground(new Background( new BackgroundImage( new Image("backgroundRightPane.png") ,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));



        buttonBox.setSpacing(20);

        rightPane.setPrefSize(350, 270);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setHgap(25);
        rightPane.setVgap(25);

        buttonsMenu = new ButtonsMenu(rightPane, stage);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttonsMenu.playSingleplayer, buttonsMenu.playMultiplayer, buttonsMenu.levelButton, buttonsMenu.controlButton, buttonsMenu.descrButton);
        mainGrid.add(buttonBox, 0 , 0);
        mainGrid.add(rightPane, 1, 0);
    }
    void showMenu(Scene scene){
        scene.setRoot(mainGrid);
    }

}

class ButtonsMenu{
    Button playSingleplayer = new Button("Single player");
    Button playMultiplayer = new Button("Two players");
    Button levelButton = new Button("Level");
    Button controlButton = new Button("Control");
    Button descrButton = new Button("About Game");
    Stage stage;
    GridPane paneToManage;
    String descr = new String("Gra polega na obronie swojej\nbazy. Nalezy uciekac przed\nostrzalem wroga.");
    int lvl;

    ButtonsMenu(GridPane rightPane, Stage stage){
        lvl = 1;
        this.stage = stage;
        paneToManage = rightPane;
        playSingleplayer.setPrefSize(200, 50);
        playMultiplayer.setPrefSize(200, 50);
        levelButton.setPrefSize(200, 50);
        controlButton.setPrefSize(200, 50);
        descrButton.setPrefSize(200, 50);

        playSingleplayer.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
        playMultiplayer.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
        levelButton.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
        controlButton.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
        descrButton.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");

        initButtonsAction();
    }

    void initButtonsAction(){
        playSingleBAction();
        playMultiBAction();
        levelBAction();
        controlBAction();
        descrBAction();
    }
    void playSingleBAction(){
        playSingleplayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new GameWindow(stage, lvl, GameWindow.SINGLEPLAYER).setGameScene();
            }
        });
    }
    void playMultiBAction(){
        playMultiplayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new GameWindow(stage, lvl, GameWindow.MULTIPLAYER).setGameScene();
            }
        });
    }
    void levelBAction(){
        levelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paneToManage.getChildren().removeAll(paneToManage.getChildren());

                Button level1 = new Button("Lvl 1");
                Button level2 = new Button("Lvl 2");
                Button level3 = new Button("Lvl 3");
                Button level4 = new Button("Lvl 4");
                Button level5 = new Button("Lvl 5");
                Button level6 = new Button("Lvl 6");

               // level1.setStyle("-fx-background: #121244");

                level1.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
                level2.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
                level3.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
                level4.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
                level5.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");
                level6.setStyle("-fx-font: 20px Helvetica; -fx-font-weight: bold; -fx-base: rgb(62,4,4);");


                level1.setPrefSize(paneToManage.getPrefWidth()*0.25, paneToManage.getPrefHeight()*0.25);
                level2.setPrefSize(paneToManage.getPrefWidth()*0.25, paneToManage.getPrefHeight()*0.25);
                level3.setPrefSize(paneToManage.getPrefWidth()*0.25, paneToManage.getPrefHeight()*0.25);
                level4.setPrefSize(paneToManage.getPrefWidth()*0.25, paneToManage.getPrefHeight()*0.25);
                level5.setPrefSize(paneToManage.getPrefWidth()*0.25, paneToManage.getPrefHeight()*0.25);
                level6.setPrefSize(paneToManage.getPrefWidth()*0.25, paneToManage.getPrefHeight()*0.25);

                paneToManage.add(level1, 0 ,0);
                paneToManage.add(level2, 1, 0);
                paneToManage.add(level3, 0, 1);
                paneToManage.add(level4, 1, 1);
                paneToManage.add(level5, 0, 2);
                paneToManage.add(level6, 1, 2);

                choseLvlAction(level1, 1);
                choseLvlAction(level2, 2);
                choseLvlAction(level3, 3);
                choseLvlAction(level4, 4);
                choseLvlAction(level5, 5);
                choseLvlAction(level6, 6);

            }
        });
    }

    void choseLvlAction(Button button, int cLvl){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lvl = cLvl;
            }
        });
    }

    void controlBAction(){
        controlButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paneToManage.getChildren().removeAll(paneToManage.getChildren());

                Text title = new Text("Control:");
                title.setStyle("-fx-font: 30px Verdana; -fx-font-weight: bold");
                title.setFill(Color.rgb(198,198,198));
                Text results = new Text("Player 1" + "\n" + "poruszanie: strzalki" + "\t" + "strzal - spacja" + "\n\n" + "Player 2" + "\n" + "poruszanie: WASD" + "\t" + "strzal: G");
                results.setFill(Color.rgb(198,198,198));
                results.setStyle("-fx-font: 20px Tahoma; -fx-font-weight: 600");

                paneToManage.add(title, 0, 0);
                paneToManage.add(results, 0, 1);
            }
        });
    }
    void descrBAction(){
        descrButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paneToManage.getChildren().removeAll(paneToManage.getChildren());
                Text title = new Text("About game");
                title.setStyle("-fx-font: 30px Verdana; -fx-font-weight: bold");
                title.setFill(Color.rgb(198,198,198));

                Text description = new Text(descr);
                description.setFill(Color.rgb(198,198,198));
                description.setStyle("-fx-font: 20px Tahoma; -fx-font-weight: 600");
                paneToManage.add(title, 0 ,0 );
                paneToManage.add(description,0,1);
            }
        });
    }

}

