package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.IInputNeuron;

public interface IInputLayer extends ILayer{
	public void addNeuron(IInputNeuron neuron );
	public void calculateWeights( ILayer nextLayer );
	//public void setNextLayer( ILayer nextLayer );
}