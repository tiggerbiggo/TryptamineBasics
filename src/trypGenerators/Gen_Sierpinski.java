/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypGenerators;

import trypResources.Vector2;
import tryptamine.DynamicCanvas;

/**
 *
 * @author lea14141039
 */
public class Gen_Sierpinski extends AbstractGenerator
{
    public static final double TANCONSTANT = Math.tan(Math.toRadians(60));
    
    
    
    @Override
    public DynamicCanvas draw(DynamicCanvas DC, int PaletteNum) 
    {
        return drawRecursiveTriangle(DC, new Vector2(0,0), DC.getX()-30, 20, 0, 3, 0);
    }
    
    
    public DynamicCanvas drawRecursiveTriangle(DynamicCanvas DC, Vector2 start, double endX, int depth, int colorNum, int colorSpeed, int PaletteNum)
    {
        double dist = endX-start.x;
        
        if(dist <=1 || depth <=0)
        {
            return DC;
        }
        
        double height = (TANCONSTANT * dist)/2;
        
        int currentWidth, count, cX, cY;
        currentWidth = (int)Math.round(dist);
        cX=0; cY=0;
        count = 0;
        
        do
        {
            if(count == 1)
            {
                currentWidth-=2;
                cX++;
                count = 0;
            }
            else
            {
                count++;
            }
            for(int i=0; i<currentWidth; i++)
            {
                DC.draw(start.getXInt()+cX+i, start.getYInt()+cY, PaletteNum, colorNum);
            }
            cY++;
        }
        while(currentWidth >1);
        
        DC = drawRecursiveTriangle(
                DC, 
                start, 
                endX-(dist/2), 
                depth-1, 
                colorNum+colorSpeed, 
                colorSpeed, 
                PaletteNum);
        
        DC = drawRecursiveTriangle(
                DC, 
                new Vector2(start.x + (dist/2), start.y), 
                endX, 
                depth-1, 
                colorNum+colorSpeed, 
                colorSpeed, 
                PaletteNum);
        
        DC = drawRecursiveTriangle(
                DC, 
                new Vector2(start.x+(dist/4), start.y+(height/2d)), 
                endX-(0.25*dist), 
                depth-1, 
                colorNum+colorSpeed, 
                colorSpeed, 
                PaletteNum);
        
        return DC;
    }
    
}
