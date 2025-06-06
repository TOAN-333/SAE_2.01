/**
 * Chronos.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe Chronos gère les différents chronomètres :
 * <ul>
 *     <li>Un chronomètre global (qui compte le temps total de la partie)</li>
 *     <li>Un chronomètre pour chaque joueur (avec un compte à rebours)</li>
 * </ul>
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class Chronos {

    private int countdownJoueur1 = 20;
    private int countdownJoueur2 = 20;
    private int secondesPartie = 0;

    private Timer timerJoueur1 = new Timer();
    private Timer timerJoueur2 = new Timer();
    private Timer timerPartie = new Timer();

    private TimerTask taskJoueur1;
    private TimerTask taskJoueur2;
    private TimerTask taskPartie;

    private TimeoutListener timeoutListener;

    /**
     * Démarre le chronomètre global de la partie.
     * Ce chronomètre compte vers le haut, soit en ordre croissant,
     * et en secondes.
     */
    public void demarrerChronoPartie() {
        taskPartie = new TimerTask() {
        	/**non javadoc - voir @see java.util.Timer#run(java.util.Timer)*/
            @Override
            public void run() {
                secondesPartie++;
            }
        };
        timerPartie.scheduleAtFixedRate(taskPartie, 0, 1000);
    }

    /**
     * Démarre le compte à rebours pour le joueur 1.
     * @param demarrageA temps de départ en secondes (généralement 20)
     */
    public void demarrerChronoJoueur1(int demarrageA) {
        arreterChronoJoueur2(); // arrêt du chrono adverse
        if (taskJoueur1 != null) taskJoueur1.cancel();

        countdownJoueur1 = demarrageA;
        taskJoueur1 = new TimerTask() {
        	/**non javadoc - voir @see java.util.Timer#run(java.util.Timer)*/
            @Override
            public void run() {
                countdownJoueur1--;
                if (countdownJoueur1 <= 0) {
                    countdownJoueur1 = 0;
                    cancel(); // arrêt du chrono
                    if (timeoutListener != null) {
                        timeoutListener.onTimeout(1); // notifier timeout joueur 1
                    }
                }
            }
        };
        timerJoueur1.scheduleAtFixedRate(taskJoueur1, 1000, 1000);
    }

    /**
     * Démarre le compter à rebours pour le joueur 2.
     * @param demarrageA temps de départ en secondes (généralement 20)
     */
    public void demarrerChronoJoueur2(int demarrageA) {
        arreterChronoJoueur1(); // arrêt du chrono adverse
        if (taskJoueur2 != null) taskJoueur2.cancel();

        countdownJoueur2 = demarrageA;
        taskJoueur2 = new TimerTask() {
        	/**non javadoc - voir @see java.util.Timer#run(java.util.Timer)*/
            @Override
            public void run() {
                countdownJoueur2--;
                if (countdownJoueur2 <= -1) {
                    GestionDialogues.afficherPopupVictoire(getTempsFormatJoueur1());
                    countdownJoueur2 = -1;
                    cancel();
                    if (timeoutListener != null) {
                        timeoutListener.onTimeout(2); // notifier timeout joueur 2
                    }
                }
            }
        };
        timerJoueur2.scheduleAtFixedRate(taskJoueur2, 1000, 1000);
    }

    /**
     * Arrête le chronomètre du joueur 1.
     */
    public void arreterChronoJoueur1() {
        if (taskJoueur1 != null) {
            taskJoueur1.cancel();
        }
    }

    /**
     * Arrête le chronomètre du joueur 2.
     */
    public void arreterChronoJoueur2() {
        if (taskJoueur2 != null) {
            taskJoueur2.cancel();
        }
    }

    /**
     * Arrête tous les chronomètres (partie + joueurs).
     */
    public void arreterTousLesChronos() {
        if (taskPartie != null) taskPartie.cancel();
        arreterChronoJoueur1();
        arreterChronoJoueur2();
    }

    /**
     * @return temps du joueur 1 au format MM:SS
     */
    public String getTempsFormatJoueur1() {
        return formater(countdownJoueur1);
    }

    /**
     * @return temps du joueur 2 au format MM:SS
     */
    public String getTempsFormatJoueur2() {
        return formater(countdownJoueur2);
    }

    /**
     * @return temps de la partie au format MM:SS
     */
    public String getTempsFormatPartie() {
        return formater(secondesPartie);
    }

    /**
     * @return secondes restantes pour le joueur 1
     */
    public int getTempsJoueur1() {
        return countdownJoueur1;
    }

    /**
     * @return secondes restantes pour le joueur 2
     */
    public int getTempsJoueur2() {
        return countdownJoueur2;
    }

    /**
     * @return secondes écoulées depuis le début de la partie
     */
    public int getTempsPartie() {
        return secondesPartie;
    }

    /**
     * Définit le temps du joueur 1.
     * @param countdownJoueur1 secondes restantes
     */
    public void setTempsJoueur1(int countdownJoueur1) {
        this.countdownJoueur1 = countdownJoueur1;
    }

    /**
     * Définit le temps du joueur 2.
     * @param countdownJoueur2 secondes restantes
     */
    public void setTempsJoueur2(int countdownJoueur2) {
        this.countdownJoueur2 = countdownJoueur2;
    }

    /**
     * Définit le temps de la partie depuis une chaîne (en secondes).
     * @param duree durée totale en secondes sous forme de chaîne
     */
    public void setTempsPartie(String duree) {
        this.secondesPartie = Integer.parseInt(duree);
        System.out.println(getTempsPartie());
    }

    /**
     * Réinitialise tous les chronomètres.
     * Remet les compteurs à zéro et recrée les objets Timer.
     */
    public void reset() {
        arreterTousLesChronos();
        countdownJoueur1 = 19;
        countdownJoueur2 = 19;
        secondesPartie = 0;
        timerJoueur1 = new Timer();
        timerJoueur2 = new Timer();
        timerPartie = new Timer();
    }

    /**
     * Interface pour écouter les événements de fin de temps (timeout).
     */
    public interface TimeoutListener {
        /**
         * Méthode appelée quand un joueur a dépassé son temps.
         * @param joueur numéro du joueur (1 ou 2)
         */
        void onTimeout(int joueur);
    }

    /**
     * Définit le listener pour les événements de timeout.
     * @param listener objet écoutant les fins de temps
     */
    public void setTimeoutListener(TimeoutListener listener) {
        this.timeoutListener = listener;
    }

    /**
     * Formate un nombre de secondes en chaîne de type MM:SS.
     * @param totalSecondes nombre total de secondes
     * @return chaîne formatée
     */
    private String formater(int totalSecondes) {
        int minutes = totalSecondes / 60;
        int secondes = totalSecondes % 60;
        return String.format("%02d:%02d", minutes, secondes);
    }
}