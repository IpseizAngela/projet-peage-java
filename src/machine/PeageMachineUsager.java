package machine;

import context.Context;
import context.PeageContext;
import org.graalvm.compiler.word.BarrieredAccess;

import java.util.ArrayList;

public class PeageMachineUsager {

    private ArrayList<String> USAGERS;
    private ArrayList<String> usagers;

    public PeageMachineUsager(){
        Context context = new PeageContext();
        USAGERS = context.getAll();
        usagers = new ArrayList<>();

        //entrer
        entrer(USAGERS.get(0););

        //sortir
        sortir();
    }

    private void entrer(String usager){

    }
}
