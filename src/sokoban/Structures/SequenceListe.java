package sokoban.Structures;

class Maillon<E> {
	E element;
	Maillon<E> suite;
	
	Maillon(E e, Maillon<E> s){
		element=e;
		suite=s;
	}
	
	public String toString() {
		if (suite == null)
			return "["+element+"]";
		else
			return "["+element + "] --> " + suite.toString();
	}
}

public class SequenceListe<E> implements Sequence<E> {

	Maillon<E> tete;
	Maillon<E> queue;
	
	public SequenceListe(){
		
		tete = null;
		queue = null;
	}
	
	@Override
	public void insereTete(E element) {
		Maillon<E> m = new Maillon<>(element, tete);
		tete = m;
	}

	@Override
	public void insereQueue(E element) {
		Maillon<E> m = new Maillon<>(element, null);
		if (this.estVide()) {
			this.insereTete(element);
			queue = tete;
		}
		else {
			queue.suite = m;
			queue = queue.suite;
		}
	}

	@Override
	public E extraitTete() throws RuntimeException {

		if (tete == null) 
			throw new RuntimeException("Séquence Vide"); 
		
		Maillon<E> tmp=tete;
		tete=tete.suite;
	
		return tmp.element;
	}

	@Override
	public boolean estVide() {
		
		return tete == null;
		
	}
	
	public String toString() {
		if (estVide())
			return "";
		else
			return tete.toString();
	}

	@Override
	public Iterateur<E> iterator() {
		return new IterateurSequenceListe<E>(this);
	}
}
