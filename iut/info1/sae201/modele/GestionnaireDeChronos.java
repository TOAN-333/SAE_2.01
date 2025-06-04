package iut.info1.sae201.modele;

import java.util.Timer;
import java.util.TimerTask;

public class GestionnaireDeChronos {

    private int countdownJoueur1 = 18;
    private int countdownJoueur2 = 18;
    private int secondesPartie = 0;

    private Timer timerJoueur1 = new Timer();
    private Timer timerJoueur2 = new Timer();
    private Timer timerPartie = new Timer();

    private TimerTask taskJoueur1;
    private TimerTask taskJoueur2;
    private TimerTask taskPartie;

    // Chrono global (compte vers le haut)
    public void demarrerChronoPartie() {
        taskPartie = new TimerTask() {
            @Override
            public void run() {
                secondesPartie++;
            }
        };
        timerPartie.scheduleAtFixedRate(taskPartie, 0, 1000);
    }

    // Chrono joueur 1 (compte à rebours)
    public void demarrerChronoJoueur1() {
        arreterChronoJoueur2(); // arrêt chrono joueur 2 si actif
        if (taskJoueur1 != null) taskJoueur1.cancel();

        countdownJoueur1 = 19; // reset à 20 sec à chaque démarrage
        taskJoueur1 = new TimerTask() {
            @Override
            public void run() {
                countdownJoueur1--;
                if (countdownJoueur1 <= -1) {
                    countdownJoueur1 = -1;
                    cancel();  // stop chrono joueur 1
                    if (timeoutListener != null) {
                        timeoutListener.onTimeout(1);
                    }
                }
            }
        };
        timerJoueur1.scheduleAtFixedRate(taskJoueur1, 1000, 1000);
    }

    // Chrono joueur 2 (compte à rebours)
    public void demarrerChronoJoueur2() {
        arreterChronoJoueur1(); // arrêt chrono joueur 1 si actif
        if (taskJoueur2 != null) taskJoueur2.cancel();

        countdownJoueur2 = 19; // reset à 20 sec à chaque démarrage
        taskJoueur2 = new TimerTask() {
            @Override
            public void run() {
                countdownJoueur2--;
                if (countdownJoueur2 <= -1) {
                    countdownJoueur2 = -1;
                    cancel();  // stop chrono joueur 2
                    if (timeoutListener != null) {
                        timeoutListener.onTimeout(2);
                    }
                }
            }
        };
        timerJoueur2.scheduleAtFixedRate(taskJoueur2, 1000, 1000);
    }

    public void arreterChronoJoueur1() {
        if (taskJoueur1 != null) {
            taskJoueur1.cancel();
        }
    }

    public void arreterChronoJoueur2() {
        if (taskJoueur2 != null) {
            taskJoueur2.cancel();
        }
    }

    public void arreterTousLesChronos() {
        if (taskPartie != null) taskPartie.cancel();
        arreterChronoJoueur1();
        arreterChronoJoueur2();
    }

    public String getTempsFormatJoueur1() {
        return formater(countdownJoueur1);
    }

    public String getTempsFormatJoueur2() {
        return formater(countdownJoueur2);
    }

    public String getTempsFormatPartie() {
        return formater(secondesPartie);
    }

    private String formater(int totalSecondes) {
        int minutes = totalSecondes / 60;
        int secondes = totalSecondes % 60;
        return String.format("%02d:%02d", minutes, secondes);
    }

    public void reset() {
        arreterTousLesChronos();
        countdownJoueur1 = 19;
        countdownJoueur2 = 19;
        secondesPartie = 0;
        timerJoueur1 = new Timer();
        timerJoueur2 = new Timer();
        timerPartie = new Timer();
    }
    
    public interface TimeoutListener {
        void onTimeout(int joueur);
    }

    private TimeoutListener timeoutListener;

    public void setTimeoutListener(TimeoutListener listener) {
        this.timeoutListener = listener;
    }

}
