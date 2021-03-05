package machine;

import context.TypeUsagerContext;

import java.util.ArrayList;

public class TicketMachine extends PeageMachineBarriere{

    // Ensembles des contextes
    private ArrayList<String> BADGEUSAGERS;
    private ArrayList<String> FRAUDULEUXUSAGER;
    private ArrayList<String> TICKETUSAGERS;
    private ArrayList<String> USAGERS;

    // Variables
    //Tous les usagers du tronçon
    private ArrayList<String> usagers;
    //le bouton a été pressé
    private boolean boutonPresse;
    //le ticket a été pris
    private boolean ticketPris;
    //le ticket a été inséré
    private boolean ticketInsere;
    //le montant a été payé
    private boolean paye;
    //barrière du péage d'entrée
    private boolean bar1;
    //barrière du péage de sortie
    private boolean bar2;
    //les usagers dans le premier péage
    private ArrayList<String> peage1;
    //les usagers dans le deuxième péage
    private ArrayList<String> peage2;
    //Dispositif de détection de passage d'un usager Vrai si un usager vient de passer le télépéage
    private boolean dispositif;
    //Usager à signaler
    private ArrayList<String> aSignaler;
    //Usager qui n'ont pas été vérifié encore, s'ils ont un badge ou non
    private ArrayList<String> aVerifier;
    //Les usagers qui passeront par la barrière
    private ArrayList<String> ticketUsagers;
    //Indique si l'usager peut passer au payement
    private boolean dispositifPaye;
    private ArrayList<String> badgeUsagers;

    public TicketMachine(){
        TypeUsagerContext context = new TypeUsagerContext();

        BADGEUSAGERS = context.getBADGE_USAGERS();
        FRAUDULEUXUSAGER = context.getFRAUDULEUX_USAGERS();
        TICKETUSAGERS = context.getTICKET_USAGERS();
        USAGERS = context.getAll();

        //l'ensemble des usagers courant est vide, aucun usager n'est sur le tronçon
        usagers = new ArrayList<>();
        //aucun usager avec badge n'est sur le tronçon
        badgeUsagers = new ArrayList<>();
        //aucun usager n'est à vérifier
        aVerifier = new ArrayList<>();
        //le dispositif n'est pas activé
        dispositif = false;
        //aucun usager n'est à signaler
        aSignaler = new ArrayList<>();
        // la barrière d'entrée est fermée
        bar1 = false;
        //la barrière de sortie est fermée
        bar2 = false;
        //aucun usager n'est dans le péage d'entrée
        peage1 = new ArrayList<>();
        //aucun usager n'est dans le péage de sortie
        peage2 = new ArrayList<>();
        //les usagers usagers qui sont passés par la barrière, aucun initialement
        ticketUsagers = new ArrayList<>();
        //le dispositif n'a pas badgé pour le payement
        dispositifPaye = true;
        //le bouton n'a pas été pressé
        boutonPresse = false;
        //le ticket n'a pas été pris
        ticketPris = false;
        //le ticket n'a pas été inséré
        ticketInsere = false;
        //le payement n'a pas été effectué
        paye = false;
    }


    //on entre dans le péage d'entrée
    protected void debutEntree(String usager){
        if(!usagers.contains(usager) &&
            !peage1.contains(usager) &&
            !peage2.contains(usager)){

            //l'usager entre sur le péage d'entrée
            peage1.add(usager);
        }
    }

    //on appuie sur le bouton
    protected void appuyeBouton(String usager){
        if(!usagers.contains(usager) &&
            peage1.contains(usager) &&
            !peage2.contains(usager) &&
            !boutonPresse &&
            TICKETUSAGERS.contains(usager)){

            //on appuie sur le bouton
            boutonPresse = true;
            //le ticket n'a pas été pris
            ticketPris = true;
        }
    }

    //on prend un ticket
    protected void prendreTicket(String usager){
        if(!usagers.contains(usager) &&
            boutonPresse &&
            !ticketPris &&
            peage1.contains(usager) &&
            !peage2.contains(usager) &&
            TICKETUSAGERS.contains(usager)){

            //on prend le ticket
            ticketPris = true;
        }
    }

    protected void entrerTronconTele(String usager) {
        if (!usagers.contains(usager) &&
            USAGERS.contains(usager) &&
            !aVerifier.contains(usager) &&
            !dispositif &&
            peage1.contains(usager) &&
            !peage2.contains(usager)) {

            //l'usager fait maintenant parti des usagers du tronçon
            usagers.add(usager);
            //L'usager fait maintenant parti des usagers à vérifier
            aVerifier.add(usager);
            //Le dispositif détecte qu'un usager est passé
            dispositif = true;
        }
    }

    //À l'arrivé d'un usager au péage d'entré, la barrière s'ouvre
    protected void entrerTronconBarriere(String usager) {
        if (!usagers.contains(usager) &&
                !bar1 &&
                peage1.contains(usager) &&
                !peage2.contains(usager) &&
                TICKETUSAGERS.contains(usager) &&
                !ticketUsagers.contains(usager) &&
                !usagers.contains(usager) &&
                boutonPresse &&
                ticketPris) {

            //Alors la barrière s'ouvre
            bar1 = true;
            //l'usager fait maintenant parti des usagers du tronçon
            usagers.add(usager);
            //l'usager fait maintenant parti des usagers avec ticket du tronçon
            ticketUsagers.add(usager);
        }
    }


    //Après que l'usagers soit entré sur le tronçon, il faut refermer la barrière
    protected void fermetureEntre(String usager) {
        if(bar1 &&
            peage1.contains(usager) &&
            usagers.contains(usager) &&
            ticketUsagers.contains(usager) &&
            usagers.contains(usager) &&
            boutonPresse &&
            ticketPris){

            //Alors la barrière se ferme
            bar1 = false;
            //'usager a passé la barrière, il n'appartient plus au péage d'entrée
            peage1.remove(usager);
            //le bouton n'est plus pressé (place à l'usager suivant)
            boutonPresse = false;
            //le ticket n'est plus pris (place à l'usager suivant)
            ticketPris = false;
        }
    }

    protected void autorise(String usager){
        
    }


}
