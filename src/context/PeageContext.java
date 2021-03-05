package context;

import java.util.ArrayList;
import java.util.List;

public class PeageContext implements Context{

    protected ArrayList<String> USAGERS = new ArrayList<>();
    {
        USAGERS.add("Anastasia");
        USAGERS.add("Lucy");
        USAGERS.add("Jean");
        USAGERS.add("Paul");
        USAGERS.add("Claude");
        USAGERS.add("Herve");
        USAGERS.add("Josette");
    }

    public PeageContext(){
    }

    public ArrayList<String> getAll(){
        return USAGERS;
    }
}
