package iut.info1.sae201.vue;

public class EnsembleDesVues {
    public static final int VUE_MENU = 0;
    public static final int VUE_PUISSANCE4 = 1;
    public static final int VUE_PARAMETRE = 2;

    private static final String[] NOM_DES_VUES = {
        "Menu.fxml", "Puissance4.fxml", "Parametre.fxml"
    };

    public static String getNomVue(int codeVue) {
        if (codeVue < 0 || codeVue >= NOM_DES_VUES.length) {
            throw new IllegalArgumentException("Code vue " + codeVue + " invalide");
        }
        return NOM_DES_VUES[codeVue];
    }
    
}
