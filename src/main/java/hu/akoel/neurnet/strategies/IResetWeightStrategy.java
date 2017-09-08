package hu.akoel.neurnet.strategies;

import hu.akoel.neurnet.neuron.Neuron;

public interface IResetWeightStrategy {
	public double getWeight( Neuron outputNeuron, Neuron inputNeuron );
}
