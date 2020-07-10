package game.engine;

import java.util.ArrayList;

public class TankComp extends Tank {
    // wybor akcji: nic, strzal, ruch
    final int chanceToFire = 100;    //
    final int chanceToNothing = 98; //
    final int chanceToMove = 40;    //
    int sumChances = 100;
    double currentRandom = 0;

    // wybor kierunku: lewo, prawo, gora, dol
    final int MAXMOVEDIREC = 10;
    int moveCounter = 0;    // liczba wykonanych ruchow w danym kierunku
    int lotLeft = 1;
    int lotRight = 1;
    int lotDown = 1;
    int lotUp = 1;
    int sumLots = 4;
    final int MAXSUMLOTS = 10;
    double currentLot;

    public TankComp(String tankDownPath, String tankUpPath, String tankRightPath, String tankLeftPath, double x, double y, int lifes, String destroyedTank){
        super(tankDownPath, tankUpPath, tankRightPath, tankLeftPath, x, y, lifes, destroyedTank, Tank.COMPUTER);
    }

    public void autoAction(ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, TankPlayer tankPlayer1, TankPlayer tankPlayer2, ArrayList<Bullet> bullets, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water){
        currentRandom = sumChances*Math.random();
        if( currentRandom < chanceToMove ){
            choseDirection(bricks, tankComps, tankPlayer1, tankPlayer2, bullets, baseOne, baseTwo, water);
        }else if ( currentRandom < chanceToNothing ){
            // nothing
        }else{
            this.tryFire(bullets);
        }
        if ( checkPlayerOnLineOfFire(tankPlayer1) ){
            if( currentRandom < sumChances*3/4 ){
                this.tryFire( bullets );
            }
        }
    }

    void choseDirection( ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, TankPlayer tankPlayer1, TankPlayer tankPlayer2, ArrayList<Bullet> bullets, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water ){
        if(moveCounter<MAXMOVEDIREC){
            SimpleObject so = null;
            so = moveTank(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);    // wykonaj ruch bez losowania
            if ( so != null ){
                switch ( side ){
                    case LEFT:
                        lotLeft /=2+1;
                        break;
                    case RIGHT:
                        lotRight /=2+1;
                        break;
                    case UP:
                        lotUp /=2+1;
                        break;
                    case DOWN:
                        lotDown /=2+1;
                        break;
                }
            }
            ++moveCounter;
        }else {
            SimpleObject so = null;
            moveCounter = 0;
            currentLot = sumLots * Math.random();
            if (currentLot < lotLeft) {
                so = moveLeft(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);
                if ( so == null ){
                    //lotLeft += 1;
                    checkPathToBase( baseOne );
                }else{
                    lotLeft = 1;
                    lotUp += 2;
                    lotDown += 2;
                }
            } else if (currentLot < lotLeft + lotRight) {
                so = moveRight(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo,water);
                if (so == null )
                {
                    //lotRight += 1;
                    checkPathToBase( baseOne );
                }else{
                    lotRight = 1;
                    lotUp += 2;
                    lotDown += 2;
                }

            } else if (currentLot < lotLeft + lotRight + lotDown) {
                so = moveDown(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);
                if( so==null){
                    //lotDown += 1;
                    checkPathToBase( baseOne );
                }else{
                    lotDown = 1;
                    lotLeft += 2;
                    lotRight += 2;
                }
            } else {
                so = moveUp(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);
                if( so==null ){
                    //lotUp += 1;
                    checkPathToBase( baseOne );
                }else{
                    lotUp = 1;
                    lotLeft += 2;
                    lotRight += 2;
                }
            }
            sumLots = lotDown + lotRight + lotUp + lotLeft;
            if (sumLots > MAXSUMLOTS) {
                resetLots();
            }
        }
    }

    void resetLots(){
        if (lotLeft>lotRight){
            lotRight = 1;
        }else{
            lotLeft = 1;
        }
        if ( lotUp > lotDown ){
            lotDown = 1;
        }else{
            lotUp = 1;
        }
        sumLots = 4;
    }

    SimpleObject moveTank(ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<Bullet> bullets, TankPlayer tankPlayer1, TankPlayer tankPlayer2, Base baseOne, Base baseTwo, ArrayList<SimpleObject> water){
        SimpleObject so=null;
        switch(side){
            case LEFT:
                so=moveLeft(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);
                break;
            case RIGHT:
                so=moveRight(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);
                break;
            case UP:
                so=moveUp(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);
                break;
            case DOWN:
                so=moveDown(bricks, tankComps, bullets, tankPlayer1, tankPlayer2, baseOne, baseTwo, water);
        }
        return so;
    }

    boolean checkPlayerOnLineOfFire( TankPlayer tankPlayer ){
        if ( this.getCenterX()+20<tankPlayer.getCenterX() && this.getCenterX() - 20 > tankPlayer.getCenterX() && ((side==UP && this.getCenterY()>tankPlayer.getCenterY()) || (side==DOWN && this.getCenterY()<tankPlayer.getCenterY()))  ) return true;
        if (this.getCenterY()+20<tankPlayer.getCenterY() && this.getCenterY() - 20 > tankPlayer.getCenterY() && ((side==LEFT && this.getCenterX()>tankPlayer.getCenterX()) || (side==RIGHT && this.getCenterX()<tankPlayer.getCenterX())) ) return true;
        return false;
    }

    void checkPathToBase (Base base){
        if ( this.getCenterX() > base.getCenterX() ) lotLeft +=2;
        if ( this.getCenterX() < base.getCenterX() ) lotRight +=2;
        if ( this.getCenterY() < base.getCenterY() ) lotDown +=2;
    }

}
