package trypLayerPanels;

import java.awt.GridLayout;
import javax.swing.JComboBox;
import trypComponents.IntegerPanel;
import trypGenerators.Gen_Polkadot;
import trypParams.Parameter;
import trypResources.CircleModes;


public class PolkaDotLayerPanel extends AbstractLayerPanel
{
    IntegerPanel[] intPanels;
    JComboBox modes;
    int panelNum;
    
    @Override
    public void initGUI()
    {
        this.setLayout(new GridLayout(0,2));
        
        panelNum = 6;
        intPanels = new IntegerPanel[panelNum];
        String[] panelTexts = {"Radius", "X Offset", "Y Offset", "Color Offset", "Color Speed", "Number"};
        boolean[] canBeNegative = {false, true, true, true, true, false};
        for(int i=0;i<=panelNum-1;i++)
        {
            intPanels[i]=new IntegerPanel(panelTexts[i], canBeNegative[i]);
            this.add(intPanels[i]);
        }
        
        modes = new JComboBox(CircleModes.values());
        this.add(modes);
    }
    
    @Override
    public Parameter[] getParams() 
    {
        int[] tempArray = new int[panelNum];
        for(int i=0;i<panelNum;i++)
        {
            try
            {
                tempArray[i] = intPanels[i].getInt();
            }
            catch(Exception e)
            {
                
                return null;
            }
        }
        return Gen_Polkadot.constructParams(
                tempArray[0], 
                tempArray[1], 
                tempArray[2],
                tempArray[3],
                tempArray[4],
                tempArray[5]);
    }

    @Override
    public void setParams(Parameter[] params) 
    {
        try
        {
            for(int i=0; i<panelNum; i++)
            {
                intPanels[i].setInt((Integer)params[i].get());
            }
            CircleModes[] values = CircleModes.values();
            for(int i=0; i<values.length; i++)
            {
                if(values[i] == (CircleModes)params[panelNum].get())
                {
                    modes.setSelectedIndex(i);
                    break;
                }
            }
        }
        catch(Exception e){}
    }
    
}
