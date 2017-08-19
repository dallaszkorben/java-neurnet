package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.INormalNeuron;

public interface IInnerLayer extends ILayer{
	public void addNeuron(INormalNeuron neuron );	
	public void calculateWeights( ILayer nextLayer );
	public void setPreviousLayer( ILayer previousLayer );
	//public void setNextLayer( ILayer nextLayer );
}
