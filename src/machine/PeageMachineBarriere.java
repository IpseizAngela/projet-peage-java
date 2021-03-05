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


    public PeageMachineBarriere(){

        TypeUsagerContext context = new TypeUsagerContext();
        
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

    protected void debutEntre(String usager){
        if(!usagers.contains(usager) && !peage1.contains(usager) && !peage2.contains(usager)){
            peage1.add(usager);
            System.out.println("Ajout de "+usager+" au peage1 / "+usager+" arrive au péage pour rentrer sur l'autoroute");
        }else{
            System.err.println("Erreur à debutEntre : l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    protected void entreTroncon(String usager){

        if(!a_verifier.contains(usager)
            && !peage2.contains(usager)
            && !usagers.contains(usager)
            && !dispositif){

            usagers.add(usager);
            a_verifier.add(usager);
            dispositif = true;

            if(!bar1){
                ticketUsager.add(usager);
                bar1 = true;
            }else{
                System.err.println("Erreur à entreTronconBarriere : l'usager ne rempli pas les contraintes");
                System.exit(-1);
            }

        }else{
            System.err.println("Erreur à entreTroncon : l'usager ne rempli pas les contraintes");
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
        }else{
            System.err.println("Erreur à autorise : l'usager ne rempli pas les contraintes");
            System.exit(-1);
        }
    }

    protected void debutSortie(String usager){

        // usagers INTER a_signaler
        ArrayList<String> frauduleuxSurAutoroute = new ArrayList<>();
        for(String u : usagers){
            if(aSignaler.contains(u)){
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
            }
            //sortie
            if(ticketUsager.contains(usager) || frauduleuxSurAutoroute.contains(usager)){
                peage2.add(usager);
            }
        }else{
            System.err.println("Erreur à debutSortie :  l'usager ne rempli pas les contraintes");
            System.exit(-1);

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

    protected void sortieTronconBadge(String usager) {
        if (usagers.contains(usager)
        && !a_verifier.contains(usager)
        && badgeUsagers.contains(usager)
        && peage2.contains(usager)
        && !dispositif_payé) {
            usagers.remove(usager);
            badgeUsagers.remove(usager);
            peage2.remove(usager);

        }else{
            System.err.println("Erreur à sortieTronconBadge :  l'usager ne rempli pas les contraintes");
            System.exit(-1);

        }
    }

    protected void sortieFrauduleux(String usager){
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

    }
}
