/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trypResources;

import java.util.ArrayList;

/**
 *
 * @author lea14141039
 */
public class Formula 
{
    private ArrayList<Formula> formulaList;
    private Operation thisOp;
    private Function thisFunc;
    private double thisNum;

    public Formula(Function thisFunc, double thisNum) {
        thisOp = null;
        this.thisFunc = thisFunc;
        this.thisNum = thisNum;
        formulaList = new ArrayList();
    }
    
    public Formula(Function thisFunc, double thisNum, Operation thisOp) {
        this.thisOp = thisOp;
        this.thisFunc = thisFunc;
        this.thisNum = thisNum;
        formulaList = new ArrayList();
    }
    
    public Formula(Function thisFunc, double thisNum, Operation thisOp, Formula single) {
        this.thisOp = thisOp;
        this.thisFunc = thisFunc;
        this.thisNum = thisNum;
        formulaList = new ArrayList();
        formulaList.add(single);
    }
    
    public Formula(Function thisFunc, double thisNum, Operation thisOp, ArrayList<Formula> list) {
        this.thisOp = thisOp;
        this.thisFunc = thisFunc;
        this.thisNum = thisNum;
        if(list != null)
        {
            formulaList = list;
        }
        else
        {
            formulaList = new ArrayList();
        }
    }
    
    public double Calculate(double x)
    {
        if(formulaList.isEmpty())
        {
            return getSelf(x);
        }
        
        double tmp1, tmp2;
        
        tmp2=0;
        for(int i=0; i<formulaList.size(); i++)
        {
            tmp1 = formulaList.get(i).Calculate(x);
            if(i!=0)
            {
                tmp2=doOperation(tmp1, tmp2, formulaList.get(i-1).thisOp);
            }
            else
            {
                tmp2 = tmp1;
            }
        }
        
        if(thisOp == Operation.ENCLOSE)
        {
            return getSelf(tmp2);
        }
        return doOperation(thisNum, tmp2, thisOp);
    }
    
    
    public double doOperation(double a, double b, Operation op)
    {
        if(op ==null)
        {
            return b+a;
        }
        switch(op)
        {
            case ADD:
                return b+a;
            case SUBTRACT:
                return b-a;
            case MULTIPLY:
                return b*a;
            case DIVIDE:
                if(a==0)
                {
                    return 0;
                }
                else
                {
                    return b/a;
                }
            case POWER:
                return b*a*a;//Real powers are really slow, so I'm replacing them with this until I get something better and faster
        }
        return 0;
    }
    private double getSelf(double x)
    {
        switch(thisFunc)
        {
            case SIN:
                return thisNum*Math.sin(x);
            case COS:
                return thisNum*Math.cos(x);
            case TAN:
                return thisNum*Math.tan(x);
            case LOG:
                return thisNum*Math.log(x);
            case CONSTANT:
                return thisNum;
        }
        
        return thisNum;
    }
}
