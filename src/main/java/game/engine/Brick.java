package game.engine;

public class Brick extends SimpleObject{
    boolean destructible;

    public Brick (String imagePath, boolean canDestruct, double x, double y){
        super(imagePath);
        destructible = canDestruct;
        setPosition(x,y);
    }

    protected boolean isDestructible(){
        return destructible;
    }

    public void setDestructible( boolean newValue ){
        destructible = newValue;
    }

}
