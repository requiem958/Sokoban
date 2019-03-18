package sokoban.Structures;

public class IterateurSequenceTableau<E> implements Iterateur<E> {

	int indiceCourant;
	boolean deleted;
	
	SequenceTableau<E> l;
	
	public IterateurSequenceTableau(SequenceTableau<E> l) {
		this.indiceCourant = l.indiceDebut;
		this.l = l;
		this.deleted = false;
	}
	@Override
	public boolean aProchain() {
		return  l.extraitIndice(indiceCourant+1) != 
				l.extraitIndice(l.indiceDebut+l.nbElement);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E prochain() {
		E tmp = (E) l.tableau[indiceCourant];
		
		this.indiceCourant = l.extraitIndice(indiceCourant+1);
		deleted = false;
		return tmp;
	}

	@Override
	public void supprime() throws IllegalStateException {
		if (deleted || this.indiceCourant == l.indiceDebut || l.nbElement == 0)
			throw new IllegalStateException();
		else {
			deleted = true;
			
			int indASupprimer = l.extraitIndice(indiceCourant-1);
			
			for (int i = indASupprimer; i < l.extraitIndice(l.indiceDebut + l.nbElement); i=l.extraitIndice(i+1))
				l.tableau[i] = l.tableau[l.extraitIndice(i+1)];
			
			l.nbElement--;
			this.indiceCourant = l.extraitIndice(this.indiceCourant-1);
		}
		
	}

}
