package game.engine;

import java.util.ArrayList;

public class TankPlayer extends Tank {
    boolean strike;

    long timeDestroyTank;

    public TankPlayer( ){
        super("tank1_down.png", "tank1_up.png", "tank1_right.png", "tank1_left.png", 0, 0, 0, "explosion.png", Tank.PLAYER);
    }

    public TankPlayer(String tankDownPath, String tankUpPath , String tankRightPath, String tankLeftPath, double x, double y, int lifes, String destroyedTankPath){
        super(tankDownPath, tankUpPath, tankRightPath, tankLeftPath, x, y, lifes, destroyedTankPath, Tank.PLAYER);
    }

    public void setStrike( boolean value){
        strike = value;
    }

    public boolean ifStrike(){
        return strike;
    }

    public void tryFire(ArrayList<Bullet> bullets){
        if(strike){
            super.tryFire(bullets);
        }
    }


    public void movePlayerOne( String keyCode, ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<Bullet> bullets, TankPlayer tankPlayerOne, TankPlayer tankPlayerTwo, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water ){
        switch (keyCode){
            case "DOWN":
                moveDown(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
            case "UP":
                moveUp(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
            case "RIGHT":
                moveRight(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
            case "LEFT":
                moveLeft(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
        }
    }

    public void movePlayerTwo(String keyCode, ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<Bullet> bullets, TankPlayer tankPlayerOne, TankPlayer tankPlayerTwo, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water ){
        switch (keyCode){
            case "S":
                moveDown(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
            case "W":
                moveUp(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
            case "D":
                moveRight(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
            case "A":
                moveLeft(bricks, tankComps, bullets, tankPlayerOne, tankPlayerTwo, baseOne, baseTwo, water );
                break;
        }
    }
}
