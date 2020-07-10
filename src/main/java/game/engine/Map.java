package game.engine;

import java.util.ArrayList;

public class Map {
    public static final double WIDTH_MAP = 600;
    public static final double HEIGHT_MAP = 600;

    // mapa dla dwoch graczy
    public Map(int lvl, ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<SimpleObject> forest, ArrayList<SimpleObject> water, TankPlayer player1, TankPlayer player2 ){
        createBaseOne(bricks );
        createBaseTwo( bricks );
        setPlayerOne( player1 );
        setPlayerTwo( player2 );
        switch(lvl){
            case 1:
                createMap1(bricks, forest);
                break;
            case 2:
                createMap2(bricks, forest);
                break;
            case 3:
                createMapMultiplayer3(bricks, forest);
                break;
            case 4:
                createMap4(bricks, forest, water);
                break;
            case 5:
                createMap5(bricks, forest, water);
                break;
            case 6:
                createMap6(bricks, forest, water);
                break;
        }
    }

    // mapa dla jednego gracza
    public Map(int lvl, ArrayList<Brick> bricks, ArrayList<TankComp> tankComps, ArrayList<SimpleObject> forest, ArrayList<SimpleObject> water, TankPlayer player1){
        createBaseOne(bricks);
        setPlayerOne( player1 );
        switch(lvl){
            case 1:
                createMap1(bricks, forest);
                createTanksMap1(tankComps, lvl);
                break;
            case 2:
                createMap2(bricks, forest);
                createTanks23(tankComps, lvl);
                break;
            case 3:
                createMapComputer3(bricks, forest);
                createTanks23(tankComps, lvl);
                break;
            case 4:
                createMap4(bricks, forest, water);
                createTanksMap1(tankComps, lvl);
                break;
            case 5:
                createMap5(bricks, forest, water);
                createTanksLvl5(tankComps, lvl);
                break;
            case 6:
                createMap6(bricks, forest, water);
                createTanksMap1(tankComps, lvl);
                break;
        }
    }

    private void createBaseOne(ArrayList<Brick> bricks){
        for( int i = 0; i<4; i++ ){
            for( int j = 0; j<3; ++j){
                if( i==0 || j==0 || i==3 )
                    {
                        bricks.add( new Brick("brick.png", true, 260+20*i, 540+20*j));
                    }
            }
        }
    }

    private void createBaseTwo(ArrayList<Brick> bricks){
        for( int i = 0; i<4; i++ ){
            for( int j = 0; j<3; ++j){
                if( i==0 || j==2 || i==3 )
                {
                    bricks.add( new Brick("brick.png", true, 260+20*i, 20*j));
                }
            }
        }
    }

    private void setPlayerOne( TankPlayer player1 ){
        player1.setLife(2);
        player1.setPosition( 230, 560 );
    }
    private void setPlayerTwo( TankPlayer player2 ){
        player2.setLife(2);
        player2.setPosition(340, 10);
        player2.setTankImages( "tank3_down.png","tank3_up.png",  "tank3_right.png", "tank3_left.png");
    }

    private void createMap1(ArrayList<Brick> bricks, ArrayList<SimpleObject> forest){
        for( int i = 0; i < 24; i++ ){
            if( i != 18 && i != 19){
                bricks.add(new Brick("wall.png", false, 40, 60+20*i));
                bricks.add(new Brick("wall.png", false, 540, 60+20*i));
            }else{
                bricks.add(new Brick("brick.png", true, 40, 60+20*i));
                bricks.add(new Brick("brick.png", true, 540, 60+20*i));
            }
        }

        for( int i = 0; i<4; i++){
            bricks.add(new Brick("wall.png", false, 60+i*20, 160));
            bricks.add(new Brick("wall.png", false, 60+i*20, 180));
            bricks.add(new Brick("brick.png", true, 140+i*20, 160));
            bricks.add(new Brick("brick.png", true, 140+i*20, 180));

            bricks.add(new Brick("wall.png", false, 520-i*20, 160));
            bricks.add(new Brick("wall.png", false, 520-i*20, 180));
            bricks.add(new Brick("brick.png", true, 440-i*20, 160));
            bricks.add(new Brick("brick.png", true, 440-i*20, 180));
        }
        bricks.add(new Brick("wall.png", false, 100, 200));
        bricks.add(new Brick("wall.png", false, 120, 200));

        for(int i=0; i<8; i++ ){
            for( int j=0; j<8; j++){
                if((i<2 && j<2) || (i>5 && j>5)){
                    bricks.add(new Brick("wall.png", false, 220+j*20, 160+i*20));
                }else if((i<2 && j>5) || (i>5 && j<2)){
                    bricks.add(new Brick("brick.png", true, 220+j*20, 160+i*20));
                }else if(i%2 == 0 && j%2==0){
                    forest.add(new SimpleObject("forest.png", 220+j*20, 160+i*20));
                }
            }
        }

        for(int i=0; i<8; i++){
            if( i == 6 || i == 7){
                bricks.add(new Brick("brick.png", true, 100+i*20, 460));
                bricks.add(new Brick("wall.png", false, 340+i*20, 460));
            }else{
                bricks.add(new Brick("wall.png", false, 100+i*20, 460));
                bricks.add(new Brick("brick.png", true, 340+i*20, 460));
            }
        }
        bricks.add(new Brick("wall.png", false, 240, 480));
        bricks.add(new Brick("wall.png", false, 240, 500));
        bricks.add(new Brick("brick.png", true, 340, 480));
        bricks.add(new Brick("brick.png", true, 340, 500));
    }

