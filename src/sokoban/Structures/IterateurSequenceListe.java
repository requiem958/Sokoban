package sokoban.Structures;

public class IterateurSequenceListe<E> implements Iterateur<E> {

	Maillon<E> courant, prec, precPrec;
	SequenceListe<E> seq;
	
	
	public IterateurSequenceListe(SequenceListe<E> l){
		seq = l;
		courant = l.tete; 
		prec = null;
		precPrec = null;
		
	}
	

	@Override
	public boolean aProchain() {
		
		return courant.suite !=null;	
	}

	@Override
	public E prochain() {
		precPrec = prec;
		prec = courant;
		courant = courant.suite;
		return prec.element;
	}

	@Override
	public void supprime() throws IllegalStateException {
		if( (prec == null) || (precPrec == prec) ) {
			throw new IllegalStateException();
		}
		else if (precPrec == null) {
			seq.tete = courant;
			prec = null;
		}
		else {
			precPrec.suite = courant;
			prec = precPrec;
		}
		
	}
}

