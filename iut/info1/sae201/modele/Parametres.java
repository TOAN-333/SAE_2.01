package iut.info1.sae201.modele;

public class GestionTour {

    private Chronometre chronoJoueur1;
    private Chronometre chronoJoueur2;

    private int joueurActif; // 1 = rouge, 2 = jaune

    public GestionTour() {
        chronoJoueur1 = new Chronometre(20);
        chronoJoueur2 = new Chronometre(20);
        joueurActif = 1;
    }

    public Chronometre getChronoJoueur1() {
        return chronoJoueur1;
    }

    public Chronometre getChronoJoueur2() {
        return chronoJoueur2;
    }

    public int getJoueurActif() {
        return joueurActif;
    }

    public void startChronoInitial() {
        joueurActif = 1;
        chronoJoueur1.reset();
        chronoJoueur1.start();

        chronoJoueur2.stop();
        chronoJoueur2.reset();
    }

    public void changerTour() {
        if (joueurActif == 1) {
        	chronoJoueur2.reset();
            chronoJoueur1.stop();
            chronoJoueur1.reset();

            
            chronoJoueur2.start();

            joueurActif = 2;
        } else {
        	chronoJoueur1.reset();
            chronoJoueur2.stop();
            chronoJoueur2.reset();

   
            chronoJoueur1.start();

            joueurActif = 1;
        }
    }
}
