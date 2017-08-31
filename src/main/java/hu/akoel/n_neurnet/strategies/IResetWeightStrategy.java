package hu.akoel.n_neurnet.strategies;

public interface IResetWeightStrategy {
	public double getWeight( int outputNeuronOrder, int inputNeuronOrder );
}
