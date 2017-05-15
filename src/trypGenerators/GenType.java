package trypGenerators;
import trypLayerPanels.*;
import trypParams.Parameter;

public enum GenType 
{
    NULL("Null"),
    CIRCULAR("Circular"),
    FORMULA("Formula"),
    SIMPLELINES("Simple Lines"),
    DISTORTFORMULA("Distort Formula"),
    POLKADOT("Polkadot"),
    SIERPINSKI("Sierpinski"),
    DUALFORMULA("Dual Formula");
    
    String name;
    GenType(String name)
    {
        this.name = name;
    }
    public static AbstractGenerator getNewGenerator(GenType ID, Parameter[] params)
    {
        try
        {
            switch(ID) // ADD YOUR GENERATOR CALL HERE
            {
                case CIRCULAR:
                    return new Gen_Circular(params);
                case FORMULA:
                    return new Gen_Formula(params);
                case SIMPLELINES:
                    return new Gen_SimpleLines(params);
                case DISTORTFORMULA:
                    return new Gen_DistortFormula(params);
                case POLKADOT:
                    return new Gen_Polkadot(params);
                case SIERPINSKI:
                    return new Gen_Sierpinski();
                case DUALFORMULA:
                    return new Gen_DualFormula(params);
            }
        }
        catch(IllegalArgumentException e){}
            
        return null;
    }
    
    public static AbstractLayerPanel getPanel(GenType type)
    {
        switch(type)
        {
            case CIRCULAR:
                return new CircularLayerPanel();
            case FORMULA:
                return new FormulaLayerPanel();
            case SIMPLELINES:
                return new SimpleLinesLayerPanel();
            case DISTORTFORMULA:
                return new DistortFormulaLayerPanel();
            case POLKADOT:
                return new PolkaDotLayerPanel();
        }
        return new NullLayerPanel();
    }
}