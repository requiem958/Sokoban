package sokoban.Structures;

public class FabriqueSequenceTableau extends FabriqueSequence{

	@Override
	public <E> Sequence<E> nouvelle() {
		return new SequenceTableau<E>();
	}
	

}
