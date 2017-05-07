/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypComponents;

import java.util.ArrayList;
import trypResources.Formula;
import trypResources.Function;
import trypResources.Operation;

/**
 *
 * @author amnesia
 */
public class FormulaPresets 
{
    
    
    public static ArrayList<Formula> setupFormulas(double coeff, double freq)
    {
        ArrayList<Formula> formulas = new ArrayList();
        
        Formula F;
        int i=0;
        
        F = new Formula(Function.SIN);
        F = setFValues(F, coeff, freq);
        i++;
        formulas.add(F);
        
        F = new Formula(Function.COS, F, Operation.ADD);
        F = setFValues(F, coeff, freq);
        i++;
        formulas.add(F);
        
        F = new Formula(Function.SIN, F, Operation.MULTIPLY);
        F = setFValues(F, coeff, freq);
        i++;
        formulas.add(F);
        
        F = new Formula(Function.TAN);
        F = setFValues(F, coeff, freq);
        i++;
        formulas.add(F);
        
        F = new Formula(Function.SIN, F, Operation.ENCLOSE);
        F = setFValues(F, coeff, freq);
        i++;
        formulas.add(F);
        
        return formulas;
    }
    
    private static Formula setFValues(Formula F, double coeff, double freq)
    {
        try
        {
            F.setCoeff(coeff);
            F.setFreq(freq);
            return F;
        }
        catch(Exception e)
        {
            
            return null;
        }
    }
}
