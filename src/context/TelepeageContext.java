package context;

import java.util.ArrayList;

public class TelepeageContext implements Context{

    private ArrayList<String> BADGE_USAGERS;
    private ArrayList<String> FRAUDULEUX_USAGERS;

    public TelepeageContext(){
        BADGE_USAGERS = new ArrayList<>();
        BADGE_USAGERS.add("Anastasia");
        BADGE_USAGERS.add("Lucy");
        BADGE_USAGERS.add("Jean");

        FRAUDULEUX_USAGERS = new ArrayList<>();
        FRAUDULEUX_USAGERS.add("Paul");
        FRAUDULEUX_USAGERS.add("Claude");

    }

    public ArrayList<String> getBADGE_USAGERS(){return BADGE_USAGERS;}

    public ArrayList<String> getFRAUDULEUX_USAGERS(){return FRAUDULEUX_USAGERS;}

    public ArrayList<String> getAll(){
        ArrayList<String> res = new ArrayList<>(BADGE_USAGERS);
        res.addAll(FRAUDULEUX_USAGERS);
        return res;
    }
}
