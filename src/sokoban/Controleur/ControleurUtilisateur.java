package sokoban.Controleur;

import javafx.scene.input.KeyCode;
import sokoban.Modele.Deplacement;
import sokoban.Modele.Jeu;
import sokoban.Modele.level.Level;
import sokoban.Modele.level.LevelReader;
import sokoban.Patterns.Observateur;
import sokoban.Vue.graphics.Fenetre;

public class ControleurUtilisateur implements Observateur{
	Fenetre f;
	Jeu j;
	
	public ControleurUtilisateur(Fenetre f, Jeu j) {
		this.f = f;
		this.j = j;
	}
	
	public void clicSouris(double x,double y) {
		int caseX = (int) x / f.getImgWidth();
		int caseY = (int) y / f.getImgHeight();
		if(j.estAligneP(caseX, caseY)) {
			j.deplaceJoueur(
					new Deplacement(
							j.posX, j.posY, 
							Deplacement.dir(j.posX, j.posY, caseX, caseY)
							)
					);
		}
		f.traceNiveau(j.getL());
	}
	
	public void touchePressee(KeyCode code) {
		switch(code) {
		case Q:
			j.deplaceJoueur(new Deplacement(j.posX, j.posY, Deplacement.dir(1, 1, 0, 1)));
			break;
		case D:
			j.deplaceJoueur(new Deplacement(j.posX, j.posY, Deplacement.dir(1, 1, 2, 1)));
			break;
		case Z:
			j.deplaceJoueur(new Deplacement(j.posX, j.posY, Deplacement.dir(1, 1, 1, 0)));
			break;
		case S:
			j.deplaceJoueur(new Deplacement(j.posX, j.posY, Deplacement.dir(1, 1, 1, 2)));
			break;
		default:
			break;
		}
		f.traceNiveau(j.getL());
	}
	
	public void redimension(Level l) {
		f.retraceWindow(l);
	}
	
	public void finJeu() {
		System.out.println("Fin du jeu");
	}
	
	
	
	public void changeLevel() {
		Level l = (new LevelReader()).ReadNextLevel(f.getInFile());
		if (l != null) {
			j.setL(l);
			f.traceNiveau(l);
		}
		f.getC().logger().info("Niveau courant : " + l.nom());

	}

	@Override
	public void miseAJour() {
		f.traceNiveau(j.getL());
		
	}

}
