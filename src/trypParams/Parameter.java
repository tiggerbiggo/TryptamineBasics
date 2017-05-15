package trypParams;

import trypResources.CircleModes;
import trypResources.Formula;

/**
 *
 * @author timot_000
 */
public final class Parameter<T> 
{
    private T t;

    public Parameter(T t)
    {
        this.t = t;
    }
    
    public T get()
    {
        return t;
    }
    
    public Object getType()
    {
        return t.getClass();
    }
}
