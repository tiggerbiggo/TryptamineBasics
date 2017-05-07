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
import tryptamine.DynamicCanvas;

public class Gen_Polkadot extends AbstractGenerator
{

    int radius, offsetX, offsetY, colorOffset, colorSpeed, num;
    CircleModes mode;
    
    public Gen_Polkadot(Parameter[] params)throws IllegalArgumentException
    {
        if(parseParams(params))
        {
            //all is well
        }
        else
        {
            throw new IllegalArgumentException("INVALID PARAMETERS");
        }
    }
    
    @Override
    public DynamicCanvas draw(DynamicCanvas DC, int PaletteNum) 
    {
        Gen_Circular gen;
        for(int i=0; i<num; i++)
        {
            for(int j=0; j<num; j++)
            {
                try
                {
                    Parameter[] params = 
                            Gen_Circular.constructParams(
                                    radius, 
                                    (radius+offsetX)*i, 
                                    (radius+offsetY)*j, 
                                    (i+j)*colorOffset,
                                    colorSpeed);
                    
                    gen = new Gen_Circular(params);
                    DC = gen.draw(DC, PaletteNum);
                }
                catch(Exception e){}
            }
        }
        
        return DC;
    }
    
    public static Parameter[] constructParams(int radius, 
                                              int x, int y, 
                                              int colorOffset, 
                                              int colorSpeed,
                                              int num)
    {
        Parameter[] params = {
            new Parameter(radius), 
            new Parameter(x), 
            new Parameter(y), 
            new Parameter(colorOffset), 
            new Parameter(colorSpeed), 
            new Parameter(num)};
        return params;
    }
    
    public final boolean parseParams(Parameter[] params)
    {
        if(params != null && params.length == 6)
        {
            if(validateParams(params))
            {
                radius=params[0].getInt();
                offsetX=params[1].getInt();
                offsetY=params[2].getInt();
                colorOffset = params[3].getInt();
                colorSpeed = params[4].getInt();
                num = params[5].getInt();
                return true;
            }
        }
        
        return false;
    }

    public static boolean validateParams(Parameter[] params) 
    {
        return params != null &&
               params.length==6 &&
               params[0].getType()==paramType.INTEGER &&
               params[1].getType()==paramType.INTEGER &&
               params[2].getType()==paramType.INTEGER &&
               params[3].getType()==paramType.INTEGER &&
               params[4].getType()==paramType.INTEGER &&
               params[5].getType()==paramType.INTEGER;
    }

    public static Parameter[] getRandomParams(Random rand, int weight1, int weight2, int weight3) 
    {
        if(rand == null)
        {
            rand = new Random();
        }
        
        
        
        return constructParams(
                rand.nextInt(30)*weight1,
                rand.nextInt(30)*weight2,
                rand.nextInt(30)*weight2,
                rand.nextInt(10),
                rand.nextInt(10),
                weight3);
    }
}
