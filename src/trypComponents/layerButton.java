/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypComponents;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import trypResources.Layer;
import trypResources.Palette;
import tryptamine.DynamicCanvas;
import tryptamine.ImageManager;

/**
 *
 * @author amnesia
 */
public class layerButton extends JButton
{
    Layer l;
    DynamicCanvas DC;
    
    public void setLayer(Layer l)
    {
        this.l = l;
    }
    
    public Layer getLayer()
    {
        return l;
    }
    
    public void updateIcon(Palette P)
    {
        if(l != null && l.getGenerator() != null)
        {
            DynamicCanvas DC = new DynamicCanvas(this.getWidth(), this.getHeight()-10, P);
            DC = l.getGenerator().draw(DC, 0);
            this.setIcon(new ImageIcon(ImageManager.constructImage(DC)));
        }
    }
}
