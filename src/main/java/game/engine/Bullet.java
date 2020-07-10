package game.engine;

import java.util.ArrayList;

public class Bullet extends MoveableObject {
    int whoseBullet;
    Bullet(String imagePath, double x, double y, int side, int whoFires){
        super(imagePath, x, y);
        setSide(side);
        whoseBullet = whoFires;
        String system = System.getProperty("os.name");
        if(system.matches("Linux(.*)")){
            setVelocity(1.3, 1.3);    
        }else if(system.matches("Windows(.*)")){
            setVelocity(8, 8);
        }else{
            setVelocity(6, 6);
        }
    }

    public Bullet moveAndHit(ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, TankPlayer tankPlayer1, TankPlayer tankPlayer2, ArrayList<Bullet> bullets, Base baseOne, Base baseTwo, long nanoTime){
        SimpleObject destroyedObject = null;
        switch (side){
            case UP:
                destroyedObject = moveObjectUp(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
                break;
            case DOWN:
                destroyedObject = moveObjectDown(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
                break;
            case RIGHT:
                destroyedObject = moveObjectRight(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
                break;
            case LEFT:
                destroyedObject = moveObjectLeft(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
                break;
        }
        if(destroyedObject != null ){
            this.setVelocity(0, 0);
            if(destroyedObject == this){
                return this;
            }
            if( destroyedObject instanceof Base ){
                Base destrBase = (Base)destroyedObject;
                if( destrBase == baseOne ){
                    baseOne.setDestroyed();
                }else{
                    baseTwo.setDestroyed();
                }
            }else if( destroyedObject instanceof Brick ){
                if(((Brick) destroyedObject).isDestructible()){
                    bricks.remove(destroyedObject);
                }
                this.setVelocity(0, 0);
            }else if( destroyedObject instanceof TankComp && whoseBullet!=Tank.COMPUTER ){
                if(((Tank)destroyedObject).loseLife()){
                    ((TankComp)destroyedObject).showExplotion(nanoTime);
                }
            }else if ( destroyedObject instanceof Bullet ){
                return (Bullet)destroyedObject;
            }else if ( destroyedObject instanceof TankPlayer ){
                TankPlayer tankP = (TankPlayer)destroyedObject;
                if( tankP.loseLife() ){
                    if ( tankP == tankPlayer1 ){
                        tankPlayer1.showExplotion(nanoTime);
                    }else if ( tankP == tankPlayer2 ){
                        tankPlayer2.showExplotion(nanoTime);
                    }
                }
            }
            return this;
        }
        return null;
    }


}
