package tryagaintetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TType extends Polygon implements Drawable, ActionListener{

    private Timer tim = new Timer(10, this);
    private int dx = 350, dy = 665;
    private String direction;
    private boolean nothingBelow, nothingToTheRight, nothingToTheLeft;
    private boolean hardDrop = false;
    
    public TType(int[] xpoints, int[] ypoints, int npoints){
        super(xpoints, ypoints, npoints);
        tim.setDelay(500);
        tim.start();
    }
    
    public void gameOver(){
        tim.stop();
    }
    
    public void rotateARGH(){
        if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false){
            if(this.getDirection().equals("down")){
                this.xpoints[0]+=70; this.ypoints[0]-=35;
                this.xpoints[1]+=35;
                this.ypoints[2]+=35;
                this.xpoints[3]-=35; this.ypoints[3]+=70;
                this.xpoints[4]-=70; this.ypoints[4]+=35;
                this.xpoints[5]-=35;
                this.xpoints[6]-=70; this.ypoints[6]-=35;
                this.xpoints[7]-=35; this.ypoints[7]-=70;
                this.ypoints[8]-=35;
                this.xpoints[9]+=35; this.ypoints[9]-=70;
            }else if(this.getDirection().equals("left") && checkdx("right", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0){
                this.xpoints[0]+=35; this.ypoints[0]+=70;
                this.ypoints[1]+=35;
                this.xpoints[2]-=35;
                this.xpoints[3]-=70; this.ypoints[3]-=35;
                this.xpoints[4]-=35; this.ypoints[4]-=70;
                this.ypoints[5]-=35;
                this.xpoints[6]+=35; this.ypoints[6]-=70;
                this.xpoints[7]+=70; this.ypoints[7]-=35;
                this.xpoints[8]+=35;
                this.xpoints[9]+=70; this.ypoints[9]+=35;
            }else if(this.getDirection().equals("up")){
                this.xpoints[0]-=70; this.ypoints[0]+=35;
                this.xpoints[1]-=35;
                this.ypoints[2]-=35;
                this.xpoints[3]+=35; this.ypoints[3]-=70;
                this.xpoints[4]+=70; this.ypoints[4]-=35;
                this.xpoints[5]+=35;
                this.xpoints[6]+=70; this.ypoints[6]+=35;
                this.xpoints[7]+=35; this.ypoints[7]+=70;
                this.ypoints[8]+=35;
                this.xpoints[9]-=35; this.ypoints[9]+=70;
            }else if(this.getDirection().equals("right")){
                this.xpoints[0]-=35; this.ypoints[0]-=70;
                this.ypoints[1]-=35;
                this.xpoints[2]+=35;
                this.xpoints[3]+=70; this.ypoints[3]+=35;
                this.xpoints[4]+=35; this.ypoints[4]+=70;
                this.ypoints[5]+=35;
                this.xpoints[6]-=35; this.ypoints[6]+=70;
                this.xpoints[7]-=70; this.ypoints[7]+=35;
                this.xpoints[8]-=35;
                this.xpoints[9]-=70; this.ypoints[9]-=35;
            }
        }
    }
    
    public String getDirection(){
        if(this.xpoints[0] < this.xpoints[3]) direction = "down";
        else if(this.xpoints[0] == this.xpoints[3] && this.ypoints[0] < this.ypoints[3]) direction = "left";
        else if(this.xpoints[0] > this.xpoints[3]) direction = "up";
        else if(this.xpoints[0] == this.xpoints[3] && this.ypoints[0] > this.ypoints[3]) direction = "right";
        return direction;
    }
    
    public int checkdx(String direction, Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){
        nothingToTheRight = true; nothingToTheLeft = true;
        int dx = 350, pt = 0, ptStart = 0, goOut = 0;
        
        if(this.getDirection().equals("left") || this.getDirection().equals("right")) goOut = 3;
        else if(this.getDirection().equals("up") || this.getDirection().equals("down")) goOut = 2;
        
        if(direction.equals("right")){
            if(this.getDirection().equals("down")){
                pt = 3;
                ptStart = 3;
            }else if(this.getDirection().equals("left")){
                pt = 0;
                ptStart = 0;
            }else if(this.getDirection().equals("up")){
                pt = 7;
                ptStart = 7;
            }else if(this.getDirection().equals("right")){
                pt = 4;
                ptStart = 4;
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

                if(pt == 3 && ptStart == 3) pt+=2;
                else if(pt == 7 && ptStart == 7) pt+=2;
                else if((ptStart == 4 && pt == 4) || (ptStart == 4 && pt == 7)) pt+=2;
                else pt++;
            }

            
        }else if(direction.equals("left")){
            System.out.println("left");
            if(this.getDirection().equals("down")){
                ptStart = 0;
                pt = 0;
            }else if(this.getDirection().equals("left")){
                ptStart = 9;
                pt = 9;
            }else if(this.getDirection().equals("up")){
                ptStart = 6;
                pt = 6;
            }else if(this.getDirection().equals("left")){
                ptStart = 3;
                pt = 3;
            }
            
            for(int n = this.ypoints[ptStart]/35; n < (this.ypoints[ptStart]/35)+goOut; n++){
                for(int j = this.xpoints[pt]/35; j > -1; j--){
                    if(!booleanBlocksArray[n][j]){
                        nothingToTheLeft = false;
                        if(this.xpoints[pt] - (blocksArray[n][j].x+35) < dx){
                            dx = this.xpoints[pt] - (blocksArray[n][j].x+35);
                            break;
                        }
                    }
                }
                if(nothingToTheLeft && this.xpoints[pt] < dx) dx = this.xpoints[pt];
                
                if(ptStart == 0 && pt == 0) pt = 9;
                else if(ptStart == 9 && (pt == 9 || pt == 6)) pt-=2;
                else if(ptStart == 6 && (pt == 6 || pt == 4)) pt-=2;
                else pt--;
            }
        }
        
        return dx;
    }
    
    public int checkdy(Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){
        nothingBelow = true;
        int pt = 0, ptStart = 0, goOut = 0;
        
        if(this.getDirection().equals("down") || this.getDirection().equals("up")) goOut = 3;
        else if(this.getDirection().equals("right") || this.getDirection().equals("left")) goOut = 2;
        
        if(this.getDirection().equals("down")){
            pt = 9;
            ptStart = 9;
        }else if(this.getDirection().equals("left")){
            pt = 6;
            ptStart = 6;
        }else if(this.getDirection().equals("up")){
            pt = 3;
            ptStart = 3;
        }else if(this.getDirection().equals("right")){
            pt = 0;
            ptStart = 0;
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
            
            if(nothingBelow && ptStart == 9) dy = 665 - this.ypoints[7];
            else if(nothingBelow && ptStart == 6) dy = 665 - this.ypoints[4];
            else if(nothingBelow && ptStart == 3) dy = 665 - this.ypoints[3];
            else if(nothingBelow && ptStart == 0) dy = 665 - this.ypoints[0];
                        
            if(ptStart == 9 && (pt == 9 || pt == 7)) pt-=2;
            else if(ptStart == 6 && pt == 6) pt-=2;
            else if(ptStart == 0 && pt == 0) pt = 8;
            else pt--;
        }
        
        
        return dy;
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
    public void actionPerformed(ActionEvent e) {
        if(TryAgainTetris.T && TryAgainTetris.pause == false){
            if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0) this.translate(0, 35);
        }
    }

    @Override
    public void draw(Graphics g) {}
    
    
    
}