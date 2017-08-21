package hu.akoel.neurnet.listeners;

public interface ICycleListener {

	public void handlerError( int cycleCounter, double totalMeanSquareError );
}
