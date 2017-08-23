package hu.akoel.neurnet.strategies;

import hu.akoel.neurnet.neuron.ANeuron;

public interface DefaultWeightStrategy {

	public Double getValue( ANeuron previousNeuron, ANeuron actualNeuron );
}
