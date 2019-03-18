package sokoban.Structures;

public interface Iterateur<E> {
	
	boolean aProchain();
	
	E prochain();
	
	void supprime() throws IllegalStateException;
}
