package tryptamine;

public class Main
{
    public static void main(String[] args) 
    {
        Splash S = new Splash();
        S.showMe();
        
        //initGUI(true, 5000, new int[]{50, 100, 100}, false, -1);
        
        Control C = S.getControlObject();
        C.initGUI();
        C.show();
    }
}
