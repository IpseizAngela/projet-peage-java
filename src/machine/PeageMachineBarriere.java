package machine;

import context.TypeUsagerContext;

import java.util.ArrayList;

public class PeageMachineBarriere extends TelepeageMachinePaiement{

    private boolean bar1;
    private boolean bar2;
    private boolean dispositif_payé;
    private boolean dispositif;
    private ArrayList<String> usagers;
    private ArrayList<String> badgeUsagers;
    private ArrayList<String> peage1;
    private ArrayList<String> peage2;
    private ArrayList<String> a_signaler;
    private ArrayList<String> a_verifier;
    private ArrayList<String> ticketUsager;
    private ArrayList<String> BADGEUSAGERS;
    private ArrayList<String> FRAUDULEUXUSAGER;
    private ArrayList<String> TICKETUSAGERS;
    private ArrayList<String> USAGERS;

    public PeageMachineBarriere(){

        TypeUsagerContext context = new TypeUsagerContext();

        BADGEUSAGERS = context.getBADGE_USAGERS();
        FRAUDULEUXUSAGER = context.getFRAUDULEUX_USAGERS();
        TICKETUSAGERS = context.getTICKET_USAGERS();
        USAGERS = context.getAll();


        bar1 = false;
        bar2 = false;
        dispositif = false;
        dispositif_payé = true;
        usagers = new ArrayList<>();
        badgeUsagers = new ArrayList<>();
        peage1 = new ArrayList<>();
        peage2 = new ArrayList<>();
        a_verifier = new ArrayList<>();
        a_signaler = new ArrayList<>();
        ticketUsager = new ArrayList<>();
    }

    protected void entrer(String usager){
        debutEntre(usager);
        entreTroncon(usager);
        autorise(usager);
    }

    protected void sortir(String usager) {
        debutSortie(usager);
        sortirTroncon(usager);
    }

