/**
 * Parametres.java                           02/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

/**
 * La classe {@code Parametres} représente les paramètres configurables
 * d'une partie, tels que les pseudonymes et couleurs des joueurs, ainsi 
 * que la couleur de la grille.
 * 
 * <p>
 * 		Elle est utilisée pour initialiser la configuration d'une partie 
 * 		de Puissance 4.
 * </p>
 * 
 * @author Thomas Bourgougnon
 * @author Nathael Dalle
 * @author Enzo Dumas
 * @author Toan Hery
 */
public class Parametres {

    private String pseudoJoueur1;
    private String pseudoJoueur2;
    private String couleurJoueur1;
    private String couleurJoueur2;
    private String couleurGrille;

    /**
     * Construit un ensemble de paramètres personnalisés pour une partie.
     *
     * @param pseudoJoueur1 le pseudonyme du joueur 1
     * @param pseudoJoueur2 le pseudonyme du joueur 2
     * @param couleurJoueur1 la couleur CSS du joueur 1
     * @param couleurJoueur2 la couleur CSS du joueur 2
     * @param couleurGrille la couleur CSS de la grille de jeu
     */
    public Parametres(String pseudoJoueur1, String pseudoJoueur2,
                      String couleurJoueur1, String couleurJoueur2,
                      String couleurGrille) {
        this.pseudoJoueur1 = pseudoJoueur1;
        this.pseudoJoueur2 = pseudoJoueur2;
        this.couleurJoueur1 = couleurJoueur1;
        this.couleurJoueur2 = couleurJoueur2;
        this.couleurGrille = couleurGrille;
    }

    /**
     * Renvoie un ensemble de paramètres par défaut :
     * <ul>
     *   <li>joueur 1 : "joueur 1", couleur rouge</li>
     *   <li>joueur 2 : "joueur 2", couleur jaune</li>
     *   <li>grille : couleur bleue</li>
     * </ul>
     *
     * @return une instance {@code Parametres} avec les valeurs par défaut
     */
    public Parametres parametredefaut() {
        String pseudo1 = "joueur 1";
        String pseudo2 = "joueur 2";
        String couleurJ1 = "red";      // noms compatibles CSS
        String couleurJ2 = "yellow";
        String couleurGrille = "blue";

        return new Parametres(pseudo1, pseudo2, couleurJ1, couleurJ2,
        		              couleurGrille);
    }

    /** @return le pseudonyme du joueur 1 */
    public String getPseudoJoueur1() {
        return pseudoJoueur1;
    }

    /**
     * Modifie le pseudonyme du joueur 1.
     * @param pseudoJoueur1 le nouveau nom
     */
    public void setPseudoJoueur1(String pseudoJoueur1) {
        this.pseudoJoueur1 = pseudoJoueur1;
    }

    /** @return le pseudonyme du joueur 2 */
    public String getPseudoJoueur2() {
        return pseudoJoueur2;
    }

    /**
     * Modifie le pseudonyme du joueur 2.
     * @param pseudoJoueur2 le nouveau nom
     */
    public void setPseudoJoueur2(String pseudoJoueur2) {
        this.pseudoJoueur2 = pseudoJoueur2;
    }

    /** @return la couleur CSS du joueur 1 */
    public String getCouleurJoueur1() {
        return couleurJoueur1;
    }

    /**
     * Modifie la couleur du joueur 1.
     * @param couleurJoueur1 nouvelle couleur CSS
     */
    public void setCouleurJoueur1(String couleurJoueur1) {
        this.couleurJoueur1 = couleurJoueur1;
    }

    /** @return la couleur CSS du joueur 2 */
    public String getCouleurJoueur2() {
        return couleurJoueur2;
    }

    /**
     * Modifie la couleur du joueur 2.
     * @param couleurJoueur2 nouvelle couleur CSS
     */
    public void setCouleurJoueur2(String couleurJoueur2) {
        this.couleurJoueur2 = couleurJoueur2;
    }

    /** @return la couleur CSS de la grille de jeu */
    public String getCouleurGrille() {
        return couleurGrille;
    }

    /**
     * Modifie la couleur de la grille.
     * @param couleurGrille nouvelle couleur CSS
     */
    public void setCouleurGrille(String couleurGrille) {
        this.couleurGrille = couleurGrille;
    }

    /**
     * Représentation textuelle des paramètres de la partie.
     *
     * @return une chaîne contenant les valeurs des paramètres
     */
    @Override
    public String toString() {
        return "Parametres {" + '\n'
                + "    pseudoJoueur1 = " + pseudoJoueur1 + '\n'
                + "    pseudoJoueur2 = " + pseudoJoueur2 + '\n'
                + "    couleurJoueur1 = " + couleurJoueur1 + '\n'
                + "    couleurJoueur2 = " + couleurJoueur2 + '\n'
                + "    couleurGrille = " + couleurGrille + '\n'
                + '}';
    }
}
