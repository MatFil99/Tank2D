package game.engine;

import javafx.scene.image.*;
import javafx.scene.canvas.*;

public class SimpleObject {
    public Image image;
    protected double width;
    protected double height;
    protected double positionX;
    protected double positionY;

    SimpleObject(String filePath){
        image = new Image(filePath);
        width = image.getWidth();
        height = image.getHeight();
    }

    public SimpleObject(String filePath, double posX, double posY){
        image = new Image(filePath);
        width = image.getWidth();
        height = image.getHeight();
        setPosition(posX, posY);
    }

    protected void setPosition( double x, double y){
        positionX = x;
        positionY = y;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public double getPositionX(){
        return positionX;
    }

    public void setSize( int w, int h){
        width = w;
        height = h;
    }

    public double getPositionY(){ return positionY; }

    protected double getBoundTop() { return positionY; }
    protected double getBoundBottom() { return (positionY+height); }
    protected double getBoundLeft() { return positionX; }
    protected double getBoundRight() { return positionX+height; }   // bo obiekty sie obracaja!

    protected double getCenterX() { return positionX+width/2; }
    protected double getCenterY() { return positionY+height/2; }
    public void render(GraphicsContext gc){
        gc.drawImage(image, positionX, positionY);
    }

    public Image getImage(){ return image; }
}
