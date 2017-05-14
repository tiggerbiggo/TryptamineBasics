/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tryptamine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import trypComponents.ColorPicker;
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
    
    JFrame controlWindow, viewWindow, buttonWindow;
    
    Viewport viewport;
    
    JPanel sliderPanel, colorPanel;
    
    IconButtonPanel buttonPanel;
    
    Thread buttonPanelThread;
    
    ArrayList<BufferedImage> images;
    int currentImage = 0;
    int seed;
    final int pNum = 50;
    
    JSlider[] sliders;
    
    ColorPicker cPicker1, cPicker2;
    JButton newSeedButton;
    
    Palette P;
    
    Layer currentLayer;
    
    Timer refreshTimer;
    
    public void initGUI() 
    {
        //Begin object definitions
        
        buttonWindow = new JFrame("Click the patterns");
        controlWindow = new JFrame("Control Panel");
        viewWindow = new JFrame("Viewport");
        viewport = new Viewport();
        sliderPanel = new JPanel();
        buttonPanel = new IconButtonPanel(buttonX, buttonY, this);
        colorPanel = new JPanel();
        
        cPicker1 = new ColorPicker(true, null, this);
        cPicker2 = new ColorPicker(false, null, this);
        
        newSeedButton = new JButton("Current Seed:" + seed);
        setSeed(-1);
        
        
        //Begin setup
        
        controlWindow.setLayout(new GridBagLayout());
        controlWindow.setBounds(10, 10, 500, 500);
        controlWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlWindow.setResizable(true);
        controlWindow.setLocationRelativeTo(null);
        controlWindow.setVisible(false);
        
        viewWindow.setLayout(new GridLayout(1,1));
        viewWindow.setBounds(10, 10, 500, 500);
        viewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewWindow.setResizable(false);
        viewWindow.setLocationRelativeTo(controlWindow);
        viewWindow.setVisible(false);
        viewWindow.addComponentListener(this);
        
        buttonWindow.setLayout(new GridLayout(1,1));
        buttonWindow.setBounds(10, 10, 500, 500);
        buttonWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonWindow.setResizable(false);
        buttonWindow.setLocationRelativeTo(null);
        buttonWindow.setVisible(false);
        buttonWindow.addComponentListener(this);
        
        
        buttonPanel.setLayout(new GridLayout(buttonX, buttonY));
        buttonWindow.add(buttonPanel);
        
        sliderPanel.setLayout(new GridLayout(1,0));
        colorPanel.setLayout(new GridLayout(1,3));
        
        newSeedButton.addActionListener(this);
        
        colorPanel.add(cPicker1);
        colorPanel.add(cPicker2);
        
        
        
        viewWindow.add(viewport);
        
        //begin slider definitions
        
        sliders = new JSlider[3];
        int[] nums = {
            10, 50, 50
        };
        
        for(int i=0; i<3; i++)
        {
            sliders[i] = new JSlider();
            sliders[i].setMaximum(nums[i]);
            sliders[i].setValue(0);
            sliders[i].addChangeListener(this);
            sliderPanel.add(sliders[i]);
        }
        
        //begin gridbag definitions
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx=0;
        c.gridy=0;
        c.fill=GridBagConstraints.BOTH;
        c.weightx=1;
        c.weighty=0.3;
        c.gridwidth=1;
        
        controlWindow.add(sliderPanel, c);
        c.weighty=1;
        
        
        c.gridy=1;
        controlWindow.add(colorPanel, c);
        
        c.gridy=2;
        
        controlWindow.add(newSeedButton, c);
        
        
        P = new Palette(pNum,0);
        
        updatePalette();
        
        
        refreshTimer = new Timer(50, this);
    }
    
    public void updatePalette()
    {
        P.setGradient(0, pNum/2, cPicker1.getColor(), cPicker2.getColor());
        P.setGradient(pNum/2, pNum-1, cPicker2.getColor(), cPicker1.getColor());
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
            //buttonPanelThread.interrupt();
        }
        else
        {
            
            buttonPanel.setWeights(
                new Random(seed), 
                new Random(seed), 
                sliders[0].getValue(), 
                sliders[1].getValue(), 
                sliders[2].getValue());
            buttonPanel.setPalette(P);
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
        viewWindow.setUndecorated(true);
        viewWindow.setVisible(true);
        
        buttonWindow.setVisible(true);
        
        buttonWindow.revalidate();
        
        
        cPicker1.setColor(Color.red);
        cPicker2.setColor(Color.black);
        
        refreshTimer.start();
        
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
        updatePalette();
        updateButtonPanel();
        //updateViewport(null);
        
    }

    public void setSeed(int seed)
    {
        if(seed <=-1)
        {
            this.seed = new Random().nextInt();
        }
        else
        {
            this.seed = seed;
        }
        
        newSeedButton.setText("Click me to randomise the patterns");
        System.out.println(this.seed);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //get layer from button panel
        
        if(e.getSource() == newSeedButton)
        {
            setSeed(-1);
            updateButtonPanel();
        }
        else updateViewport(e);
    }
    
    public void updateViewport(ActionEvent e)
    {
        if(e != null)
            currentLayer = buttonPanel.getLayerFromButton(e);
        
        if(currentLayer != null)
        {
            
            try
            {
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[1];

                if(gd.isFullScreenSupported())
                {
                    
                    
                    gd.setFullScreenWindow(viewWindow);
                    
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            viewport.setPalette(P);
            viewport.setLayer(currentLayer);
        }
    }
}