    private void createMap2(ArrayList<Brick> bricks, ArrayList<SimpleObject> forest){
        for ( int i=0; i<25; i++){
            for ( int j=0; j<25; j++){
                if( ((j>=i && j<25-i) && i%3==0 || (j%3==0 && (j<i || j>=25-i) && !(j>=25-i && j<=i)) || (i%3==0 && (j>=25-i && j<=i)) )  ){
                    bricks.add(new Brick("brick.png", true, 50+20*j, 50+20*i));
                }
            }
        }
    }

    private void createMapComputer3(ArrayList<Brick> bricks, ArrayList<SimpleObject> forest){
        for ( int i=0; i<25; i++){
            for ( int j=0; j<25; j++){
                if( ((j>=i && j<25-i) && i%3==0 || (j%3==0 && (j<i || j>=25-i) && !(j>=25-i && j<=i)) || (i%3==0 && (j>=25-i && j<=i)) )  ){
                    if( i%3==0 ){
                        bricks.add(new Brick("wall.png", false, 50+20*j, 50+20*i));
                    }else{
                        bricks.add(new Brick("brick.png", true, 50+20*j, 50+20*i));
                    }
                }
            }
        }
        bricks.add(new Brick("brick.png", true, 400 ,580 ));
        bricks.add(new Brick("brick.png", true, 400 ,560 ));
        bricks.add(new Brick("brick.png", true, 420 ,580 ));
        bricks.add(new Brick("brick.png", true, 420 ,560 ));
    }

    private void createMapMultiplayer3(ArrayList<Brick> bricks, ArrayList<SimpleObject> forest){
        for ( int i=0; i<25; i++){
            for ( int j=0; j<25; j++){
                if( ((j>=i && j<25-i) && i%3==0 || (j%3==0 && (j<i || j>=25-i) && !(j>=25-i && j<=i)) || (i%3==0 && (j>=25-i && j<=i)) )  ){
                    if( i%3==0 && ( (i==0 || i==24) && j!=6 && j!=7 && j!=18 && j!=17)  ){
                        bricks.add(new Brick("wall.png", false, 50+20*j, 50+20*i));
                    }else{
                        bricks.add(new Brick("brick.png", true, 50+20*j, 50+20*i));
                    }
                }
            }
        }
    }

    private void createMap4(ArrayList<Brick> bricks, ArrayList<SimpleObject> forest, ArrayList<SimpleObject> water){
        for( int i=0; i<15; ++i ){
            if(i!=9)
                water.add(new SimpleObject("water.png", 40*i,150));
        }
        for( int i=0; i<15; ++i ){
            bricks.add(new Brick("brick.png", true, 40*i,350+(i%3)*20));
            bricks.add(new Brick("brick.png", true, 40*i+20,350+(i%3)*20));
            bricks.add(new Brick("brick.png", true, 40*i,370+(i%3)*20));
        }
        for( int i=0; i<15; ++i ){
            forest.add(new SimpleObject("forest.png", 40*i, 210));
            forest.add(new SimpleObject("forest.png", 40*i, 250));
        }
    }

    private void createMap5(ArrayList<Brick> bricks, ArrayList<SimpleObject> forest, ArrayList<SimpleObject> water){
        for ( int i=0; i<22; i++){
            for ( int j=0; j<22; j++){
                if( ((j>=i && j<22-i) && i%3==0 || (j%3==0 && (j<i || j>=22-i) && !(j>=22-i && j<=i)) || (i%3==0 && (j>=22-i && j<=i)) )  ){
                    water.add(new SimpleObject("water_small.png", 80+20*j, 60+20*i));
                }
            }
        }
    }

    private void createMap6(ArrayList<Brick> bricks, ArrayList<SimpleObject> forest, ArrayList<SimpleObject> water){
        for ( int i=0; i<25; i++){
            for ( int j=0; j<25; j++){
                if( i<13 && ((j>=i && j<25-i) && i%3==0 || (j%3==0 && (j<i || j>=25-i) && !(j>=25-i && j<=i)) || (i%3==0 && (j>=25-i && j<=i)) )  ){
                    bricks.add(new Brick("brick.png", true, 50+20*j, 50+20*i));
                }
            }
            if((i+1)%3 != 0) water.add(new SimpleObject("water.png", 60*i+10, 280));
        }
    }

    private void createTanksMap1(ArrayList<TankComp> tankComps, int lvl){
        lvl += 1;
        if(lvl>2) lvl = 2;
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 5, 65, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 45, 25, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 345, 35, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 65, 125, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 65, 205, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 225, 125, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 385, 105, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 415, 255, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 185, 245, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 315, 65, lvl, "explosion.png"));
        tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 565, 125, lvl, "explosion.png"));
    }

    private void createTanksLvl5( ArrayList<TankComp> tankComps, int lvl){
        if( lvl >2 ) lvl = 3;
        for( int i=0; i<10; i++){
            tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 20+50*i, 10, lvl, "explosion.png"));
        }
    }

    private void createTanks23(ArrayList<TankComp> tankComps, int lvl){
        lvl++;
        if(lvl>2) lvl = 2;
        int nrTanks = 11;
        if( lvl-1 == 2){
            nrTanks = 9;
        }
        for (int i=0; i<10; i++ ){
            tankComps.add(new TankComp("tank2_down.png", "tank2_up.png", "tank2_right.png", "tank2_left.png", 12+60*i, 12+60*i, lvl, "explosion.png"));
        }
    }

}
