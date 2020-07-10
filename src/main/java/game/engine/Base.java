package game.engine;

import javafx.scene.image.*;

public class Base extends Brick {
    private Image destroyedBase;
    private boolean destroyed = false;

    public Base( int playerNr){
        super("eagleBase.png", false, POS_X_SP, POS_Y_SP);
        if( playerNr == 2 ){
            setPosition( POS_X_MP, POS_Y_MP);
        }
        destroyedBase = new Image( "destroyed_base.png" );
    }

    public Base(String imagePath, boolean canDestruct, String destroyedBasePath, int playerNr){
        super( imagePath, canDestruct, POS_X_SP, POS_Y_SP);
        if( playerNr == 2 ){
            setPosition( POS_X_MP, POS_Y_MP);
        }
        destroyedBase = new Image(destroyedBasePath);
    }

    public boolean isDestroyed() { return destroyed; }

    public void setDestroyed(){
        destroyed = true;
        image = destroyedBase;
    }

    static final double POS_X_SP = 280;
    static final double POS_Y_SP = 560;
    static final double POS_X_MP = 280;
    static final double POS_Y_MP = 0;
}
