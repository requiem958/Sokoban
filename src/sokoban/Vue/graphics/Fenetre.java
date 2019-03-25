package sokoban.Vue.graphics;

import java.io.InputStream;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sokoban.Modele.Jeu;
import sokoban.Modele.Global.Configuration;
import sokoban.Modele.level.Level;
import sokoban.Patterns.Observateur;

public class Fenetre implements Observateur{

	private Pane vue;
	private Canvas can;
	private VBox boutons;
	private Button nextLevel;
	private Stage s;

	private Image pousseur;
	private Image caisse;
	private Image mur;
	private Image but;
	private Image sol;

	private int imgHeight;
	private int imgWidth;

	private Configuration c;
	private InputStream inFile;
	
	public Jeu j;

	public Fenetre(Stage s) throws Exception{
		this.s =s;
	}

	public Pane getVue() {
		return vue;
	}
	public void setVue(Pane vue) {
		this.vue = vue;
	}
	public Stage getS() {
		return s;
	}

	public void setS(Stage s) {
		this.s = s;
	}

	public VBox getBoutons() {
		return boutons;
	}
	public void setBoutons(VBox boutons) {
		this.boutons = boutons;
	}
	public Button getNextLevel() {
		return nextLevel;
	}
	public void setNextLevel(Button nextLevel) {
		this.nextLevel = nextLevel;
	}
	public Canvas getCan() {
		return can;
	}
	public void setCan(Canvas can) {
		this.can = can;
	}
	public void setPousseur(Image pousseur) {
		this.pousseur = pousseur;
	}
	public void setCaisse(Image caisse) {
		this.caisse = caisse;
	}
	public void setMur(Image mur) {
		this.mur = mur;
	}
	public void setBut(Image but) {
		this.but = but;
	}
	public void setSol(Image sol) {
		this.sol = sol;
	}
	public Configuration getC() {
		return c;
	}
	public void setC(Configuration c) {
		this.c = c;
	}
	public InputStream getInFile() {
		return inFile;
	}
	public void setInFile(InputStream inFile) {
		this.inFile = inFile;
	} 

	public int getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}
	public int getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public Scene createScene() {
		// Zone de dessin
		can = new Canvas(600, 400);

		vue = new Pane(can);
		
		boutons = new VBox();

		nextLevel = new Button();
		nextLevel.setText("Niveau suivant");
		boutons.getChildren().add(nextLevel);

		HBox scene = new HBox();
		scene.getChildren().add(vue);
		scene.getChildren().add(boutons);
		
		HBox.setHgrow(vue, Priority.ALWAYS);

		// Contenu de la fenêtre
		return new Scene(scene);
	}

	public void initWindow() {
		// Titre de la fenêtre
		s.setTitle("Sokoban");

		s.setScene(createScene());
		// On affiche la fenêtre (donne leur taille aux objets graphiques)
		s.show();

		boutons.setMinWidth(nextLevel.getWidth());

	}

	// Efface tout puis trace l'image aux coordonnées enregistrées
	void trace(Image img, int x, int y) {
		GraphicsContext gc = can.getGraphicsContext2D();
		gc.drawImage(img, x, y, imgWidth, imgHeight);
	}

	public void retraceWindow() {
		this.getBoutons().setMinWidth(this.getNextLevel().getWidth());
		this.getCan().getGraphicsContext2D().setFill(Paint.valueOf("WHITE"));
		this.getCan().getGraphicsContext2D().fillRect(0, 0, this.getCan().getWidth(), this.getCan().getHeight());
		miseAJour();
	}

	@Override
	public void miseAJour() {
		Level l = j.getL();
		imgHeight = (int) (can.getHeight() / l.lignes());
		imgWidth = (int) (can.getWidth() / l.colonnes());

		for (int i = 0; i < l.colonnes(); i++) {
			for (int j = 0; j < l.lignes(); j++) {

				trace(sol, i * imgWidth, j * imgHeight);

				if (l.aBut(j, i)) {
					trace(but, i * imgWidth, j * imgHeight);
				}
				if (l.aPousseur(j, i)) {
					trace(pousseur, i * imgWidth, j * imgHeight);
				} 
				if (l.aCaisse(j, i)) {
					trace(caisse, i * imgWidth, j * imgHeight);
				}
				if (l.aMur(j, i)) {
					trace(mur, i * imgWidth, j * imgHeight);
				}
			}
		}
		
	}


}
