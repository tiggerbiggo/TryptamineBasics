package trypGenerators;

import trypParams.Parameter;
import tryptamine.DynamicCanvas;
import java.util.Random;

/**
 *
 * @author timot_000
 */
public abstract class AbstractGenerator 
{
    
    public abstract DynamicCanvas draw(DynamicCanvas DC, int PaletteNum);
    
    public static Parameter[] getRandomParams(Random rand, int weight1, int weight2, int weight3)
    {
        return null;
    }
    
    public DynamicCanvas lineDraw(DynamicCanvas DC, boolean HV, int colorIncrement, int pos, int colorNum, int PaletteNum)
    {
        int x=0, y=0;
        
        if(HV) y=pos;
        else x=pos;
        
        while(DC.checkDimensions(x,y))
        {
            colorNum=DC.getPalette(PaletteNum).normalize(colorNum);
            DC.draw(x, y, PaletteNum, colorNum);
            colorNum+=colorIncrement;
            if(HV) x++;
            else y++;
        }
        return DC;
    }
    
}
