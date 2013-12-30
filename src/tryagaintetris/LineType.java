package tryagaintetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LineType extends Rectangle implements Drawable, ActionListener{

    Timer tim = new Timer(10, this); 
    private int dx, dy;
    boolean completelyEmpty, hardDrop = false;
    
        
    public LineType(int x, int y, int width, int height){
        super(x, y, width, height);
        tim.setDelay(500);
        tim.start();
    }
    
    public void gameOver(){
        tim.stop();
    }
             
    public boolean checkEntireGridBooleans(){
        for(int n = 0; n < TryAgainTetris.blocksArray.length; n++){
            for(int j = 0; j < TryAgainTetris.blocksArray[n].length; j++){
                if(!TryAgainTetris.booleanBlocksArrayEmpty[n][j]){
                    completelyEmpty = false;
                    return completelyEmpty;
                }else completelyEmpty = true;
            }
        }
        return completelyEmpty;
    }
        
    public void rotateARGH(int width, int height){
        if(((this.x == 280 && width < 140) || (this.intersectsLine(0, 0, 0, 700) || this.intersectsLine(350, 0, 350, 700)) && width < 140) 
            || (checkdx("right", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) == 0 && !this.intersectsLine(350, 0, 350, 700) && TryAgainTetris.pause == false)
            || (checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) <= 0)) this.x = this.x;
        else if(TryAgainTetris.pause == false){
            if(width > 35) this.x = this.x+35;
            else this.x = this.x-35;
            this.y = this.y-(width/2)+(height/2)+18;
            if(this.y%35 != 0) this.y = this.y - (this.y%35);
            this.width = height;
            this.height = width;
        }
    }
    
    public int checkdx(String direction, Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){ 
        if(!checkEntireGridBooleans()){
            for(int n = 0; n < blocksArray.length; n++){
                for(int j = 0; j < blocksArray[n].length; j++){
                    if(direction.equals("right")){
                        if(!booleanBlocksArray[n][j] && blocksArray[n][j].intersects(new Rectangle(this.x+2, this.y, this.width, this.height))){
                            dx = blocksArray[n][j].x - (this.x + this.width);
                            return dx;
                        }else dx = 350 - (this.x + this.width);
                    }else if(direction.equals("left")){
                        if(!booleanBlocksArray[n][j] && blocksArray[n][j].intersects(new Rectangle(this.x-2, this.y, this.width, this.height))){
                            dx = this.x - (blocksArray[n][j].x + blocksArray[n][j].width);
                            return dx;
                        }else dx = this.x;
                    }
                }
            }
        }else{
            if(direction.equals("right")) dx = 350 - (this.x + this.width);
            else if(direction.equals("left")) dx = this.x;
        }
        return dx;
    }
    
    public int checkdy(Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){
        if(this.y > 10){
            for(int n = this.y/35; n < blocksArray.length; n++){
                for(int j = this.x/35; j < ((this.x + this.width)/35); j++){
                    if(!checkEntireGridBooleans()){ 
                        if(!booleanBlocksArray[n][j]){ 
                            dy = blocksArray[n][j].y - this.y - this.height; 
                            return dy;
                        }else dy = 665 - (this.y + this.height);
                    }else dy = 665 - this.y - this.height;
                }    
            }
        }else dy = 700;
        
        return dy;
    }
        
    public void moveLeft(){
        if(!hardDrop && TryAgainTetris.pause == false && checkdx("left", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0) this.x -= 35;
    }
    
    public void moveRight(){
        if(!hardDrop && TryAgainTetris.pause == false && checkdx("right", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0) this.x += 35;
    }
    
    public void moveDown(){
        if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false) this.y += 35;
    }
    
    public void hardDrop(){
        if(TryAgainTetris.pause == false){
            hardDrop = true;
            this.y+=checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) { 
        if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false) this.y+=35;
    }
    
    @Override
    public void draw(Graphics g) {}
    
}