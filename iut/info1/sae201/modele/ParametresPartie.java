package iut.info1.sae201.modele;

public class ParametresPartie {
    private static String joueur1 = "Rouge";
    private static String joueur2 = "Jaune";
    private static String jCommence = "Rouge";

    public static String getJoueur1() { return joueur1; }
    public static void setJoueur1(String j1) { joueur1 = j1; }

    public static String getJoueur2() { return joueur2; }
    public static void setJoueur2(String j2) { joueur2 = j2; }

    public static String getJoueurCommence() { return jCommence; }
    public static void setJoueurCommence(String choixJeu) { jCommence = choixJeu; }
}
