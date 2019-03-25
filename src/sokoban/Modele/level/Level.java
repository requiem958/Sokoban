package sokoban.Modele.level;




enum Case {
	SOL,
	CAISSE,
	POUSSEUR,
	MUR,
	BUT,
	POUSSEUR_SUR_BUT,
	CAISSE_SUR_BUT
};

public class Level {

	private String nom;
	private int nbLigne;
	private int nbColonne;
	private Case terrain[][];
	
	int nbBut;
	private int nbCaissePlace;
	
	private int posXDep;
	private int posYDep;
	
	
	public int getPosXDep() {
		return posXDep;
	}

	public int getPosYDep() {
		return posYDep;
	}

	public Level(Level l){
		this.nbColonne = l.nbColonne;
		this.nbLigne = l.nbLigne;
		this.terrain = l.terrain;
		this.fixeNom(l.nom);
	}

	public Level(String nom, int nbLigne, int nbCol){
		this.nbColonne = nbCol;
		this.nbLigne = nbLigne;
		this.terrain = new Case[nbLigne][nbCol];
		this.fixeNom(nom);
	}

	public String toString() {
		return "Nom : " + nom + "\nl :" + nbLigne + "\nc :" + nbColonne;
	}

	public void fixeNom(String s) {
		this.nom = s;
	}

	public void videCase(int i, int j) {
		if (aCaisseSurBut(i,j)) {
			nbCaissePlace--;
			this.ajouteBut(i, j);
		}
		else if(aPousseurSurBut(i, j)) {
			this.ajouteBut(i, j);
		}
		else 
			this.terrain[i][j] = Case.SOL;
	}

	public void ajouteMur(int i, int j) {
		this.terrain[i][j] = Case.MUR;
	}

	public void ajoutePousseur(int i, int j) {
		if (this.aBut(i,j)) {
			this.terrain[i][j] = Case.POUSSEUR_SUR_BUT;
		}
		else
			this.terrain[i][j] = Case.POUSSEUR;
		posXDep = j;
		posYDep = i;

	}

	public void ajouteCaisse(int i, int j) {
		if (this.aBut(i,j)) {
			this.terrain[i][j] = Case.CAISSE_SUR_BUT;
			nbCaissePlace++;
		}
		else
			this.terrain[i][j] = Case.CAISSE;
	}

	public void ajouteBut(int i, int j) {
		this.terrain[i][j] = Case.BUT;
	}

	public int lignes() {
		return this.nbLigne;
	}

	public int colonnes() {
		return this.nbColonne;
	}

	public String nom() {
		return this.nom;
	}
	
	/* Fonctions de test */

	public boolean estVide(int l, int c) {
		return this.terrain[l][c] == Case.SOL;
	}

	public boolean aMur(int l, int c) {
		return this.terrain[l][c] == Case.MUR;
	}

	public boolean aBut(int l, int c) {
		return this.terrain[l][c] == Case.BUT || aPousseurSurBut(l,c) || aCaisseSurBut(l,c);
	}

	public boolean aPousseur(int l, int c) {
		return this.terrain[l][c] == Case.POUSSEUR || aPousseurSurBut(l,c);
	}

	public boolean aCaisse(int l, int c) {
		return this.terrain[l][c] == Case.CAISSE  || aCaisseSurBut(l,c);
	}

	public boolean aPousseurSurBut(int l, int c) {
		return this.terrain[l][c] == Case.POUSSEUR_SUR_BUT;
	}

	public boolean aCaisseSurBut(int l, int c) {
		return this.terrain[l][c] == Case.CAISSE_SUR_BUT;
	}
	
	/* Fonctions de déplacement */
	
	
	public boolean estFini() {
		return (nbCaissePlace == nbBut);
	}

}
