package sokoban.Structures;

public class FabriqueSequenceListe extends FabriqueSequence {

	@Override
	public <E> Sequence<E> nouvelle() {
		return new SequenceListe<E>();
	}

}
