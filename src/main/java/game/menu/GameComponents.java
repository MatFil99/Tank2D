package game.menu;
import game.engine.*;

import javafx.scene.canvas.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasa zawiera komponenty, ktore biora udzial w rogrywce oraz jej rezultat ( kto wygral )
 */
public class GameComponents {
    protected boolean endGame = false;
    private String winner;
    protected ArrayList<Brick> bricks = new ArrayList<Brick>();
    protected ArrayList<TankComp> tanksComputer = new ArrayList<TankComp>();
    protected ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    protected ArrayList<SimpleObject> forest = new ArrayList<SimpleObject>();
    protected ArrayList<SimpleObject> water = new ArrayList<SimpleObject>();
    protected TankPlayer playerOne;
    protected TankPlayer playerTwo = null;
    protected Base baseOne, baseTwo = null;

    /**
     * Utworzenie odpowiedniej rozgrywki - z jednym graczem, badz dwoma
     * @param lvl
     * @param multiplayer
     */
    public GameComponents( int lvl, boolean multiplayer ){
       if( multiplayer ){
            playerOne = new TankPlayer();
            playerTwo = new TankPlayer();
            baseOne = new Base( 1 );
            baseTwo = new Base( 2 );
            new Map(lvl, bricks, tanksComputer, forest, water, playerOne, playerTwo);
        }else if ( !multiplayer ){
            playerOne = new TankPlayer( );
            baseOne = new Base( 1 );
            new Map(lvl, bricks, tanksComputer, forest, water, playerOne);
        }
    }


    // gettery
    public ArrayList<Brick> getBricks(){ return bricks; }
    public ArrayList<TankComp> getTanksComputer(){ return tanksComputer; }
    public ArrayList<Bullet> getBullets(){ return bullets; }
    public TankPlayer getPlayerOne(){ return playerOne; }
    public TankPlayer getPlayerTwo(){ return playerTwo; }
    public Base getBaseEagleOne(){ return baseOne; }
    public Base getBaseEagleTwo(){ return baseTwo; }


    public void moveTanks(long nanoTime){
        for( TankComp tankComp: tanksComputer){
            if( !tankComp.isDestroyed()) {
                tankComp.autoAction(bricks, tanksComputer, playerOne, playerTwo, bullets, baseOne, baseTwo, water);
            }
        }
    }

    public void checkExplotions(long nanoTime){
        Iterator<TankComp> tankCompIterator = tanksComputer.iterator();
        while( tankCompIterator.hasNext() ){
            TankComp tank = tankCompIterator.next();
            if( tank.isDestroyed() && tank.endExplotion(nanoTime) ){
                tankCompIterator.remove();
            }
        }
    }

    public boolean checkEndGame( long nanoTime ){
        if( tanksComputer.isEmpty() && playerTwo == null ){
            endGame = true;
            winner = new String ("You win!");
            return true;
        }
        if( playerOne.isDestroyed() && playerOne.endExplotion(nanoTime) ){
            endGame = true;
            if(playerTwo!=null){
                winner = new String("Player 2 win");
            }else{
                winner = new String("You lost");
            }
            return true;
        }
        if( playerTwo != null && playerTwo.isDestroyed() && playerTwo.endExplotion(nanoTime)){
            endGame = true;
            winner = new String("Player 1 win");
            return true;
        }
        if( baseOne.isDestroyed() ){
            endGame = true;
            if( playerTwo != null ){
                winner = new String("Player 2 win");
            }else
            {
                winner = new String("You lost");
            }
            return true;
        }else if( baseTwo != null && baseTwo.isDestroyed() ){
            endGame = true;
            winner = new String("Player 1 win");
            return true;
        }
        return false;
    }

    public void moveBullets( long nanoTime ){
        Bullet secondDestroyedBullet = null;
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            Bullet bulletHit = bullet.moveAndHit(bricks, tanksComputer, playerOne, playerTwo, bullets, baseOne, baseTwo, nanoTime);
            if(bulletHit != null){
                bulletIterator.remove();
                secondDestroyedBullet = bulletHit;
            }
        }
        if( bullets.contains(secondDestroyedBullet) ){
            bullets.remove(secondDestroyedBullet);
        }
    }

    public void movePlayerOne( String keyCode ){
        playerOne.tryFire(bullets);
        playerOne.movePlayerOne( keyCode, bricks, tanksComputer, bullets, playerOne, playerTwo, baseOne, baseTwo, water );
    }

    public void movePlayersTwo(String keyCode){
        playerTwo.tryFire( bullets );
        playerTwo.movePlayerTwo( keyCode, bricks, tanksComputer, bullets, playerOne, playerTwo, baseOne, baseTwo, water );
    }

    public void setFirePlayerOne(boolean fireFlag){
        playerOne.setStrike( fireFlag );
    }

    public void setFirePlayerTwo( boolean fireFlag){
        playerTwo.setStrike(fireFlag);
    }

    public void updateTimeTanks( long nanoTime){
        playerOne.updateTimeFire( nanoTime );
        if( playerTwo != null){
            playerTwo.updateTimeFire( nanoTime );
        }
        for ( TankComp tank: tanksComputer ){
            tank.updateTimeFire(nanoTime);
        }
    }

    public void displayComponents( GraphicsContext gc){
        for ( TankComp tank: tanksComputer ){
            tank.render(gc);
        }
        for ( Brick brick: bricks ){
            brick.render(gc);
        }
        for (SimpleObject lake: water){
            lake.render(gc);
        }
        for ( Bullet bullet: bullets ){
            bullet.render(gc);
        }
        playerOne.render(gc);
        if( playerTwo != null ){
            playerTwo.render(gc);
        }
        for( SimpleObject tree: forest ){
            tree.render(gc);
        }
        baseOne.render(gc);
        if(baseTwo != null ){
            baseTwo.render(gc);
        }
    }

    protected String getWinner(){ return winner; }
}
