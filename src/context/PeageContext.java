package context;

import java.util.ArrayList;
import java.util.List;

public class PeageContext implements Context{

    private ArrayList<String> USAGERS;

    public PeageContext(){
        USAGERS = new ArrayList<>();
        USAGERS.add("Anastasia");
        USAGERS.add("Lucy");
        USAGERS.add("Jean");
        USAGERS.add("Paul");
        USAGERS.add("Claude");
    }

    public ArrayList<String> getAll(){
        return USAGERS;
    }
}
