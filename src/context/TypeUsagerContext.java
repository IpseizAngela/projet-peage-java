package context;

import java.util.ArrayList;

public class TypeUsagerContext extends TelepeageContext implements Context{

    protected ArrayList<String> TICKET_USAGERS = new ArrayList<>();
    {
        TICKET_USAGERS.add(USAGERS.get(5));
        TICKET_USAGERS.add(USAGERS.get(6));
    }

    public TypeUsagerContext(){
    }

    public ArrayList<String> getTICKET_USAGERS(){
        return TICKET_USAGERS;
    }

    @Override
    public ArrayList<String> getAll(){
        ArrayList<String> res = new ArrayList<>(BADGE_USAGERS);
        res.addAll(FRAUDULEUX_USAGERS);
        res.addAll(TICKET_USAGERS);
        return res;
    }
}
