/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypGenerators;

import java.util.Random;
import trypParams.Parameter;
import trypParams.paramType;
import trypResources.CircleModes;
import trypResources.Vector2;
import tryptamine.DynamicCanvas;

public class Gen_Circular extends AbstractGenerator
{

    int r, x, y, colorOffset, colorSpeed;
    CircleModes mode;
    
    public Gen_Circular(Parameter[] params)throws IllegalArgumentException
    {
        if(parseParams(params))
        {
            //all is well
        }
        else throw new IllegalArgumentException("INVALID PARAMETERS");
    }
    
    @Override
    public DynamicCanvas draw(DynamicCanvas DC, int PaletteNum) 
    {
        DC=drawCircle(DC, PaletteNum, colorSpeed, colorOffset, x, y, r);
        return DC;
    }
    
    public DynamicCanvas drawCircle(DynamicCanvas DC, int PaletteNum, int colorSpeed, int colorOffset, int x0, int y0, int radius)
    {
        int width = DC.getX();
        int height = DC.getY();
        
        //radius = -1;
        
        Vector2 center = new Vector2(x0, y0);
        
        for(int i=0; i<width; i++)
        {
            for(int j=0; j<height; j++)
            {
                int dist = (int)Vector2.distanceToPoint(center, new Vector2(i,j));
                if(dist <= radius || radius == -1)
                {
                    DC.draw(i, j, PaletteNum, DC.normalizePalette((dist*colorSpeed)+colorOffset, 0));
                }
            }
        }
        
        return DC;
    }
    
    public static Parameter[] constructParams(
            int radius, 
            int x, int y, 
            int colorOffset, 
            int colorSpeed)
    {
        Parameter[] params = {
            new Parameter(radius), 
            new Parameter(x), 
            new Parameter(y), 
            new Parameter(colorOffset), 
            new Parameter(colorSpeed)};
        return params;
    }
    
    public final boolean parseParams(Parameter[] params)
    {
        if(params != null && params.length == 5)
        {
            if(validateParams(params))
            {
                r=params[0].getInt();
                x=params[1].getInt();
                y=params[2].getInt();
                colorOffset = params[3].getInt();
                colorSpeed = params[4].getInt();
                return true;
            }
        }
        
        return false;
    }

    public static boolean validateParams(Parameter[] params) 
    {
        return params != null &&
               params.length==5 &&
               params[0].getType()==paramType.INTEGER &&
               params[1].getType()==paramType.INTEGER &&
               params[2].getType()==paramType.INTEGER &&
               params[3].getType()==paramType.INTEGER &&
               params[4].getType()==paramType.INTEGER;
    }

    /*
    int radius, 
    int x, int y, 
    int colorOffset, 
    int colorSpeed,
    CircleModes mode
    */
    
    public static Parameter[] getRandomParams(Random rand, int weight1, int weight2, int weight3) 
    {
        if(rand == null)
        {
            rand = new Random();
        }
        
        
        
        return constructParams(
                (rand.nextInt(weight1)*weight3)-1,
                rand.nextInt(weight1)*weight2,
                rand.nextInt(weight1)*weight2,
                rand.nextInt(10)*weight1,
                rand.nextInt(10)*weight2);
    }

    
}
