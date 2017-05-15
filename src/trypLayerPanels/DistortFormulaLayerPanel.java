/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypLayerPanels;

import java.awt.GridLayout;
import trypComponents.*;
import trypGenerators.Gen_DistortFormula;
import trypParams.Parameter;
import trypResources.Formula;
import trypResources.Function;
import tryptamine.GapPresets;

/**
 *
 * @author root
 */
public class DistortFormulaLayerPanel extends AbstractLayerPanel
{
    //hv, dir, gaps, formula
    
    BooleanPanel boolHVPanel;
    BooleanPanel boolDirPanel;
    GapPanel gapPanel;
    FormulaPresetBar presets;
    
    @Override
    public Parameter[] getParams() 
    {
        //hv, dir, gaps, formula
        try
        {
            Formula F = new Formula(Function.SIN);
            F.setCoeff(20);
            return Gen_DistortFormula.constructParams(
                    boolHVPanel.getBoolean(), 
                    boolDirPanel.getBoolean(), 
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
        
        String[][] labelNames = {
            {"Horizontal", "Vertical"},
            {"+", "-"}
        };
        boolHVPanel = new BooleanPanel(labelNames[0]);
        this.add(boolHVPanel);
        
        boolDirPanel = new BooleanPanel(labelNames[1]);
        this.add(boolDirPanel);
        
        gapPanel = new GapPanel();
        this.add(gapPanel);
        
        presets = new FormulaPresetBar();
        this.add(presets);
    }

    @Override
    public void setParams(Parameter[] params) 
    {
        try
        {
            Formula f = (Formula)params[3].get();
            
            boolHVPanel.setBoolean((Boolean)params[0].get());
            boolDirPanel.setBoolean((Boolean)params[1].get());
            gapPanel.setSelected(GapPresets.parseArray((int[])params[2].get()));
            double coeff = f.getCoeff();
            double freq = f.getFreq();
            int selected = f.getSelected();
            presets.setAll(coeff, freq, selected);
        }
        catch(Exception e){}
    }
    
}
