package trypLayerPanels;

import java.awt.GridLayout;
import trypComponents.*;
import trypGenerators.Gen_SimpleLines;
import trypParams.Parameter;
import tryptamine.GapPresets;

/**
 *
 * @author root
 */
public class SimpleLinesLayerPanel extends AbstractLayerPanel
{
    //hv, colorspeed, gaps
    
    BooleanPanel boolHVPanel;
    IntegerPanel colorSpeed;
    GapPanel gapPanel;
    
    @Override
    public Parameter[] getParams() 
    {
        //hv, dir, colorspeed, gaps, formula
        try
        {
            return Gen_SimpleLines.constructParams(
                    boolHVPanel.getBoolean(), 
                    colorSpeed.getInt(), 
                    gapPanel.getGaps());
        }
        catch(Exception e)
        {
            return null;
        }
    }

    @Override
    public void initGUI() 
    {
        //
        this.setLayout(new GridLayout(0,1));
        
        String[] labelNames = {"Horizontal", "Vertical"};
        boolHVPanel = new BooleanPanel(labelNames);
        this.add(boolHVPanel);
        
        colorSpeed = new IntegerPanel("Color Speed", true);
        this.add(colorSpeed);
        
        gapPanel = new GapPanel();
        this.add(gapPanel);
    }

    @Override
    public void setParams(Parameter[] params) 
    {
        try
        {
            boolHVPanel.setBoolean((Boolean)params[0].get());
            colorSpeed.setInt((Integer)params[1].get());
            gapPanel.setSelected(GapPresets.parseArray((int[])params[2].get()));
        }
        catch(Exception e){}
    }
    
}
