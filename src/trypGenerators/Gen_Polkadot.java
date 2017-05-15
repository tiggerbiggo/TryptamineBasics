/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypGenerators;

import java.util.Random;
import trypParams.Parameter;
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
        try
        {
            radius=(Integer)params[0].get();
            offsetX=(Integer)params[1].get();
            offsetY=(Integer)params[2].get();
            colorOffset = (Integer)params[3].get();
            colorSpeed = (Integer)params[4].get();
            num = (Integer)params[5].get();
            return true;
        }
        catch(Exception e){}
        return false;
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
