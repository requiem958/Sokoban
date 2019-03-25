package sokoban.Modele;

import sokoban.Modele.level.Level;
import sokoban.Patterns.Observable;

public class Jeu extends Observable {


	private Level l;
	public int posX, posY;
	
	public Jeu() throws Exception {
		super();
	}

	public Level getL() {
		return l;
	}
	
	

	public void setL(Level l) {
		this.l = l;
		posX = l.getPosXDep();
		posY = l.getPosYDep();
	}
	
	public boolean estValide(Deplacement d)
	{
		int x = d.xArrive();
		int y = d.yArrive();
		if(x > l.colonnes() || y> l.lignes() || x<0 || y<0) {
			return false;
		}
		else if(l.aMur(y, x)) {
			return false;
		}
		else if(l.aCaisse(y, x) && ( l.aMur(d.yArriveN(), d.xArriveN()) || l.aCaisse(d.yArriveN(), d.xArriveN())) ) {
			return false;
		}
		return true ;
	}
	
	public void deplaceJoueur(Deplacement d) {
		if(estValide(d)) {
			if(l.aCaisse(d.yArrive(), d.xArrive())) {
				l.videCase(d.yArrive(), d.xArrive());
				l.ajouteCaisse(d.yArriveN(), d.xArriveN());
			}
			l.videCase(posY, posX);
			l.ajoutePousseur(d.yArrive(), d.xArrive());
			posX = d.xArrive();
			posY = d.yArrive();
		}
	}

	public boolean estAligneP(int x, int y) {
		if(x == posX && (y > posY || y < posY)){
			return true;
		}
		else if((x > posX ||x < posX) && (y == posY)){
			return true;
		}
		return false;
	}
}
