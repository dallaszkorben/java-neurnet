package hu.akoel.n_neurnet.strategies;

import hu.akoel.n_neurnet.neuron.Neuron;

public interface IResetWeightStrategy {
	public double getWeight( Neuron outputNeuron, Neuron inputNeuron );
}
