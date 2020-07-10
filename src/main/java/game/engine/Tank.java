package game.engine;

import javafx.scene.image.*;

import java.util.ArrayList;

public class Tank extends MoveableObject {
    private int whoseTank;
    private int life;
    public boolean destroyed;
    public Image destroyedTank;
    public Image tankDown;
    public Image tankUp;
    public Image tankLeft;
    public Image tankRight;

    public long timeDestroyTank;
    public long elapsedTimeFire;
    public long strikeTimer;
    boolean canFire;    // if elapsed time enough to next strike - it blocks striking as minigun



    Tank( String tankDownPath, String tankUpPath, String tankRightPath, String tankLeftPath , double x, double y ,int numberLife, String destroyedTankPath, int whoseTank ){
        super(tankDownPath, x, y);  // pierwotnie wyswietlany obrazek
        life = numberLife;
        tankLeft = new Image(tankLeftPath);
        tankRight = new Image(tankRightPath);
        tankUp = new Image(tankUpPath);
        tankDown = new Image(tankDownPath);
        destroyedTank = new Image(destroyedTankPath);
        side = DOWN;

        this.whoseTank = whoseTank;

        String system = System.getProperty("os.name");
        if(system.matches("Linux(.*)")){
            setVelocity(0.35, 0.35);    
        }else if(system.matches("Windows(.*)")){
            setVelocity(2, 2);
        }else{
            setVelocity(3, 3);
        }
    }

    public void setLife( int nrlifes){
        life = nrlifes;
    }

    public void setTankImages(String tankDownPath, String tankUpPath, String tankRightPath, String tankLeftPath ){
        tankDown = new Image(tankDownPath);
        tankUp = new Image(tankUpPath);
        tankLeft = new Image(tankLeftPath);
        tankRight = new Image(tankRightPath);
        image = tankDown;
    }

    protected boolean loseLife(){
        life -= 1;
        return isDestroyed();
    }

    public boolean isDestroyed(){ return life <= 0; }

    public void showExplotion( long currentNanoTime ){
        setVelocity(0,0);
        setSize(0, 0);
        image = destroyedTank;
        timeDestroyTank = currentNanoTime;
    }

    public boolean endExplotion( long nanoTime ){   // odlicza czas od czasu eksplozji - czy eksplozja ma zniknac
        if( (nanoTime - timeDestroyTank)/1000000 < 140) return false;
        else return true;
    }

    public void updateTimeFire(long currentNanoTime){
        elapsedTimeFire = (currentNanoTime - strikeTimer)/1000000;
        if(elapsedTimeFire > STRIKE_WAITING ){
            canFire = true;
        }
        if(canFire == true){
            strikeTimer = currentNanoTime;
        }
    }

    public void tryFire(ArrayList<Bullet> bullets){
        if(canFire) fire(bullets);
    }

    public void fire(ArrayList<Bullet> bullets){
        // create Bullet and add to Bullets collection
        canFire = false;
        Bullet bullet;
        if(side == UP || side == DOWN ){
            bullet = new Bullet("bullet_vertical.png", 0, 0, this.side, whoseTank);
        }else{
            bullet = new Bullet("bullet_horizontal.png", 0, 0, this.side, whoseTank);
        }

        switch (side){
            case UP:
                bullet.setPosition(positionX+this.width/2-bullet.width/2, positionY-bullet.height);
                break;
            case DOWN:
                bullet.setPosition(positionX+this.width/2-bullet.width/2, positionY+this.height);
                break;
            case RIGHT:
                bullet.setPosition(positionX+this.width, positionY+this.height/2-bullet.height/2);
                break;
            case LEFT:
                bullet.setPosition(positionX-bullet.width, positionY+this.height/2-bullet.height/2);
        }
        bullets.add(bullet);
    }

    public SimpleObject moveUp(ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water){
        SimpleObject so = null;
        image = tankUp;
        if(side==UP && !checkCollisionWater(water)){
            so=super.moveObjectUp(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
        }
        side = UP;
        return so;
    }

    public SimpleObject moveDown(ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water){
        SimpleObject so = null;
        image = tankDown;
        if(side == DOWN && !checkCollisionWater(water)) {
            so=super.moveObjectDown(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
        }
        side = DOWN;
        return so;
    }

    public SimpleObject moveRight(ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water){
        SimpleObject so = null;
        image = tankRight;
        if(side == RIGHT && !checkCollisionWater(water)) {
            so=super.moveObjectRight(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
        }
        side = RIGHT;
        return so;
    }

    public SimpleObject moveLeft(ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<Bullet> bullets, TankPlayer tankPlayer, TankPlayer tankPlayer2, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water){
        SimpleObject so = null;
        image = tankLeft;
        if(side == LEFT && !checkCollisionWater(water)) {
            so=super.moveObjectLeft(bricks, tankComps, bullets, tankPlayer, tankPlayer2, baseOne, baseTwo);
        }
        side = LEFT;
        return so;
    }

    protected boolean checkCollisionWater(ArrayList<SimpleObject> water){
        switch(side){
            case UP:
                return checkCollisionTop(water)!=null;
            case DOWN:
                return checkCollisionBottom(water)!=null;
            case RIGHT:
                return checkCollisionRight(water)!=null;
            case LEFT:
                return checkCollisionLeft(water)!=null;
        }
        return false;
    }

    final long STRIKE_WAITING = 600; // milisekundy
    final long MOVE_WAITING = 200;

    static final int COMPUTER=0;
    static final int PLAYER=1;
}