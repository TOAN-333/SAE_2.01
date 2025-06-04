package iut.info1.sae201.modele;

import iut.info1.sae201.modele.Parametres;

public class Parametres {
	
    private String pseudoJoueur1;
    private String pseudoJoueur2;
    private String couleurJoueur1;
    private String couleurJoueur2;
    private String couleurGrille;

    public Parametres(String pseudoJoueur1, String pseudoJoueur2, 
    		          String couleurJoueur1, String couleurJoueur2, 
    		          String couleurGrille) {
        this.pseudoJoueur1 = pseudoJoueur1;
        this.pseudoJoueur2 = pseudoJoueur2;
        this.couleurJoueur1 = couleurJoueur1;
        this.couleurJoueur2 = couleurJoueur2;
        this.couleurGrille = couleurGrille;
    }
    
    public Parametres parametredefaut() {
    	String pseudo1 = "joueur 1";
		String pseudo2 = "joueur 2";
		String couleurJ1 = "red"; // en anglais car il est placé dans le css
		String couleurJ2 = "yellow";
		String couleurGrille = "blue";
		 
		Parametres parametres = new Parametres(pseudo1, pseudo2, couleurJ1, couleurJ2, couleurGrille);
		
		return parametres;
    }

    public String getPseudoJoueur1() { 
    	return pseudoJoueur1; 
    }
    
    public void setPseudoJoueur1(String pseudoJoueur1) {
    	this.pseudoJoueur1 = pseudoJoueur1;
    }
    
    public String getPseudoJoueur2() { 
    	return pseudoJoueur2;
    }
    
    public void setPseudoJoueur2(String pseudoJoueur2) {
    	this.pseudoJoueur2 = pseudoJoueur2;
    }
    
    public String getCouleurJoueur1() { 
    	return couleurJoueur1; 
    }
    
    public void setCouleurJoueur1(String couleurJoueur1) {
    	this.couleurJoueur1 = couleurJoueur1;
    }
    
    public String getCouleurJoueur2() {
    	return couleurJoueur2; 
    }
    
    public void setCouleurJoueur2(String couleurJoueur2) {
    	this.couleurJoueur2 = couleurJoueur2;
    }
    
    public String getCouleurGrille() { 
    	return couleurGrille; 
    }
    
    public void setCouleurGrille(String couleurGrille) { 
    	this.couleurGrille = couleurGrille;
    }

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
