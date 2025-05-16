/*
 * ControleurFenetrePrincipale.java                                   14/05/2025
 * IUT de Rodez, BUT1 2024-2025, pas de copyright
 */

package iut.info1.sae201;

import javafx.fxml.FXML;
import iut.info1.sae201.Main;

/**
 * Classe Controller associé à la vue de la fenetre principal.
 * @author Toan Hery
 * @author Enzo Dumas
 */
public class ControleurFenetrePrincipale {
	
	@FXML
	private void handleNouvellePartie() {
			Main.activeNewGame();
	}
}
