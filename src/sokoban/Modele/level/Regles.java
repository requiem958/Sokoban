package sokoban.Modele.level;

import sokoban.Modele.Deplacement;

public class Regles {

	Level l;
	int posX,posY;
	int caissePlace;
	int caisseTotale;

	public Regles(Level niveau) {
		l = niveau;
		for (int i=0; i<l.colonnes(); i++) {
			for(int j=0; j<l.lignes(); j++) {
				if(l.aPousseur(j, i)) {
					posX = i;
					posY = j;
				}
				else if (l.aCaisseSurBut(j, i)) {
					caissePlace++;
					caisseTotale++;
				}
				else if(l.aCaisse(j, i)) {
					caisseTotale++;
				}
			}
		}
	}
	public void reloadLevel(Level l) {
		this.l = l;
		
	}
	public boolean estValide(Deplacement d)
	{
		int x = d.xArrive();
		int y = d.yArrive();
		if(x > l.colonnes() || y> l.colonnes() || x<0 || y<0) {
			return false;
		}
		else if(l.aMur(y, x)) {
			return false;
		}
		else if(l.aCaisse(y, x) && l.aMur(d.yArriveN(), d.xArriveN()) ) {
			return false;
		}
		return true ;
	}
	public boolean estFini() {
		return (caissePlace == caisseTotale);
	}
	
}
