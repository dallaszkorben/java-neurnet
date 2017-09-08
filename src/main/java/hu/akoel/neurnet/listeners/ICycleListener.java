package hu.akoel.neurnet.listeners;

import java.util.ArrayList;

import hu.akoel.neurnet.resultiterator.IResultIterator;

public interface ICycleListener {
	public void handlerError( int cycleCounter, double totalMeanSquareError, ArrayList<IResultIterator> resultIteratorArray );
}
