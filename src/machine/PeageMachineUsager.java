package machine;

import context.Context;
import context.PeageContext;

import java.util.ArrayList;

public class PeageMachineUsager {

    private ArrayList<String> USAGERS;
    private ArrayList<String> usagers;

    public PeageMachineUsager(){
        Context context = new PeageContext();
        USAGERS = context.getAll();
        usagers = new ArrayList<>();

        test(USAGERS.get(0));
    }

    private void test(String u){

        System.out.println("\n========================");
        //entrer
        entrer(u);

        //sortir
        sortir(u);
    }

    private void entrer(String usager){
        if(!usagers.contains(usager)){
            usagers.add(usager);
            System.out.println("Ajout de "+usager+" dans usagers / "+usager+" rentre sur l'autoroute");
        }else{
            System.out.println(usager+" est déjà sur l'autoroute");
        }
    }

    private void sortir(String usager){
        if(usagers.contains(usager)){
            usagers.remove(usager);
            System.out.println("Suppression de "+usager+" dans usagers / "+usager+" sort de l'autoroute");
        }else{
            System.out.println(usager+" n'est pas sur l'autoroute");
        }
    }

    public static void main(String[] args) {
        new PeageMachineUsager();
    }
}
