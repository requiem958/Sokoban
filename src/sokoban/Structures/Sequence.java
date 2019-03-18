package sokoban.Structures;

public interface Sequence<E> {

	void insereTete(E element);
	void insereQueue(E element);
	E extraitTete() throws RuntimeException;
	boolean estVide();
	
	Iterateur<E> iterator();
}
