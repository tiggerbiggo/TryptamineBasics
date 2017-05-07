/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypLayerPanels;

import java.awt.GridLayout;
import trypComponents.*;
import trypGenerators.Gen_Formula;
import trypParams.Parameter;
import trypResources.Formula;
import trypResources.Function;
import tryptamine.GapPresets;

/**
 *
 * @author root
 */
public class FormulaLayerPanel extends AbstractLayerPanel
{
    //hv, colorspeed, gaps, formula
    
    BooleanPanel boolHVPanel;
    IntegerPanel colorSpeed;
    GapPanel gapPanel;
    FormulaPresetBar presets;
    
    @Override
    public Parameter[] getParams() 
    {
        //hv, dir, colorspeed, gaps, formula
        try
        {
            Formula F = new Formula(Function.SIN);
            F.setCoeff(20);
            return Gen_Formula.constructParams(
                    boolHVPanel.getBoolean(), 
                    colorSpeed.getInt(), 
                    gapPanel.getGaps(), 
                    presets.getFormula());
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
        
        presets = new FormulaPresetBar();
        this.add(presets);
    }

    @Override
    public void setParams(Parameter[] params) 
    {
        //hv, colorspeed, gaps, formula
        if(Gen_Formula.validateParams(params))
        {
            try
            {
                boolHVPanel.setBoolean(params[0].getBoolean());
                colorSpeed.setInt(params[1].getInt());
                gapPanel.setSelected(GapPresets.parseArray(params[2].getIntArray()));
                double coeff = params[3].getFormula().getCoeff();
                double freq = params[3].getFormula().getFreq();
                int selected = params[3].getFormula().getSelected();
                presets.setAll(coeff, freq, selected);
            }
            catch(Exception e)
            {
                
            }
        }
    }
    
}
