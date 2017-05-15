package trypGenerators;

import java.util.ArrayList;
import java.util.Random;
import trypParams.Parameter;
import trypResources.Formula;
import tryptamine.DynamicCanvas;
import tryptamine.GapPresets;

/**
 *
 * @author timot_000
 */
public class Gen_Formula extends AbstractGenerator
{
    
    boolean HV;
    int colorSpeed;
    int[] gaps;
    Formula F;
    
    public Gen_Formula(Parameter[] params) throws IllegalArgumentException
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
            startPos=(int)Math.round(F.recursiveCalc(i));
            
            
            
            if(n>=gaps.length)
            {
                n=0;
            }
            if(c>=gaps[n])
            {
                c=0;
                n++;
                
                DC = lineDraw(DC, HV, colorSpeed, i, startPos, PaletteNum);
            }
            else
            {
                DC = lineDraw(DC, HV, -colorSpeed, i, startPos, PaletteNum);
                c++;
            }
        }
        return DC;
    }
    
    public static Parameter[] constructParams(boolean HV, int colorSpeed, int[] gaps, Formula F)
    {
        Parameter[] params = {new Parameter(HV), new Parameter(colorSpeed), new Parameter(gaps), new Parameter(F)};
        
        return params;
    }
    
    public final boolean parseParams(Parameter[] params)
    {
        try
        {
            HV=(Boolean)params[0].get();
            colorSpeed = (Integer)params[1].get();
            gaps=(int[])params[2].get();
            F=(Formula)params[3].get();
            return true;
        }
        catch(Exception e)
        {
            
        }
        
        return false;
    }
    
    public static Parameter[] getRandomParams(Random rand, int weight1, int weight2, int weight3)
    {
        if(rand == null)
        {
            rand = new Random();
        }
        
        ArrayList<Formula> f = FormulaPresets.setupFormulas(rand.nextDouble()*weight2*2, rand.nextDouble()*(weight3/40.0));
        
        //GapPresets.gaps[rand.nextInt(GapPresets.gaps.length-1)]
        
        return constructParams(
                rand.nextBoolean(), 
                rand.nextInt(50)*weight1, 
                GapPresets.gaps[1], 
                f.get(rand.nextInt(f.size()-1)));
    }
    
}
