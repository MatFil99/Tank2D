package game.engine;

public class Bonus extends SimpleObject {
    int bonusType;

    Bonus( String imagePath, double x, double y){
        super(imagePath);
        setPosition(x, y);

    }

    int randomBonus(){
        return (int) (3*Math.random());
    }

    private void activateBonus(){
        switch(bonusType){
            case 1:
                indestructibleTank();
                break;
            case 2:
                indestructibleBase();
                break;
            case 3:
                powerfulBullet();
                break;
        }
    }

    private void indestructibleTank(){

    }
    private void indestructibleBase(){

    }
    private void powerfulBullet(){

    }

}
