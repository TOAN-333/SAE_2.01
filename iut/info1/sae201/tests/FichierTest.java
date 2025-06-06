package iut.info1.sae201.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.*;

import iut.info1.sae201.modele.Fichier;
import iut.info1.sae201.modele.Jeu;
import iut.info1.sae201.modele.ParametresPartie;

/**
 * Tests unitaires pour la classe Fichier.
 */
public class FichierTest {

    private static final String TEMP_SAVE_FILE = "test_sauvegarde.txt";
    private static final String TEMP_EXPORT_FILE = "test_export.txt";

    private Jeu jeuMock;

    /**
     * Initialisation d'une partie fictive avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        // Mock simple d'un jeu avec grille 6x7 remplie par des 
    	// "R" ou "J"
        jeuMock = new Jeu();
        String[][] grille = new String[Jeu.LIGNES][Jeu.COLONNES];
        for (int i = 0; i < Jeu.LIGNES; i++) {
            for (int j = 0; j < Jeu.COLONNES; j++) {
                grille[i][j] = (i + j) % 2 == 0 ? "R" : "J";
            }
        }
        jeuMock.setGrille(grille);

        // Paramètres fictifs
        ParametresPartie.setJoueur1("Alice");
        ParametresPartie.setJoueur2("Bob");
        ParametresPartie.setJoueurCommence("Alice");
    }

    /**
     * Nettoyage après chaque test pour supprimer
     * les fichiers temporaires créés.
     */
    @AfterEach
    public void cleanUp() {
        // Suppression des fichiers temporaires si présents
        new File(TEMP_SAVE_FILE).delete();
        new File(TEMP_EXPORT_FILE).delete();
    }

    /**
     * Teste la sauvegarde d'une partie dans un fichier.
     * @throws IOException si 
     */
    @Test
    public void testSauvegarderPartie_creeFichierCorrect() throws IOException {
        Fichier.sauvegarderPartie(jeuMock, TEMP_SAVE_FILE);
        File f = new File(TEMP_SAVE_FILE);
        assertTrue(f.exists(), "Le fichier de sauvegarde doit exister");

        List<String> lignes = Files.readAllLines(f.toPath());
        assertFalse(lignes.isEmpty(), "Le fichier ne doit pas être vide");
        assertEquals("Alice;Bob;Alice", lignes.get(0), "La première ligne doit"
                                              + " contenir les infos joueurs");

        // Vérifier la grille (nombre de lignes = LIGNES + 1)
        assertEquals(Jeu.LIGNES + 1, lignes.size(), "Le fichier doit contenir "
                                                 + "la ligne d'info + grille");
    }

    /**
     * Teste l'exportation de la partie dans un fichier lisible.
     * @throws IOException si une erreur d'ecriture/lecture survient
     */
    @Test
    public void testExporterPartie_creeFichierLisible() throws IOException {
        File f = new File(TEMP_EXPORT_FILE);
        Fichier.exporterPartie(jeuMock, f);

        assertTrue(f.exists(), "Le fichier exporté doit exister");

        List<String> lignes = Files.readAllLines(f.toPath());
        assertTrue(lignes.get(0).startsWith("Joueur1:"), "Première ligne doit "
                                            + "commencer par Joueur1:");
        assertTrue(lignes.get(1).startsWith("Joueur2:"), "Deuxième ligne doit "
                                            + "commencer par Joueur2:");
        assertEquals("Grille:", lignes.get(2), "Troisième ligne doit être "
                                               + "'Grille:'");

        // Vérifier que la grille est bien présente
        assertEquals(Jeu.LIGNES + 3, lignes.size(), "Nombre total de lignes "
                                                    + "doit correspondre");
    }

    /**
     * Teste du format de la grille en texte.
     */
    @Test
    public void testToStringGrille_format() {
        char[][] grille = {
            {'R', 'J', 'R'},
            {'J', 'R', 'J'}
        };
        String attendu = "R J R \nJ R J \n";
        String resultat = Fichier.toStringGrille(grille);
        assertEquals(attendu, resultat, "La représentation texte de la grille "
                                        + "doit être correcte");
    }

    /**
     * Teste la méthode setGrille et getGrille de la classe Fichier.
     */
    @Test
    public void testSetGetGrille() {
        char[][] grille = {
            {'A', 'B'},
            {'C', 'D'}
        };
        Fichier fichier = new Fichier();
        fichier.setGrille(grille);
        assertArrayEquals(grille, fichier.getGrille(), "La grille doit être "
                                                   +"la même après set/get");
    }
}