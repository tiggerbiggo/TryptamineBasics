/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypResources;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author amnesia
 */
public class ColorTools 
{
    public static Color invert(Color C)
    {
        return new Color(255-C.getRed(), 255-C.getGreen(), 255-C.getBlue());
    }
    
    public static Color randomColor(Random rand)
    {
        if(rand == null) rand = new Random();
        
        return new Color(
                rand.nextInt(255), 
                rand.nextInt(255), 
                rand.nextInt(255));
    }
}
