package sokoban.Structures;

public class SequenceTableau<E> implements Sequence<E>{

	Object tableau[];
	int taille;
	int nbElement;
	int indiceDebut;
	
	
	public SequenceTableau() {
		this(10);
	}
	
	public SequenceTableau(int n) {
		this.taille = n;
		this.tableau = new Object[taille];
		this.indiceDebut = 0;
	}
	
	protected int extraitIndice(int indice){
		if(indice >= taille){
			return indice - taille;
		}
		else if(indice < 0){
			return taille + indice;
		}
		else{		// 0 <= indice < tailleTab
			return indice;
		}
	}
	
	protected void insereIndice(Object element, int indice){
		if(indice>=0 && indice<taille){
			tableau[indice] = element;
		}
		else if (indice < 0){
			tableau[taille + indice] = element;
		}
		else{		// indice > tailleTab
			tableau[indice - taille] = element;
		}
	}
	
	@Override
	public void insereTete(E element){
		if(nbElement == taille) {
			this.doubleTaille();
		}
		insereIndice(element, indiceDebut - 1);
		indiceDebut = extraitIndice(indiceDebut - 1);
		nbElement++;
	}

	private void doubleTaille() {
		int tmp = taille;
		taille = taille* 2;
		Object[] doubleTab;
		
		doubleTab = new Object[taille];
		
		for(int i=0; i<nbElement; i++){
			doubleTab[i] = tableau[i];
		}
		tableau = doubleTab;
		if(indiceDebut != 0){
			int j = 0;
			for(int i = tmp-1; i>=indiceDebut; i--){
				tableau[taille - 1 - j] = tableau[i];
				j++;
			}
			indiceDebut = taille - j;
		}
	}

	@Override
	public void insereQueue(E element) {
		if(nbElement == taille) {
			this.doubleTaille();
		}
		insereIndice(element, indiceDebut + nbElement);
		nbElement++;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E extraitTete() throws RuntimeException {
		if (estVide())
			throw new RuntimeException("Séquence Vide");
		
		
		E tmp = (E) tableau[indiceDebut];
		indiceDebut = extraitIndice(indiceDebut + 1);
		nbElement--;
		return tmp;
	}

	@Override
	public boolean estVide() {
		return nbElement == 0;
	}
	
	public String toString() {
		String sequence = new String();
		for(int i=indiceDebut; i<indiceDebut + nbElement; i++) {
			sequence = sequence + tableau[extraitIndice(i)] + " ";
		}
		if (sequence.length() > 0)
			sequence = sequence.substring(0, sequence.length()-1);

		return sequence;
	}

	@Override
	public Iterateur<E> iterator() {
		return new IterateurSequenceTableau<E>(this);
	}		
	

}
