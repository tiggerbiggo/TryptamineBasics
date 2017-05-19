/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tryptamine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import trypResources.Layer;
import trypResources.Palette;

/**
 *
 * @author tiggerbiggo
 */
public class Viewport extends JPanel implements ActionListener
{
    ArrayList<BufferedImage> images;
    BufferedImage currentImage;
    
    int imageNumber;
    
    private boolean isWaiting;
    
    private ImageManager managerObject;
    private Thread managerThread;
    
    private Layer l;
    
    private Palette defaultPalette;
    
    Timer timer;
    
    public Viewport()
    {
        defaultPalette = new Palette(50,0);
        defaultPalette.setGradient(0, 25, Color.red, Color.blue);
             defaultPalette.setGradient(25, 49, Color.blue, Color.red);   
    }
    
    public void setLayer(Layer l)
    {
        this.l = l;
        
        updateImages();
        if(timer == null)
        {
            timer = new Timer(100, this);
            timer.start();
        }
    }
    
    public void setPalette(Palette P)
    {
        if(P != null)
            this.defaultPalette = P;
    }
    
    public void updateImages()
    {
        if(l != null)
        {
            if(managerThread == null || managerObject == null)
            {
                managerObject = new ImageManager();
                managerThread = new Thread(managerObject);
            }

            if(managerThread.isAlive())
            {
                //currently processing images
                isWaiting = true;
            }
            else
            {
                
                if(managerObject.isDone)
                {
                    isWaiting = false;
                    images = managerObject.threadImages;
                    managerObject.isDone = false;
                }
                else
                {
                    try
                    {
                        managerObject.setupForThread(l, this.getWidth(), this.getHeight(), defaultPalette);
                        isWaiting = true;
                        managerThread = new Thread(managerObject);
                        managerThread.start();
                    }
                    catch(Exception e)
                    {
                    }
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        if(isWaiting)
            updateImages();
        nextImage();
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
    
    public void nextImage() 
    {
        try
        {
            imageNumber++;
            imageNumber = normalize(imageNumber, images.size());
            currentImage = images.get(imageNumber);
            this.repaint();
        }
        catch(Exception e){}
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(currentImage != null)
        {
            g.drawImage(currentImage, 0,0,null);
        }
    }
    
    public void saveImage(String filename)
    {
        if(images != null)
        {
            fileManager.writeGif(images, filename);
        }
    }
    
}
