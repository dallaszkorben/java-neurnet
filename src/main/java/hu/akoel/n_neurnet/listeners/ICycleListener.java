package hu.akoel.n_neurnet.listeners;

import java.util.ArrayList;

import hu.akoel.n_neurnet.resultiterator.IResultIterator;

public interface ICycleListener {
	public void handlerError( int cycleCounter, double totalMeanSquareError, ArrayList<IResultIterator> resultIteratorArray );
}
