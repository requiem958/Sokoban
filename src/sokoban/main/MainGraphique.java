package sokoban.main;

import java.io.InputStream;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import sokoban.Controleur.ControleurUtilisateur;
import sokoban.Modele.Jeu;
import sokoban.Modele.Global.Configuration;
import sokoban.Modele.level.LevelReader;
import sokoban.Vue.graphics.Fenetre;

public class MainGraphique extends Application {

	Fenetre f;
	Jeu j;
	ControleurUtilisateur c;

	public static void main(String[] args) {
		MainGraphique.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		f = new Fenetre(primaryStage);
		j = new Jeu();

		f.setC(Configuration.instance());
		f.setInFile(ClassLoader.getSystemClassLoader().getResourceAsStream(f.getC().lis("SokobanLevel")));
		j.setL((new LevelReader()).ReadNextLevel(f.getInFile()));

		loadImgs();
		
		f.initWindow();
		f.j = j;
		
		c = new ControleurUtilisateur(f,j);
		j.ajouteObservateur(f);
				
		/*
		 * On définit l'interaction : - toute interaction est gérée par l'utilisateur
		 * via une fonction de réaction, EventHandler.handle - tout composant peut
		 * réagir à l'interaction EventHandler est générique, on la spécialise pour le
		 * type d'évènement qui nous intéresse
		 */

		// Clics de souris sur le label
		// Exemple avec un objet d'une classe anonyme pour réagir au clic
		f.getCan().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				c.clicSouris(e.getX(),e.getY());
				if (j.getL().estFini()) {
					c.changeLevel();
				}
			}
		});
		
		f.getS().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				c.touchePressee(e.getCode());
				if (j.getL().estFini()) {
					c.changeLevel();
				}
			}
		});

		f.getNextLevel().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				c.changeLevel();
			}

		});

		// Dans le cas de l'image, on veut compter le nombre de clics
		// Dans cet exemple, on passe par un objet d'une classe nommée CibleMouvante qui
		// contient
		// un entier pour faire le compte
		// f.getCan().setOnMouseClicked(new ExempleJavaFXAvecf.getCan()vas.CibleMouvante(this));

		// Le redimensionnement aussi est géré par un évènement
		// Ici, on crée un objet d'une classe anonyme qui servira à réagir au
		// redimensionnent selon les deux axes
		ChangeListener<Number> ecouteurRedimensionnement = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				c.redimension();
			}
		};
		f.getCan().widthProperty().addListener(ecouteurRedimensionnement);
		f.getCan().heightProperty().addListener(ecouteurRedimensionnement);

		// On redimensionne le f.getCan()vas en même temps que son conteneur
		// Remarque : à faire après le primaryStage.show() sinon le Pane 'vue' a une
		// taille nulle qui est transmise au f.getCan()vas
		f.getCan().widthProperty().bind(f.getVue().widthProperty());
		f.getCan().heightProperty().bind(f.getVue().heightProperty());

		// Petit message dans la console quand la fenetre est fermée
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent we) {
				c.finJeu();
			}
		});

		primaryStage.show();


	}

	private void loadImgs() {
		Configuration c = f.getC();
		String dossier = c.lis("ImgDossier");

		InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(dossier+"/"+c.lis("ImgPousseur"));
		f.setPousseur(new Image(in));
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgCaisse"));
		f.setCaisse(new Image(in));
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgMur"));
		f.setMur(new Image(in));
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgBut"));
		f.setBut(new Image(in));
		in = ClassLoader.getSystemClassLoader().getResourceAsStream(c.lis("ImgDossier")+"/"+c.lis("ImgSol"));
		f.setSol(new Image(in));
	}
}