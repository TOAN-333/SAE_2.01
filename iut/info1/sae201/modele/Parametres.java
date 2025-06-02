/**
 * Parametre.java                                  02/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

/**
 * La classe {@code Parametres} permet de représenter et stocker les paramètres
 * d'une partie, tels que les pseudos des joueurs, leurs couleurs et la couleur
 * de la grille.
 * 
 * Elle fournit également une méthode pour générer des paramètres par défaut.
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
     * Constructeur permettant d'initialiser les paramètres d'une partie.
     * 
     * @param pseudoJoueur1 le pseudo du joueur 1
     * @param pseudoJoueur2 le pseudo du joueur 2
     * @param couleurJoueur1 la couleur du joueur 1
     * @param couleurJoueur2 la couleur du joueur 2
     * @param couleurGrille la couleur de la grille de jeu
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
     * Retourne une instance de {@code Parametres} contenant des valeurs 
     * par défaut.
     * 
     * @return une instance avec les paramètres par défaut
     */
    public Parametres parametredefaut() {
        String pseudo1 = "joueur 1";
        String pseudo2 = "joueur 2";
        String couleurJ1 = "red";     // En anglais pour correspondre au CSS
        String couleurJ2 = "yellow";
        String couleurGrille = "blue";

        return new Parametres(pseudo1, pseudo2, couleurJ1, couleurJ2, 
        		              couleurGrille);
    }

    /**
     * Retourne le pseudo du joueur 1.
     * 
     * @return le pseudo du joueur 1
     */
    public String getPseudoJoueur1() {
        return pseudoJoueur1;
    }

    /**
     * Définit le pseudo du joueur 1.
     * 
     * @param pseudoJoueur1 le pseudo à affecter
     */
    public void setPseudoJoueur1(String pseudoJoueur1) {
        this.pseudoJoueur1 = pseudoJoueur1;
    }

    /**
     * Retourne le pseudo du joueur 2.
     * 
     * @return le pseudo du joueur 2
     */
    public String getPseudoJoueur2() {
        return pseudoJoueur2;
    }

    /**
     * Définit le pseudo du joueur 2.
     * 
     * @param pseudoJoueur2 le pseudo à affecter
     */
    public void setPseudoJoueur2(String pseudoJoueur2) {
        this.pseudoJoueur2 = pseudoJoueur2;
    }

    /**
     * Retourne la couleur du joueur 1.
     * 
     * @return la couleur du joueur 1
     */
    public String getCouleurJoueur1() {
        return couleurJoueur1;
    }

    /**
     * Définit la couleur du joueur 1.
     * 
     * @param couleurJoueur1 la couleur à affecter
     */
    public void setCouleurJoueur1(String couleurJoueur1) {
        this.couleurJoueur1 = couleurJoueur1;
    }

    /**
     * Retourne la couleur du joueur 2.
     * 
     * @return la couleur du joueur 2
     */
    public String getCouleurJoueur2() {
        return couleurJoueur2;
    }

    /**
     * Définit la couleur du joueur 2.
     * 
     * @param couleurJoueur2 la couleur à affecter
     */
    public void setCouleurJoueur2(String couleurJoueur2) {
        this.couleurJoueur2 = couleurJoueur2;
    }

    /**
     * Retourne la couleur de la grille.
     * 
     * @return la couleur de la grille
     */
    public String getCouleurGrille() {
        return couleurGrille;
    }

    /**
     * Définit la couleur de la grille.
     * 
     * @param couleurGrille la couleur à affecter
     */
    public void setCouleurGrille(String couleurGrille) {
        this.couleurGrille = couleurGrille;
    }

    /**
     * Retourne une représentation textuelle des paramètres.
     * 
     * @return une chaîne de caractères représentant l'objet
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
