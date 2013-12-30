package tryagaintetris;

//Done! :D

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BackZType extends Polygon implements Drawable, ActionListener{

    private Timer tim = new Timer(10, this);
    private int dx = 350, dy = 700;
    private boolean nothingBelow, nothingToTheRight, nothingToTheLeft;
    private String direction;
    private boolean hardDrop = false;
    
    public BackZType(int[] xpoints, int[] ypoints, int npoints){
        super(xpoints, ypoints, npoints);
        tim.setDelay(500);
        tim.start();
    }
    
    public void gameOver(){
        tim.stop();
    }
    
    public String getDirection(){
        if(this.ypoints[2] == this.ypoints[4] && this.ypoints[7] > this.ypoints[4]) direction = "horizontal";
        else if(this.xpoints[2] == this.xpoints[4] && this.ypoints[7] < this.ypoints[4]) direction = "vertical";
        return direction;
    }
    
    public void rotateARGH(){
        if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false){
            if(this.getDirection().equals("horizontal")){
                this.xpoints[0]+=35; this.ypoints[0]-=70;
                this.ypoints[1]-=35;
                this.xpoints[2]+=35;
                this.ypoints[3]+=35;
                this.xpoints[4]-=35; this.ypoints[4]+=70;
                this.xpoints[5]-=70; this.ypoints[5]+=35;
                this.xpoints[6]-=35;
                this.xpoints[7]-=70; this.ypoints[7]-=35;
                this.xpoints[8]-=35; this.ypoints[8]-=70;
                this.ypoints[9]-=105;
            }else if(this.getDirection().equals("vertical") && checkdx("right", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0){
                this.xpoints[0]-=35; this.ypoints[0]+=70;
                this.ypoints[1]+=35;
                this.xpoints[2]-=35;
                this.ypoints[3]-=35;
                this.xpoints[4]+=35; this.ypoints[4]-=70;
                this.xpoints[5]+=70; this.ypoints[5]-=35;
                this.xpoints[6]+=35;
                this.xpoints[7]+=70; this.ypoints[7]+=35;
                this.xpoints[8]+=35; this.ypoints[8]+=70;
                this.ypoints[9]+=105;
            }
        }
    }
    
    public int checkdx(String direction, Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){
        nothingToTheRight = true; nothingToTheLeft = true;
        int dx = 350, pt = 0, ptStart = 0, goOut = 0;
        
        if(direction.equals("right")){
            if(this.getDirection().equals("horizontal")){
                pt = 4;
                ptStart = 4;
                goOut = 2;
            }else if(this.getDirection().equals("vertical")){
                pt = 0;
                ptStart = 0;
                goOut = 3;
            }
            
            for(int n = this.ypoints[ptStart]/35; n < (this.ypoints[ptStart]/35)+goOut; n++){
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
                
                if((ptStart == 4 && pt == 4) || (ptStart == 0 && pt == 0)) pt+=2;
                else pt++;
            }
        }else if(direction.equals("left")){
            
            if(this.getDirection().equals("horizontal")){
                pt = 2;
                ptStart = 2;
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
                            break;
                        }
                    }
                }
                
                if(nothingToTheLeft && this.xpoints[pt] < dx) dx = this.xpoints[pt];
                
                if((ptStart == 2 && pt == 2) || (ptStart == 9 && pt == 7)) pt-= 2;
                else if(ptStart == 2 && pt == 0) pt = 9;
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
            pt = 7;
            ptStart = 7;
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
            
            if((ptStart == 9 && pt == 8) || (ptStart == 7 && pt == 7)) pt-=2;
            else pt--;
        }
        
        return dy;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(TryAgainTetris.backzsquiggle && TryAgainTetris.pause == false){
            if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0) this.translate(0, 35);
        }
    }

    public void moveLeft(){
        if(!hardDrop && checkdx("left", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false) this.translate(-35, 0);
    }
    
    public void moveRight(){
        if(!hardDrop && checkdx("right", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false) this.translate(35, 0);
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
    public void draw(Graphics g) {}
    
}