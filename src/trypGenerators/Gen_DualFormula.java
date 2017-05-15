/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypGenerators;

import java.util.ArrayList;
import java.util.Random;
import trypParams.Parameter;
import trypResources.Formula;
import tryptamine.DynamicCanvas;

/**
 *
 * @author tiggerbiggo
 */
public class Gen_DualFormula extends AbstractGenerator
{
    Formula xFormula, yFormula;
    double zoomx, zoomy;
    
    public Gen_DualFormula(Parameter[] params) throws IllegalArgumentException
    {
        if(parseParams(params))
        {
            //all is well
        }
        else throw new IllegalArgumentException();
    }
    
    @Override
    public DynamicCanvas draw(DynamicCanvas DC, int PaletteNum)
    {
        if(zoomx == 0 || zoomy == 0)return DC;
        
        for(int i=0; i<DC.getX(); i++)
        {
            for(int j=0; j<DC.getY(); j++)
            {
                double numToDraw = Math.abs(xFormula.recursiveCalc((i/zoomx)+yFormula.recursiveCalc(j/zoomy)));
                DC.draw(i, j, PaletteNum, (int)Math.round(numToDraw));
            }
        }
        return DC;
    }
    
    public static Parameter[] constructParams(Formula xFormula, Formula yFormula, double zoomx, double zoomy)
    {
        Parameter[] params = {new Parameter(xFormula), new Parameter(yFormula), new Parameter(zoomx), new Parameter(zoomy)};
        
        return params;
    }

    private boolean parseParams(Parameter[] params)
    {
        try
        {
            xFormula = (Formula)params[0].get();
            yFormula = (Formula)params[1].get();
            zoomx = (double)params[2].get();
            zoomy = (double)params[3].get();
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
                f.get(Math.min(rand.nextInt(weight1), f.size()-1)), 
                f.get(Math.min(rand.nextInt(weight1), f.size()-1)), 
                (double)weight2/100,
                (double)weight3/100);
        
        /*return constructParams(
                rand.nextBoolean(), 
                rand.nextInt(50)*weight1, 
                GapPresets.gaps[1], 
                f.get(rand.nextInt(f.size()-1)));*/
    }
}
