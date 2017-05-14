package trypResources;

public class Vector2 
{
    public double x, y;
    public Vector2(double x, double y)
    {
        this.x=x;
        this.y=y;
    }
    
    public Vector2 add(Vector2 toAdd)
    {
        if(toAdd != null)
        {
            x+=toAdd.getX();
            y+=toAdd.getY();
        }
        return this;
    }
    
    public int getXInt()
    {
        return (int)Math.round(x);
    }
    
    public int getYInt()
    {
        return (int)Math.round(y);
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public static double distanceToPoint(Vector2 start, Vector2 end)
    {
        double a, b;
        
        a=Math.abs(Math.abs(start.x)-Math.abs(end.x));
        
        b=Math.abs(Math.abs(start.y)-Math.abs(end.y));
        
        return Math.sqrt(Math.pow(a, 2)+Math.pow(b,2));//sqrt(a^2+b^2)
    }
}
