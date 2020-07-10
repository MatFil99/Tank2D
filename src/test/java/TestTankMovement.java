import game.engine.*;

import game.menu.GameComponents;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.*;

import org.junit.Before;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

public class TestTankMovement {
    JFXPanel jfxPanel = new JFXPanel();

    private TankComp tankComp1, tankComp2, tankComp3, tankComp4;
    private TankPlayer tankPlayer, tankPlayer2;
    private ArrayList<TankComp> tanks = new ArrayList<TankComp>();
    private ArrayList<Brick> bricks = new ArrayList<Brick>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<SimpleObject> water = new ArrayList<SimpleObject>();
    private Base baseOne, baseTwo;

    @BeforeEach
    void init(){
        //
        baseOne = new Base( 1 );
        baseTwo = new Base(2);
        tankComp1 = new TankComp( "tank1_down.png", "tank1_up.png", "tank1_right.png", "tank1_left.png", 20,20,2, "explosion.png" );
        tankComp3 = new TankComp("tank1_down.png", "tank1_up.png", "tank1_right.png", "tank1_left.png", 20,60,2, "explosion.png");
        tankComp4 = new TankComp("tank1_down.png", "tank1_up.png", "tank1_right.png", "tank1_left.png", 60,60,2, "explosion.png");
        tankComp2 = new TankComp("tank1_down.png", "tank1_up.png", "tank1_right.png", "tank1_left.png", 60,20,2, "explosion.png");
        tanks.add(tankComp1);
        tanks.add(tankComp2);
        tanks.add(tankComp3);
        tanks.add(tankComp4);
        tankPlayer = new TankPlayer();
    }

    @Test
    void tankRightCollision(){
        double posX = tankComp1.getPositionX();
        double posY = tankComp1.getPositionY();
        tankComp1.moveRight(bricks, tanks , bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);   // kolizja w dol - brak ruchu
        tankComp1.moveRight(bricks, tanks , bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        boolean flag = ( abs( posX - tankComp1.getPositionX()) <= 2.0 );
        assertTrue( flag );
    }

    @Test
    void tankLeftCollision(){
        double posX = tankComp2.getPositionX();
        double posY = tankComp2.getPositionY();
        tankComp2.moveLeft(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        tankComp2.moveLeft(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        boolean flag = ( abs( posX - tankComp2.getPositionX()) <= 2.0 );
        assertTrue( flag );
    }

    @Test
    void tankUpCollision(){
        double posX = tankComp3.getPositionX();
        double posY = tankComp3.getPositionY();
        tankComp3.moveUp(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        tankComp3.moveUp(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        boolean flag = ( abs( posY - tankComp3.getPositionY()) <= 2.0 );
        assertTrue( flag );
    }

    @Test
    void tankDownCollision() { // wykryty blad
        double posX = tankComp1.getPositionX();
        double posY = tankComp1.getPositionY();
        tankComp1.moveDown(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        boolean flag = ( abs( posY - tankComp1.getPositionY()) <= 2.0 );
        assertTrue( flag );
    }

    @Test
    void moveRight(){
        tankComp2.moveRight(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);    // obroc w prawo
        double posX = tankComp2.getPositionX();
        double posY = tankComp2.getPositionY();
        tankComp2.moveRight(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        boolean flag = ( (tankComp2.getPositionX() - posX) == tankComp2.getVelociotyX() );
        assertTrue( flag );
    }

    @Test
    void moveLeft(){
        tankComp2.moveLeft(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);    // obroc w prawo
        double posX = tankComp2.getPositionX();
        double posY = tankComp2.getPositionY();
        tankComp2.moveLeft(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        boolean flag = ( ( posX - tankComp2.getPositionX() ) == tankComp2.getVelociotyX() );
        assertTrue( flag );
    }

    @Test
    void moveUp(){
        tankComp2.moveUp(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);    // obroc w prawo
        double posX = tankComp2.getPositionX();
        double posY = tankComp2.getPositionY();
        tankComp2.moveUp(bricks, tanks, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo, water);
        boolean flag = (  (posY - tankComp2.getPositionY()) == tankComp2.getVelociotyY() );
        assertTrue( flag );
    }

}
