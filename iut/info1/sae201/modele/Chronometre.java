package iut.info1.sae201.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.function.Consumer;

public class Chronometre {

    private int tempsInitial;
    private int tempsRestant;
    private Timeline timeline;

    private Consumer<Integer> onTick;     // Callback chaque seconde
    private Runnable onTimeExpired;        // Callback quand temps fini

    public Chronometre(int tempsInitial) {
        this.tempsInitial = tempsInitial;
        this.tempsRestant = tempsInitial;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void tick() {
        tempsRestant--;

        if (onTick != null) {
            onTick.accept(tempsRestant);
        }

        if (tempsRestant <= 0) {
            stop();
            if (onTimeExpired != null) {
                onTimeExpired.run();
            }
        }
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public void reset() {
        stop();
        tempsRestant = tempsInitial;
        if (onTick != null) {
            onTick.accept(tempsRestant);
        }
    }

    public void setOnTick(Consumer<Integer> onTick) {
        this.onTick = onTick;
    }

    public void setOnTimeExpired(Runnable onTimeExpired) {
        this.onTimeExpired = onTimeExpired;
    }
}
