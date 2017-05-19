/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypGenerators;

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
    
    
    public static ArrayList<Formula> setupFormulas(double coeff)
    {
        ArrayList<Formula> formulas = new ArrayList();
        ArrayList<Formula> tmpList = new ArrayList();
        
        Formula F;
        
        F = new Formula(Function.TAN, coeff, Operation.ADD); //y=sin(coeff*x)
        tmpList.add(F);
        formulas.add(F);
        
        F = new Formula(Function.SIN, coeff*1.25, Operation.ADD, F);//y=cos(coeff*1.25*x)
        tmpList.add(F);
        formulas.add(F);
        
        F = new Formula(Function.LOG, coeff, Operation.POWER, new ArrayList(tmpList));//y=1*(sin(coeff*x)+cos(coeff*1.25*x))
        tmpList = new ArrayList();
        //tmpList.add(F);
        formulas.add(F);
        
        F = new Formula(Function.SIN, coeff, Operation.DIVIDE);
        tmpList.add(F);
        formulas.add(F);
        
        F = new Formula(Function.TAN, coeff/10, Operation.SUBTRACT);
        tmpList.add(F);
        formulas.add(F);
        
        F = new Formula(Function.LOG, coeff*3, Operation.DIVIDE);
        tmpList.add(F);
        formulas.add(F);
        
        F = new Formula(Function.CONSTANT, 1, Operation.ENCLOSE, new ArrayList(tmpList));
        formulas.add(F);
        
        return formulas;
    }
}
