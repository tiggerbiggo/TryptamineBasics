/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tryptamine;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import trypComponents.IntegerPanel;

class intChooser extends JPanel
{
    JButton[] buttons;
    
    //for(int i=0; i<=3; i++)
    //{
        
    //}
}

/**
 *
 * @author lea14141039
 */
public class Splash extends JFrame implements ActionListener
{
    JCheckBox fullScreenEnabled, saveButtonEnabled;
    
    //IntegerPanel randomTime;
    
    JRadioButton[] buttons;
    
    JPanel radioButtonPanel;
    
    ButtonGroup group;
    
    JButton launch;
    
    public Splash()
    {
        this.setBounds(0,0,500,500);
        this.setLayout(new GridLayout(0,1));
        this.setResizable(false);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        fullScreenEnabled = new JCheckBox("Full Screen");
        this.add(fullScreenEnabled);
        
        
        
        radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new GridLayout(1,0));
        group = new ButtonGroup();
        
        try
        {
            GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
            
            buttons = new JRadioButton[gd.length];
            
            for(int i=0; i<gd.length; i++)
            {
                buttons[i] = new JRadioButton(gd[i].getIDstring());
                group.add(buttons[i]);
                radioButtonPanel.add(buttons[i]);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            buttons = null;
            radioButtonPanel.removeAll();
            radioButtonPanel.add(new JLabel("No valid display devices found"));
        }
        
        this.add(radioButtonPanel);
        
        launch = new JButton("Launch");
        launch.addActionListener(this);
        
        this.add(launch);
    }
    
    public synchronized void showMe()
    {
        this.setVisible(true);
        
        try
        {
            wait();
        }
        catch(Exception e)
        {
            
        }
        
        this.setVisible(false);
    }
    
    public Control getControlObject()
    {
        return new Control(false, -1, new int[]{50,100,100}, fullScreenEnabled.isSelected(), getDisplay());
    }
    
    public int getDisplay()
    {
        if(buttons != null)
        {
            for(int i=0; i<buttons.length; i++)
            {
                if(buttons[i].isSelected())
                {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e)
    {
        notify();
    }
}
