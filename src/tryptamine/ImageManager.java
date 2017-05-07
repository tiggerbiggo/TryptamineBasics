/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tryptamine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import trypResources.Layer;
import trypResources.Palette;



/**
 *
 * @author amnesia
 */
public class ImageManager implements Runnable
{
    public static BufferedImage constructImage(DynamicCanvas DC, int x, int y)
    {
        BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<x; i++)
        {
            for(int k=0; k<y; k++)
            {
                Color C = DC.getColor(i, k);
                if(C != null) img.setRGB(i, k, C.getRGB());
            }
        }
        return img;
    }
    
    public static BufferedImage constructImage(DynamicCanvas DC)
    {
        BufferedImage img = new BufferedImage(DC.x, DC.y, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<DC.x; i++)
        {
            for(int k=0; k<DC.y; k++)
            {
                Color C = DC.getColor(i, k);
                if(C != null) img.setRGB(i, k, C.getRGB());
            }
        }
        return img;
    }
    
    public static void constructImage(DynamicCanvas DC, int x, int y, Graphics G)
    {
        BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<x; i++)
        {
            for(int k=0; k<y; k++)
            {
                G.setColor(DC.getColor(i, k));
                G.drawLine(i-1, k-1, i-1, k-1);
            }
        }
    }
    
    public static ArrayList<BufferedImage> constructSequence(DynamicCanvas DC)
    {
        try
        {
            int pNum = DC.getPalette(0).getNum()-1;

            ArrayList<BufferedImage> ImageSequence = new ArrayList();
            for(int o=0; o<=pNum; o++)
            {


                ImageSequence.add(ImageManager.constructImage(DC, DC.getX(), DC.getY()));
                DC.cycleAll(1);
            }
            return ImageSequence;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public static ArrayList<BufferedImage> constructSequence(DynamicCanvas DC, int num)
    {
        try
        {
            ArrayList<BufferedImage> ImageSequence = new ArrayList();
            for(int o=0; o<=num; o++)
            {


                ImageSequence.add(ImageManager.constructImage(DC, DC.getX(), DC.getY()));
                DC.cycleAll(1);
            }
            return ImageSequence;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    private ArrayList<BufferedImage> constructSequenceThreaded(DynamicCanvas DC)
    {
        try
        {
            int pNum = DC.getPalette(0).getNum()-1;

            ArrayList<BufferedImage> ImageSequence = new ArrayList();
            for(int o=0; o<=pNum; o++)
            {
                ImageSequence.add(ImageManager.constructImage(DC, DC.getX(), DC.getY()));
                if(Thread.interrupted())
                {
                    return null;
                }
                DC.cycleAll(1);
            }
            isDone = true;
            return ImageSequence;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    
    Layer threadLayer;
    ArrayList<BufferedImage> threadImages;
    int x, y;
    boolean isDone;
    Palette P;
    
    public void setupForThread(Layer l, int x, int y, Palette P) throws IllegalArgumentException
    {
        if(
                l == null || 
                x<=0 || 
                y<=0 || 
                P == null)
        {
            throw new IllegalArgumentException();
        }
        
        this.threadLayer = l;
        this.x = x;
        this.y = y;
        this.P = P;
    }
    
    @Override
    public void run()
    {
        if(threadLayer != null)
        {
            isDone = false;
            DynamicCanvas DC = new DynamicCanvas(x,y,P);
            DC = threadLayer.getGenerator().draw(DC, 0);
            threadImages = constructSequenceThreaded(DC);
        }
    }
}
