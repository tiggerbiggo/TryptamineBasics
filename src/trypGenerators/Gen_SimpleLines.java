/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypGenerators;

import trypParams.Parameter;
import tryptamine.DynamicCanvas;

/**
 *
 * @author timot_000
 */
public class Gen_SimpleLines extends AbstractGenerator
{
    boolean HV;
    int colorSpeed;
    int[] gaps;
    
    Parameter[] params;
    
    public Gen_SimpleLines(Parameter[] params) throws IllegalArgumentException
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
        int startPos, maxPos;
        if(HV) maxPos = DC.getY();
        else maxPos = DC.getX();
        startPos=0;
        int n=0;
        int c=0;
        for(int i=0; i<maxPos; i++)
        {
            if(n>=gaps.length)
            {
                c=0;
                n=0;
                
            }
            if(gaps[n] >=1)
            {
                gaps[n]-=1;
                c+=1;
            }
            else
            {
                gaps[n]=c;
                c=0;
                n++;
                DC = lineDraw(DC, HV, colorSpeed, i, startPos, PaletteNum);
            }
        }
        
        return DC;
    }
    public static Parameter[] constructParams(boolean HV, int colorSpeed, int[] gaps)
    {
        Parameter[] params = {new Parameter(HV), new Parameter(colorSpeed), new Parameter(gaps)};
        
        return params;
    }
    
    public final boolean parseParams(Parameter[] params)
    {
        try
        {
            this.HV=(Boolean)params[0].get();
            this.colorSpeed=(Integer)params[1].get();
            this.gaps=(int[])params[2].get();
            return true;
        }
        catch(Exception e){}
        return false;
    }
}
