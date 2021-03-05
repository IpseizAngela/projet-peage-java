package machine;

import context.Context;
import context.PeageContext;

import java.util.ArrayList;

public class PeageMachineUsager {

    protected ArrayList<String> USAGERS;
    protected ArrayList<String> usagers;

    public PeageMachineUsager(){
        Context context = new PeageContext();
        USAGERS = context.getAll();
        usagers = new ArrayList<>();

    }

    protected void test(){
        String u = USAGERS.get(0);
        System.out.println("\n================ICI========");
        //entrer
        entrer(u);

        //sortir
        sortir(u);
    }

    protected void entrer(String usager){
        if(!usagers.contains(usager)){
            usagers.add(usager);
            System.out.println("Ajout de "+usager+" dans usagers / "+usager+" rentre sur l'autoroute");
        }else{
            System.out.println(usager+" est déjà sur l'autoroute");
        }
    }

    protected void sortir(String usager){
        if(usagers.contains(usager)){
            usagers.remove(usager);
            System.out.println("Suppression de "+usager+" dans usagers / "+usager+" sort de l'autoroute");
        }else{
            System.out.println(usager+" n'est pas sur l'autoroute");
        }
    }

    protected void sep(){
        System.out.println("----------------------");
    }

    public static void main(String[] args) {
        PeageMachineUsager m = new PeageMachineUsager();
        m.test();
    }
}
