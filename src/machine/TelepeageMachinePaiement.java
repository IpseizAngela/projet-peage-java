package machine;

import context.Context;
import context.PeageContext;
import context.TelepeageContext;

import java.util.ArrayList;

public class TelepeageMachinePaiement extends PeageMachineUsager{

    // Ensembles des contextes
    private ArrayList<String> USAGERS;
    private ArrayList<String> BADGEUSAGERS;
    private ArrayList<String> FRAUDULEUXUSAGER;

    // Variables
    //Usagers sur l'autoroute avec un badge de télépéage
    protected ArrayList<String> badge_usagers;
    //Dispositif de détection de passage d'un usager Vrai si un usager vient de passer le télépéage
    protected boolean dispositif;
    //Usager à signaler
    protected ArrayList<String> aSignaler;
    //Usager qui n'ont pas été vérifié encore, s'ils ont un badge ou non
    protected ArrayList<String> aVerifier;

    public TelepeageMachinePaiement(){
        TelepeageContext context = new TelepeageContext();
        // On récupère les ensembles du contexte
        FRAUDULEUXUSAGER = context.getFRAUDULEUX_USAGERS();
        BADGEUSAGERS = context.getBADGE_USAGERS();
        USAGERS = new PeageContext().getAll();
        //l'ensemble des usagers courant est vide, aucun usager n'est sur le tronçon
        usagers = new ArrayList<>();
        //aucun usager avec badge n'est sur le tronçon au début
        badge_usagers = new ArrayList<>();
        //le dispositif n'est pas activé
        dispositif = false;
        // aucun usager n'est à vérifier au début
        aVerifier = new ArrayList<>();
        //aucun usager n'est à signaler au début
        aSignaler = new ArrayList<>();

        test(USAGERS);
    }

    protected void test(ArrayList<String> aTest){

        System.out.println("\n========================");
        for (String u : aTest) {
            //entrer
            entrerTroncon(u);
            autorise(u);
            //sortir
            sortirTroncon(u);
        }
        System.out.println("\n========================");
    }

    protected void afficherErreur(String msg){
        System.err.println(msg);
        System.exit(-1);
    }

    //Entrée dans le tronçon
    protected void entrerTroncon(String usager){
        if(!usagers.contains(usager) &&
            !aVerifier.contains(usager) &&
            !dispositif &&
            USAGERS.contains(usager)){

            // On fait un extends de la fonctions entrer
            entrer(usager);
            //L'usager fait maintenant parti des usagers à vérifier
            aVerifier.add(usager);
            //Le dispositif détecte qu'un usager est passé
            dispositif = true;

            System.out.println("Ajout de "+usager+" dans usagers / "+usager+" est en attente d'autorisation");
        }else{
            System.out.println(usager+" est déjà sur l'autoroute");
        }
    }

    // Regarde si l'usager est autorisé à entrer sur le tronçon ou non
    protected void autorise(String usager){
        //autorisé à entrer sur le tronçon car utilisation d'un badge
        if(aVerifier.contains(usager) &&
            BADGEUSAGERS.contains(usager) &&
            !FRAUDULEUXUSAGER.contains(usager) &&
            dispositif){

            //Le dispositif se remet dans son état de départ car l'usager est passé
            dispositif = false;
            //On ajoute les usagers avec un badge sur le tronçon dans la liste
            badge_usagers.add(usager);
            //On enlève l'usager de la liste des usagers à vérifier
            aVerifier.remove(usager);

            System.out.println("L'usager: "+usager+" a un badge et est autorisé à passer");
        //non autorisé à entrer sur le tronçon car utilisation entré sans badge
        }else if(aVerifier.contains(usager) &&
                 !BADGEUSAGERS.contains(usager) &&
                 FRAUDULEUXUSAGER.contains(usager) &&
                 dispositif){

            //Le dispositif se remet dans son état de départ car l'usager est passé
            dispositif = false;
            //On ajoute l'usager aux signalements
            aSignaler.add(usager);
            //On enlève l'usager de la liste des usagers à vérifier
            aVerifier.remove(usager);

            System.out.println("L'usager: "+usager+" n'a pas de badge et n'est pas autorisé à passer, il est signalé");
        }else{
            afficherErreur("Erreur: la fonction \"autorise\" a rencontré un mauvais cas");
        }
    }

    protected void sortirTroncon(String usager){
        // Les frauduleux qui sont sur l'autoroute(Intersection entre usagers et a_signaler)
        ArrayList<String> frauduleuxSurAutoroute = new ArrayList<>();
        for(String u : usagers){
            if(aSignaler.contains(u)){
                frauduleuxSurAutoroute.add(u);
            }
        }
        // Sortie pour les usages avec badge
        if(usagers.contains(usager) &&
            !aVerifier.contains(usager) &&
            badge_usagers.contains(usager)){

            // On fait un extends de la fonctions entrer
            sortir(usager);
            //l'usager avec badge sort du tronçon
            badge_usagers.remove(usager);

            System.out.println("Suppression de "+usager+" dans usagers et badge_usagers / "+usager+" sort de l'autoroute");
        // Sortie pour les usagers sans badge
        }else if(usagers.contains(usager) &&
                !aVerifier.contains(usager) &&
                frauduleuxSurAutoroute.contains(usager)){

            // On fait un extends de la fonctions entrer
            sortir(usager);
            System.out.println("Suppression de "+usager+" dans usagers et badge_usagers / "+usager+" sort de l'autoroute");
        }else{
            afficherErreur("Erreur: la fonction \"sortirTroncon\" a rencontré un mauvais cas");
        }
    }

    public static void main(String[] args) {
        new TelepeageMachinePaiement();
    }
}
