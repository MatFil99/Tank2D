package game.engine;

import java.util.ArrayList;

import static game.engine.Map.HEIGHT_MAP;
import static game.engine.Map.WIDTH_MAP;
import static java.lang.Math.abs;


public class MoveableObject extends SimpleObject {
    double velocityX;
    double velocityY;
    int side;   // direction of movement
    MoveableObject(String imagePath, double x, double y){
        super(imagePath);
        setPosition(x,y);
    }
    protected SimpleObject moveObjectDown(ArrayList<Brick> bricks, ArrayList<TankComp> tanks, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo ){
        if( positionY + velocityY + height > HEIGHT_MAP ){
            setPosition(positionX, HEIGHT_MAP-height);
            return this; // nie ruszamy sie, ale brak kolizji z innymi obiektami
        }
        SimpleObject so = checkCollision(bricks, tanks, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
        if( so == null ) {
            setPosition(positionX, positionY + velocityY);
        }
        return so;
    }

    protected void setSide(int newSide){
        side = newSide;
    }

    protected SimpleObject moveObjectUp(ArrayList<Brick> bricks, ArrayList<TankComp> tanks, ArrayList<Bullet> bullets, TankPlayer tankPlayer1 , TankPlayer tankPlayer2, Base baseOne, Base baseTwo ){
        if( positionY - velocityY < 0 ){
            setPosition(positionX, 0);
            return this;
        }
        SimpleObject so = checkCollision(bricks, tanks, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
        if( so == null ) {
            setPosition(positionX, positionY - velocityY);
        }
        return so;
    }

    protected SimpleObject moveObjectRight(ArrayList<Brick> bricks, ArrayList<TankComp> tanks, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo  ){
        if( positionX + velocityX + height > WIDTH_MAP ){
            setPosition(WIDTH_MAP-height, positionY);
            return this;
        }
        SimpleObject so = checkCollision(bricks, tanks, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo);
        if( so == null ) {
            setPosition(positionX + velocityX , positionY );
        }
        return so;
    }

    protected SimpleObject moveObjectLeft(ArrayList<Brick> bricks, ArrayList<TankComp> tanks, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo ){
        if( positionX - velocityX  < 0 ){
            setPosition(0, positionY);
            return this;
        }
        SimpleObject so = checkCollision(bricks, tanks, bullets, tankPlayer1, tankPlayer2 , baseOne, baseTwo);
        if( so == null ) {
            setPosition(positionX - velocityX, positionY );
        }
        return so;
    }

    protected SimpleObject checkCollision( ArrayList<Brick> bricks, ArrayList<TankComp> tanks, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo ){
        SimpleObject ob;
        switch (side){
            case UP:
                ob = checkCollisionTop(bricks);
                if( ob != null ) return ob;
                ob = checkCollisionTop(tanks);
                if( ob != null ) return ob;
                ob = checkCollisionTop(bullets);
                if( ob != null ) return ob;
                if(this != tankPlayer1 && isCollisionTop(tankPlayer1) ){
                    return tankPlayer1;
                }
                if(this != tankPlayer2 && tankPlayer2 != null && isCollisionTop(tankPlayer2)){
                    return tankPlayer2;
                }
                if(isCollisionTop(baseOne)) return baseOne;
                if( baseTwo != null && isCollisionTop(baseTwo) ) return baseTwo;
                break;
            case DOWN:
                ob = checkCollisionBottom(bricks);
                if( ob != null ) return ob;
                ob = checkCollisionBottom(tanks);
                if( ob != null ) return ob;
                ob = checkCollisionBottom(bullets);
                if( ob != null ) return ob;
                if(this != tankPlayer1 && isCollisionBotton(tankPlayer1) ){
                    return tankPlayer1;
                }
                if(this != tankPlayer2 && tankPlayer2 != null && isCollisionBotton(tankPlayer2) ){
                    return tankPlayer2;
                }
                if(isCollisionBotton(baseOne)) return baseOne;
                if( baseTwo != null && isCollisionBotton(baseTwo) ) return baseTwo;
                break;
            case RIGHT:
                ob = checkCollisionRight(bricks);
                if( ob != null ) return ob;
                ob = checkCollisionRight(tanks);
                if( ob != null ) return ob;
                ob = checkCollisionRight(bullets);
                if( ob != null ) return ob;
                if( this!= tankPlayer1 && isCollisionRight(tankPlayer1)){
                    return tankPlayer1;
                }
                if( this != tankPlayer2 && tankPlayer2 != null && isCollisionRight(tankPlayer2)){
                    return tankPlayer2;
                }
                if(isCollisionRight(baseOne)) return baseOne;
                if( baseTwo != null && isCollisionRight(baseTwo) ) return baseTwo;
                break;
            case LEFT:
                ob = checkCollisionLeft(bricks);
                if( ob != null ) return ob;
                ob = checkCollisionLeft(tanks);
                if( ob != null ) return ob;
                ob = checkCollisionLeft(bullets);
                if( ob != null ) return ob;
                if( this != tankPlayer1 && isCollisionLeft(tankPlayer1)){
                    return tankPlayer1;
                }
                if( this != tankPlayer2 && tankPlayer2 != null && isCollisionLeft(tankPlayer2)){
                    return tankPlayer2;
                }
                if(isCollisionLeft(baseOne)) return baseOne;
                if( baseTwo != null && isCollisionLeft(baseTwo) ) return baseTwo;
                break;
        }
        return null;
    }

    protected SimpleObject checkCollisionBottom(ArrayList<? extends SimpleObject> objects ){
        for( SimpleObject simpleObject: objects){
            if( isCollisionBotton(simpleObject) ){
                this.setPosition( this.positionX, simpleObject.positionY - this.height);
                return simpleObject;
            }
        }
        return null;
    }

    protected SimpleObject checkCollisionTop(ArrayList<? extends SimpleObject> objects){
        for( SimpleObject simpleObject: objects){
            if( isCollisionTop(simpleObject) ) {
                this.setPosition(this.positionX, simpleObject.positionY+simpleObject.height );
                return simpleObject;
            }
        }
        return null;
    }

    protected SimpleObject checkCollisionRight(ArrayList<? extends SimpleObject> objects){
        for( SimpleObject simpleObject: objects ){
            if( isCollisionRight(simpleObject) ) {
                this.setPosition(simpleObject.positionX-this.height, this.positionY );
                return simpleObject;
            }
        }
        return null;
    }

    protected SimpleObject checkCollisionLeft(ArrayList<? extends SimpleObject> objects){
        for( SimpleObject simpleObject: objects ){
            if( isCollisionLeft(simpleObject) ) {
                this.setPosition(simpleObject.positionX+simpleObject.width, this.positionY );
                return simpleObject;
            }
        }
        return null;
    }

    private boolean isCollisionBotton(SimpleObject object){
        return ( ((object.getBoundTop() < this.getBoundBottom()+velocityY && object.getBoundTop() > this.getBoundTop()+velocityY)
                  || (object.getBoundTop() < this.getBoundTop()+velocityY && object.getBoundBottom() > this.getBoundBottom()) ) &&
                ( abs( object.getCenterX()-this.getCenterX())<(object.width+this.width)/2)
                );
    }

    private boolean isCollisionTop(SimpleObject object){
        return ( (this.getBoundTop() - velocityY < object.getBoundBottom() && this.getBoundTop() > object.getBoundTop() ) &&
                ( abs( object.getCenterX()-this.getCenterX())<(object.width+this.width)/2)
                 );
    }

    private boolean isCollisionRight(SimpleObject object){
        return ( (this.getBoundRight() + velocityX > object.getBoundLeft() && this.getBoundRight() < object.getBoundRight()) &&
                 ( abs( object.getCenterY()-this.getCenterY())<(object.height+this.height)/2)
                );
    }

    private boolean isCollisionLeft(SimpleObject object){
        return ( (this.getBoundLeft() - velocityX < object.getBoundRight() && this.getBoundLeft() > object.getBoundLeft()) &&
                ( abs( object.getCenterY()-this.getCenterY())<(object.height+this.height)/2)
                );
    }

    protected void setVelocity( double vX, double vY){
        velocityX = vX;
        velocityY = vY;
    }

    public double getVelociotyX(){ return velocityX; }
    public double getVelociotyY(){ return velocityY; }

    final int UP = 0;
    final int DOWN = 1;
    final int RIGHT = 2;
    final int LEFT = 3;
}
