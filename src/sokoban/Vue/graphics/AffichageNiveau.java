package sokoban.Vue.graphics;

import java.io.InputStream;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sokoban.Modele.Global.Configuration;
import sokoban.Modele.level.Level;
import sokoban.Modele.level.LevelReader;

public class AffichageNiveau extends Application {

	public static void main(String[] args) {
		AffichageNiveau.launch(args);
	}

	private Level l;
	private Canvas can;

	private Image pousseur;
	private Image caisse;
	private Image mur;
	private Image but;
	private Image sol;

	private int imgHeight;
	private int imgWidth;
	
	private Configuration c;
	private InputStream inFile; 
	@Override
	public void start(Stage primaryStage) throws Exception {
		c = Configuration.instance();
		inFile = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("SokobanLevel"));
		l = (new LevelReader()).ReadNextLevel(inFile);
		
		loadImgs();
		
		// Titre de la fenêtre
		primaryStage.setTitle("Sokoban");
		// Plein écran (éventuellement)
		primaryStage.setFullScreen(true);

		// Zone de dessin
		can = new Canvas(600, 400);

		// Un conteneur simple qui remplit toute la place disponible
		Pane vue = new Pane(can);
		VBox boutons = new VBox();

		Button nextLevel = new Button();
		nextLevel.setText("Niveau suivant");
		boutons.getChildren().add(nextLevel);

		HBox scene = new HBox();
		scene.getChildren().add(vue);
		scene.getChildren().add(boutons);
		HBox.setHgrow(vue, Priority.ALWAYS);

		// Contenu de la fenêtre
		Scene s = new Scene(scene);
		primaryStage.setScene(s);
		// On affiche la fenêtre (donne leur taille aux objets graphiques)
		primaryStage.show();

		boutons.setMinWidth(nextLevel.getWidth());

		traceNiveau();
		primaryStage.show();

		/*
		 * On définit l'interaction : - toute interaction est gérée par l'utilisateur
		 * via une fonction de réaction, EventHandler.handle - tout composant peut
		 * réagir à l'interaction EventHandler est générique, on la spécialise pour le
		 * type d'évènement qui nous intéresse
		 */

		// Clics de souris sur le label
		// Exemple avec un objet d'une classe anonyme pour réagir au clic
		can.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Coordonées du clic : Colonne{" + (int) e.getX() / imgWidth + "};Ligne{"
						+ (int) e.getY() / imgHeight + "}");
			}
		});

		nextLevel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				changeLevel();
			}

		});

		// Dans le cas de l'image, on veut compter le nombre de clics
		// Dans cet exemple, on passe par un objet d'une classe nommée CibleMouvante qui
		// contient
		// un entier pour faire le compte
		// can.setOnMouseClicked(new ExempleJavaFXAvecCanvas.CibleMouvante(this));

		// Le redimensionnement aussi est géré par un évènement
		// Ici, on crée un objet d'une classe anonyme qui servira à réagir au
		// redimensionnent selon les deux axes
		ChangeListener<Number> ecouteurRedimensionnement = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				boutons.setMinWidth(nextLevel.getWidth());

				GraphicsContext gc = can.getGraphicsContext2D();
				gc.setFill(Paint.valueOf("WHITE"));
				gc.fillRect(0, 0, can.getWidth(), can.getHeight());
				traceNiveau();

			}
		};
		can.widthProperty().addListener(ecouteurRedimensionnement);
		can.heightProperty().addListener(ecouteurRedimensionnement);

		// On redimensionne le canvas en même temps que son conteneur
		// Remarque : à faire après le primaryStage.show() sinon le Pane 'vue' a une
		// taille nulle qui est transmise au Canvas
		can.widthProperty().bind(vue.widthProperty());
		can.heightProperty().bind(vue.heightProperty());

		// Petit message dans la console quand la fenetre est fermée
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent we) {
				System.out.println("Fin du jeu");
			}
		});
	}

	private void loadImgs() {
		String dossier = c.lis("ImgDossier");
		InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(dossier+"/"+c.lis("ImgPousseur"));
		pousseur = new Image(in);
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgCaisse"));
		caisse = new Image(in);
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgMur"));
		mur = new Image(in);
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgBut"));
		but = new Image(in);
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgSol"));
		sol = new Image(in);
	}

	// Efface tout puis trace l'image aux coordonnées enregistrées
	void trace(Image img, int x, int y) {
		GraphicsContext gc = can.getGraphicsContext2D();
		gc.drawImage(img, x, y, imgWidth, imgHeight);
	}

	void traceNiveau() {
		imgHeight = (int) (can.getHeight() / l.lignes());
		imgWidth = (int) (can.getWidth() / l.colonnes());
		for (int i = 0; i < l.colonnes(); i++) {
			for (int j = 0; j < l.lignes(); j++) {
				trace(sol, i * imgWidth, j * imgHeight);

				if (l.aPousseur(j, i)) {
					trace(pousseur, i * imgWidth, j * imgHeight);
				} else if (l.aBut(j, i)) {
					trace(but, i * imgWidth, j * imgHeight);
				} else if (l.aCaisse(j, i)) {
					trace(caisse, i * imgWidth, j * imgHeight);
				} else if (l.aMur(j, i)) {
					trace(mur, i * imgWidth, j * imgHeight);
				}
			}
		}
	}
	
	private void changeLevel() {
		Level old = new Level(l);
		l = (new LevelReader()).ReadNextLevel(inFile);
		if (l != null) {
			traceNiveau();
		} else
			l = old;
		c.logger().info("Niveau courant : " + l.nom());
		
	}
}