package hu.akoel.n_neurnet.listeners;

public interface ICycleListener {
	public void handlerError( int cycleCounter, double totalMeanSquareError );
}
