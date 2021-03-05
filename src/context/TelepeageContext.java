package context;

import java.util.ArrayList;

public class TelepeageContext extends PeageContext implements Context{

    protected ArrayList<String> BADGE_USAGERS = new ArrayList<>();
    protected ArrayList<String> FRAUDULEUX_USAGERS = new ArrayList<>();
    {
        BADGE_USAGERS.add(USAGERS.get(0));
        BADGE_USAGERS.add(USAGERS.get(1));
        BADGE_USAGERS.add(USAGERS.get(2));

        FRAUDULEUX_USAGERS.add(USAGERS.get(3));
        FRAUDULEUX_USAGERS.add(USAGERS.get(4));
    }

    public TelepeageContext(){
    }

    public ArrayList<String> getBADGE_USAGERS(){return BADGE_USAGERS;}

    public ArrayList<String> getFRAUDULEUX_USAGERS(){return FRAUDULEUX_USAGERS;}

    public ArrayList<String> getAll(){
        ArrayList<String> res = new ArrayList<>(BADGE_USAGERS);
        res.addAll(FRAUDULEUX_USAGERS);
        return res;
    }
}
