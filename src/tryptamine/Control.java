/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tryptamine;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import trypComponents.IconButtonPanel;
import trypResources.Layer;
import trypResources.Palette;

/**
 *
 * @author amnesia
 */
public class Control implements ComponentListener, ChangeListener, ActionListener
{
    
    final int buttonX = 5;
    final int buttonY = 5;
    
    JFrame controlWindow, viewWindow;
    
    Viewport viewport;
    
    JPanel sliderPanel;
    
    IconButtonPanel buttonPanel;
    
    Thread buttonPanelThread;
    
    ArrayList<BufferedImage> images;
    int currentImage = 0;
    int seed = 11;
    
    JSlider[] sliders;
    
    Palette P;
    
    public void initGUI() 
    {
        //Begin object definitions
        
        controlWindow = new JFrame("Control Panel");
        viewWindow = new JFrame("Viewport");
        viewport = new Viewport();
        sliderPanel = new JPanel();
        buttonPanel = new IconButtonPanel(buttonX, buttonY, this);
        
        //Begin setup
        
        controlWindow.setLayout(new GridBagLayout());
        controlWindow.setBounds(10, 10, 500, 500);
        controlWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlWindow.setResizable(true);
        controlWindow.setLocationRelativeTo(null);
        controlWindow.setVisible(false);
        controlWindow.addComponentListener(this);
        
        viewWindow.setLayout(new GridLayout(1,1));
        viewWindow.setBounds(10, 10, 500, 500);
        viewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewWindow.setResizable(true);
        viewWindow.setLocationRelativeTo(null);
        viewWindow.setVisible(false);
        viewWindow.addComponentListener(this);
        
        buttonPanel.setLayout(new GridLayout(buttonX, buttonY));
        
        sliderPanel.setLayout(new GridLayout(1,0));
        
        viewWindow.add(viewport);
        
        //begin slider definitions
        
        sliders = new JSlider[3];
        
        for(int i=0; i<3; i++)
        {
            sliders[i] = new JSlider();
            sliders[i].setMaximum(50);
            sliders[i].addChangeListener(this);
            sliderPanel.add(sliders[i]);
        }
        
        //begin gridbag definitions
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx=0;
        c.gridy=0;
        c.fill=GridBagConstraints.BOTH;
        c.weightx=1;
        c.weighty=1;
        c.gridwidth=1;
        
        
        
        
        c.gridx=0;
        
        controlWindow.add(buttonPanel, c);
        
        
        c.fill=GridBagConstraints.BOTH;
        c.gridwidth=1;
        c.gridy=1;
        
        c.weighty=1;
        
        controlWindow.add(sliderPanel, c);
        
        
        
        P = new Palette(100,0);
        P.setGradient(0, 50, Color.black, Color.white);
        P.setGradient(50, 99, Color.white, Color.black);
        
        Random tmpRand = new Random();
        
        seed = tmpRand.nextInt();
    }
    
    public void setImages(ArrayList<BufferedImage> images) {
        this.images = images;
        currentImage = 0;
        drawImage();
    }

    public void addImage(BufferedImage toAdd) {

        try
        {
            images.add(toAdd);
        }
        catch(Exception e){}
    }

    

    public int getImageIndex()
    {
        return currentImage;
    }
    
    public void setImageIndex(int index) 
    {
        try
        {
            currentImage = normalize(index, images.size());
        }
        catch(Exception e){}
        
    }
    
    public boolean imagesExist()
    {
        return images!=null;
    }
    

    public int normalize(int toNormalize, int normal) 
    {
        while (toNormalize >= normal) 
        {
            toNormalize -= normal;
        }
        while (toNormalize < 0) 
        {
            toNormalize += normal;
        }
        return toNormalize;
    }

    public boolean checkGraphics()
    {
        return sliderPanel.getGraphics() != null;
    }
    
    public void drawImage() 
    {
        
        try
        {
            sliderPanel.getGraphics().drawImage(images.get(currentImage), 0, 0, null);
        }
        catch(Exception e){}
    }
    
    
    
    public int getWidth()
    {
        return controlWindow.getWidth();
    }
    
    public int getHeight()
    {
        return controlWindow.getHeight();
    }
    
    public void updateButtonPanel()
    {
        if(buttonPanelThread == null)
        {
            buttonPanelThread = new Thread(buttonPanel);
        }
        
        
        
        if(buttonPanelThread.isAlive())
        {
            buttonPanelThread.interrupt();
        }
        else
        {
            
            buttonPanel.setWeights(
                new Random(seed), 
                new Random(seed), 
                sliders[0].getValue(), 
                sliders[1].getValue(), 
                sliders[2].getValue());
            try
            {
                buttonPanelThread = new Thread(buttonPanel);
                buttonPanelThread.start();
            }
            catch(Exception e)
            {
                
            }
        }
        
        
        
        
    }
    
    public synchronized void show()
    {
        controlWindow.setVisible(true);
        viewWindow.setVisible(true);
        
        updateButtonPanel();
        
        try
        {
            wait();
        }
        catch(Exception e)
        {
            
        }
    }

    @Override
    public void componentResized(ComponentEvent e) 
    {
        //refreshImages();
        updateButtonPanel();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void stateChanged(ChangeEvent e) 
    {
        updateButtonPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //get layer from button panel
        
        Layer l = buttonPanel.getLayerFromButton(e);
        
        if(l != null)
        {
            viewport.setLayer(l);
            System.out.println("Layer Set");
        }
    }
}
