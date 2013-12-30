package tryagaintetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SquareType extends Rectangle implements Drawable, ActionListener{
    
    private Timer tim = new Timer(10, this);
    private int dx, dy;
    private boolean completelyEmpty, hardDrop = false;
        
    public SquareType(int x, int y, int width, int height){
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

    public int checkdx(String direction, Rectangle[][] blocksArray, boolean[][] booleanBlocksArray){
        if(!checkEntireGridBooleans()){
            for(int n = 0; n < blocksArray.length; n++){
                for(int j = 0; j < blocksArray[n].length; j++){
                    if(direction.equals("right")){
                        if(!booleanBlocksArray[n][j] && blocksArray[n][j].intersects(new Rectangle(this.x + 2, this.y, this.width, this.height))){
                            dx = blocksArray[n][j].x - (this.x + this.width);
                            return dx;
                        }else dx = 350 - (this.x + this.width);
                    }else if(direction.equals("left")){
                        if(!booleanBlocksArray[n][j] && blocksArray[n][j].intersects(new Rectangle(this.x - 2, this.y, this.width, this.height))){
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
        if(!hardDrop && TryAgainTetris.pause == false && checkdx("left", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0) this.x-=35;
    }
    
    public void moveRight(){
        if(!hardDrop && TryAgainTetris.pause == false && checkdx("right", TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0)this.x+=35;
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
        if(checkdy(TryAgainTetris.blocksArray, TryAgainTetris.booleanBlocksArrayEmpty) > 0 && TryAgainTetris.pause == false)this.y+=35;
    }
    
    @Override
    public void draw(Graphics g) {}
    
}