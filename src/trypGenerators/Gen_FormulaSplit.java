/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypGenerators;

import java.util.ArrayList;
import trypParams.Parameter;
import trypResources.Formula;
import trypResources.PaletteReference;
import tryptamine.DynamicCanvas;

/**
 *
 * @author timot_000
 */
public class Gen_FormulaSplit extends AbstractGenerator
{
    boolean HV;
    boolean dir;
    int[] gaps;
    Formula F;
    
    public Gen_FormulaSplit(Parameter[] params) throws IllegalArgumentException
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
        int n=0;
        int c=0;
        for(int i=0; i<maxPos; i++)
        {
            startPos=(int)Math.round(F.Calculate(i));
            //if(!dir)startPos*=-1;
            if(n>=gaps.length)
            {
                n=0;
            }
            if(c>=gaps[n])
            {
                c=0;
                n++;
                DC = shiftLine(DC, HV, startPos, i);
            }
            else
            {
                c++;
            }
        }
        
        
        return DC;
    }
    
    public DynamicCanvas shiftLine(DynamicCanvas DC, boolean HV, int num, int pos)
    {
        if(num==0) return DC;
        
        
        int max, dx, dy, cx, cy, n;
        
        ArrayList<PaletteReference> listA = new ArrayList();
        ArrayList<PaletteReference> listB = new ArrayList();
        
        if(HV) 
        {
            max=DC.getX();
            dx=Integer.signum(num);
            dy=0;
            cx=0;
            cy=pos;
        }
        else 
        {
            max=DC.getY();
            dx=0;
            dy=Integer.signum(num);
            cx=pos;
            cy=0;
        }
        
        for(int i=0; i<max; i++)
        {
            listA.add(DC.getReference(cx, cy));
            cx+=dx;
            cy+=dy;
        }
        
        if(HV) 
        {
            if(num<0) 
            {
                //dx=1;
                cx=max;
            }
            else cx=0;
            cy=pos;
        }
        else 
        {
            cx=pos;
            if(num<0)
            {
                //dy*=-1;
                cy=max;
            }
            else cy = 0;
            cy=0;
            
        }
        
        
        
        
        for(int i=0; i<listA.size(); i++)
        {
            DC.setReference(cx, cy, listA.get(normalize(i+num, 0,listA.size())));
            cx+=dx;
            cy+=dy;
        }
        
        return DC;
    }
    
    public static Parameter[] constructParams(boolean HV, boolean dir, int[] gaps, Formula F)
    {
        Parameter[] params = {new Parameter(HV), new Parameter(dir), new Parameter(gaps), new Parameter(F)};
        
        return params;
    }
    
    public final boolean parseParams(Parameter[] params)
    {
        try
        {
                HV=(Boolean)params[0].get();
                dir=(Boolean)params[1].get();
                gaps=(int[])params[2].get();
                F=(Formula)params[3].get();
                return true;
        }
        catch(Exception e){}
        
        return false;
    }
    
    public int normalize(int n, int min, int max)
    {
        if(max>min)
        {
            while(n>=max)
            {
                n-=(max-min);
            }
            while(n<min)
            {
                n+=(max-min);
            }
        }
        return n;
    }
}
