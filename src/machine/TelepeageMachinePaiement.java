package machine;

import context.Context;
import context.PeageContext;
import context.TelepeageContext;

import java.util.ArrayList;

public class TelepeageMachinePaiement extends PeageMachineUsager{

    private ArrayList<String> USAGERS;
    private ArrayList<String> BADGEUSAGERS;
    private ArrayList<String> FRAUDULEUXUSAGER;
    private ArrayList<String> usagers;
    private ArrayList<String> badge_usagers;
    private boolean dispositif;
    private ArrayList<String> aSignaler;
    private ArrayList<String> aVerifier;

    public TelepeageMachinePaiement(){
        TelepeageContext context = new TelepeageContext();
        FRAUDULEUXUSAGER = context.getFRAUDULEUX_USAGERS();
        BADGEUSAGERS = context.getBADGE_USAGERS();
        USAGERS = new PeageContext().getAll();
        usagers = new ArrayList<>();
        badge_usagers = new ArrayList<>();
        dispositif = false;
        aVerifier = new ArrayList<>();
        aSignaler = new ArrayList<>();

        test(BADGEUSAGERS.get(0));
    }

    protected void test(String u){

        System.out.println("\n========================");
        //entrer
        entrer(u);

        //sortir
        sortir(u);

        System.out.println("\n========================");
    }

    protected void entrerTroncon(String usager){
        if(!usagers.contains(usager) &&
            !aVerifier.contains(usager) &&
            !dispositif &&
            USAGERS.contains(usager)){
            // On fait un extends de la fonctions entrer
            entrer(usager);
            aVerifier.add(usager);
            dispositif = true;

            System.out.println("Ajout de "+usager+" dans usagers / "+usager+" rentre sur l'autoroute et est en attente d'autorisation");
        }else{
            System.out.println(usager+" est déjà sur l'autoroute");
        }
    }

    protected void autorise(String usager){
        if(aVerifier.contains(usager) &&
            BADGEUSAGERS.contains(usager) &&
            !FRAUDULEUXUSAGER.contains(usager) &&
            dispositif){

            dispositif = false;
            badge_usagers.add(usager);
            aVerifier.remove(usager);

            System.out.println("L'usager: "+usager+" a un badge et est autorisé à passer");
        }else if(aVerifier.contains(usager) &&
                 !BADGEUSAGERS.contains(usager) &&
                 FRAUDULEUXUSAGER.contains(usager) &&
                 dispositif){

            dispositif = false;
            aSignaler.add(usager);
            aVerifier.remove(usager);

            System.out.println("L'usager: "+usager+" n'a pas de badge et n'est pas autorisé à passer, il est signalé");
        }else{
            System.out.println("Erreur: la fonction \"autorise\" a rencontré un mauvais cas");
            System.exit(-1);
        }
    }


    protected void sortirTronconBadge(String usager){
        if(usagers.contains(usager) &&
            !aVerifier.contains(usager) &&
            badge_usagers.contains(usager)){
            // On fait un extends de la fonctions entrer
            sortir(usager);
            badge_usagers.remove(usager);
            System.out.println("Suppression de "+usager+" dans usagers et badge_usagers / "+usager+" sort de l'autoroute");
        }else{
            System.err.println(usager+" n'est pas sur l'autoroute");
        }
    }

    protected void sortirTronconFrauduleux(String usager){
        // Les frauduleux qui sont sur l'autoroute(Intersection entre usagers et a_signaler)
        ArrayList<String> frauduleuxSurAutoroute = new ArrayList<>();
        for(String u : usagers){
            if(aSignaler.contains(u)){
                frauduleuxSurAutoroute.add(u);
            }
        }
        if(usagers.contains(usager) &&
                !aVerifier.contains(usager) &&
                badge_usagers.contains(usager) &&
                frauduleuxSurAutoroute.contains(usager)){
            // On fait un extends de la fonctions entrer
            sortir(usager);
            System.out.println("Suppression de "+usager+" dans usagers et badge_usagers / "+usager+" sort de l'autoroute");
        }else{
            System.out.println(usager+" n'est pas sur l'autoroute");
        }
    }

    public static void main(String[] args) {
        new TelepeageMachinePaiement();
    }
}
