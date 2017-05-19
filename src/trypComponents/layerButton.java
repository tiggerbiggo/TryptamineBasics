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
public class layerButton extends JButton implements Runnable
{
    public Layer l;
    public Palette P;
    public boolean needsUpdate;
    
    public void updateIcon()
    {
        if(l != null && l.getGenerator() != null)
        {
            DynamicCanvas DC = new DynamicCanvas(this.getWidth()-10, this.getHeight()-10, P);
            DC = l.getGenerator().draw(DC, 0);
            this.setIcon(new ImageIcon(ImageManager.constructImage(DC)));
        }
    }

    @Override
    public void run()
    {
        do
        {
            needsUpdate = false;
            updateIcon();
        }
        while(needsUpdate);
        repaint();
    }
}
