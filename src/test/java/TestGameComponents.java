import game.engine.Brick;

import game.engine.Bullet;
import game.engine.TankComp;
import game.menu.GameComponents;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.*;

import org.junit.Before;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class TestGameComponents {
    private JFXPanel panel = new JFXPanel();

    private GameComponents gameComponents;

    // inicjalizacja obiektu Brick
    boolean multiplayer = true;    // wybierz rodzaj testowania
    @BeforeEach
    public void createGameComponents(){
        gameComponents = new GameComponents( 1, multiplayer );
    }

    @Test
    public void testCreatingGameComponents_Bricks(){
        ArrayList<Brick> brickArrayList = gameComponents.getBricks();
        boolean flag = !brickArrayList.isEmpty();
        assertTrue( flag );
    }


    @Test
    public void testCreatingGameComponents_TanksComputer(){
        ArrayList<TankComp> tankCompArrayList = gameComponents.getTanksComputer();
        boolean flag;
        if( multiplayer ){  // jesli 2 graczy to bez czolgow komputera
            flag = tankCompArrayList.isEmpty();
        }else{
            flag = !tankCompArrayList.isEmpty();
        }
        assertTrue( flag );
    }

    @Test
    public void testCreatingGameComponents_Bullets(){
        ArrayList<Bullet> bulletArrayListl = gameComponents.getBullets();
        boolean flag = bulletArrayListl.isEmpty();
        assertTrue( flag );
    }

    // testowanie tworzenia jednego gracza
    @Tag("singlePlayer")
    @Test
    public void testCreatingGameComponents_TankPlayerOne(){
        boolean flag = gameComponents.getPlayerOne() != null;
        assertTrue( flag );
    }

    @Tag("singlePlayer")
    @Test
    public void testCreatingGameComponents_BaseEagleOne(){
        boolean flag = gameComponents.getBaseEagleOne() != null;
        assertTrue( flag );
    }

    // testowanie tworzenia dwoch graczy
    @Tag("twoPlayer")
    @Test
    public void testCreatingGameComponents_TankPlayerTwo(){
        boolean flag = gameComponents.getPlayerTwo() != null;
        assertTrue( flag );
    }

    @Tag("twoPlayer")
    @Test
    public void testCreatingGameComponents_BaseEagleTwo(){
        boolean flag = gameComponents.getBaseEagleTwo() != null;
        assertTrue( flag );
    }
}