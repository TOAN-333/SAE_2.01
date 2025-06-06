package iut.info1.sae201.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import iut.info1.sae201.modele.GestionSauvegarde;
import iut.info1.sae201.modele.Jeu;

/**
 * Tests unitaires pour la classe GestionSauvegarde.
 */
public class GestionSauvegardeTest {

    private Jeu jeu;
    private GestionSauvegarde gestionSauvegarde;

    /**
     * Initialisation d'une instance de Jeu et 
     * GestionSauvegarde avant chaque test.
     */
    @Before
    public void setUp() {
        jeu = new Jeu();  // instanciation de la vraie classe Jeu
        gestionSauvegarde = new GestionSauvegarde(jeu);
    }

    /**
     * Test pour vérifier que le constructeur de GestionSauvegarde
     * n'est pas vide.
     */
    @Test
    public void testConstructeur() {
        assertNotNull(gestionSauvegarde);
    }

    /**
     * Test pour savoir si le modèle est correctement attribué.
     * @throws Exception si l'accès au champ échoue
     */
    @Test
    public void testModelEstCorrectementAttribue() throws Exception {
        java.lang.reflect.Field field = 
        		GestionSauvegarde.class.getDeclaredField("model");
        field.setAccessible(true);
        Object modelValue = field.get(gestionSauvegarde);
        assertEquals(jeu, modelValue);
    }
}
