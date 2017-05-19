/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypComponents;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import trypGenerators.*;
import trypParams.Parameter;
import trypResources.Layer;
import trypResources.Palette;

/**
 *
 * @author tiggerbiggo
 */
public class IconButtonPanel extends JPanel
{

    protected final int x, y;
    
    layerButton[][] buttons;
    
    Thread[][] buttonThreads;
    
    Palette normal, selected;
    
    Random randType, randParams;
    int weight1, weight2, weight3, selectedX, selectedY;
    
    public IconButtonPanel(int x, int y, ActionListener a)
    {
        this.x = x;
        this.y = y;
        
        this.setLayout(new GridLayout(x,y));
        
        buttons = new layerButton[x][y];
        buttonThreads = new Thread[x][y];
        
        for(int i=0; i<x; i++)
        {
            for(int j=0; j<y; j++)
            {
                buttons[i][j] = new layerButton();
                buttons[i][j].addActionListener(a);
                this.add(buttons[i][j]);
                
            }
        }
        
        normal = new Palette(50,0);
        selected = new Palette(50,0);
        
        normal.setGradient(0, 49, Color.black, Color.white);
        selected.setGradient(0, 49, Color.yellow, Color.red);
    }
    
    public void setWeights(Random randType, Random randParams, int weight1, int weight2, int weight3)
    {
        this.randType= randType; 
        this.randParams = randParams;
        this.weight1 = weight1;
        this.weight2 = weight2;
        this.weight3 = weight3;
    }
    
    public void setPalette(Palette P)
    {
        if(P != null)
            normal = P;
    }
    
    public void updateButtons()
    {
        for(int i=0; i<buttons.length; i++)
        {
            for(int j=0; j<buttons[i].length; j++)
            {
                //int i = randType.nextInt(GenType.TYPENAMES.length-1);
                
                GenType type = GenType.values()[randParams.nextInt(GenType.values().length-1)];
                
                Parameter[] params;
                
                /*switch(type)
                {
                    /*case CIRCULAR:
                        params = Gen_Circular.getRandomParams(randType, 
                            weight1, 
                            weight2, 
                            weight3);
                        break;
                    case POLKADOT:
                        params = Gen_Polkadot.getRandomParams(randType, 
                            weight1, 
                            weight2, 
                            weight3);
                        break;
                    case FORMULA:
                        params = Gen_Formula.getRandomParams(randType, 
                            weight1, 
                            weight2, 
                            weight3);
                        break;
                    case DUALFORMULA:
                        params = Gen_DualFormula.getRandomParams(randType, 
                            weight1, 
                            weight2, 
                            weight3);
                        break;
                }*/
                
                if(false)//randParams.nextBoolean()
                {
                    type = GenType.FORMULA;
                    params = Gen_Formula.getRandomParams(randType, 
                            weight1, 
                            weight2, 
                            weight3);
                }
                else
                {
                    type = GenType.DUALFORMULA;
                    params = Gen_DualFormula.getRandomParams(randType, 
                            weight1, 
                            weight2, 
                            weight3);
                }
                
                
                
                //Layer l = new Layer("doot", GenType.FORMULA, params);
                Layer l = new Layer("doot", type, params);
                buttons[i][j].l = l;
                buttons[i][j].P = normal;
                if(buttonThreads[i][j] == null || !buttonThreads[i][j].isAlive())//if thread is null or dead
                {
                    buttons[i][j].needsUpdate = false;
                    buttonThreads[i][j] = new Thread(buttons[i][j]);
                    buttonThreads[i][j].start();
                }
                else
                {
                    buttons[i][j].needsUpdate=true;
                }
            }
        }
    }

    
    
    public Layer getLayerFromButton(ActionEvent e)
    {
        for(int i=0; i<buttons.length; i++)
        {
            for(int j=0; j<buttons[i].length; j++)
            {
                if(e.getSource() == buttons[i][j])
                {
                    selectedX=i;
                    selectedY=j;
                    return buttons[i][j].l;
                }
            }
        }
        return null;
    }
    
    public String getSelected()
    {
        return selectedX+","+selectedY;
    }
    
    public void randomClick(Random rand)
    {
        if(rand == null) rand = new Random();
        
        int xClick = rand.nextInt(x-1);
        int yClick = rand.nextInt(y-1);
        
        buttons[xClick][yClick].doClick();
    }
}