    protected void debutEntre(String usager){
        if(!usagers.contains(usager)
        && !peage1.contains(usager)
        && !peage2.contains(usager)){
            peage1.add(usager);
            System.out.println("Ajout de "+usager+" au peage1 / "+usager+" arrive au péage pour rentrer sur l'autoroute");
        }else{
            System.err.println("Erreur à debutEntre : l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    protected void entreTroncon(String usager){

        if(peage1.contains(usager)
            && !peage2.contains(usager)
            && !usagers.contains(usager)){

            if(!bar1
            && !ticketUsager.contains(usager)
            && TICKETUSAGERS.contains(usager)){
                ticketUsager.add(usager);
                bar1 = true;
                usagers.add(usager);

                System.out.println(usager+" entre sur le tronçon et prend un ticket");

                fermetureEntre(usager);

                //entrer badge
            }else if(!a_verifier.contains(usager)
            && !dispositif){
                a_verifier.add(usager);
                dispositif = true;
                usagers.add(usager);

                System.out.println(usager+" entre sur le troncon grace à son badge");

            }else{
                System.err.println("Erreur à entreTroncon_2 : l'usager ne rempli pas les contraintes");
                System.exit(-1);
            }

        }else{
            System.err.println("Erreur à entreTroncon_1 : l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    protected void fermetureEntre(String usager){
        if(bar1
        && peage1.contains(usager)
        && usagers.contains(usager)
        && ticketUsager.contains(usager)){
            bar1 = false;
            peage1.remove(usager);

            System.out.println("Fermeture de la barrière");
        }else{
            System.err.println("Erreur à fermetureSortie:  l'usager ne rempli pas les contraintes");
            System.exit(-1);

        }
    }

    protected void autorise(String usager){
        if(a_verifier.contains(usager)
        && dispositif){
            dispositif = false;
            a_verifier.remove(usager);
            peage1.remove(usager);

            if(BADGEUSAGERS.contains(usager)
            && !FRAUDULEUXUSAGER.contains(usager) ){

               badgeUsagers.add(usager);
               System.out.println("L'usager: " + usager + " a un badge et est autorisé à passer");

            }else
                if(!BADGEUSAGERS.contains(usager)
                && FRAUDULEUXUSAGER.contains(usager)){

                    a_signaler.add(usager);
                    System.out.println("L'usager: "+usager+" n'a pas de badge et n'est pas autorisé à passer, il est signalé");

                }
        }else if(!ticketUsager.contains(usager)){
            System.err.println("Erreur à autorise : l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    protected void debutSortie(String usager){

        // usagers INTER a_signaler
        ArrayList<String> frauduleuxSurAutoroute = new ArrayList<>();
        for(String u : usagers){
            if(a_signaler.contains(u)){
                frauduleuxSurAutoroute.add(u);
            }
        }

        if( usagers.contains(usager)
        && !peage2.contains(usager)
        && !peage1.contains(usager)){

            //sortie badge
            if(dispositif_payé
            && badgeUsagers.contains(usager)){
                dispositif_payé = false;
                peage2.add(usager);

                System.out.println(usager+" entre dans la zone de péage de sortie (utilisateur de badge)");
            }else {
                //sortie ticket
                if (ticketUsager.contains(usager) || frauduleuxSurAutoroute.contains(usager)) {
                    peage2.add(usager);

                    System.out.println(usager + " entre dans la zone de péage de sortie (utilisateur de ticket)");
                } else if(FRAUDULEUXUSAGER.contains(usager)) {
                    System.out.println(usager + " est un fraudeur");
                }else{
                    System.err.println("Erreur à débutSortie_2 :  l'usager ne rempli pas les contraintes");
                    System.exit(-1);
                }
            }
        }else{
            System.err.println("Erreur à debutSortie_1 :  l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    protected void sortirTroncon(String usager){

        if(usagers.contains(usager)) {
            if (peage2.contains(usager)){
                if(!a_verifier.contains(usager)){

                    //sortie badge
                    if (badgeUsagers.contains(usager)
                            && !dispositif_payé) {
                        badgeUsagers.remove(usager);
                        usagers.remove(usager);
                        peage2.remove(usager);

                        System.out.println(usager+" est sortie du tronçon (utilisateur de badge)");

                    }else{
                        //sortie frauduleux
                        ArrayList<String> frauduleuxSurAutoroute = new ArrayList<>();
                        for(String u : usagers){
                            if(a_signaler.contains(u)){
                                frauduleuxSurAutoroute.add(u);
                            }
                        }

                        if(frauduleuxSurAutoroute.contains(usager)){
                            usagers.remove(usager);
                            peage2.remove(usager);

                            System.out.println(usager+" est sortie du tronçon en fraudant ! ");

                        }
                    }
                }
            }else {

                //sortie barrière
                if (!peage1.contains(usager)
                        && ticketUsager.contains(usager)
                        && !bar1
                        && !bar2) {
                    bar2 = true;
                    ticketUsager.remove(usager);
                    usagers.remove(usager);

                    System.out.println(usager + " est sortie du tronçon (utilisateur de badge)");

                    fermetureSortie(usager);
                } else {
                    System.err.println("Erreur à sortieTroncon:  l'usager ne rempli pas les contraintes");
                    System.exit(-1);
                }
            }
        }
    }

    protected void sortirTronconBarriere(String usager){
        if( usagers.contains(usager)
        && !peage1.contains(usager)
        && peage2.contains(usager)
        && ticketUsager.contains(usager)
        && !bar1
        && !bar2){
            usagers.remove(usager);
            bar2 = true;
            ticketUsager.remove(usager);
        }else{
            System.err.println("Erreur à sortieTronconBarriere :  l'usager ne rempli pas les contraintes");
            System.exit(-1);

        }
    }

    protected void sortirTronconBadge(String usager) {
        if (usagers.contains(usager)
        && !a_verifier.contains(usager)
        && badgeUsagers.contains(usager)
        && peage2.contains(usager)
        && !dispositif_payé) {
            usagers.remove(usager);
            badgeUsagers.remove(usager);
            peage2.remove(usager);
            dispositif_payé = true;
        }else{
            System.err.println("Erreur à sortieTronconBadge :  l'usager ne rempli pas les contraintes");
            System.exit(-1);

        }
    }

    protected void sortirFrauduleux(String usager){
        // usagers INTER a_signaler
        ArrayList<String> frauduleuxSurAutoroute = new ArrayList<>();
        for(String u : usagers){
            if(aSignaler.contains(u)){
                frauduleuxSurAutoroute.add(u);
            }
        }

        if(usagers.contains(usager)
        && !a_verifier.contains(usager)
        && peage2.contains(usager)
        && frauduleuxSurAutoroute.contains(usager)){
            usagers.remove(usager);
            peage2.remove(usager);
        }else{
            System.err.println("Erreur à sortieTronconFrauduleux :  l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    protected void fermetureSortie(String usager){
        if( bar2
        && peage2.contains(usager)
        && !peage1.contains(usager)
        && usagers.contains(usager)){
            bar2 = false;
            peage2.remove(usager);
        }else{
            System.err.println("Erreur à fermetureSortie : l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        PeageMachineBarriere m = new PeageMachineBarriere();
        System.out.println("\n========================");
        for (String u : m.USAGERS) {
            m.entrer(u);
            m.sortir(u);
            m.sep();
        }
        System.out.println("\n========================");

        /*
        m.entrer(m.BADGEUSAGERS.get(0));
        m.sortir(m.BADGEUSAGERS.get(0));
        m.sep();

        m.entrer(m.TICKETUSAGERS.get(0));
        m.sortir(m.TICKETUSAGERS.get(0));
        m.sep();

        m.entrer(m.FRAUDULEUXUSAGER.get(0));
        m.sortir(m.FRAUDULEUXUSAGER.get(0));
        m.sep();*/

    }
}
