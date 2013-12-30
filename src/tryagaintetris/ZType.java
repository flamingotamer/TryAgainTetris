package tryagaintetris;

//Done! :D

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ZType extends Polygon implements Drawable, ActionListener{ //Yay! Done! :D

    private Timer tim = new Timer(10, this);
    private int dx = 350, dy = 700;
    private boolean completelyEmpty, hardDrop = false;
    private String direction;
    private boolean nothingBelow, nothingToTheRight, nothingToTheLeft;
    
    public ZType(int[] xpoints, int[] ypoints, int npoints){
        super(xpoints, ypoints, npoints);
        tim.setDelay(500);
        tim.start();
    }
    
    public void gameOver(){
        tim.stop();
    }
    
    public String getDirection(){
        if(this.ypoints[0] == this.ypoints[1]) direction = "horizontal";
        else direction = "vertical";
        return direction;
    }
    
    public void rotateARGH(){
        if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false){
            if(this.getDirection().equals("horizontal") && !hardDrop){
               this.xpoints[0]+= 105; this.ypoints[0]-= 35;
               this.xpoints[1]+= 70;
               this.xpoints[2]+= 35; this.ypoints[2]+= 35;
               this.xpoints[4]-= 35; this.ypoints[4]+= 35;
               this.xpoints[5]-= 70;
               this.xpoints[6]-= 35; this.ypoints[6]-= 35;
               this.ypoints[7]-=70;
               this.xpoints[8]+= 35; this.ypoints[8]-=35;
               this.xpoints[9]+= 70; this.ypoints[9]-=70;
           }else if(this.getDirection().equals("vertical") && checkdx("left", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && !hardDrop){
               this.xpoints[0]-= 105; this.ypoints[0]+= 35;
               this.xpoints[1]-= 70;
               this.xpoints[2]-= 35; this.ypoints[2]-= 35;
               this.xpoints[4]+= 35; this.ypoints[4]-= 35;
               this.xpoints[5]+= 70;
               this.xpoints[6]+= 35; this.ypoints[6]+= 35;
               this.ypoints[7]+=70;
               this.xpoints[8]-= 35; this.ypoints[8]+=35;
               this.xpoints[9]-= 70; this.ypoints[9]+=70;
           }
        }
    }
    
    public int checkdx(String direction, Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){
        nothingToTheRight = true; nothingToTheLeft = true;
        int dx = 350, pt = 0 , ptStart = 0, goOut = 0;
        
        if(direction.equals("right")){
            
            if(this.getDirection().equals("horizontal")){
                pt = 2;
                ptStart = 2;
                goOut = 2;
            }else if(this.getDirection().equals("vertical")){
                pt = 0; 
                ptStart = 0;
                goOut = 3;
            }
            
            for(int n = this.ypoints[ptStart]/35; n< (this.ypoints[ptStart]/35)+goOut; n++){
                for(int j = this.xpoints[pt]/35; j < 10; j++){
                    if(!booleanBlocksArray[n][j]){ 
                        nothingToTheRight = false;
                        if(blocksArray[n][j].x - this.xpoints[pt] < dx){
                            dx = blocksArray[n][j].x - this.xpoints[pt];
                            break;
                        }
                    }
                }
                if(nothingToTheRight && 350 - this.xpoints[pt] < dx) dx = 350 - this.xpoints[pt];
                
                if(ptStart == 2 && pt == 2) pt+=2;
                else pt++;
            }
        }else if(direction.equals("left")){
            
            if(this.getDirection().equals("horizontal")){
                pt = 0;
                ptStart = 0;
                goOut = 2;
            }else if(this.getDirection().equals("vertical")){
                pt = 9;
                ptStart = 9;
                goOut = 3;
            }
            
            for(int n = this.ypoints[ptStart]/35; n < (this.ypoints[ptStart]/35)+goOut; n++){
                for(int j = this.xpoints[pt]/35; j > -1; j--){
                    if(!booleanBlocksArray[n][j]){
                        nothingToTheLeft = false;
                        if(this.xpoints[pt] - (blocksArray[n][j].x + 35) < dx){
                            dx = this.xpoints[pt] - (blocksArray[n][j].x + 35);
                            System.out.println(dx);
                            break;
                        }
                    }
                }
                
                if(nothingToTheLeft && this.xpoints[pt] < dx) dx = this.xpoints[pt];
                
                if(ptStart == 0 && pt == 0) pt = 8;
                else if(ptStart == 9 && pt == 9) pt = 7;
                else pt--;
            }
            
        }
        
        return dx;
    }
    
    public int checkdy(Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){
        nothingBelow = true;
        int pt = 0, ptStart = 0, goOut = 0;
        
        if(this.getDirection().equals("horizontal")){
            pt = 9;
            ptStart = 9;
            goOut = 3;
        }else if(this.getDirection().equals("vertical")){
            pt = 5;
            ptStart = 5;
            goOut = 2;
        }
        
        for(int j = this.xpoints[ptStart]/35; j < (this.xpoints[ptStart]/35)+goOut; j++){
            for(int n = this.ypoints[pt]/35; n < 20; n++){
                if(!booleanBlocksArray[n][j]){
                    nothingBelow = false;
                    if(blocksArray[n][j].y - this.ypoints[pt] < dy){
                        dy = blocksArray[n][j].y - this.ypoints[pt];
                        break;
                    }
                }
            }
            
            if(nothingBelow && 665 - this.ypoints[pt] < dy) dy = 665 - this.ypoints[pt];
            
            if((ptStart == 9 && pt == 9) || (ptStart == 5 && pt == 5)) pt-=2;
            else pt--;
        }
            
        return dy;
    }    
    
    public void moveLeft(){
        if(checkdx("left", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && !hardDrop && TryAgainTetris.pause == false) this.translate(-35, 0);
    }
    
    public void moveRight(){
        if(checkdx("right", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && !hardDrop && TryAgainTetris.pause == false) this.translate(35, 0);
    }
    
    public void moveDown(){
        if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false) this.translate(0, 35);
    }
    
    public void hardDrop(){
        if(TryAgainTetris.pause == false){
            hardDrop = true;
            int add = checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty);
            this.translate(0, add);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(TryAgainTetris.zsquiggle && TryAgainTetris.pause == false){
            if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0) this.translate(0, 35);
        }
    }
    
    @Override
    public void draw(Graphics g) {}
    
}