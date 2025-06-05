package iut.info1.sae201.modele;

import java.util.Timer;
import java.util.TimerTask;

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
    public void demarrerChronoJoueur1(int demarrageA) {
        arreterChronoJoueur2(); // arrêt chrono joueur 2 si actif
        if (taskJoueur1 != null) taskJoueur1.cancel();

        countdownJoueur1 = demarrageA; // reset à 20 sec à chaque démarrage
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
    public void demarrerChronoJoueur2(int demarrageA) {
        arreterChronoJoueur1(); // arrêt chrono joueur 1 si actif
        if (taskJoueur2 != null) taskJoueur2.cancel();

        countdownJoueur2 = demarrageA; // reset à 20 sec à chaque démarrage
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
    
    public int getTempsJoueur1() {
        return countdownJoueur1;
    }

    public int getTempsJoueur2() {
        return countdownJoueur2;
    }

    public int getTempsPartie() {
        return secondesPartie;
    }
    
    public void setTempsJoueur1(int countdownJoueur1) {
        this.countdownJoueur1 = countdownJoueur1;
    }

    public void setTempsJoueur2(int countdownJoueur2) {
    	this.countdownJoueur2 = countdownJoueur2;
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
